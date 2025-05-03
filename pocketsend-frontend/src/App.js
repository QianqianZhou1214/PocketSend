import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/NavBar';
import Home from './pages/Home';
import Login from './pages/Login';
import Account from './pages/Account';
import { AuthProvider, useAuth } from './context/AuthContext';
import Register from './pages/Register';

function ProtectedRoute({ children }) {
  const { isAuthenticated, isLoadingUser } = useAuth();

  if(isLoadingUser) {
    return <div>Loading...</div>;
  }

  return isAuthenticated ? children : <Navigate to="/login" />;
}

export default function App() {

  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/" element={<ProtectedRoute><Home /></ProtectedRoute>} />
          <Route path="/profile" element={<ProtectedRoute><Account /></ProtectedRoute>} />
          <Route path="/login" element={ <Login />} />
        </Routes>
      </Router>
    </AuthProvider>
    
  );
}
