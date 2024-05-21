import { useContext } from "react"
import { AuthContext } from "../../context/AuthProvider/AuthProvider"
import { Button } from 'primereact/button'

const Logout = () => {

    const { logout } = useContext(AuthContext)

    const handleLogout = () => {
        logout()
    }; 
    
    return(<Button label='Logout' className='btn btn-primary' onClick={() => { handleLogout() }} />)
}
export default Logout