package com.alsab.boozycalc.party.controller;

import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.service.MenuService;
import com.alsab.boozycalc.party.service.data.MenuDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final MenuDataService menuDataService;
    @GetMapping("/get")
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
