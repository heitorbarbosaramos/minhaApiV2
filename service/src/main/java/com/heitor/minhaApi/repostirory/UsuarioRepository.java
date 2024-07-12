package com.heitor.minhaApi.repostirory;

import com.heitor.minhaApi.entity.Usuario;
import com.heitor.minhaApi.enums.UsuarioStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByTimestampRecuperaSenhaAndCodigoConfirmacaoAndStatus(Long timeStamp, String codigoConfirmacao, UsuarioStatus status);

    Usuario findByEmail(String email);

    @Query(value =
            " SELECT " +
            "   tu " +
            " FROM " +
            "   Usuario tu " +
            " WHERE " +
            "   tu.idUsuarioKeycloak is null " +
            "   and timestampRecuperaSenha < :timeStamp")
    List<Usuario> findByUserTimoutCreat(@Param("timeStamp") Long timeStamp);

    Usuario findByIdUsuarioKeycloak(UUID uuid);
}
