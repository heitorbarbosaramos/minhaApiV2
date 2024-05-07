import axios from "axios";

let baseURL;

switch (window.location.origin) {
    case "http://localhost:3000":
        baseURL = "http://localhost:8080/rest";
        break;
    case "http://algum_endereco_remoto":
        baseURL = "http://remoto:8080/rest";
        break;
    default :
        baseURL = "http://default:8080/rest";
        break;
}

const ApiService = axios.create({
    baseURL
});

export default ApiService;