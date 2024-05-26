import { IoHomeOutline } from "react-icons/io5"
import { MenuModel } from "../../types/MenuItem"
import { BiAbacus, BiAlarm, BiAlbum, BiAnchor, BiAtom, BiBot, BiBug } from "react-icons/bi"
import { MenuContext } from "../../context/menu/MenuContext"
import { Link } from "react-router-dom"
import { useContext, useState } from "react"
import "./Menu.css"

const Menu = () => {

    const { oculta } = useContext(MenuContext)

    const items: MenuModel[] = [
        {
            label: "Home",
            items: [
                { label: "Principal", icon: <IoHomeOutline />, to: "home", visible: true, disabled: false, target: "_parent" }
            ]
        },
        {
            label: "Complementos",
            items: [
                { label: "Secundaria", icon: <BiAtom />, to: "secundaria", visible: true, disabled: false, target: "_parent" },
                { label: "Tercearia", icon: <BiBot />,   to: "tercearia",  visible: true, disabled: false, target: "_parent" }
            ]
        },
        {
            label: "Outros",
            items: [
                { label: "Página 4", icon: <BiBug />,    to: "pg4", visible: true,  disabled: false, target: "_parent" },
                { label: "Página 5", icon: <BiAbacus />, to: "pg5", visible: false, disabled: false, target: "_parent" },
                { label: "Página 6", icon: <BiAlarm />,  to: "pg6", visible: true,  disabled: false, target: "_parent" },
                { label: "Página 7", icon: <BiAlbum />,  to: "pg7", visible: true,  disabled: false, target: "_parent" },
                { label: "Página 8", icon: <BiAnchor />, to: "pg8", visible: true,  disabled: false, target: "_parent" },
               
            ]
        }
    ]

    const [active, setActive] = useState("")

    return (
        <nav className={oculta ? "menu mostrar" : "menu ocultar"}>
            {items.map((menuSection, sectionIndex) => (
                <ul key={sectionIndex}>
                    <h3 className="nameLabelMenu">{menuSection.label}</h3>
                        {menuSection.items.map((item, itemIndex) => item.visible && (
                            <Link to={item.to} onClick={() => {setActive(item.to)}}>
                                <li key={itemIndex} className={active === item.to ? "active" : ""}>
                                    {item.icon} {item.label}
                                </li>
                            </Link>
                        ))}
                </ul>
            ))}
        </nav>
    );
}
export default Menu