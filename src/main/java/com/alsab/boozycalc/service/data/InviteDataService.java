package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.InviteDto;
import com.alsab.boozycalc.dto.OrderDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.InviteMapper;
import com.alsab.boozycalc.mapper.OrderMapper;
import com.alsab.boozycalc.repository.InviteRepo;
import com.alsab.boozycalc.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteDataService {
    private final InviteRepo inviteRepo;
    private final InviteMapper mapper;

    public InviteDto add(InviteDto inviteDto){
        return mapper.inviteToDto((inviteRepo.save(mapper.dtoToInvite(inviteDto))));
    }

    public InviteDto findById(Long id){
        return mapper.inviteToDto(inviteRepo.findById(id).orElseThrow((() -> new ItemNotFoundException(InviteDto.class, id))
        ));
    }
}
