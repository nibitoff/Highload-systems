package com.alsab.boozycalc.party.controller;

import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.service.PartyService;
import com.alsab.boozycalc.party.service.data.PartyDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Party controller", description = "Controller to manage parties")
@RestController
@RequestMapping("/api/v1/parties")
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;
    private final PartyDataService partyDataService;

    @GetMapping("/allInOne")
    @Operation(description = "Get all parties list",
            summary = "Get all parties",
            tags = "parties")
    public ResponseEntity<?> getParties() {
        try {
            return ResponseEntity.ok(partyDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/all")
    @Operation(description = "Get parties by page",
            summary = "Get parties by page",
            tags = "parties")
    public ResponseEntity<?> getAllPartiesWithPagination(Integer page) {
        try {
            return ResponseEntity.ok(partyDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/delete")
    @Operation(description = "Delete existing party",
            summary = "Delete party",
            tags = "parties")
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        try {
            partyDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    @Operation(description = "Add new party",
            summary = "Add new party",
            tags = "parties")
    public ResponseEntity<?> addParty(@Valid @RequestBody PartyDto party) {
        try {
            partyService.add(party);
            return ResponseEntity.ok("party successfully added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    @Operation(description = "Edit existing party",
            summary = "Edit party",
            tags = "parties")
    public ResponseEntity<?> editParty(@Valid @RequestBody PartyDto party) {
        try {
            partyService.edit(party);
            return ResponseEntity.ok("party successfully added");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }


    @GetMapping("/purchases")
    @Operation(description = "Get purchases for certain party",
            summary = "Get party's purchases",
            tags = "parties")
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

