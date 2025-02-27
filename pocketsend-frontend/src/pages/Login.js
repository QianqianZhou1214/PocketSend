import React, { useState } from "react";
import "./styles/Login.css";

export default function Login({ onLogin }) {
  const [password, setPassword] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if(password) {
      onLogin(password);
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