import { Link } from "react-router-dom"

const NavBar = () => {

    return(
        <nav>
            <Link to="/home">Home</Link> 
            <Link to="/secundaria">Secundario</Link> 
        </nav>
    )
}
export default  NavBar