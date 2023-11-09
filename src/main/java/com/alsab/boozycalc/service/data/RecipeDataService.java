package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.RecipeDto;
import com.alsab.boozycalc.entity.RecipeEntity;
import com.alsab.boozycalc.mapper.RecipeMapper;
import com.alsab.boozycalc.repository.RecipeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RecipeDataService {
    private final RecipeRepo recipeRepo;
    private final RecipeMapper recipeMapper;

    public RecipeDto add(RecipeDto dto) {
        return recipeMapper.recipeToDto(
                recipeRepo.save(recipeMapper.dtoToRecipe(dto))
        );
    }

    public RecipeDto edit(RecipeDto dto) {
        RecipeEntity recipe = recipeMapper.dtoToRecipe(dto);
        recipeRepo.findById(recipe.getId());
        return recipeMapper.recipeToDto(
                recipeRepo.save(recipe)
        );
    }

    public void delete(RecipeDto dto) {
        RecipeEntity recipe = recipeMapper.dtoToRecipe(dto);
        recipeRepo.findById(recipe.getId());
        recipeRepo.delete(
                recipeMapper.dtoToRecipe(dto)
        );
    }

    public List<RecipeDto> findAll() {
        return recipeRepo.findAll().stream().map(recipeMapper::recipeToDto).toList();
    }


    public List<RecipeDto> findAllByCocktail(Long id) {
        return recipeRepo.findAllByCocktail(id).stream().map(recipeMapper::recipeToDto).toList();
    }
}
