/* eslint-disable @typescript-eslint/no-explicit-any */

import { createContext, useState } from "react"

export const MenuContext = createContext("menuContext")

export const MenuProvider = ({ children }) =>{
    
    const [oculta, setOculta] = useState(true)

    function ocultar(){
        setOculta(!oculta)
    }

    return(
        <MenuContext.Provider value={{ ocultar, oculta }}>
            {children}
        </MenuContext.Provider>
    )
}