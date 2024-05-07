import axios from "axios";
import Cookies from 'js-cookie';

let baseURL= "http://localhost:8080/rest";


const ApiService = axios.create({
  baseURL
});

ApiService.interceptors.request.use(
  (config) => {
    const token = Cookies.get("jwt-token")
    if (token && config.headers) {
      config.headers.authorization = `Bearer ${token}`;
    }

    return config;
  },
  (erro) => {
    Promise.reject(erro)
  },
)

ApiService.interceptors.response.use(
  (response) => {
     
    const JWT_TOKEN = Cookies.get("jwt-token");

    if (JWT_TOKEN) {
      response.headers.Authorization = `${JWT_TOKEN}`
    }

    return response;
  },
)

export default ApiService;