import { Outlet } from "react-router-dom";
import NavBar from "./components/navBar/NavBar";


function App() {

  return (
    <>
      <NavBar />
      <p>OutLet</p>
      <Outlet />
      <p>Footer</p>
    </>
  )
}

export default App
