package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.service.data.CocktailDataService;
import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.service.CocktailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cocktails")
@RequiredArgsConstructor
public class CocktailController {
    private final CocktailService cocktailService;
    private final CocktailDataService cocktailDataService;

    @GetMapping("/allInOne")
    public ResponseEntity<?> getAllCocktails() {
        try {
            return ResponseEntity.ok(cocktailDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCocktailsWithPagination(@RequestParam Integer page) {
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
    public ResponseEntity<?> deleteCocktail(@RequestParam Long id) {
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