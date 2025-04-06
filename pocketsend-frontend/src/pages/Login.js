import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import "./styles/Login.css";

export default function Login() {
  const [identifier, setIdentifier] = useState(""); //username or email
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();
  const { login } = useAuth(); // get access to login from AuthContext

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const result = await login(identifier, password);
    if (result.success) {
      navigate("/");
    } else {
      setError(result.message || "Login failed.");
    }

/*     if(password) {
      onLogin(password);
      navigate("/");
    } */
  };

/*  const handleLoginSuccess = () => {
    localStorage.setItem("isLoggedIn", "true");
    localStorage.setItem("lastActivity", Date.now().toString());
    navigate("/home");
  }; */


  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
      <input
          type="text"
          placeholder="Username or Email"
          value={identifier}
          onChange={(e) => setIdentifier(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit" disabled={!identifier || !password}>
          Login
        </button>
        <p className="register-request">Don't have an account? <a href="/register">Register here</a></p>
        {error && <p className="error-msg">{error}</p>}
      </form>
    </div>
  );
}