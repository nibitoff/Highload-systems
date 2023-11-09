package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.InviteDto;
import com.alsab.boozycalc.dto.MenuDto;
import com.alsab.boozycalc.dto.OrderDto;
import com.alsab.boozycalc.entity.InviteEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.InviteMapper;
import com.alsab.boozycalc.mapper.OrderMapper;
import com.alsab.boozycalc.repository.InviteRepo;
import com.alsab.boozycalc.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteDataService {
    private final InviteRepo inviteRepo;
    private final InviteMapper mapper;

    public List<InviteDto> findAll(){
        return inviteRepo.findAll().stream().map(mapper::inviteToDto).toList();
    }

    public void delete(InviteDto dto){
        InviteEntity invite = mapper.dtoToInvite(dto);
        inviteRepo.findById(invite.getId());
        inviteRepo.delete(mapper.dtoToInvite(dto));
    }

    public InviteDto add(InviteDto inviteDto){
        return mapper.inviteToDto((inviteRepo.save(mapper.dtoToInvite(inviteDto))));
    }

    public InviteDto edit(InviteDto dto){
        InviteEntity invite = mapper.dtoToInvite(dto);
        inviteRepo.findById(invite.getId());
        return mapper.inviteToDto(inviteRepo.save(invite));
    }

    public InviteDto findByUser(Long id){
        return mapper.inviteToDto(
                inviteRepo.findByUser(id).orElseThrow(() -> new ItemNotFoundException(InviteDto.class, id))
        );
    }

    public InviteDto findByParty(Long id){
        return mapper.inviteToDto(
                inviteRepo.findByParty(id).orElseThrow(() -> new ItemNotFoundException(InviteDto.class, id))
        );
    }
}
