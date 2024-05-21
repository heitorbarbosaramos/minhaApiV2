import { useState } from 'react'
import reactLogo from '../../assets/react.svg'
import viteLogo from '/vite.svg'
import InfoSystem from '../../components/informations/InfoSystem.jsx'
import ToastSystem from '../../components/Toast/ToastSystem.jsx'
import { Button } from 'primereact/button'
import Logout from '../Login/Logout.jsx'

const Home = () => {

    const [show, setShow] = useState(false)
    const [severity, setSeverity] = useState("info")
    const [summary, setSummary] = useState("info")
    const [message, setMessage] = useState("")
    const [life, setLife] = useState(3000)
    const [position, setPosition] = useState("top-right")

    function mostarToast() {
        setShow(!show)
    }
    return (
        <>
            <div>
                <a href="https://vitejs.dev" target="_blank">
                    <img src={viteLogo} className="logo" alt="Vite logo" />
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={reactLogo} className="logo react" alt="React logo" />
                </a>
            </div>
            <h1>Vite + React</h1>

            <p className="read-the-docs">
                Click on the Vite and React logos to learn more
            </p>

            <div className="d-flex p-2 gap-3">
                <Logout />
            </div>

            <div className="d-flex p-2 gap-3">
                <Button label='TOP LEFT' className='btn btn-primary' onClick={() => { setPosition("top-left") }} />
                <Button label='TOP CENTER' className='btn btn-primary' onClick={() => { setPosition("top-center") }} />
                <Button label='TOP RIGHT' className='btn btn-primary' onClick={() => { setPosition("top-right") }} />
                <Button label='CENTER' className='btn btn-primary' onClick={() => { setPosition("center") }} />
                <Button label='BOTTOM LEFT' className='btn btn-primary' onClick={() => { setPosition("bottom-left") }} />
                <Button label='BOTTOM CENTER' className='btn btn-primary' onClick={() => { setPosition("bottom-center") }} />
                <Button label='BOTTOM RIGHT' className='btn btn-primary' onClick={() => { setPosition("bottom-right") }} />
            </div>

            <div className="d-flex p-2 gap-3">
                <Button label='INFO'
                    className='btn btn-primary'
                    onClick={() => {
                        setSeverity("info")
                        setSummary("info")
                        setMessage("Isso é uma mensagem de Info")
                        mostarToast()
                        setLife(3000)
                    }} />

                <Button label='SUCESS'
                    className='btn btn-success'
                    onClick={() => {
                        setSeverity("success")
                        setSummary("success")
                        setMessage("Isso é uma mensagem de Sucesso")
                        mostarToast()
                        setLife(3000)
                    }} />

                <Button label='ALERTA'
                    className='btn btn-warning'
                    onClick={() => {
                        setSeverity("warn")
                        setSummary("Warning")
                        setMessage("Isso é uma mensagem de Alerta")
                        mostarToast()
                        setLife(3000)
                    }} />

                <Button label='ERRO'
                    className='btn btn-danger'
                    onClick={() => {
                        setSeverity("error")
                        setSummary("Error")
                        setMessage("Isso é uma mensagem de Erro")
                        mostarToast()
                        setLife(3000)
                    }} />

                <Button label='SECUNDÁRIO'
                    className='btn btn-primary'
                    onClick={() => {
                        setSeverity("secondary")
                        setSummary("Secondary")
                        setMessage("Isso é uma mensagem Secundária")
                        mostarToast()
                        setLife(3000)
                    }} />

                <Button label='CONSTRATE'
                    className='btn btn-light'
                    onClick={() => {
                        setSeverity("contrast")
                        setSummary("Contrast")
                        setMessage("Isso é uma mensagem Constrate")
                        mostarToast()
                        setLife(3000)
                    }} />
            </div>

            <ToastSystem
                show={show}
                setShow={setShow}
                message={message}
                severity={severity}
                summary={summary}
                life={life}
                position={position}
            />
            <InfoSystem />
        </>
    )
}
export default Home