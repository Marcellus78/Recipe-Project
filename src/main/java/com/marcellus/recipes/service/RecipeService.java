package com.marcellus.recipes.service;

import com.marcellus.recipes.commands.RecipeCommand;
import com.marcellus.recipes.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface RecipeService  {

    Flux<Recipe> findAll();
    Mono<Recipe> findById(String id);
    Mono<RecipeCommand> findCommandById(String id);
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);
    Mono<Void> deleteById(String idToDelete);
}
