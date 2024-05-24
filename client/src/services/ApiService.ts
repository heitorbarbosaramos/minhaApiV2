/* eslint-disable prefer-const */
import  Axios  from "axios";

let baseURL= "http://localhost:8080/rest";

const ApiService = Axios.create({
    withCredentials:true,
    baseURL
})

export default ApiService