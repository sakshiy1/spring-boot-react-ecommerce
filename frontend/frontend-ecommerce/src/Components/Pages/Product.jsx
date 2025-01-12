import React, { useEffect, useState } from "react";
import "../Pages/Product.css"; // Ensure the path is correct
import axios from "axios";
import DefaultImage from "../images/bag.jpg";
import { listProducts } from "../Service/ProductService";

const getImage = async (ProductId) => {
  try {
    const image = await import(`../images/Product${ProductId}.jpg`);
    return image.default;
  } catch (error) {
    console.error(`Failed to load image for product ${Productid}`);
    return DefaultImage;
  }
};

const handleAddToCart = (product) => {
  addToCart(product);
  alert(`${product.name} has been added to your cart!`);
};

const ProductList = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await listProducts();
        const productsWithImages = await Promise.all(
          response.data.map(async (product) => ({
            ...product,
            image: await getImage(product.id),
          }))
        );
        setProducts(productsWithImages);
      } catch (error) {
        console.error(error);
      }
    };
    fetchProducts();
  }, []);

  return (
    <div className="product-list">
      {products.map((product) => (
        <div key={product.id} className="product-card">
          <img
            src={product.image}
            alt={product.name}
            className="product-image"
          />
          <h2>{product.name}</h2>
          <p>{product.price}</p>
          <button
            type="button"
            className="btn btn-primary"
            onClick={() => handleAddToCart(product)}
          >
            Add to Cart
          </button>
        </div>
      ))}
    </div>
  );
};

const Product = () => {
  return (
    <div className="product-page">
      <h1 className="fst-italic">Our Products</h1>
      <ProductList />
    </div>
  );
};

export default Product;
