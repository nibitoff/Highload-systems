package com.alsab.boozycalc.party.controller;

import com.alsab.boozycalc.party.dto.OrderDto;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.exception.NoCocktailInMenuException;
import com.alsab.boozycalc.party.exception.NoIngredientsForCocktailException;
import com.alsab.boozycalc.party.service.OrderService;
import com.alsab.boozycalc.party.service.data.OrderDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderDataService orderDataService;
    private final OrderService orderService;
    @GetMapping("/allInOne")
    public ResponseEntity<?> getOrders() {
        try {
            return ResponseEntity.ok(orderDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllOrdersWithPagination(Integer page) {
//        try {
//            return ResponseEntity.ok(orderDataService.findAllWithPagination(page));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e);
//        }
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        try {
            orderDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDto order) {
        try {
            orderDataService.add(order);
            return ResponseEntity.ok("order successfully added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editOrder(@Valid @RequestBody OrderDto order) {
        try {
            orderDataService.edit(order);
            return ResponseEntity.ok("party successfully added");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
            @RequestParam Long partyId,
            @RequestParam Long userId,
            @RequestParam Long cocktailId) {
        try {
            return ResponseEntity.ok(orderService.createOrder(partyId, userId, cocktailId));
        } catch (ItemNotFoundException  e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (NoCocktailInMenuException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (NoIngredientsForCocktailException e) {
            return ResponseEntity.badRequest().body(e.getDescription());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
