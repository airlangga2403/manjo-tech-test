import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1"
});

export const getTransactions = async () => {

    const response =
        await api.get(
            "/payment/transactions"
        );

    return response.data;
};

export const searchTransactions = async (
    params
) => {

    const response =
        await api.get(
            "/payment/transactions/search",
            {
                params
            }
        );

    return response.data;
};

export default api;