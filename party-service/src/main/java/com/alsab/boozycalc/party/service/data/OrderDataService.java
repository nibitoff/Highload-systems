package com.alsab.boozycalc.party.service.data;

import com.alsab.boozycalc.party.dto.OrderDto;
import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.dto.PurchaseDto;
import com.alsab.boozycalc.party.dto.UserDto;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.mapper.OrderMapper;
import com.alsab.boozycalc.party.repository.OrderRepo;
import com.alsab.boozycalc.party.service.AuthFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDataService {
    private final OrderRepo orderRepo;
    private final OrderMapper mapper;
    private final AuthFeignService authFeignService;

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
        UserDto userDto = authFeignService.findById(user.getId());
        return mapper.orderToDto(
                orderRepo.findByPartyAndUser(party.getId(), userDto.getId())
                        .orElseThrow(() -> new ItemNotFoundException(OrderDto.class, party.getId()))
        );
    }

//    public Iterable<OrderDto> findAllWithPagination(Integer page){
//        Pageable pageable = PageRequest.of(page, 50);
//        return orderRepo.findAllWithPagination(pageable).stream().map(mapper::orderToDto).toList();
//    }


}
