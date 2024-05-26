import { FiDollarSign } from "react-icons/fi"
import CardBasic from "../../components/card/basic/CardBasic"
import { AiFillAudio, AiOutlineAlert } from "react-icons/ai"
import { LineChart } from '@mui/x-charts/LineChart';
import { FaRedditSquare } from "react-icons/fa";

const Home = () => {

    const years = [
        new Date(2015, 0, 1),
        new Date(2016, 0, 1),
        new Date(2017, 0, 1),
        new Date(2018, 0, 1),
    ];

    const Feijao = [250, 450, 500, 900.918,];

    const Arroz = [25, 300, 380.83, 150.086,];

    const Macarrao = [25, 500, 250.785, 100.617,];

    const lineChartsParams = {
        series: [
            {
                label: 'Feijão',
                data: Feijao,
                showMark: true,
            },
            {
                label: 'Macarrão',
                data: Macarrao,
                showMark: true,
            },
            {
                label: 'Arroz',
                data: Arroz,
                showMark: true,
            },
        ],
        width: 600,
        height: 400,
    };

    const yearFormatter = (date: Date) => date.getFullYear().toString();
    const currencyFormatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
    }).format;


    return (
        <div className="page">

            <div className="group">
                <CardBasic width={260} title="Saldo em Vendas" subheader="Saldo mensal"  >
                    <span>
                        <FiDollarSign color="blue" size="25" /> 2.010,50 em vendas
                    </span>
                </CardBasic>
                <CardBasic width={260} title="Total em estoque" subheader="Estoque"  >
                    <span>
                        <FiDollarSign color="red" size="25" /> 2.010,50 em estoque
                    </span>
                </CardBasic>
                <CardBasic width={260} title="Alerta visual" subheader="Alerta de estoque baixo"  >
                    <span>
                        <AiOutlineAlert color="red" size="25" /> Produto em estoque acabando
                    </span>
                </CardBasic>
                <CardBasic width={260} title="Ouvinte no ar" subheader="Estção WHV"  >
                    <span>
                        <AiFillAudio color="red" size="25" /> Quem está falando
                    </span>
                </CardBasic>
            </div>
            <div className="group">
                <CardBasic width={590} title="Histórico Vendas" subheader=""  >
                    <LineChart
                        {...lineChartsParams}
                        xAxis={[{ data: years, scaleType: 'time', valueFormatter: yearFormatter }]}
                        series={lineChartsParams.series.map((series) => ({
                            ...series,
                            valueFormatter: (v: number | bigint | null) => (v === null ? '' : currencyFormatter(v)),
                        }))}
                    />
                </CardBasic>
                <div className="group">
                    <CardBasic width={200} height={180} title="Vago" subheader="Vago"  >
                        <span>
                            <FaRedditSquare color="yellow" size="25" /> Espaço em aberto
                        </span>
                    </CardBasic>
                    <CardBasic width={200} height={180} title="Vago" subheader="Vago"  >
                        <span>
                            <FaRedditSquare color="yellow" size="25" /> Espaço em aberto
                        </span>
                    </CardBasic>
                </div>
            </div>
        </div>
    )
}
export default Home