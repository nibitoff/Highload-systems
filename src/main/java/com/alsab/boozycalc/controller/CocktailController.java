package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.CocktailTypeEntity;
import com.alsab.boozycalc.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cocktails")
public class CocktailController {
    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCocktails() {
        try {
            return ResponseEntity.ok(cocktailService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewCocktail(@RequestBody CocktailEntity cocktail) {
        try {
            return ResponseEntity.ok(cocktailService.add(cocktail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/all_types")
    public ResponseEntity<?> getAllCocktailTypes() {
        try {
            return ResponseEntity.ok(cocktailService.findAllTypes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PostMapping("/add_type")
    public ResponseEntity<?> addNewCocktailType(@RequestBody CocktailTypeEntity type) {
        try {
            return ResponseEntity.ok(cocktailService.addType(type));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
