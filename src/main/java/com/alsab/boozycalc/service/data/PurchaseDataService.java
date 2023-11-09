package com.alsab.boozycalc.service.data;

import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.dto.PurchaseDto;
import com.alsab.boozycalc.entity.PurchaseEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.mapper.PurchaseMapper;
import com.alsab.boozycalc.repository.PurchaseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<PurchaseDto> findAllByProduct(Long id){
        return repo.findByProduct(id).stream().map(mapper::purchaseToDto).toList();
    }

    public List<PurchaseDto> findAllByParty(Long id){
            return repo.findByParty(id).stream().map(mapper::purchaseToDto).toList();
    }

    public Iterable<PurchaseDto> findAllWithPagination(Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return repo.findAllWithPagination(pageable).stream().map(mapper::purchaseToDto).toList();
    }
}

