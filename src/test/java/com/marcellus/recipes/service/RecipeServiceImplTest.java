package com.marcellus.recipes.service;

import com.marcellus.recipes.commands.RecipeCommand;
import com.marcellus.recipes.converters.RecipeCommandToRecipe;
import com.marcellus.recipes.converters.RecipeToRecipeCommand;
import com.marcellus.recipes.domain.Recipe;
import com.marcellus.recipes.repositories.RecipeRepository;
import com.marcellus.recipes.repositories.reactive.RecipeReactiveRepository;
import exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Max;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeReactiveRepository recipeReactiveRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void findByIdShouldReturnRecipeWhenMocked(){
        //given
        Recipe recipe = new Recipe();
        recipe.setId("3");
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        //when
        Recipe recipeReturned = recipeService.findById("3").block();

        //then
        assertNotNull(recipeReturned);
        verify(recipeReactiveRepository,times(1)).findById(anyString());
        verify(recipeReactiveRepository,never()).findAll();


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
    @Disabled
    @Test
    void exceptionTesting() {
    NotFoundException thrown =
            assertThrows(NotFoundException.class,
                    () -> recipeService.findById("1").block(),
                    "Expected doThing() to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("Recipe not found"));
}

    @Test
    void findCommandByIdShouldReturnRecipeCommnad(){
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand recipeById = recipeService.findCommandById("1").block();

        assertNotNull(recipeById);
        verify(recipeReactiveRepository, times(1)).findById("1");
        verify(recipeReactiveRepository, never()).findAll();

    }
    @Test
    void findAllShouldReturnRecipesWhenMocked() {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
//        HashSet<Recipe> recipesData = new HashSet<>();
//        recipesData.add(recipe);

        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(recipe1,recipe2));

        List<Recipe> recipes = recipeService.findAll().collectList().block();

        assertEquals(recipes.size(),2);
        verify(recipeReactiveRepository,times(1)).findAll();
    }
    @Test
    void deleteByIdShouldBeSuccess() throws Exception{

        //given
        String idToDelete = "2";

        //when
        recipeService.deleteById(idToDelete);

        //no when since method has void return
        //then
        verify(recipeRepository,times(1)).deleteById(anyString());
    }
}