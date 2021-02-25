package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealData.*;
import static ru.javawebinar.topjava.MealData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL2.getId(), USER_ID);
        assertMatch(meal, MEAL2);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND,USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL1.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1.getId(), USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND,USER_ID));
    }


    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2021, Month.JANUARY, 29), LocalDate.of(2021, Month.JANUARY, 30), USER_ID);
        assertMatch(meals, MEALS_between);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEALS);
    }


    @Test
    public void update() {
        Meal updated = MealData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), MealData.getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(new Meal(LocalDateTime.of(2021, Month.APRIL, 1, 10, 00),
                "new Meal", 10), USER_ID);

        Meal mealNew = MealData.getNew();
        mealNew.setId(created.getId());
        assertMatch(service.get(mealNew.getId(), USER_ID),mealNew);
    }
}