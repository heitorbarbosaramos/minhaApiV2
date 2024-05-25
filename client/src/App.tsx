import { Outlet } from "react-router-dom"

function App() {
  return (
    <>
      <p>Olá bem vindo ao projeto</p>
      <p>menu</p>
      <Outlet />
      <p>footer</p>
    </>
  )
}

export default App
