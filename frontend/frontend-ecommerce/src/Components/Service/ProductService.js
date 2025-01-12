import axios from 'axios';

const REST_API_BASE_URL = 'http://localhost:8000/api/products';

export const listProducts = () => axios.get(REST_API_BASE_URL);

export const createProduct = (product) => axios.post(REST_API_BASE_URL, product);