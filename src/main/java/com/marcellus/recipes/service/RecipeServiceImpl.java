package com.marcellus.recipes.service;

import com.marcellus.recipes.commands.RecipeCommand;
import com.marcellus.recipes.converters.RecipeCommandToRecipe;
import com.marcellus.recipes.converters.RecipeToRecipeCommand;
import com.marcellus.recipes.domain.Recipe;
import com.marcellus.recipes.repositories.RecipeRepository;
import com.marcellus.recipes.repositories.reactive.RecipeReactiveRepository;
import exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand){
        this.recipeRepository = recipeRepository;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> findAll() {
        log.debug("I'm in the service");
//        Set<Recipe> recipes = new HashSet<>();
//
//        recipeRepository.findAll().forEach(x->recipes.add(x));

        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {

//        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
//
//        if(!optionalRecipe.isPresent()){
//            throw new NotFoundException("Recipe not found. For ID value " + id.toString());
//        }

        return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {

//        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id).block());
//
//        //enhance command object with id value
//        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
//            recipeCommand.getIngredients().forEach(rc -> {
//                rc.setRecipeId(recipeCommand.getId());
//            });
//        }
//
//        return Mono.just(recipeCommand);

        return recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                    recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipeCommand.getId()));

                    return recipeCommand;
                });
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {

        return recipeReactiveRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
        recipeRepository.deleteById(idToDelete);
        return Mono.empty();
    }
}
