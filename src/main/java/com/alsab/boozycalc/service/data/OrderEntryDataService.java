package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.OrderEntryDto;
import com.alsab.boozycalc.entity.OrderEntryEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.OrderEntryMapper;
import com.alsab.boozycalc.repository.OrderEntryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderEntryDataService {
    private final OrderEntryRepo entryRepo;
    private final OrderEntryMapper mapper;

    public List<OrderEntryDto> findAll(){
        return entryRepo.findAll().stream().map(mapper::orderEntryToDto).toList();
    }

    public void delete(OrderEntryDto dto){
        OrderEntryEntity entry = mapper.dtoToOrderEntry(dto);
        entryRepo.findById(entry.getId());
        entryRepo.delete(mapper.dtoToOrderEntry(dto));
    }

    public OrderEntryDto add(OrderEntryDto entryDto){
        return mapper.orderEntryToDto(entryRepo.save(mapper.dtoToOrderEntry(entryDto)));
    }

    public OrderEntryDto edit(OrderEntryDto dto){
        OrderEntryEntity entry = mapper.dtoToOrderEntry(dto);
        entryRepo.findById(entry.getId());
        return mapper.orderEntryToDto(entryRepo.save(entry));
    }

    public OrderEntryDto findByCocktail(Long id){
        return mapper.orderEntryToDto(
                entryRepo.findByCocktail(id).orElseThrow(() -> new ItemNotFoundException(OrderEntryDto.class, id))
        );
    }

    public OrderEntryDto findByOrder(Long id){
        return mapper.orderEntryToDto(
                entryRepo.findByOrder(id).orElseThrow(() -> new ItemNotFoundException(OrderEntryDto.class, id))
        );
    }

    public OrderEntryDto findById(OrderEntryDto dto){
        OrderEntryEntity entity = mapper.dtoToOrderEntry(dto);
        return mapper.orderEntryToDto(
                entryRepo.findById(entity.getId()).orElseThrow(() -> new ItemNotFoundException(OrderEntryDto.class, dto.getCocktail().getId()))
        );
    }

    public Boolean existsById(OrderEntryDto dto){
        OrderEntryEntity entity = mapper.dtoToOrderEntry(dto);
        return entryRepo.existsById(entity.getId());
    }
}
