package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.CocktailTypeDto;
import com.alsab.boozycalc.dto.IngredientTypeDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.CocktailTypeMapper;
import com.alsab.boozycalc.mapper.IngredientTypeMapper;
import com.alsab.boozycalc.repository.CocktailTypeRepo;
import com.alsab.boozycalc.repository.IngredientTypeRepo;
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
}