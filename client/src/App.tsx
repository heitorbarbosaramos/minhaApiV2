import { Outlet } from "react-router-dom"
import Menu from "./components/menu/Menu"
import Header from "./components/header/Header"
import Footer from "./components/footer/Footer"

function App() {
  return (
    <>
      <p><Header /></p>
      <p>
        <Menu />
      </p>
      <Outlet />
      <p><Footer /></p>
    </>
  )
}

export default App
