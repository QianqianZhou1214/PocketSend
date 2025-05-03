import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem("accessToken")
  );
  
  const [isLoadingUser, setIsLoadingUser] = useState(true);

  const fetchCurrentUser = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/auth/profile", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${localStorage.getItem("accessToken")}`
        },
        credentials: "include",
      });

      if(res.ok) {
        const data = await res.json();
        setUser(data);
        setIsAuthenticated(true);
      } else {
        setUser(null);
        setIsAuthenticated(false);
        console.error("Failed to fetch user info.");
      }
    } catch (err) {
      setUser(null);
      setIsAuthenticated(false);
      console.error("Error fetching info", err);
    } finally {
      setIsLoadingUser(false); // done loading
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      fetchCurrentUser();
    } else {
      setIsLoadingUser(false); // still need to stop loading
    }
  }, []);

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
        await fetchCurrentUser();
        return { success: true };
      } else {
        const error = await response.text();
        return { success: false, message: error };
      }
    } catch (error) {
      return { success: false, message: error.message };
    }
  };

  const logout = () => {
    localStorage.removeItem("accessToken");
    setIsAuthenticated(false);
    setUser(null);
  };

  /*const logout = async () => {
    await fetch("http://localhost:8080/api/auth/logout", {
      method: "POST",
      credentials: "include"
    });
    localStorage.removeItem("accessToken");
    setIsAuthenticated(false);
  };*/



  const updateUser = async (updatedData) => {
    try {
      const response = await fetch("http://localhost:8080/api/auth/profile", {
        method: "PUT",
        headers: {
          "Authorization": `Bearer ${localStorage.getItem("accessToken")}`, 
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(updatedData),
      });
  
      if (response.ok) {
        const updatedUser = await response.json();
        setUser(updatedUser);
      } else {
        console.error("Failed to update user");
      }
    } catch (error) {
      console.error("Error updating user", error);
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout, user, updateUser, isLoadingUser, fetchCurrentUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);