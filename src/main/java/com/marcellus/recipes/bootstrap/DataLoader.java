package com.marcellus.recipes.bootstrap;

import com.marcellus.recipes.domain.*;
import com.marcellus.recipes.repositories.CategoryRepository;
import com.marcellus.recipes.repositories.RecipeRepository;
import com.marcellus.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public DataLoader(RecipeRepository recipeRepository,
                      CategoryRepository categoryRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadData();
    }

    private void loadData() {

        //categories
        Category texMex = categoryRepository.findByCategoryName("Tex-Mex").get();
        Category mexican = categoryRepository.findByCategoryName("Mexican").get();

        //Ingredients
        Ingredient avocado = new Ingredient();
        avocado.setAmount(BigDecimal.valueOf(2.00));
        avocado.setDescription("Avocado");
        avocado.setUom(unitOfMeasureRepository.findByDescription("Piece").get());

        Ingredient salt = new Ingredient();
        salt.setAmount(BigDecimal.valueOf(0.25));
        salt.setDescription("of salt, more to taste");
        salt.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());

        Ingredient limeJuice = new Ingredient();
        limeJuice.setAmount(BigDecimal.valueOf(1));
        limeJuice.setDescription("fresh lime juice or lemon juice");
        limeJuice.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());

        Recipe guacamole = new Recipe();
        Set<Category> guacamoleCategories = new HashSet<>();
        guacamoleCategories.add(texMex);
        guacamoleCategories.add(mexican);
        guacamole.setCategories(guacamoleCategories);
        Set<Ingredient> guacamoleIngredients = new HashSet<>();
        guacamoleIngredients.add(avocado);
        guacamole.setCategories(guacamoleCategories);
        guacamole.setIngredients(guacamoleIngredients);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setCookTime(0);
        guacamole.setPrepTime(10);
        guacamole.setServings(2);
        guacamole.setDescription("Perfect Guacamole");
        recipeRepository.save(guacamole);
    }
}
