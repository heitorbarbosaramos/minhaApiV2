package com.heitor.minhaApi.repostirory;

import com.heitor.minhaApi.entity.Usuario;
import com.heitor.minhaApi.enums.UsuarioStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByTimestampRecuperaSenhaAndCodigoConfirmacaoAndStatus(Long timeStamp, String codigoConfirmacao, UsuarioStatus status);

    Usuario findByEmail(String email);
}
