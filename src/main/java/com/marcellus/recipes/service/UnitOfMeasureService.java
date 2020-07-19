package com.marcellus.recipes.service;

import com.marcellus.recipes.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;


public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> findAllUoms();
}
