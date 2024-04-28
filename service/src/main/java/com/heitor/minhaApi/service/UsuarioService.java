package com.heitor.minhaApi.service;

import com.heitor.minhaApi.entity.Usuario;
import com.heitor.minhaApi.repostirory.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final EnderecoService enderecoService;

    public Usuario save(Usuario usuario){

        if(usuario.getEndereco() != null){
            usuario.setEndereco(enderecoService.save(usuario.getEndereco()));
        }
        return repository.save(usuario);
    }
}
