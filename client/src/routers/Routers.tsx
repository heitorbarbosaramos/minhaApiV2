import { createBrowserRouter } from "react-router-dom";
import App from "../App";
import Home from "../pages/home/Home";
import Secundaria from "../pages/secundaria/Secundaria";
import Tercearia from "../pages/tercearia/Tercearia";

const routers = createBrowserRouter([
    {
        path: "/",
        element: <App />,
        children: [
            {
                path: "home",
                element: <Home />
            },
            {
                path: "secundaria",
                element: <Secundaria />
            },
            {
                path: "tercearia",
                element: <Tercearia />
            }
        ]
    }
])
export default routers