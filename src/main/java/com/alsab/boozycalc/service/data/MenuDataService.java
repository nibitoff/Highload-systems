package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.MenuDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.MenuMapper;
import com.alsab.boozycalc.repository.MenuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuDataService {
    private final MenuRepo menuRepo;
    private final MenuMapper mapper;

    public MenuDto add(MenuDto menuDto){
        return mapper.menuToDto((menuRepo.save(mapper.dtoToMenu(menuDto))));
    }

    public MenuDto findById(Long id){
        return mapper.menuToDto(menuRepo.findById(id).orElseThrow((() -> new ItemNotFoundException(MenuDto.class, id))
        ));
   }
}
