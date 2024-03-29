package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.OrderDto;
import com.alsab.boozycalc.dto.PartyDto;
import com.alsab.boozycalc.dto.UserDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.OrderMapper;
import com.alsab.boozycalc.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDataService {
    private final OrderRepo orderRepo;
    private final OrderMapper mapper;

    public List<OrderDto> findAll() {
        return orderRepo.findAll().stream().map(mapper::orderToDto).toList();
    }

    public OrderDto findById(Long id) {
        return mapper.orderToDto(
                orderRepo.
                        findById(id).orElseThrow(() -> new ItemNotFoundException(OrderDto.class, id))
        );
    }

    public void deleteById(Long id) {
        orderRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(OrderDto.class, id));
    }

    public OrderDto add(OrderDto orderDto) {
        return mapper.orderToDto((orderRepo.save(mapper.dtoToOrder(orderDto))));
    }

    public OrderDto edit(OrderDto dto) {
        orderRepo.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException(OrderDto.class, dto.getId()));
        return mapper.orderToDto(orderRepo.save(mapper.dtoToOrder(dto)));
    }

    public OrderDto findByPartyAndUser(PartyDto party, UserDto user) {
        return mapper.orderToDto(
                orderRepo.findByPartyAndUser(party.getId(), user.getId())
                        .orElseThrow(() -> new ItemNotFoundException(OrderDto.class, party.getId()))
        );
    }

    public boolean existsByPartyAndUser(PartyDto party, UserDto user) {
        try {
            findByPartyAndUser(party, user);
            return true;
        } catch (ItemNotFoundException e){
            return false;
        }
    }

}
