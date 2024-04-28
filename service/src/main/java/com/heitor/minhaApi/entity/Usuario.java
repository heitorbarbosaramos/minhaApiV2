package com.heitor.minhaApi.entity;

import com.heitor.minhaApi.enums.UsuarioStatus;
import com.heitor.minhaApi.security.feignClient.UserRepresentarioKeyCloak;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "TB_USUARIO", schema = "minhaapi_service")
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_USUARIO_KEYCLOAK")
    private UUID idUsuarioKeycloak;

    @Column(name = "TELEFONE", unique = true)
    private String telefone;

    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "RECUPERA_SENHA")
    private Long timestampRecuperaSenha;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private UsuarioStatus status;

    @Column(name = "OBSERVACOES")
    private StringBuilder observacoes;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    @Transient
    private UserRepresentarioKeyCloak userKeycloak;

}
