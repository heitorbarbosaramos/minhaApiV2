import axios from "axios";
import Cookies from 'js-cookie';
import { useEffect } from "react";

let baseURL= "http://localhost:8080/rest";


const ApiService = axios.create({
  withCredentials: true,
  baseURL
});

ApiService.interceptors.request.use(
  (config) => {
    const token = Cookies.get("jwt-token")
    if (token && config.headers) {
      config.headers.authorization = `Bearer ${token}`;
    }
    console.log(config)
    return config;
  },
  (erro) => {
    Promise.reject(erro)
  },
)

ApiService.interceptors.response.use(
  (response) => {
    console.log("response")
     
    const JWT_TOKEN = Cookies.get("jwt-token");

    if (JWT_TOKEN) {
      response.headers.Authorization = `${JWT_TOKEN}`
    }
    return response;
  },
  (error) => {
    console.error(error)
    if (error.response && error.response.status === 401) {
      console.error("Erro 401: Não autorizado");
      let token_refresh = Cookies.get("jwt-token-refresh")
      useEffect(() => {
         ApiService.post(`/user/refreshToken?refresh_token=${token_refresh}`)
         console.log("FFFFF")
      },[token_refresh])
      token_refresh = undefined
      console.log(token_refresh)
    }
    return Promise.reject(error);
  }
)

export default ApiService;