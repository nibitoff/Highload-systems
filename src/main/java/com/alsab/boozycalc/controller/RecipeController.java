package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.RecipeDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.service.RecipeService;
import com.alsab.boozycalc.service.data.RecipeDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeDataService recipeDataService;

    @GetMapping("/allInOne")
    public ResponseEntity<?> getAllRecipes() {
        try {
            return ResponseEntity.ok(recipeDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRecipesWithPagination(Integer page) {
        try {
            return ResponseEntity.ok(recipeDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewRecipe(@Valid @RequestBody RecipeDto recipe) {
        try {
            recipeService.add(recipe);
            return ResponseEntity.ok("recipe successfully added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editRecipe(@Valid @RequestBody RecipeDto recipe) {
        try {
            recipeService.edit(recipe);
            return ResponseEntity.ok("recipe successfully edited");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRecipe(@RequestBody RecipeDto recipe) {
        try {
            recipeService.delete(recipe);
            return ResponseEntity.ok(recipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
