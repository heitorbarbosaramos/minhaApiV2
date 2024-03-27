package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountRoles {

    private List<String> roles;
}
