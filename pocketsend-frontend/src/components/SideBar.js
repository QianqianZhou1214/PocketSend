import React from "react";
import { useNavigate } from "react-router-dom";
import "./styles/SideBar.css";

export default function SideBar({ isOpen, toggleSideBar }) {
  const navigate = useNavigate();

  return (
    <div className={`sidebar ${isOpen ? "open" : ""}`}>
      <button className="close-btn" onClick={toggleSideBar}>
        X
      </button>
      <ul>
        <li onClick={() => { navigate("/"); toggleSideBar(); }}>Home</li>
        <li onClick={() => { navigate("/profile"); toggleSideBar(); }}>Account</li>
        <li onClick={() => { navigate("/logout"); toggleSideBar(); }}>Logout</li>
      </ul>
    </div>
  );
}