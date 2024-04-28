package com.heitor.minhaApi.service;

import com.heitor.minhaApi.entity.Endereco;
import com.heitor.minhaApi.feign.EnderecoCliente;
import com.heitor.minhaApi.repostirory.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository repository;
    private final EnderecoCliente cliente;

    public Endereco findByCep(String cep){
        return cliente.getEndereco(cep);
    }

    public Endereco save(Endereco endereco){
        return repository.save(endereco);
    }
}
