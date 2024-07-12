package com.heitor.minhaApi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TB_IDENTYTI_PROVIDER_SSO", schema = "minhaapi_service")
public class IdentytiProviderSSO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @NotEmpty(message = "Campo Obrigatório")
    @Column(name = "ALIAS", unique = true)
    private String alias;
    @NotEmpty(message = "Campo Obrigatório")
    @Column(name = "PROVIDER_ID", unique = true)
    private String providerId;
}
