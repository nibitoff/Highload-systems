package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.ProductDto;
import com.alsab.boozycalc.entity.ProductEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.service.ProductService;
import com.alsab.boozycalc.service.data.ProductDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductDataService productDataService;

    @GetMapping("/all")
    public ResponseEntity<?> getProducts() {
        try {
            return ResponseEntity.ok(productDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteProductById(@RequestParam Long id) {
        try {
            productDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductDto product) {
        try {
            productService.add(product);
            return ResponseEntity.ok("product successfully added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity editProduct(@RequestBody ProductDto product) {
        try {
            productService.edit(product);
            return ResponseEntity.ok("product successfully edited");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }
}