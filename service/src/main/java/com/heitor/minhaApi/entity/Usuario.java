package com.heitor.minhaApi.entity;

import com.heitor.minhaApi.enums.UsuarioStatus;
import com.heitor.minhaApi.security.feignClient.UserRepresentarioKeyCloak;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Campo Obrigatório")
    @Column(name = "NOME")
    private String nome;

    @NotEmpty(message = "Campo Obrigatório")
    @Column(name = "SOBRE_NOME")
    private String sobreNome;

    @Column(name = "TELEFONE", unique = true)
    private String telefone;

    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "RECUPERA_SENHA")
    private Long timestampRecuperaSenha;

    @Column(name = "CODIGO_CONFIRMACAO")
    private String codigoConfirmacao;

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
