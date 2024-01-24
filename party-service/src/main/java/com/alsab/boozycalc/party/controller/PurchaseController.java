package com.alsab.boozycalc.party.controller;

import com.alsab.boozycalc.party.dto.PurchaseDto;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.service.PurchaseService;
import com.alsab.boozycalc.party.service.data.PurchaseDataService;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Purchase controller", description = "Controller to manage purchases")
@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseDataService purchaseDataService;

    @GetMapping("/all")
    @Operation(description = "Get all purchases list",
            summary = "Get all purchases",
            tags = "purchases")
    public ResponseEntity<?> getAllPurchases() {
        return ResponseEntity.ok(purchaseDataService.findAll());
    }

    @GetMapping("/all/{page}")
    @Operation(description = "Get purchases by page",
            summary = "Get purchases by page",
            tags = "purchases")
    public ResponseEntity<?> getAllPurchasesWithPagination(@PathVariable Integer page) {
        try {
            return ResponseEntity.ok(purchaseDataService.findAllWithPagination(page));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    @Operation(description = "Add new purchase",
            summary = "Add new purchase",
            tags = "purchases")
    public ResponseEntity<?> addNewPurchase(@Valid @RequestBody PurchaseDto purchase) {
        try {
            return ResponseEntity.ok(purchaseService.add(purchase));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (FeignException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/edit")
    @Operation(description = "Edit existing purchase",
            summary = "Edit purchase",
            tags = "purchases")
    public ResponseEntity<?> editPurchase(@Valid @RequestBody PurchaseDto purchase) {
        try {
            return ResponseEntity.ok(purchaseService.edit(purchase));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (FeignException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @Operation(description = "Delete existing purchase",
            summary = "Delete purchase",
            tags = "purchases")
    public ResponseEntity<?> deletePurchase(@RequestBody PurchaseDto purchase) {
        try {
            purchaseService.delete(purchase);
            return ResponseEntity.ok(purchase);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (FeignException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
