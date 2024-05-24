import { useEffect, useState } from "react"
import ApiService from "../../services/ApiService"

const SystemInfo = () => {

    const [data, setData] = useState()
    useEffect(() => {
        ApiService.get("/actuator/info").then((response) => {
            setData(response.data.app)
        }).catch((error) => {
            console.error(error)
        })
    },[])

    console.log(data)
    return(
        <>
            System Info
            <p>{data.name}</p>
            <p>{data.description}</p>
            <p>{data.version}</p>
        </>
    )
}
export default SystemInfo