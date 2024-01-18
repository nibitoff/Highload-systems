package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.dto.RecipeDto;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.service.RecipeService;
import com.alsab.boozycalc.cocktail.service.data.RecipeDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeDataService recipeDataService;

    @GetMapping("/all")
    public ResponseEntity<Flux<RecipeDto>> getAllRecipes() {
        try {
            return ResponseEntity.ok(recipeDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/page/{num}")
    public ResponseEntity<Flux<RecipeDto>> getAllCocktailsWithPageAndSize(@PathVariable Integer num, @RequestParam Integer size) {
        try {
            return ResponseEntity.ok(recipeDataService.findAllWithPageAndSize(num, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<?> getAllRecipesWithPagination(Integer page) {
        try {
            return ResponseEntity.ok(recipeDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/find-by-cocktail")
    public ResponseEntity<Mono<?>> findAllByCocktail(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(recipeDataService.findAllByCocktail(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Mono<?>> addNewRecipe(@Valid @RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok(recipeService.add(recipe));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<Mono<?>> editRecipe(@Valid @RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok(recipeService.edit(recipe));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        }
    }

    @DeleteMapping("/delete")
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
