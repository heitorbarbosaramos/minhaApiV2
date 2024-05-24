import { createBrowserRouter } from "react-router-dom";
import Home from "../pages/home/Home"
import Secundaria from "../pages/secundaria/Secundaria"
import App from "../App";
import Error404 from "../components/errors/Error404";


const router = createBrowserRouter([
    {
        path: "/", 
        element: <App />,
        errorElement: <Error404 />,
        children: [
            {
                path: "home", 
                element: <Home />,
            },
            {
                path: "secundaria", 
                element: <Secundaria />,
            }
        ]
    }
   
])
export default router