package com.alsab.boozycalc.controller;

import com.alsab.boozycalc.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parties")
public class PartyController {
//    @Autowired
//    private PartyService partyService;
//
//    @GetMapping("/all")
//    public ResponseEntity getParties() {
//        try {
//            return ResponseEntity.ok(partyService.getAllParties());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @GetMapping("/invited")
//    @CrossOrigin(origins = "http://localhost:8081")
//    public ResponseEntity getParties(@RequestParam Long person_id) {
//        try {
//            return ResponseEntity.ok(partyService.getInvitings(person_id));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @GetMapping("/menu")
//    @CrossOrigin(origins = "http://localhost:8081")
//    public ResponseEntity getMenu(@RequestParam Long id) {
//        try {
//            return ResponseEntity.ok(partyService.getMenu(id));
//        } catch (ItemNameNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @DeleteMapping
//    @CrossOrigin(origins = "http://localhost:8081")
//    public ResponseEntity deletePartyById(@RequestParam Long id) {
//        try {
//            return ResponseEntity.ok(partyService.deleteById(id));
//        } catch (ItemNameNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @PostMapping("/add")
//    @CrossOrigin(origins = "http://localhost:8081")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity addParty(@RequestBody PartyModel newParty) {
//        try {
//            partyService.addParty(newParty);
//            return ResponseEntity.ok("party successfully added");
//        } catch (ItemNameUsedException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @PostMapping("/edit")
//    @CrossOrigin(origins = "http://localhost:8081")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity editParty(@RequestBody PartyModel newParty) {
//        try {
//            partyService.editParty(newParty);
//            return ResponseEntity.ok("party successfully added");
//        } catch (ItemIdNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @GetMapping("/available")
//    @CrossOrigin(origins = "http://localhost:8081")
//    public ResponseEntity getAvailable(@RequestParam Long id) {
//        try {
//            return ResponseEntity.ok(partyService.getAvailableCocktails(id));
//        } catch (ItemIdNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @GetMapping("/orders")
//    @CrossOrigin(origins = "http://localhost:8081")
//    public ResponseEntity getOrders(@RequestParam Long id) {
//        try {
//            return ResponseEntity.ok(partyService.getOrders(id));
//        } catch (ItemIdNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @GetMapping("/grouped")
//    @CrossOrigin(origins = "http://localhost:8081")
//    public ResponseEntity getGrouped(@RequestParam Long id) {
//        try {
//            return ResponseEntity.ok(partyService.getGroupedOrders(id));
//        } catch (ItemIdNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @PostMapping("/order")
//    @CrossOrigin(origins = "http://localhost:8081")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity addOrder(@RequestBody OrderModel newOrder) {
//        try {
//            partyService.addOrder(newOrder);
//            return ResponseEntity.ok("order successfully added");
//        } catch (ItemIdNotFoundException | ItemNotAddedException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
//
//    @GetMapping("/needed")
//    @CrossOrigin(origins = "http://localhost:8081")
//    public ResponseEntity getAvailable(@RequestParam Long p_id, @RequestParam Long c_id) {
//        try {
//            return ResponseEntity.ok(partyService.getNeededIngredients(p_id, c_id));
//        } catch (ItemIdNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }
}

