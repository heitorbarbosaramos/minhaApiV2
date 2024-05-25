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
            },
            {
                path: "pg4",
                element: <span>PG - 4</span>
            }
            ,
            {
                path: "pg5",
                element: <span>PG - 5</span>
            }
            ,
            {
                path: "pg6",
                element: <span>PG - 6</span>
            }
            ,
            {
                path: "pg7",
                element: <span>PG - 7</span>
            }
            ,
            {
                path: "pg8",
                element: <span>PG - 8</span>
            }
        ]
    }
])
export default routers