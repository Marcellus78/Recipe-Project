package com.marcellus.recipes.converters;

import com.marcellus.recipes.commands.IngredientCommand;
import com.marcellus.recipes.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomcConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomcConverter) {
        this.uomcConverter = uomcConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if(source == null){
            return null;
        }
        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(source.getId());
        ingredientCommand.setAmount(source.getAmount());
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setUom(uomcConverter.convert(source.getUom()));

        return ingredientCommand;
    }
}
