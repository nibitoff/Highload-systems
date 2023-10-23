package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.RecipeDto;
import com.alsab.boozycalc.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRecipes() {
        try {
            return ResponseEntity.ok(recipeService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewRecipe(@RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok(recipeService.add(recipe));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editRecipe(@RequestBody RecipeDto recipe) {
        try {
            return ResponseEntity.ok(recipeService.edit(recipe));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
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
