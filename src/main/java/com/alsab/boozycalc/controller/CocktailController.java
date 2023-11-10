package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.service.CocktailService;
import com.alsab.boozycalc.service.data.CocktailDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cocktails")
@RequiredArgsConstructor
public class CocktailController {
    private final CocktailService cocktailService;
    private final CocktailDataService cocktailDataService;
    private final ModelMapper modelMapper;

    @GetMapping("/allInOne")
    public ResponseEntity<?> getAllCocktails() {
        try {
            return ResponseEntity.ok(cocktailDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCocktailsWithPagination(Integer page) {
        try {
            return ResponseEntity.ok(cocktailDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/allUltimate")
    public ResponseEntity<?> getAllCocktailsWithPageAndSize(@RequestParam Integer page, @RequestParam Integer size) {
        try {
            return ResponseEntity.ok(cocktailDataService.findAllWithPageAndSize(page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addNewCocktail(@Valid @RequestBody CocktailDto cocktail) {
        try {
            return ResponseEntity.ok(cocktailService.add(cocktail));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PostMapping("/edit")
    public ResponseEntity<?> editCocktail(@Valid @RequestBody CocktailDto cocktail) {
        try {
            return ResponseEntity.ok(cocktailService.edit(cocktail));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCocktail(Long id) {
        try {
            cocktailDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
