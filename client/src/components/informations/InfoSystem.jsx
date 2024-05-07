import { useEffect } from "react";
import { useState } from "react";
import ApiService from "../../services/ApiService";
import { Card } from 'primereact/card';
import './InfoSystem.css'

const InfoSystem = () => {

    const [dados, setDados] = useState("")

    useEffect(() => {
        ApiService.get("/actuator/info").then((response) => {
            setDados(response.data.app)
        }).catch((error) => {
            console.error(error)
        })
    }, [])

    console.log(dados)

    return (
        <Card subTitle={dados.name} className="text">
            <p className="m-0" style={{ fontSize: "10px" }}>
                {dados.description}
            </p>
            <p className="m-0" style={{ fontSize: "10px" }}>
                Versão: {dados.version}
            </p>
        </Card>
    )
}
export default InfoSystem;