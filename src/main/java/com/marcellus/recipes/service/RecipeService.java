package com.marcellus.recipes.service;

import com.marcellus.recipes.commands.RecipeCommand;
import com.marcellus.recipes.domain.Recipe;

import java.util.Set;

public interface RecipeService  {

    Set<Recipe> findAll();
    Recipe findById(Long id);
    RecipeCommand findCommandById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

}
