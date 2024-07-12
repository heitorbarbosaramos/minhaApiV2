package com.heitor.minhaApi.service;

import com.heitor.minhaApi.entity.IdentytiProviderSSO;
import com.heitor.minhaApi.repostirory.IdentytiProviderSSORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public IdentytiProviderSSO findByAliasProvider(String aliasProvider){
        return repository.findByAlias(aliasProvider).orElseThrow(()-> new NoSuchElementException("Provider não localizado"));
    }

    public void deleteProvider(String aliasProvider) {
        IdentytiProviderSSO provider = findByAliasProvider(aliasProvider);
        repository.delete(provider);
    }
}
