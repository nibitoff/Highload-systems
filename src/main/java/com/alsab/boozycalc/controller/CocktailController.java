package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.CocktailTypeEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.service.CocktailService;
import com.alsab.boozycalc.service.data.CocktailDataService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cocktails")
@RequiredArgsConstructor
public class CocktailController {
    private final CocktailService cocktailService;
    private final CocktailDataService cocktailDataService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCocktails() {
        try {
            return ResponseEntity.ok(cocktailDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewCocktail(@RequestBody CocktailDto cocktail) {
        try {
            return ResponseEntity.ok(cocktailService.add(cocktail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PostMapping("/edit")
    public ResponseEntity<?> editCocktail(@RequestBody CocktailDto cocktail) {
        try {
            return ResponseEntity.ok(cocktailService.edit(cocktail));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCocktail(Long id) {
        try {
            cocktailDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
//    @GetMapping("/all_types")
//    public ResponseEntity<?> getAllCocktailTypes() {
//        try {
//            return ResponseEntity.ok(cocktailService.findAllTypes());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//    @PostMapping("/add_type")
//    public ResponseEntity<?> addNewCocktailType(@RequestBody CocktailTypeEntity type) {
//        try {
//            return ResponseEntity.ok(cocktailService.addType(type));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
}
