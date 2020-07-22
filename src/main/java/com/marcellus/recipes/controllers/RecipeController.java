package com.marcellus.recipes.controllers;

import com.marcellus.recipes.commands.RecipeCommand;
import com.marcellus.recipes.service.RecipeService;
import exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findById(id).block());

        return "recipe/show";
    }
    @GetMapping("recipe/new")
    public String newRecipe(Model model){

        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeForm";
    }
    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(id).block());

        return "recipe/recipeForm";
    }
    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(command).block();

        return "redirect:/recipe/"+ savedRecipe.getId() +"/show";

    }
    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting with id: " + id);

        recipeService.deleteById(id);
        return "redirect:/";
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
