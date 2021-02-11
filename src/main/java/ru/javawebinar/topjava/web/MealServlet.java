package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String INSERT_OR_EDIT = "meal.jsp";
    private static String LIST_USER = "meals.jsp";
    private MealDao dao;


   public MealServlet(){
       dao = new MealDao();
   }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to mealsToList");
        String action = req.getParameter("action");
        String forward="";
        List<MealTo> mealToList = dao.index();

        if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            forward = LIST_USER;
            req.setAttribute("mealsToList", dao.deleteMeal(mealId));
            resp.sendRedirect("meals?action=ListMeals");
            return;
        } else if (action.equalsIgnoreCase("edit")){
            req.setCharacterEncoding("UTF-8");
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            MealTo meal = dao.getMealById(mealId);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("ListMeals")){
            forward = LIST_USER;
            req.setAttribute("mealsToList", mealToList);
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mealId = req.getParameter("mealId");
        if(mealId == null || mealId.isEmpty()) {
            dao.addMeal(req.getParameter("datetime"), req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
        }
        else{
           dao.updateMeal(Integer.parseInt(mealId),req.getParameter("datetime"), req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
        }

        RequestDispatcher view = req.getRequestDispatcher(LIST_USER);
        req.setAttribute("mealsToList", dao.index());
        view.forward(req, resp);

   }
}





