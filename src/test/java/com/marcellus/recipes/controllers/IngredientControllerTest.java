package com.marcellus.recipes.controllers;

import com.marcellus.recipes.commands.IngredientCommand;
import com.marcellus.recipes.commands.RecipeCommand;
import com.marcellus.recipes.commands.UnitOfMeasureCommand;
import com.marcellus.recipes.service.IngredientService;
import com.marcellus.recipes.service.RecipeService;
import com.marcellus.recipes.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    IngredientService ingredientService;

    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListingIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findCommandById(anyString());
    }
    @Test
    public void testShowIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString()))
                .thenReturn(Mono.just(ingredientCommand));

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));


    }
    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString()))
                .thenReturn(Mono.just(ingredientCommand));
        when(unitOfMeasureService.findAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));


    }
    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("2");
        command.setRecipeId("3");

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(Mono.just(command));

        //then
        mockMvc.perform(post("/recipe/3/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/3/ingredient/2/show"));

    }
    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");

        //when
        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
        when(unitOfMeasureService.findAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/2/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
        verify(recipeService, times(1)).findCommandById(anyString());

    }
    @Test
    public void testDeleteIngredient() throws Exception {

        when(ingredientService.deleteById(anyString(), anyString())).thenReturn(Mono.empty());

        mockMvc.perform((get("/recipe/2/ingredient/3/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyString(), anyString());
    }
}
