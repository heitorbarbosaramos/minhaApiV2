/* eslint-disable @typescript-eslint/no-explicit-any */
import { createContext, useState, ReactNode } from "react";

interface MenuContextType {
  ocultar: () => void;
  oculta: boolean;
}

const defaultValue: MenuContextType = {
  ocultar: () => {},
  oculta: true,
};

export const MenuContext = createContext<MenuContextType>(defaultValue);

interface MenuProviderProps {
  children: ReactNode;
}

export const MenuProvider = ({ children }: MenuProviderProps) => {
  const [oculta, setOculta] = useState(true);

  function ocultar() {
    setOculta(!oculta);
  }

  return (
    <MenuContext.Provider value={{ ocultar, oculta }}>
      {children}
    </MenuContext.Provider>
  );
};