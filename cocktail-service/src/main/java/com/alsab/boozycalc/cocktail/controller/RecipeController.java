package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.dto.RecipeDto;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.service.RecipeService;
import com.alsab.boozycalc.cocktail.service.data.RecipeDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipe controller", description = "Controller to manage recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeDataService recipeDataService;

    @GetMapping("/all")
    @Operation(description = "Get all recipes list",
            summary = "Get all recipes",
            tags = "recipes")
    public ResponseEntity<Flux<RecipeDto>> getAllRecipes() {
        try {
            return ResponseEntity.ok(recipeDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/page/{num}")
    @Operation(description = "Get recipes by page and amount",
            summary = "Get recipes by page and amount",
            tags = "recipes")
    public ResponseEntity<Flux<RecipeDto>> getAllCocktailsWithPageAndSize(@PathVariable Integer num, @RequestParam Integer size) {
        try {
            return ResponseEntity.ok(recipeDataService.findAllWithPageAndSize(num, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/all/{page}")
    @Operation(description = "Get recipes by page",
            summary = "Get recipes by page",
            tags = "recipes")
    public ResponseEntity<?> getAllRecipesWithPagination(Integer page) {
        try {
            return ResponseEntity.ok(recipeDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/find-by-cocktail")
    @Operation(description = "Find all recipes with certain cocktail",
            summary = "Find all recipes with cocktail",
            tags = "recipes")
    public ResponseEntity<Mono<?>> findAllByCocktail(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(recipeDataService.findAllByCocktail(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @PostMapping("/add")
    @Operation(description = "Add new recipe",
            summary = "Add new recipe",
            tags = "recipes")
    public ResponseEntity<Mono<?>> addNewRecipe(@Valid @RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok(recipeService.add(recipe));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @PostMapping("/edit")
    @Operation(description = "Edit existing recipe",
            summary = "Edit recipe",
            tags = "recipes")
    public ResponseEntity<Mono<?>> editRecipe(@Valid @RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok(recipeService.edit(recipe));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        }
    }

    @DeleteMapping("/delete")
    @Operation(description = "Delete existing recipe",
            summary = "Delete recipe",
            tags = "recipes")
    public ResponseEntity<Mono<?>> deleteRecipe(@RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok(recipeDataService.delete(recipe));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }
}
