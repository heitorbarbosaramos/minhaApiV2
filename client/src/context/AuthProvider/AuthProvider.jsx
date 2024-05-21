import { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { UsuarioLoginDTO } from '../../models/UsuarioLoginDTO'
import ApiService from '../../services/ApiService.jsx'
import ToastSystem from '../../components/Toast/ToastSystem.jsx'
import Cookies from "js-cookie";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const navigate = useNavigate();

    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [recoveryPassword, setRecoveryPassword] = useState(false);
    const [showToastError, setShowToastError] = useState(false);
    const [mensagemToast, setMensagemToast] = useState("");

    const [usuarioLoginDTO] = useState(new UsuarioLoginDTO())

    let logado = Cookies.get("jwt-token")  !==  undefined ? true : false;
  
    useEffect(() => {
        const recoveredUser = Cookies.get("jwt-token");
        if(recoveredUser) {
            setUser(recoveredUser)
        }
        setLoading(false);
    }, []);

    const login = (login, senha) => {
  
        usuarioLoginDTO.setUsername(login);
        usuarioLoginDTO.setPassword(senha);

        ApiService.post("/user/login", usuarioLoginDTO).then((response) => {

            const LoggedUser = {
                login,
                token: response.data.tokenJWT,
            }

            setUser(LoggedUser);
            setRecoveryPassword(false);
            setShowToastError(false);
            navigate("/");
        }).catch((error) => {
            console.error(error);
            logado = false;
            setRecoveryPassword(true);
            setShowToastError(true);
            setMensagemToast(error.response.data.mensagem);
        })

    }

    const logout = () => {
        
        ApiService.get("/user/logout").catch(error => {console.error(error)});
        setUser(null);
        logado = false;
        navigate("/login")
    }

    return(
        <>
            <ToastSystem 
                message={mensagemToast} 
                severity="error" 
                summary="Error" 
                life={3000} 
                position="top-right" 
                show={showToastError} 
                setShow={setShowToastError} 
            />
            <AuthContext.Provider value={{ authenticate: logado, login, user, loading, logout, recoveryPassword, mensagemToast}}>
                { children }
            </AuthContext.Provider>
        </>
    )
}