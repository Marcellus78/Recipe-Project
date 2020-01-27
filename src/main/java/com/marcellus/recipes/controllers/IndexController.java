package com.marcellus.recipes.controllers;

import com.marcellus.recipes.domain.Category;
import com.marcellus.recipes.domain.UnitOfMeasure;
import com.marcellus.recipes.repositories.CategoryRepository;
import com.marcellus.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping({"","/"})
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }
    @RequestMapping({"index","index.html"})
    public String getIndex(){

        Optional<Category> category = categoryRepository.findByCategoryName("American");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Cup");
        System.out.println("Uom Id is: " + unitOfMeasure.get().getId() );
        System.out.println("Category Id is: " + category.get().getId());
        return "index";
    }
}
