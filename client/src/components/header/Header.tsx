/* eslint-disable @typescript-eslint/no-unused-vars */
import { useContext } from "react"
import { MenuContext } from "../../context/menu/MenuContext"
import { RiMenuFold2Fill, RiMenuUnfold2Fill } from "react-icons/ri"
import "./Header.css"
import { BiAbacus, BiCalendar, BiUser, BiUserPin } from "react-icons/bi"
import logo from "../../assets/vite.svg"

const Header = () => {

    const { ocultar, oculta } = useContext(MenuContext)

    function button() {
        if (oculta) {
            return <RiMenuUnfold2Fill className="hideBotton" />
        } else {
            return <RiMenuFold2Fill className="hideBotton" />
        }
    }

    return (
        <div className="header">
            <span className="logoHideBotton">
                <span className="logo" >
                    <img src={logo} title="Imagem Logotipo" />
                    Minha Instituiçao
                </span>
                <span onClick={() => { ocultar() }}>
                    {button()}
                </span>
            </span>
            <span>
                <BiCalendar className="headerBotton" />
                <BiAbacus className="headerBotton" />
                <BiUser className="headerBotton" />
            </span>
        </div>
    )
}
export default Header