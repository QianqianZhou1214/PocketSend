import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './styles/Register.css';

export default function Register() {
  const [form, setForm] = useState(
    { username: '',
      email: '',
      password: ''
    }
  );

  const navigate = useNavigate();

  const handleChange = e => setForm({ ...form, [e.target.name]: e.target.value});

  const handleSubmit = async e => {
    e.preventDefault();
    const response = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams(form),
      credentials: "include",
    });

    if (response.ok) {
      alert("Registration successful!");
      navigate("/login");
    } else {
      const error = await response.text();
      alert("Registration failed: " + error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="auth-form">
      <h2>Register</h2>
      <input name="username" value={form.username} onChange={handleChange} placeholder="Username" required />
      <input name="email" value={form.email} onChange={handleChange} placeholder="Email" required />
      <input name="password" value={form.password} onChange={handleChange} placeholder="Password" required />
      <button>Register</button>
    </form>
  );
}