import './App.css';
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/NavBar';
import Home from './pages/Home';
import Login from './pages/Login';

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const correctPassword = "12345"

  const handleLogin = (password) => {
    console.log("mima", password);
    if(password === correctPassword) {
      console.log("loading");
      setIsAuthenticated(true);
    } else {
      alert("Incorrect password!");
    }
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
  };

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={isAuthenticated ? <Home /> : <Navigate to="/login" />} />
        <Route path="/login" element={ <Login onLogin={handleLogin}/>} />
      </Routes>
    </Router>
  );
}
