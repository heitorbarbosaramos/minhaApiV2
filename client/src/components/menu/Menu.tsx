import { IoHomeOutline } from "react-icons/io5"
import { MenuModel } from "../../types/MenuItem"
import { BiAbacus, BiAlarm, BiAlbum, BiAnchor, BiAtom, BiBot, BiBug } from "react-icons/bi"
import { Link } from "react-router-dom"

const Menu = () => {

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

    console.log(items)
    return (
        <nav>
            {items.map((menuSection, sectionIndex) => (
                <div key={sectionIndex}>
                    <h3>{menuSection.label}</h3>
                    <ul>
                        {menuSection.items.map((item, itemIndex) => item.visible && (
                            <Link to={item.to}>
                                <li key={itemIndex}>
                                    {item.icon} {item.label}
                                </li>
                            </Link>
                        ))}
                    </ul>
                </div>
            ))}
        </nav>
    );
}
export default Menu