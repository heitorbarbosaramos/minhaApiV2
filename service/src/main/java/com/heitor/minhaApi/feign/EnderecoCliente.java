package com.heitor.minhaApi.feign;

import com.heitor.minhaApi.entity.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "enderecoCliente", url = "http://viacep.com.br", path = "/ws")
public interface EnderecoCliente {

    @RequestMapping(method = RequestMethod.GET, value = "/{cep}/json")
    Endereco getEndereco(@PathVariable(value = "cep") String cep);
}
