package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.dto.IngredientDto;
import com.alsab.boozycalc.cocktail.service.IngredientService;
import com.alsab.boozycalc.cocktail.service.data.IngredientDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
@Tag(name = "Ingredient controller", description = "Controller to manage ingredients")
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientDataService ingredientDataService;

    @GetMapping("/all")
    @Operation(description = "Get all ingredients list",
            summary = "Get all ingredients",
            tags = "ingredients")
    public ResponseEntity<Flux<IngredientDto>> getAllIngredients() {
        return ResponseEntity.ok(ingredientDataService.findAll());
    }

    @GetMapping("/all/{page}")
    @Operation(description = "Get ingredients by page",
            summary = "Get ingredients by page",
            tags = "ingredients")
    public ResponseEntity<Flux<IngredientDto>> getAllIngredientsWithPagination(@PathVariable Integer page) {
        try {
            return ResponseEntity.ok(ingredientDataService.findAllWithPagination(page));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @PostMapping("/add")
    @Operation(description = "Add new ingredient",
            summary = "Add new ingredient",
            tags = "ingredients")
    public ResponseEntity<Mono<?>> addNewIngredient(@Valid @RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.add(ingredient));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @PostMapping("/edit")
    @Operation(description = "Edit existing ingredient",
            summary = "Edit ingredient",
            tags = "ingredient")
    public ResponseEntity<Mono<?>> editIngredient(@Valid @RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.edit(ingredient));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @DeleteMapping("/delete")
    @Operation(description = "Delete existing ingredient",
            summary = "Delete ingredient",
            tags = "ingredient")
    public ResponseEntity<Mono<?>> deleteIngredient(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(ingredientDataService.deleteById(id));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        }
    }
}
