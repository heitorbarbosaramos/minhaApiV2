import { Outlet, useLocation } from "react-router-dom";
import Menu from "./components/menu/Menu";
import Header from "./components/header/Header";
import Footer from "./components/footer/Footer";
import { MenuProvider } from "./context/menu/MenuContext";
import "./App.css";
import { AnimatePresence, motion } from "framer-motion";

function App() {
  const location = useLocation();
  console.log(location);
  return (
    <>
      <MenuProvider>
        <Header />
        <div className="bodyPrimary">
          <Menu />
          <div className="bodyRenderiza">
            <AnimatePresence mode="wait">
              <motion.div
                key={location.pathname}
                initial={{ opacity: 10, x: 10 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{
                  opacity: { ease: "linear" },
                  layout: { duration: 0.3 },
                  duration: 0.5,
                }}
              >
                <Outlet />
              </motion.div>
            </AnimatePresence>
          </div>
          <Footer />
        </div>
      </MenuProvider>
    </>
  );
}

export default App;
