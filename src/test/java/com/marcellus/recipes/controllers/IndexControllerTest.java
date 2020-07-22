package com.marcellus.recipes.controllers;

import com.marcellus.recipes.domain.Recipe;
import com.marcellus.recipes.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }


    @Test
    void testIndexControllerWithMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        when(recipeService.findAll()).thenReturn(Flux.empty());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void indexControllerShouldReturnList(){
        //given
        Recipe recipe1 = new Recipe();
        recipe1.setId("1");
        Recipe recipe2 = new Recipe();
        recipe2.setId("2");

        when(recipeService.findAll()).thenReturn(Flux.just(recipe1, recipe2));
        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //when
        indexController.getIndexPage(model);

        //then
        verify(model,times(1)).
                addAttribute(eq("recipes"), argumentCaptor.capture());
        List<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}