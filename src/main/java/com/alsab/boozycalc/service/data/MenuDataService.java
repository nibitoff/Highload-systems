package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.MenuDto;
import com.alsab.boozycalc.dto.OrderDto;
import com.alsab.boozycalc.entity.MenuEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.MenuMapper;
import com.alsab.boozycalc.repository.MenuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuDataService {
    private final MenuRepo menuRepo;
    private final MenuMapper mapper;

    public List<MenuDto> findAll(){
        return menuRepo.findAll().stream().map(mapper::menuToDto).toList();
    }

    public void delete(MenuDto dto){
        MenuEntity menu = mapper.dtoToMenu(dto);
        menuRepo.findById(menu.getId());
        menuRepo.delete(mapper.dtoToMenu(dto));
    }

    public MenuDto add(MenuDto menuDto){
        return mapper.menuToDto(menuRepo.save(mapper.dtoToMenu(menuDto)));
    }

    public MenuDto edit(MenuDto dto){
        MenuEntity menu = mapper.dtoToMenu(dto);
        menuRepo.findById(menu.getId());
        return mapper.menuToDto(menuRepo.save(menu));
    }

    public List<MenuDto> findAllByCocktail(Long id){
        return menuRepo.findAllByCocktail(id).stream().map(mapper::menuToDto).toList();
    }

    public List<MenuDto> findByParty(Long id){
        return menuRepo.findAllByParty(id).stream().map(mapper::menuToDto).toList();
    }
}
