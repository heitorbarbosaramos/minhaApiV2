import "primereact/resources/themes/lara-light-cyan/theme.css";
import 'primereact/resources/themes/lara-light-indigo/theme.css'; //theme
import 'primereact/resources/primereact.min.css'; //core css
import { BrowserRouter } from "react-router-dom";
import Private from './context/Private/Private'
import './App.css'


function App() {
 

  return (
    <div>
      <BrowserRouter>
        <Private />
      </BrowserRouter>
    </div>
  )
}

export default App
