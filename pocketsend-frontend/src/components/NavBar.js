import React, { useState } from "react";
import SideBar from "./SideBar";
import logo from "../assets/pocketsendlogo.jpg";
import "./styles/NavBar.css";

export default function Navbar() {
  const [isSideBarOpen, setIsSideBarOpen] = useState(false);

  const toggleSideBar = () => {
    setIsSideBarOpen(!isSideBarOpen);
  };

  return (
    <>
      <nav className="navbar">
        <div className="logo" onClick={toggleSideBar}>
          <img src={logo} alt="Logo" />
        </div>
      </nav>
      <SideBar isOpen={isSideBarOpen} toggleSideBar={toggleSideBar} />
    </>
  );
 
}