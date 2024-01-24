package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.dto.ProductDto;
import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.service.ProductService;
import com.alsab.boozycalc.cocktail.service.data.ProductDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product controller", description = "Controller to manage products")
public class ProductController {
    private final ProductService productService;
    private final ProductDataService productDataService;

    @GetMapping("/all")
    @Operation(description = "Get all products list",
            summary = "Get all products",
            tags = "products")
    public ResponseEntity<Flux<ProductDto>> getProducts() {
        try {
            return ResponseEntity.ok(productDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/find")
    @Operation(description = "Find product by id",
            summary = "Find product by id",
            tags = "products")
    public ResponseEntity<Mono<?>> findById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productDataService.findById(id));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        }
    }

    @GetMapping("/page/{num}")
    @Operation(description = "Get products by page and amount",
            summary = "Get products by page and amount",
            tags = "products")
    public ResponseEntity<Flux<ProductDto>> getAllProductsWithPageAndSize(@PathVariable Integer num, @RequestParam Integer size) {
        try {
            return ResponseEntity.ok(productDataService.findAllWithPageAndSize(num, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/all/{page}")
    @Operation(description = "Get products by page",
            summary = "Get products by page",
            tags = "products")
    public ResponseEntity<Flux<ProductDto>> getAllProductsWithPagination(@PathVariable Integer page) {
        try {
            return ResponseEntity.ok(productDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }


    @DeleteMapping("/delete")
    @Operation(description = "Delete existing product",
            summary = "Delete product",
            tags = "products")
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
    @Operation(description = "Add new product",
            summary = "Add new product",
            tags = "products")
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
    @Operation(description = "Edit existing product",
            summary = "Edit product",
            tags = "product")
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