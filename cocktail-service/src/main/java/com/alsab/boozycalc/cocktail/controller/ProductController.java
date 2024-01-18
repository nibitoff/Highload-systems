package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.dto.ProductDto;
import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.service.ProductService;
import com.alsab.boozycalc.cocktail.service.data.ProductDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductDataService productDataService;

    @GetMapping("/all")
    public ResponseEntity<Flux<ProductDto>> getProducts() {
        try {
            return ResponseEntity.ok(productDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/find")
    public ResponseEntity<Mono<?>> findById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productDataService.findById(id));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        }
    }

    @GetMapping("/page/{num}")
    public ResponseEntity<Flux<ProductDto>> getAllProductsWithPageAndSize(@PathVariable Integer num, @RequestParam Integer size) {
        try {
            return ResponseEntity.ok(productDataService.findAllWithPageAndSize(num, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<Flux<ProductDto>> getAllProductsWithPagination(@PathVariable Integer page) {
        try {
            return ResponseEntity.ok(productDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Mono<?>> deleteProductById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productDataService.deleteById(id));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Mono<?>> addProduct(@Valid @RequestBody ProductDto product) {
        try {
            return ResponseEntity.ok(productService.add(product));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<Mono<?>> editProduct(@Valid @RequestBody ProductDto product) {
        try {
            return ResponseEntity.ok(productService.edit(product));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }
}