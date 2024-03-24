#!/bin/bash

# Variáveis de ambiente
KEYCLOAK_HOST=localhost
KEYCLOAK_PORT=8090
KEYCLOAK_USERNAME=admin
KEYCLOAK_PASSWORD=admin
REALM_NAME=REALM_MINHAAPI

# Criação do Realm
echo "Criando realm $REALM_NAME..."
curl -X POST -H "Content-Type: application/json" \
     -d '{"realm": "'$REALM_NAME'", "enabled": true}' \
     http://$KEYCLOAK_HOST:$KEYCLOAK_PORT/auth/admin/realms \
     -u $KEYCLOAK_USERNAME:$KEYCLOAK_PASSWORD