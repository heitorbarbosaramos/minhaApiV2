import { Outlet } from "react-router-dom"
import Menu from "./components/menu/Menu"
import Header from "./components/header/Header"
import Footer from "./components/footer/Footer"
import { MenuProvider } from "./context/menu/MenuContext.jsx"
import "./App.css"

function App() {
  return (
    <>
      <MenuProvider>
        <Header />
        <div className="bodyPrimary">
          <Menu />
          <div className="bodyRenderiza">
            <Outlet />
          </div>
          <Footer />
        </div>
      </MenuProvider>
    </>
  )
}

export default App
