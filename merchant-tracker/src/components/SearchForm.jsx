import {
    Box,
    Button,
    TextField
} from "@mui/material";

export default function SearchForm({
    referenceNo,
    setReferenceNo,
    onSearch,
    loading
}) {

    return (
        <Box
            sx={{
                display: "flex",
                gap: 2
            }}
        >
            <TextField
                fullWidth
                label="Reference Number"
                value={referenceNo}
                onChange={(e) =>
                    setReferenceNo(
                        e.target.value
                    )
                }
            />

            <Button
                variant="contained"
                onClick={onSearch}
                disabled={loading}
            >
                Search
            </Button>
        </Box>
    );
}