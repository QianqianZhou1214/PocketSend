import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./styles/Login.css";

export default function Login({ onLogin }) {
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    if(password) {
      onLogin(password);
      navigate("/");
    }
  };

  return (
    <div className="login-container">
      <h2>Password</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit" disabled={!password}>
          Login
        </button>
      </form>
    </div>
  );
}