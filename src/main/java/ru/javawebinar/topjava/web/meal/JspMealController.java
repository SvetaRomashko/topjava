package ru.javawebinar.topjava.web.meal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;


@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController{

    @RequestMapping
    public String getMeals(Model model){
       model.addAttribute("meals",super.getAll());
       return "meals";
    }

   @GetMapping(value = "/delete")
    public  String deleteMeal(HttpServletRequest request){
        super.delete(Integer.parseInt(request.getParameter("id")));
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createMeal(HttpServletRequest request ){
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) ;
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeal(HttpServletRequest request , Model model){
       model.addAttribute("meal", super.get(Integer.parseInt(request.getParameter("id"))));
        return "mealForm";
    }

    @PostMapping("/filter")
    public String filterMeals(HttpServletRequest request , Model model){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";

    }

}
