package com.marcellus.recipes.service;

import com.marcellus.recipes.domain.Recipe;
import com.marcellus.recipes.repositories.RecipeRepository;


import java.util.Set;

public interface RecipeService  {

    Set<Recipe> findAll();
    Recipe findById(Long id);
    void save(Recipe recipe);
    void delete(Recipe recipe);
    void deleteById(Long id);

}
