package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


public class MealData {

    public static final int MEAL_ID = START_SEQ+2;
    public static final int NOT_FOUND = 10;

    public static final Meal MEAL1 = new Meal(MEAL_ID,LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL_ID+1,LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL_ID+2, LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL_ID+3,LocalDateTime.of(2021, Month.JANUARY, 30, 00, 0), "Ужин", 500);
    public static final Meal MEAL5= new Meal(MEAL_ID+4,LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL6 = new Meal(MEAL_ID+5,LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL7 = new Meal(MEAL_ID+6,LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal ADMIN_MEAL1 = new Meal(MEAL_ID+7,LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Завтрак админа", 1050);
    public static final Meal ADMIN_MEAL2 = new Meal(MEAL_ID+8,LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед админа", 450);

    public static final List<Meal> MEALS = Arrays.asList(MEAL7, MEAL6, MEAL5, MEAL4,MEAL3,MEAL2,MEAL1);
    public static final List<Meal> MEALS_between = Arrays.asList( MEAL3,MEAL2,MEAL1);

    public static Meal getNew(){
        return new Meal(null,LocalDateTime.of(2021,Month.APRIL,1 ,10,00),"new Meal",10);
    }

    public static Meal getUpdated(){
        Meal updated = new Meal(MEAL1);
        updated.setCalories(400);
        updated.setDescription("new_test");
        updated.setDateTime(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected){
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }


    public static void assertMatch(Iterable<Meal> actual, Meal... expected){
        assertMatch(actual,Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected){
       assertThat(actual).isEqualTo(expected);
    }

}
