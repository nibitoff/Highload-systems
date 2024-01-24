package com.alsab.boozycalc.cocktail.controller;

import com.alsab.boozycalc.cocktail.service.data.CocktailDataService;
import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.service.CocktailService;
import com.alsab.boozycalc.cocktail.service.data.CocktailTypeDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Cocktail controller", description = "Controller to manage cocktails")
@RestController
@RequestMapping("/api/v1/cocktails")
@RequiredArgsConstructor
public class CocktailController {
    private final CocktailService cocktailService;
    private final CocktailDataService cocktailDataService;
    private final CocktailTypeDataService cocktailTypeDataService;

    @GetMapping("/all")
    @Operation(description = "Get all cocktails list",
            summary = "Get all cocktails",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})

    public ResponseEntity<Flux<CocktailDto>> getAllCocktails() {
        try {
            return ResponseEntity.ok(cocktailDataService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/all/{page}")
    @Operation(description = "Get cocktails by page",
            summary = "Get cocktails by page",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Flux<CocktailDto>> getAllCocktailsWithPagination(@PathVariable Integer page) {
        try {
            return ResponseEntity.ok(cocktailDataService.findAllWithPagination(page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/page/{num}")
    @Operation(description = "Get cocktails by page and amount",
            summary = "Get cocktails by page and amount",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Flux<CocktailDto>> getAllCocktailsWithPageAndSize(@PathVariable Integer num, @RequestParam Integer size) {
        try {
            return ResponseEntity.ok(cocktailDataService.findAllWithPageAndSize(num, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Flux.error(e));
        }
    }

    @GetMapping("/find")
    @Operation(description = "Find cocktail by id",
            summary = "Find cocktail by id",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Mono<CocktailDto>> findById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(cocktailDataService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @GetMapping("/type")
    @Operation(description = "Get cocktail type by id",
            summary = "Find cocktail type by id",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<?> getTypeById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(cocktailTypeDataService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/type-exists")
    @Operation(description = "Check if cocktail type exists by id",
            summary = "Check if cocktail type exists",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<?> typeExistsById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(cocktailTypeDataService.existsById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @PostMapping("/add")
    @Operation(description = "Add new cocktail",
            summary = "Add new cocktail",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Mono<?>> addNewCocktail(@Valid @RequestBody CocktailDto cocktail) {
        try {
            return ResponseEntity.ok(cocktailService.add(cocktail));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }
    @PostMapping("/edit")
    @Operation(description = "Edit existing cocktail",
            summary = "Edit cocktail",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Mono<?>> editCocktail(@Valid @RequestBody CocktailDto cocktail) {
        try {
            return ResponseEntity.ok(cocktailService.edit(cocktail));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (ItemNameIsAlreadyTakenException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

    @DeleteMapping("/delete")
    @Operation(description = "Delete existing cocktail",
            summary = "Delete cocktail",
            tags = "cocktails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is good"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Can't find some entity"),
            @ApiResponse(responseCode = "403", description = "Not enough rights", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Mono<?>> deleteCocktail(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(cocktailDataService.deleteById(id));
        } catch (ItemNotFoundException e) {
            return ResponseEntity.badRequest().body(Mono.just(e.getDescription()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Mono.error(e));
        }
    }

}
