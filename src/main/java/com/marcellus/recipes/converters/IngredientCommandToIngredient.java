package com.marcellus.recipes.converters;

import com.marcellus.recipes.commands.IngredientCommand;
import com.marcellus.recipes.domain.Ingredient;
import com.marcellus.recipes.domain.Recipe;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if(source == null) {
            return null;
        }
        final Ingredient ingredient = new Ingredient();
        if(source.getId() != "") {
                ingredient.setId(source.getId());
        }  else {
            ingredient.setId(UUID.randomUUID().toString());
        }


        if(source.getRecipeId() != null) {
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            recipe.addIngredient(ingredient);
        }

        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());

        ingredient.setUom(uomConverter.convert(source.getUom()));
        return ingredient;
    }
}
