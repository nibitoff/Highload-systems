package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.IngredientTypeDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.IngredientTypeMapper;
import com.alsab.boozycalc.repository.IngredientTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientTypeDataService {
    private final IngredientTypeMapper mapper;
    private final IngredientTypeRepo repo;

    public IngredientTypeDto add(IngredientTypeDto typeDto){
        return mapper.ingredientTypeToDto(repo.save(mapper.dtoToIngredientType(typeDto)));
    }

    public IngredientTypeDto findById(Long id){
        return mapper.ingredientTypeToDto(
                repo.findById(id).orElseThrow((() -> new ItemNotFoundException(IngredientTypeDto.class, id))
        ));
    }
}
