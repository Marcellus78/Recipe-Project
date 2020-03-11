package com.marcellus.recipes.commands;

import java.math.BigDecimal;

public class IngredientCommand {

    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;

    public IngredientCommand() {
    }

    public Long getId() {
        return this.id;
    }

    public Long getRecipeId() {
        return this.recipeId;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public UnitOfMeasureCommand getUom() {
        return this.uom;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setUom(UnitOfMeasureCommand uom) {
        this.uom = uom;
    }
}
