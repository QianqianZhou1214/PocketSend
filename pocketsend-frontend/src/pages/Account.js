import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import "./styles/Account.css"


export default function Account() {
  const { user, logout, updateUser } = useAuth();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    if (user) {
      setUsername(user.username || "");
      setEmail(user.email || "");
      setPassword("");
    }
  }, [user]);

  const handleSave = () => {
    updateUser({ username, email, password });
    setIsEditing(false);
  };

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  if (!user) {
    return <div>Loading user info...</div>;
  }

  return (
    <div className="account-container">
      <h2>Account Setting</h2>
      <form className="account-info">
        <label>Username</label>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          disabled={!isEditing}
         />

         <label>Email</label>
         <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          disabled={!isEditing}
        />

         <label>Password</label>
         <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          disabled={!isEditing}
        />
      </form>

      {isEditing ? (
        <button className="save-btn" onClick={handleSave}>Save</button> 
      ) : (
        <button className="edit-btn" onClick={() => setIsEditing(true)}>Edit</button>
      )}

      <button className="logout-btn" onClick={handleLogout}>Logout</button>
    </div>
  )

}