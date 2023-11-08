package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.service.IngredientService;
import com.alsab.boozycalc.service.data.IngredientDataService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientDataService ingredientDataService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<?> getAllIngredients() {
        try {
            return ResponseEntity.ok(ingredientDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewIngredient(@RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.add(ingredient));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editIngredient(@RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.edit(ingredient));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteIngredient(Long id) {
        try {
            ingredientDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
