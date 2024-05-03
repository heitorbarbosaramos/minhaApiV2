package com.heitor.minhaApi.security.feignClient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRepresentarioKeyCloak {

    private String self;
    private String id;
    private String origin;
    private Long createdTimestamp;
    private String username;
    private Boolean enabled;
    private Boolean totp;
    private Boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private String federationLink;
    private String serviceAccountClientId;
//    @JsonDeserialize(
//            using = StringListMapDeserializer.class
//    )
//    private Map<String, List<String>> attributes;
//    protected List<CredentialRepresentation> credentials;
    private Set<String> disableableCredentialTypes;
    private List<String> requiredActions;
//    protected List<FederatedIdentityRepresentation> federatedIdentities;
    private List<String> realmRoles = new ArrayList<>();
    private Map<String, List<String>> clientRoles;
//    protected List<UserConsentRepresentation> clientConsents;
    private Integer notBefore;
    /** @deprecated */
    @Deprecated
    private Map<String, List<String>> applicationRoles;
    /** @deprecated */
//    @Deprecated
//    protected List<SocialLinkRepresentation> socialLinks;
    private List<String> groups;
    private Map<String, Boolean> access;
}
