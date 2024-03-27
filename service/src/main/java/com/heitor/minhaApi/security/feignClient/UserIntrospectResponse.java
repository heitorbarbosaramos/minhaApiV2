package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIntrospectResponse {

    private long exp;
    private long iat;
    private long auth_time;
    private String jti;
    private String iss;
//    private String aud;
    private String sub;
    private String typ;
    private String azp;
    private String session_state;
    private String name;
    private String given_name;
    private String family_name;
    private String preferred_username;
    private String email;
    private boolean email_verified;
    private String acr;
    private RealmAccess realm_access;
    private ResourceAccess resource_access;
    private String scope;
    private String sid;
    private String client_id;
    private String username;
    private boolean active;


}
