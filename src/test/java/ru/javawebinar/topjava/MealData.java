package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.global_seq_meal;

public class MealData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;
    public static  int MEAL_ID = global_seq_meal;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(MEAL_ID++,LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(MEAL_ID++,LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(MEAL_ID++,LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(MEAL_ID++,LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(MEAL_ID++,LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(MEAL_ID++,LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    public static Meal getNew(){
        return new Meal(null,null,0);
    }

    public static Meal getUpdated(int id){
        Meal updated = new Meal(meals.get(id));
        updated.setCalories(400);
        updated.setDescription("new_test");
        updated.setDateTime(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected){
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }


    public static void assertMatch(Iterable<Meal> actual, Meal... expected){
        assertMatch(actual, (Meal) Arrays.asList(expected));
    }
    private void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected, int userId) {
        assertThat(actual).isEqualTo(expected);
        assertThat(userId).isEqualTo(USER_ID);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected){
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
       // assertThat(actual).isEqualTo(expected);
    }

}
