import { BrowserRouter, Routes, Route } from "react-router-dom";
import HeaderComponent from "./Components/HeaderComponent";
import Product from "./Components/Pages/Product";
import "./App.css";
import Admin from "./Components/Pages/Admin";
import AddProduct from "./Components/Pages/AddProduct";

function App() {
  return (
    <div className="app-container">
      <BrowserRouter>
        <HeaderComponent />
        <Routes>
          {/* //http:localhost: */}
          <Route path="/" element={<Product />}></Route>
          {/* //localhost:5173/products */}
          <Route path="/products" element={<Product />}></Route>
          <Route path="/Admin" element={<Admin />}></Route>
          <Route path="/add-product" element={<AddProduct />}></Route>
        </Routes>
        ;
      </BrowserRouter>
    </div>
  );
}

export default App;
