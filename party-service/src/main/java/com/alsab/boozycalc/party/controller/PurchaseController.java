package com.alsab.boozycalc.party.controller;

import com.alsab.boozycalc.party.dto.PurchaseDto;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.service.PurchaseService;
import com.alsab.boozycalc.party.service.data.PurchaseDataService;
import feign.FeignException;
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllPurchases() {
        return ResponseEntity.ok(purchaseDataService.findAll());
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<?> getAllPurchasesWithPagination(@PathVariable Integer page) {
        try {
            return ResponseEntity.ok(purchaseDataService.findAllWithPagination(page));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
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
