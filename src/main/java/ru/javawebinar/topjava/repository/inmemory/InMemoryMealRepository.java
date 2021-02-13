package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if(meal.getUserId() != SecurityUtil.authUserId())
            return null;
        if (meal.isNew() ) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        if(repository.get(id).getUserId() != SecurityUtil.authUserId())
            return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        if(repository.get(id).getUserId() != SecurityUtil.authUserId())
            return null;
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        Collection<Meal> sortedMeal = (Collection<Meal>)repository.values().stream().sorted(Comparator.comparing(o -> o.getDateTime()));
        Collections.reverse(Arrays.asList(sortedMeal.toArray()));
        return sortedMeal;
    }
}

