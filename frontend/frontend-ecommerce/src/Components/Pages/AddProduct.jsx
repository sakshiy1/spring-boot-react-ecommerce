import React, { useState } from "react";
import "./AddProduct.css"; // Correctly import the CSS file
import { createProduct } from "../Service/ProductService";
import { useNavigate } from "react-router-dom";

const AddProduct = () => {
  const [productName, setProductName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState(0);
  const [image, setImage] = useState(null);

  const [errors, setErrors] = useState({
    productName: "",
    description: "",
    price: "",
    image: "",
  });

  const navigator = useNavigate();

  function handleImageChange(e) {
    if (e.target.files && e.target.files[0]) {
      setImage(e.target.files[0]);
    }
  }

  function saveProduct(e) {
    e.preventDefault();

    if (validateForm()) {
      const product = {
        name: productName,
        description: description,
        price: price,
      };
      console.log(product);

      createProduct(product).then((response) => {
        const productId = response.data.id;
        console.log(response.data);

        if (image) {
          const reader = new FileReader();
          reader.onload = () => {
            const imageFileName = `Product${productId}.jpg`;
            const imgHref = `./Components/images/${imageFileName}`;

            fetch(imgHref, {
              method: "PUT",
              headers: {
                "Content-Type": image.type,
              },
              body: reader.result,
            }).then(() => {
              console.log(`Image saved as ${imgHref}`);
              setImage(null);
              navigator("/products");
            });
          };
          reader.readAsDataURL(image);
        } else {
          navigator("/products");
        }
      });
    }
  }

  function validateForm() {
    let valid = true;

    const errorsCopy = { ...errors };

    if (productName.trim()) {
      errorsCopy.productName = "";
    } else {
      errorsCopy.productName = "Product name is required";
      valid = false;
    }

    if (description.trim()) {
      errorsCopy.description = "";
    } else {
      errorsCopy.description = "Description is required";
      valid = false;
    }

    if (price.toString().trim()) {
      errorsCopy.price = "";
    } else {
      errorsCopy.price = "Price is required";
      valid = false;
    }

    if (!image) {
      errorsCopy.image = "Image is required";
      valid = false;
    } else {
      errorsCopy.image = "";
    }

    setErrors(errorsCopy);

    return valid;
  }

  return (
    <div className="container">
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          <h2 className="text-center">Add Product</h2>
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">Product Name</label>
                <input
                  type="text"
                  placeholder="Enter Name"
                  name="name"
                  value={productName}
                  className={`form-control ${
                    errors.productName ? "is-invalid" : ""
                  }`}
                  onChange={(e) => setProductName(e.target.value)}
                  required
                ></input>
                {errors.productName && (
                  <div className="invalid-feedback">{errors.productName}</div>
                )}
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Description</label>
                <input
                  type="text"
                  placeholder="Enter Description"
                  name="description"
                  value={description}
                  className={`form-control ${
                    errors.description ? "is-invalid" : ""
                  }`}
                  onChange={(e) => setDescription(e.target.value)}
                  required
                ></input>
                {errors.description && (
                  <div className="invalid-feedback">{errors.description}</div>
                )}
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Price</label>
                <input
                  type="text"
                  placeholder="Enter Price"
                  name="price"
                  value={price}
                  className={`form-control ${errors.price ? "is-invalid" : ""}`}
                  onChange={(e) => setPrice(e.target.value)}
                  required
                ></input>
                {errors.price && (
                  <div className="invalid-feedback">{errors.price}</div>
                )}
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Image</label>
                <input
                  type="file"
                  placeholder="upload image"
                  name="image"
                  className={`form-control ${errors.image ? "is-invalid" : ""}`}
                  onChange={handleImageChange}
                  required
                ></input>
                {errors.price && (
                  <div className="invalid-feedback">{errors.price}</div>
                )}
              </div>
              <button className="btn btn-primary" onClick={saveProduct}>
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddProduct;
