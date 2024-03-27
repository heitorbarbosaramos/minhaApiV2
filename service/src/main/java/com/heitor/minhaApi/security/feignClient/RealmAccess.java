package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RealmAccess {

    private List<String> roles;
}
