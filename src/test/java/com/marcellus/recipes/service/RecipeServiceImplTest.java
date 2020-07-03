package com.marcellus.recipes.service;

import com.marcellus.recipes.commands.RecipeCommand;
import com.marcellus.recipes.converters.RecipeCommandToRecipe;
import com.marcellus.recipes.converters.RecipeToRecipeCommand;
import com.marcellus.recipes.domain.Recipe;
import com.marcellus.recipes.repositories.RecipeRepository;
import exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void findByIdShouldReturnRecipeWhenMocked(){
        //given
        Recipe recipe = new Recipe();
        recipe.setId(3L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        //when
        Recipe recipeReturned = recipeService.findById(3L);

        //then
        assertNotNull(recipeReturned);
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();


    }
//    @Test
//    void getRecipeByIdTestNotFound() {
//        Optional<Recipe> recipeOptional = Optional.empty();
//
//        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
//
//        NotFoundException notFoundException = assertThrows(
//                NotFoundException.class, () -> recipeService.findById(1L),
//                "Recipe Not Found"
//        );
//        assertTrue(notFoundException.getMessage().contains("Recipe Not Found"));
//    }
    @Test
    void exceptionTesting() {
    NotFoundException thrown =
            assertThrows(NotFoundException.class,
                    () -> recipeService.findById(1L),
                    "Expected doThing() to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("Recipe not found"));
}

    @Test
    void findCommandByIdShouldReturnRecipeCommnad(){
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand recipeById = recipeService.findCommandById(1L);

        assertNotNull(recipeById);
        verify(recipeRepository, times(1)).findById(1L);
        verify(recipeRepository, never()).findAll();

    }
    @Test
    void findAllShouldReturnRecipesWhenMocked() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.findAll();

        assertEquals(recipes.size(),1);
        verify(recipeRepository,times(1)).findAll();
    }
    @Test
    void deleteByIdShouldBeSuccess() throws Exception{

        //given
        Long idToDelete = Long.valueOf(2L);

        //when
        recipeService.deleteById(idToDelete);

        //no when since method has void return
        //then
        verify(recipeRepository,times(1)).deleteById(anyLong());
    }
}