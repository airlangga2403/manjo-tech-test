import {
    Alert,
    Box,
    Button,
    CircularProgress,
    Container,
    Grid,
    MenuItem,
    Paper,
    TextField,
    Typography
} from "@mui/material";

import {
    DataGrid
} from "@mui/x-data-grid";

import {
    useEffect,
    useState
} from "react";

import {
    getTransactions,
    searchTransactions
} from "../api/paymentApi";

export default function MerchantTrackerPage() {

    const [loading, setLoading] =
        useState(false);

    const [error, setError] =
        useState("");

    const [rows, setRows] =
        useState([]);

    const [filters, setFilters] =
        useState({
            merchantId: "",
            trxId: "",
            partnerReferenceNumber: "",
            referenceNumber: "",
            status: ""
        });

    const loadData = async () => {

        try {

            setLoading(true);

            const response =
                await getTransactions();

            setRows(
                response.data
            );

        } catch {

            setError(
                "Failed to load transactions"
            );

        } finally {

            setLoading(false);
        }
    };

    useEffect(() => {

        loadData();

    }, []);

    const handleSearch = async () => {

        try {

            setLoading(true);

            const response =
                await searchTransactions(
                    filters
                );

            setRows(
                response.data
            );

        } catch (err) {

            setError(
                err?.response?.data?.responseMessage ||
                "Transaction Not Found"
            );

        } finally {

            setLoading(false);
        }
    };

    const handleReset = () => {

        setFilters({
            merchantId: "",
            trxId: "",
            partnerReferenceNumber: "",
            referenceNumber: "",
            status: ""
        });

        loadData();
    };

    const columns = [

        {
            field: "transactionDate",
            headerName: "Trx Date",
            width: 180
        },

        {
            field: "trxId",
            headerName: "Trx ID",
            width: 220
        },

        {
            field: "merchantId",
            headerName: "Merchant",
            width: 150
        },

        {
            field: "partnerReferenceNumber",
            headerName: "Partner Ref No",
            width: 250
        },

        {
            field: "referenceNumber",
            headerName: "Reference No",
            width: 180
        },

        {
            field: "amount",
            headerName: "Amount",
            width: 130
        },

        {
            field: "status",
            headerName: "Status",
            width: 120
        },

        {
            field: "paidDate",
            headerName: "Paid Date",
            width: 180
        }
    ];

    return (
        <Container
            maxWidth="xl"
            sx={{ mt: 4 }}
        >
            <Paper
                sx={{
                    p: 4
                }}
            >

                <Typography
                    variant="h4"
                    gutterBottom
                >
                    Merchant Status Tracker
                </Typography>

                <Grid
                    container
                    spacing={2}
                >

                    <Grid item xs={12} md={3}>
                        <TextField
                            fullWidth
                            label="Merchant ID"
                            value={filters.merchantId}
                            onChange={(e) =>
                                setFilters({
                                    ...filters,
                                    merchantId:
                                        e.target.value
                                })
                            }
                        />
                    </Grid>

                    <Grid item xs={12} md={3}>
                        <TextField
                            fullWidth
                            label="Transaction ID"
                            value={filters.trxId}
                            onChange={(e) =>
                                setFilters({
                                    ...filters,
                                    trxId:
                                        e.target.value
                                })
                            }
                        />
                    </Grid>

                    <Grid item xs={12} md={3}>
                        <TextField
                            fullWidth
                            label="Partner Ref Number"
                            value={
                                filters.partnerReferenceNumber
                            }
                            onChange={(e) =>
                                setFilters({
                                    ...filters,
                                    partnerReferenceNumber:
                                        e.target.value
                                })
                            }
                        />
                    </Grid>

                    <Grid item xs={12} md={3}>
                        <TextField
                            fullWidth
                            label="Reference Number"
                            value={
                                filters.referenceNumber
                            }
                            onChange={(e) =>
                                setFilters({
                                    ...filters,
                                    referenceNumber:
                                        e.target.value
                                })
                            }
                        />
                    </Grid>

                    <Grid item xs={12} md={3}>
                        <TextField
                            select
                            fullWidth
                            label="Status"
                            value={filters.status}
                            onChange={(e) =>
                                setFilters({
                                    ...filters,
                                    status:
                                        e.target.value
                                })
                            }
                        >
                            <MenuItem value="">
                                ALL
                            </MenuItem>

                            <MenuItem value="PENDING">
                                PENDING
                            </MenuItem>

                            <MenuItem value="SUCCESS">
                                SUCCESS
                            </MenuItem>

                            <MenuItem value="FAILED">
                                FAILED
                            </MenuItem>

                        </TextField>
                    </Grid>

                    <Grid item xs={12}>

                        <Button
                            variant="contained"
                            onClick={handleSearch}
                            sx={{ mr: 2 }}
                        >
                            Search
                        </Button>

                        <Button
                            variant="outlined"
                            onClick={handleReset}
                        >
                            Reset
                        </Button>

                    </Grid>

                </Grid>

                <Box mt={3}>

                    {loading &&
                        <CircularProgress />
                    }

                    {error &&
                        <Alert severity="error">
                            {error}
                        </Alert>
                    }

                    <DataGrid
                        rows={rows}
                        columns={columns}
                        getRowId={(row) =>
                            row.referenceNumber
                        }
                        pageSizeOptions={[5, 10, 20]}
                        autoHeight
                    />

                </Box>

            </Paper>
        </Container>
    );
}