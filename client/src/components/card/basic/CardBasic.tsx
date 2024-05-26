/* eslint-disable @typescript-eslint/no-explicit-any */
import { Card, CardContent, CardHeader  } from "@mui/material"

const CardBasic = ({ width, title, subheader, children, height }: any) => {

    return (
        <>
            <Card sx={{ width: width, height: height}}>
                <CardHeader title={title} subheader={subheader}/>
                <CardContent>
                    {children}
                </CardContent>
            </Card>
        </>
    )
}
export default CardBasic