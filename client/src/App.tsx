import { Outlet } from "react-router-dom"
import Menu from "./components/menu/Menu"

function App() {
  return (
    <>
      <p>Olá bem vindo ao projeto</p>
      <p>menu
        <Menu />
      </p>
      <Outlet />
      <p>footer</p>
    </>
  )
}

export default App
