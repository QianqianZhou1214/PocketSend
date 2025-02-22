import React, { useEffect, useRef, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import "./styles/SideBar.css";

export default function Sidebar({ isOpen, toggleSideBar }) {
  const navigate = useNavigate();
  const sidebarRef = useRef(null);
  
  const handleClickOutside = useCallback((event) => {
    if (isOpen && sidebarRef.current && !sidebarRef.current.contains(event.target)) {
      toggleSideBar();
    }
  }, [isOpen, toggleSideBar]);

  useEffect(() => {
    if (isOpen) {
      document.addEventListener("mousedown", handleClickOutside);
    } else {
      document.removeEventListener("mousedown", handleClickOutside);
    }

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [isOpen, handleClickOutside]);
  return (
    <div ref={sidebarRef} className={`sidebar ${isOpen ? "open" : ""}`}>
      <div className="sidebar-header">
        <h2>PocketSend</h2>
      </div>
      <ul>
        <li onClick={() => { navigate("/"); toggleSideBar(); }}>Home</li>
        <li onClick={() => { navigate("/profile"); toggleSideBar(); }}>Account</li>
        <li onClick={() => { navigate("/logout"); toggleSideBar(); }}>Logout</li>
      </ul>
    </div>
  );
}


