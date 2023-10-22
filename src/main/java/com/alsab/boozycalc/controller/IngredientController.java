package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/all")
    public ResponseEntity getAllIngredients(){
        try {
            return ResponseEntity.ok(ingredientService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
