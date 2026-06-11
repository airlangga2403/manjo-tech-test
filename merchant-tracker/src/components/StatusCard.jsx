import {
    Card,
    CardContent,
    Chip,
    Typography,
    Stack
} from "@mui/material";

export default function StatusCard({
    data
}) {

    const getColor = () => {

        switch (data.status) {

            case "SUCCESS":
                return "success";

            case "FAILED":
                return "error";

            default:
                return "warning";
        }
    };

    return (
        <Card>
            <CardContent>

                <Stack spacing={2}>

                    <Typography variant="h6">
                        Transaction Detail
                    </Typography>

                    <Typography>
                        Reference Number :
                        {data.referenceNo}
                    </Typography>

                    <Typography>
                        Merchant :
                        {data.merchantId}
                    </Typography>

                    <Typography>
                        Amount :
                        Rp {data.amount}
                    </Typography>

                    <Chip
                        label={data.status}
                        color={getColor()}
                    />

                    <Typography>
                        Transaction Date :
                        {data.transactionDate}
                    </Typography>

                    <Typography>
                        Paid Date :
                        {data.paidDate || "-"}
                    </Typography>

                </Stack>

            </CardContent>
        </Card>
    );
}