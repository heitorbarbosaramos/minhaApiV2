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

    return (
        <Card className="text card-info">
            <p className="title">
                {dados.name}
            </p>

            <p className="m-0" style={{ fontSize: "0.8rem" }}>
                {dados.description}
            </p>
            <p className="m-0" style={{ fontSize: "0.6rem" }}>
                Versão: {dados.version}
            </p>
        </Card>
    )
}
export default InfoSystem;