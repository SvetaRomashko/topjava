package ru.javawebinar.topjava.dao;

import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.IdToMeal;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MealDao {

    private static int id;
    private static List<Meal> meals = new ArrayList<>();
    private static List<MealTo> mealToList=new ArrayList<>();
    final int caloriesPerDay = 2000;

    {

        meals.add(new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(++id, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(++id, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(++id, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(++id, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(++id, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(++id, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    }

    public List<Meal> indexMeal(){
        return meals;
    }

    public List<MealTo> index() {
        mealToList.clear();
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        mealToList = meals.stream()
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());

        return mealToList;
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }


    public void addMeal(String dateTime, String description, int calories) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        meals.add(new Meal(++id, localDateTime, description, calories));
        index();
    }

    public List<MealTo> updateMeal(int id, String dateTime, String description, int calories){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        meals.get(id).setDateTime(localDateTime);
        meals.get(id).setDescription(description);
        meals.get(id).setCalories(calories);
        return index();
    }

    public List<MealTo> deleteMeal(int mealId) {
        meals.remove(meals.get(mealId));
        for(int i=0; i<meals.size(); i++)
        {
            meals.get(i).setId(i);
        }
        id=meals.size();
        return  index();
    }

    public List<MealTo> getAllMeals() {
        return mealToList;
    }


    public MealTo getMealById(int mealId) {
        return mealToList.get(mealId);
    }


}
