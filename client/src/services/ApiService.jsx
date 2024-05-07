import axios from "axios";

let baseURL= "http://localhost:8080/rest";


const ApiService = axios.create({
  baseURL
});

export default ApiService;