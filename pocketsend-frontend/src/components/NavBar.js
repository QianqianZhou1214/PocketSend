import React, { useState, useCallback } from "react";
import SideBar from "./SideBar";
import { Rocket } from "lucide-react";
import "./styles/NavBar.css";

export default function Navbar() {
  const [isSideBarOpen, setIsSideBarOpen] = useState(false);

  const toggleSideBar = useCallback(() => {
    setIsSideBarOpen(prev => !prev);
  }, []);

  return (
    <>
      <nav className="navbar">
        <div className="logo" onClick={toggleSideBar}>
          <Rocket size={24} className="logo" />PocketSend
        </div>
      </nav>
      <SideBar isOpen={isSideBarOpen} toggleSideBar={toggleSideBar} />
    </>
  );
 
}