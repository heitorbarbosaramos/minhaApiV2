package com.heitor.minhaApi.security.feignClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentytiProvider {

    private boolean addReadTokenRoleOnCreate;
    private String alias;
    private boolean authenticateByDefault;
    private IdentytiProviderCreateConfig config;
    private boolean enabled;
    private String firstBrokerLoginFlowAlias;
    private String internalId;
    private boolean linkOnly;
    private String providerId;
    private boolean storeToken;
    private boolean trustEmail;
    private String updateProfileFirstLoginMode;

}
