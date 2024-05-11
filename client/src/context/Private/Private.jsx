import { useContext } from "react"
import { AuthContext, AuthProvider } from '../AuthProvider/AuthProvider'
import { Navigate, Outlet, Route, Routes } from "react-router-dom";
import Home from "../../pages/Home/Home";
import Login from "../../pages/Login/Login";
import PgTeste from "../../pages/PgTeste/PgTeste";
import Http404 from "../../components/http/Http404";

const Private = () => {

    const Security = ({ children }) => {

        const { authenticate, loading } = useContext(AuthContext)


        if (loading) {
            console.log("Carrengando PG");
            <div>Carregando...</div>
        }

        if (!authenticate) {
            return <Navigate to="/login" />
        }

        return children;
    }

    return (
        <AuthProvider>
            <main>
                <Routes>
                    <Route path="/" exact element={<Security><Home /></Security>} />
                    <Route path="pgteste" element={<Security><PgTeste /></Security>} />
                    <Route path="login" exact element={<Login />} />
                    <Route path="*" element={<Http404 />} />
                </Routes>
                <Outlet />
            </main>
        </AuthProvider>
    )
}
export default Private