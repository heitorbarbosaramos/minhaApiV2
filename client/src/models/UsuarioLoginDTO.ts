export class UsuarioLoginDTO {
    username: string
    password: string

    constructor(username: string, password: string) {
        this.username = username;
        this.password = password;
    }

    setUsername(username: string){
        this.username = username
    }

    setPassword(password: string){
        this.password = password
    }
}