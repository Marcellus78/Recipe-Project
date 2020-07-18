package com.marcellus.recipes.service;

import com.marcellus.recipes.commands.IngredientCommand;
import com.marcellus.recipes.converters.IngredientCommandToIngredient;
import com.marcellus.recipes.converters.IngredientToIngredientCommand;
import com.marcellus.recipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.marcellus.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.marcellus.recipes.domain.Ingredient;
import com.marcellus.recipes.domain.Recipe;
import com.marcellus.recipes.repositories.RecipeRepository;
import com.marcellus.recipes.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    IngredientServiceImplTest() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        Ingredient ingredient3 = new Ingredient();

        ingredient1.setId("1");
        ingredient2.setId("2");
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //when
        IngredientCommand ingredientCommand = ingredientService
                .findByRecipeIdAndIngredientId("1","3");

        //then
        assertEquals("3", ingredientCommand.getId());
        assertEquals("1", ingredientCommand.getRecipeId());
        verify(recipeRepository,times(1)).findById(anyString());
    }
    @Test
    public void testSaveRecipeCommand(){
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals("3", savedCommand.getId());
        verify(recipeRepository,times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
    @Test
    public void testDeleteByRecipeIdAndIngredientId(){
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        recipe.addIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById("3", "1");

        //then
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }
}