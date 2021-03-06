package com.marcellus.recipes.service;

import com.marcellus.recipes.domain.Recipe;
import com.marcellus.recipes.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);

    }

    @Test
    void saveImageFile() throws IOException {
        //given
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile","testing.txt","text/plain",
                        "Spring Framework Guru".getBytes());
        Optional<Recipe> recipeOptional = Optional.of(recipe);


        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id, multipartFile);

        //then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);

    }
}