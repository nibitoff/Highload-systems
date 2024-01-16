package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.dto.IngredientDto;
import com.alsab.boozycalc.cocktail.service.IngredientService;
import com.alsab.boozycalc.cocktail.service.data.IngredientDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientDataService ingredientDataService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllIngredients() {
        return ResponseEntity.ok(ingredientDataService.findAll());
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<?> getAllIngredientsWithPagination(@PathVariable Integer page) {
        try {
            return ResponseEntity.ok(ingredientDataService.findAllWithPagination(page));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewIngredient(@Valid @RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.add(ingredient));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editIngredient(@Valid @RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.edit(ingredient));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteIngredient(@RequestParam Long id) {
        try {
            ingredientDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        }
    }
}
