import React from "react";
import bag from "../assets/bag2.jpg";
import "../App.css";

const HeaderComponent = () => {
  return (
    <div>
      <header>
        <nav className="navbar">
          <div className="corner-image">
            <img
              src={bag}
              alt=""
              width="30"
              height="24"
              className="navbar-logo"
            />
          </div>
          <div>
            <div className="center-text">Cupid Shopping</div>
          </div>
        </nav>
      </header>
    </div>
  );
};

export default HeaderComponent;
