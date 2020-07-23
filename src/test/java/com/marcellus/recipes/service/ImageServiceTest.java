package com.marcellus.recipes.service;

import com.marcellus.recipes.domain.Recipe;
import com.marcellus.recipes.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    ImageService imageService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeReactiveRepository);

    }

    @Test
    void saveImageFile() throws IOException {
        //given
        String id = "1";
        Recipe recipe = new Recipe();
        recipe.setId(id);

        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile","testing.txt","text/plain",
                        "Spring Framework Guru".getBytes());
        Optional<Recipe> recipeOptional = Optional.of(recipe);


        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id, multipartFile).block();

        //then
        verify(recipeReactiveRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);

    }
}