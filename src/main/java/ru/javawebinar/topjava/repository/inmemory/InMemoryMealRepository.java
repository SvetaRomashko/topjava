package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        if (meal.getUserId() != SecurityUtil.authUserId())
            return null;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        if (repository.get(id).getUserId() != SecurityUtil.authUserId())
            return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id).getUserId() != SecurityUtil.authUserId())
            return null;
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> sortedMeal = repository.values().stream().sorted(Comparator.comparing(o -> o.getDate())).collect(Collectors.toList());
        Collections.reverse(sortedMeal);
        return sortedMeal;
    }

    @Override
    public Collection<Meal> filterDate(String startDateString, String endDateString) {

        Collection<Meal> MealFilter = repository.values();

        if (!startDateString.isEmpty()  && endDateString != null) {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            MealFilter=MealFilter.stream()
                    .filter(m -> m.getDate().isAfter(startDate.minusDays(1)) &&
                            m.getDate().isBefore(endDate.plusDays(1)))
                    .collect(Collectors.toList());
        }

        return MealFilter;
    }

}

