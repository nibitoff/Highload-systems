package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.PurchaseDto;
import com.alsab.boozycalc.entity.PurchaseEntity;
import com.alsab.boozycalc.mapper.PurchaseMapper;
import com.alsab.boozycalc.repository.PurchaseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseDataService {
    private final PurchaseRepo repo;
    private final PurchaseMapper mapper;

    public PurchaseDto add(PurchaseDto dto){
        return mapper.purchaseToDto(repo.save(mapper.dtoToPurchase(dto)));
    }

    public PurchaseDto edit(PurchaseDto dto) {
        PurchaseEntity purchase = mapper.dtoToPurchase(dto);
        repo.findById(purchase.getId());
        return mapper.purchaseToDto(
                repo.save(purchase)
        );
    }

    public void delete(PurchaseDto dto) {
        PurchaseEntity purchase = mapper.dtoToPurchase(dto);
        repo.findById(purchase.getId());
        repo.delete(
                mapper.dtoToPurchase(dto)
        );
    }

    public List<PurchaseDto> findAll() {
        return repo.findAll().stream().map(mapper::purchaseToDto).toList();
    }
}
