package com.marcellus.recipes.controllers;

import com.marcellus.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping({"","/","index","index.html"})
    public String getRecipes(Model model){

        model.addAttribute("recipes", recipeService.findAll());

        return "recipes/index";
    }
}
