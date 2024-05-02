package com.heitor.minhaApi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrosCampos {

    private String campo;
    private String erro;
}
