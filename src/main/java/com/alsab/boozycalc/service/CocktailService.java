package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.repository.CocktailRepo;
import com.alsab.boozycalc.service.data.CocktailDataService;
import com.alsab.boozycalc.service.data.CocktailTypeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class CocktailService {
    private final CocktailDataService cocktailDataService;
    private final CocktailTypeDataService cocktailTypeDataService;

    public CocktailDto add(CocktailDto dto){
        cocktailTypeDataService.findById(dto.getType().getId());
        return cocktailDataService.add(dto);
    }

    public CocktailDto edit(CocktailDto dto){
        cocktailTypeDataService.findById(dto.getType().getId());
        return cocktailDataService.edit(dto);
    }

}
