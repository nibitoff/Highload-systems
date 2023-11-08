package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.entity.IngredientTypeEntity;
import com.alsab.boozycalc.exception.ItemNameIsAlreadyTakenException;
import com.alsab.boozycalc.exception.ItemNotFoundByNameException;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.IngredientMapper;
import com.alsab.boozycalc.repository.IngredientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public IngredientDto findByName(String name) {
        return mapper.ingredientToDto(
                ingredientRepo.findByName(name).orElseThrow(() -> new ItemNotFoundByNameException(IngredientDto.class, name))
        );
    }

    public Boolean existsByName(String name){
        try {
            findByName(name);
            return true;
        } catch (ItemNotFoundByNameException e) {
            return false;
        }
    }

    public IngredientDto add(IngredientDto dto) {
        if(existsByName(dto.getName())){
            throw new ItemNameIsAlreadyTakenException(IngredientDto.class, dto.getName());
        }
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

        if(existsByName(ingredient.getName())){
            IngredientDto other = findByName(ingredient.getName());
            if(!Objects.equals(other.getId(), ingredient.getId()))
                throw new ItemNameIsAlreadyTakenException(CocktailDto.class, ingredient.getName());
        }

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
