import './App.css';
import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/NavBar';
import Home from './pages/Home';
import Login from './pages/Login';
import Account from './pages/Account';
import { AuthProvider, useAuth } from './context/AuthContext';
import Register from './pages/Register';

function ProtectedRoute({ children }) {
  const { user } = useAuth();
  return user ? children : <Navigate to="/login" />;
}

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(() => {
    return localStorage.getItem("isAuthenticated") === "true";
  });
  const correctPassword = "12345"

  const [user, setUser] = useState({
    username: "Guest",
    email: "guest@email.com",
    password: correctPassword
  });

  const handleLogin = (password) => {
    console.log("pw", password);
    if(password === correctPassword) {
      console.log("loading");
      setIsAuthenticated(true);
      localStorage.setItem("isAuthenticated", "true");
    } else {
      alert("Incorrect password!");
      setIsAuthenticated(false);
      localStorage.removeItem("isAuthenticated");
    }
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem("isAuthenticated");
  };

  const handleUpdateUser = (updatedUser) => {
    setUser(updatedUser);
  };

  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/" element={isAuthenticated ? <Home /> : <Navigate to="/login" />} />
          <Route path="/profile" element={isAuthenticated ? <Account user={user} onLogout={handleLogout} onUpdateUser={handleUpdateUser} /> : <Navigate to="/login" />} />
          <Route path="/login" element={ <Login onLogin={handleLogin}/>} />
        </Routes>
      </Router>
    </AuthProvider>
    
  );
}
