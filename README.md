# PROJETO - SISTEMA DE LOGIN UTILIZANDO KEYCLOAK
## Setup do ambiente de desenvolvimento

Este projeto utiliza:

### Containers
	* Postgres
	* Keycloak
	

### Tecnologias
	* Docker
	

### Execução

Acesse a pasta **docker/MINHA_API**, na raiz do repositório e execute o comando abaixo para iniciar os containers:

``` shell
docker-compose up -d
``` 

### Configurando KEYCLOAK

Após a execução do docker compose, o keycloak estará disponivel em http://localhost:8090/ e crie o realm REALM_MINHAAPI, para saber mais sobre criação de realm veja nesse [link](https://www.keycloak.org/docs/latest/server_admin/index.html#configuring-realms)