import React from "react";
import "../Pages/Admin.css";
import { useNavigate } from "react-router-dom";

const Admin = () => {
  const navigator = useNavigate();

  function AddNewProduct() {
    navigator("/add-product");
  }

  return (
    <div className="admin-container">
      <h1>Admin Dashboard</h1>
      <button type="button" className="btn btn-primary" onClick={AddNewProduct}>
        Add Product
      </button>
    </div>
  );
};

export default Admin;
