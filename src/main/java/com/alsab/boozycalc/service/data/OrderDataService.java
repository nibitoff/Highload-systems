package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.MenuDto;
import com.alsab.boozycalc.dto.OrderDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.MenuMapper;
import com.alsab.boozycalc.mapper.OrderMapper;
import com.alsab.boozycalc.repository.MenuRepo;
import com.alsab.boozycalc.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDataService {
    private final OrderRepo orderRepo;
    private final OrderMapper mapper;

    public OrderDto add(OrderDto orderDto){
        return mapper.orderToDto((orderRepo.save(mapper.dtoToOrder(orderDto))));
    }

    public OrderDto findById(Long id){
        return mapper.orderToDto(orderRepo.findById(id).orElseThrow((() -> new ItemNotFoundException(OrderDto.class, id))
        ));
    }
}
