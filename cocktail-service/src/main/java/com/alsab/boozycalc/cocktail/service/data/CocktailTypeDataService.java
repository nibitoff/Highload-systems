package com.alsab.boozycalc.cocktail.service.data;

import com.alsab.boozycalc.cocktail.mapper.CocktailTypeMapper;
import com.alsab.boozycalc.cocktail.repository.CocktailTypeRepo;
import com.alsab.boozycalc.cocktail.dto.CocktailTypeDto;
import com.alsab.boozycalc.cocktail.dto.IngredientTypeDto;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CocktailTypeDataService {
    private final CocktailTypeMapper mapper;
    private final CocktailTypeRepo repo;

    public CocktailTypeDto add(CocktailTypeDto typeDto){
        return mapper.cocktailTypeToDto(repo.save(mapper.dtoToCocktailType(typeDto)));
    }

    public CocktailTypeDto findById(Long id){
        return mapper.cocktailTypeToDto(
                repo.findById(id).orElseThrow((() -> new ItemNotFoundException(IngredientTypeDto.class, id))
                ));
    }

    public boolean existsById(Long id){
        return repo.existsById(id);
    }
}