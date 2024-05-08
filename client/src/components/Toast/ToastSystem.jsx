import { Toast } from 'primereact/toast';
import { useEffect, useRef } from 'react';

// toast primeng documentation: https://primereact.org/toast/#accessibility


const ToastSystem = ({message, severity, summary, life, position, show, setShow}) => {

    const toasting = useRef(null)
    
    useEffect(() => {
        if(show){
            toasting.current.show({ severity: severity, summary: summary, detail: message, life: life });
            setShow(false)
        }
    }, [life, message, show, severity, summary, setShow])
      
    return (
        <div>
            <Toast ref={toasting} position={position}/>
        </div>
    )
}

export default ToastSystem;