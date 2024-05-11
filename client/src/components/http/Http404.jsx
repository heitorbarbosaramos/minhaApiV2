import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import notFound from '../../assets/404-error-not-found-page.png'
import { Link } from 'react-router-dom';

const Http404 = () => {

    const header = (
        <img width="100" height="100" alt="Card" src={notFound} />
    );
    const footer = (
        <>
            <Link to="/">
                <Button label="Save" icon="pi pi-check" />
            </Link>
            <Button label="Cancel" severity="secondary" icon="pi pi-times" style={{ marginLeft: '0.5em' }} />
        </>
    );

    return (
        <div className="flex flex-column md:flex-row justify-content-between my-5">
            <Card title="Erro 404" subTitle="A página solicitada não foi encontrada ou não existe mais." footer={footer} header={header}>
                <p className="m-0">
                    
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit. Inventore sed consequuntur error repudiandae
                    numquam deserunt quisquam repellat libero asperiores earum nam nobis, culpa ratione quam perferendis esse, cupiditate neque quas!
                </p>
            </Card>
        </div>
    )
}
export default Http404