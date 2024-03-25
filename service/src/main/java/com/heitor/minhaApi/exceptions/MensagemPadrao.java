package com.heitor.minhaApi.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MensagemPadrao {

    private Integer idStatus;
    private String causa;
    private String mensagem;
    private String path;
    private LocalDateTime data;
}
