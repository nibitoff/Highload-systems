package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.dto.PartyDto;
import com.alsab.boozycalc.dto.ProductDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.service.PartyService;
import com.alsab.boozycalc.service.ProductService;
import com.alsab.boozycalc.service.data.PartyDataService;
import com.alsab.boozycalc.service.data.ProductDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parties")
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;
    private final PartyDataService partyDataService;

    @GetMapping("/all")
    public ResponseEntity<?> getParties() {
        try {
            return ResponseEntity.ok(partyDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        try {
            partyDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addParty(@RequestBody PartyDto party) {
        try {
            partyService.add(party);
            return ResponseEntity.ok("party successfully added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editParty(@RequestBody PartyDto party) {
        try {
            partyService.edit(party);
            return ResponseEntity.ok("party successfully added");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }

    @GetMapping("/menu")
    public ResponseEntity<?> getPartyMenu(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(partyService.getPartyMenu(id));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/purchases")
    public ResponseEntity<?> getPartyPurchases(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(partyService.getPartyPurchases(id));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}

