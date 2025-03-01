import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./styles/Account.css"


export default function Account({ user, onLogout,  onUpdateUser}) {
  const navigate = useNavigate();

  const [username, setUsername] = useState(user.username);
  const [email, setEmail] = useState(user.email);
  const [password, setPassword] = useState(user.password);
  const [isEditing, setIsEditing] = useState(false);

  const handleSave = () => {
    onUpdateUser({ username, email, password });
    setIsEditing(false);
  };

  const handleLogout = () => {
    onLogout();
    navigate("/login");
  };

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