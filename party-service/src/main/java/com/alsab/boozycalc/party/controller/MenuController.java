package com.alsab.boozycalc.party.controller;

import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.service.MenuService;
import com.alsab.boozycalc.party.service.data.MenuDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu controller", description = "Controller to manage menus")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final MenuDataService menuDataService;
    @GetMapping("/get")
    @Operation(description = "Get certain party menu",
            summary = "Get party menu",
            tags = "menus")
    public ResponseEntity<?> getPartyMenu(@RequestParam Long party) {
        try {
            return ResponseEntity.ok(menuDataService.findAllByParty(party));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/cocktails-info")
    @Operation(description = "Get info about cocktails in party",
            summary = "Get info about cocktails",
            tags = "menus")
    public ResponseEntity<?> getCocktailFromMenuInfo(@RequestParam Long party) {
        try {
            return ResponseEntity.ok(menuService.getPartyMenu(party));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
