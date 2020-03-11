package com.marcellus.recipes.service;

import com.marcellus.recipes.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> findAllUoms();
}
