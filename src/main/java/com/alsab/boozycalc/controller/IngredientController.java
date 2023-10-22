package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllIngredients(){
        try {
            return ResponseEntity.ok(ingredientService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewIngredient(@RequestBody IngredientEntity ingredient){
        try {
            return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
