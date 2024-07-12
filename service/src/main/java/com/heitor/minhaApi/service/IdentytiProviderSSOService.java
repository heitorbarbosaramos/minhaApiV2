package com.heitor.minhaApi.service;

import com.heitor.minhaApi.entity.IdentytiProviderSSO;
import com.heitor.minhaApi.repostirory.IdentytiProviderSSORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IdentytiProviderSSOService {

    private final IdentytiProviderSSORepository repository;

    public IdentytiProviderSSO save(IdentytiProviderSSO obj){
        return repository.save(obj);
    }

    public List<IdentytiProviderSSO> findAll() {
        return repository.findAll();
    }
}
