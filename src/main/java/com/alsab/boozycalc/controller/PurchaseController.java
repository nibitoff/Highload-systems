package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.PurchaseDto;
import com.alsab.boozycalc.dto.RecipeDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.service.PurchaseService;
import com.alsab.boozycalc.service.data.PurchaseDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseDataService purchaseDataService;

    @GetMapping("/allInOne")
    public ResponseEntity<?> getAllPurchases() {
        try {
            return ResponseEntity.ok(purchaseDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPurchasesWithPagination(Integer page) {
        try {
            return ResponseEntity.ok(purchaseDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewPurchase(@Valid @RequestBody PurchaseDto purchase) {
        try {
            purchaseService.add(purchase);
            return ResponseEntity.ok("purchase successfully added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editPurchase(@Valid @RequestBody PurchaseDto purchase) {
        try {
            purchaseService.edit(purchase);
            return ResponseEntity.ok("purchase successfully edited");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePurchase(@RequestBody PurchaseDto purchase) {
        try {
            purchaseService.delete(purchase);
            return ResponseEntity.ok(purchase);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
