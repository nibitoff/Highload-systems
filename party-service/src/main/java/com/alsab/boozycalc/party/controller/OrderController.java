package com.alsab.boozycalc.party.controller;

import com.alsab.boozycalc.party.dto.OrderDto;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.exception.NoCocktailInMenuException;
import com.alsab.boozycalc.party.exception.NoIngredientsForCocktailException;
import com.alsab.boozycalc.party.service.OrderService;
import com.alsab.boozycalc.party.service.data.OrderDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order controller", description = "Controller to manage orders")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderDataService orderDataService;
    private final OrderService orderService;
    @GetMapping("/allInOne")
    @Operation(description = "Get all orders list",
            summary = "Get all orders",
            tags = "orders")
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
    @Operation(description = "Delete existing order",
            summary = "Delete order",
            tags = "orders")
    public ResponseEntity<?> deleteById(@RequestParam Long id) {
        try {
            orderDataService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    @Operation(description = "Add new order",
            summary = "Add new order",
            tags = "orders")
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderDto order) {
        try {
            orderDataService.add(order);
            return ResponseEntity.ok("order successfully added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    @Operation(description = "Edit existing order",
            summary = "Edit order",
            tags = "orders")
    public ResponseEntity<?> editOrder(@Valid @RequestBody OrderDto order) {
        try {
            orderDataService.edit(order);
            return ResponseEntity.ok("party successfully added");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body("ERROR " + e.getMessage());
        }
    }

    @PostMapping("/create")
    @Operation(description = "Create new user's order in party",
            summary = "Create order",
            tags = "orders")
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
