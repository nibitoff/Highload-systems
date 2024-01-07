package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.dto.ProductDto;
import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.service.ProductService;
import com.alsab.boozycalc.cocktail.service.data.ProductDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductDataService productDataService;

    @GetMapping("/allInOne")
    public ResponseEntity<?> getProducts() {
        try {
            return ResponseEntity.ok(productDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProductsWithPagination(Integer page) {
        try {
            return ResponseEntity.ok(productDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProductById(@RequestParam Long id) {
        try {
            productDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDto product) {
        try {
            return ResponseEntity.ok(productService.add(product));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editProduct(@Valid @RequestBody ProductDto product) {
        try {
            return ResponseEntity.ok(productService.edit(product));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}