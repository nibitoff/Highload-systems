package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.entity.IngredientTypeEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.IngredientMapper;
import com.alsab.boozycalc.repository.IngredientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientDataService {
    private final IngredientRepo ingredientRepo;
    private final IngredientMapper mapper;

    public Iterable<IngredientDto> findAll() {
        return ingredientRepo.findAll().stream().map(mapper::ingredientToDto).toList();
    }

    public IngredientDto findById(Long id) {
        return mapper.ingredientToDto(
                ingredientRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(IngredientDto.class, id))
        );
    }

    public IngredientDto add(IngredientDto dto) {
        return mapper.ingredientToDto(
                ingredientRepo.save(
                        mapper.dtoToIngredient(dto)
                )
        );
    }

    public IngredientDto edit(IngredientDto ingredient) throws ItemNotFoundException {
        IngredientDto ingr = mapper.ingredientToDto(
                ingredientRepo.findById(ingredient.getId()).orElseThrow(() -> new ItemNotFoundException(IngredientDto.class, ingredient.getId()))
        );
        ingr.setName(ingredient.getName());
        ingr.setDescription(ingredient.getDescription());
        ingr.setType(ingredient.getType());
        return mapper.ingredientToDto(
                ingredientRepo.save(mapper.dtoToIngredient(ingr))
        );
    }

    public void deleteById(Long id) {
        if (!ingredientRepo.existsById(id)) throw new ItemNotFoundException(IngredientDto.class, id);
        ingredientRepo.deleteById(id);
    }
}
