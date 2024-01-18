package com.alsab.boozycalc.cocktail.service.data;

import com.alsab.boozycalc.cocktail.dto.RecipeDto;
import com.alsab.boozycalc.cocktail.entity.RecipeEntity;
import com.alsab.boozycalc.cocktail.mapper.RecipeMapper;
import com.alsab.boozycalc.cocktail.repository.RecipeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RecipeDataService {
    private final RecipeRepo recipeRepo;
    private final RecipeMapper recipeMapper;

    public Mono<RecipeDto> add(RecipeDto dto) {
        return Mono.just(recipeMapper.recipeToDto(
                recipeRepo.save(recipeMapper.dtoToRecipe(dto))
        ));
    }

    public Mono<RecipeDto> edit(RecipeDto dto) {
        RecipeEntity recipe = recipeMapper.dtoToRecipe(dto);
        recipeRepo.findById(recipe.getId());
        return Mono.just(recipeMapper.recipeToDto(
                recipeRepo.save(recipe)
        ));
    }

    public Mono<RecipeDto> delete(RecipeDto dto) {
        RecipeEntity recipe = recipeMapper.dtoToRecipe(dto);
        recipeRepo.findById(recipe.getId());
        recipeRepo.delete(
                recipeMapper.dtoToRecipe(dto)
        );
        return Mono.just(dto);
    }

    public Flux<RecipeDto> findAll() {
        return Flux.fromStream(recipeRepo.findAll().stream().map(recipeMapper::recipeToDto));
    }


    public Mono<?> findAllByCocktail(Long id) {
        return Mono.just(recipeRepo.findAllByCocktail(id).stream().map(recipeMapper::recipeToDto));
    }

    public Iterable<RecipeDto> findAllWithPagination(Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return recipeRepo.findAllWithPagination(pageable).stream().map(recipeMapper::recipeToDto).toList();
    }

    public Flux<RecipeDto> findAllWithPageAndSize(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return Flux.fromStream(recipeRepo.findAllWithPagination(pageable).stream().map(recipeMapper::recipeToDto));
    }
}
