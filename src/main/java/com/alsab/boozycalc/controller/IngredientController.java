package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.entity.IngredientTypeEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientController(IngredientService ingredientService, ModelMapper modelMapper) {
        this.ingredientService = ingredientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllIngredients() {
        try {
            return ResponseEntity.ok(ingredientService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewIngredient(@RequestBody IngredientDto ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.addIngredient(convertToEntity(ingredient)));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editIngredient(@RequestBody IngredientEntity ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.editIngredient(ingredient));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteIngredient(Long id) {
        try {
            ingredientService.deleteIngredient(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    private IngredientEntity convertToEntity(IngredientDto dto){
        IngredientEntity ingredient = modelMapper.map(dto, IngredientEntity.class);
        ingredient.setType(new IngredientTypeEntity(dto.getType_id(), ""));
        return ingredient;
    }

    private IngredientDto convertToDto(IngredientEntity ingredient){
        IngredientDto dto = modelMapper.map(ingredient, IngredientDto.class);
        dto.setType_id(ingredient.getType().getId());
        return dto;
    }
}
