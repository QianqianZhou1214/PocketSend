import { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem("accessToken")
  );


  const login = async (identifier, password) => {
    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ identifier, password }),
        credentials: "include",
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("accessToken", data.token);
        //localStorage.setItem("isLoggedIn", "true");
        setIsAuthenticated(true);
        return { success: true };
      } else {
        const error = await response.text();
        return { success: false, message: error };
      }
    } catch (error) {
      return { success: false, message: error.message };
    }
  };

  const logout = async () => {
    await fetch("http://localhost:8080/api/auth/logout", {
      method: "POST",
      credentials: "include"
    });
    localStorage.removeItem("accessToken");
    setIsAuthenticated(false);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);