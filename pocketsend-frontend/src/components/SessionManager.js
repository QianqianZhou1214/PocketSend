import { useEffect } from "react";
import { useNavigate } from "react-router-dom";


const SessionManager = () => {
  const INACTIVITY_LIMIT = 30 * 60 * 1000; // 30 minutes
  const navigate = useNavigate();

  useEffect(() => {
    const isLoggedIn = localStorage.getItem("isLoggedIn");
    if (!isLoggedIn) {
      navigate("/login");
      return;
    }

    const updateActivity = () => {
      localStorage.setItem("lastActivity", Date.now().toString());
    };

    const checkInactivity = () => {
      const lastActivity = parseInt(localStorage.getItem("lastActivity") || "0", 10);
      const now = Date.now();
      if (now - lastActivity > INACTIVITY_LIMIT) {
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("lastActivity");
        navigate("/login");
      }
    };

    updateActivity(); // set initial activity timestamp

    const events = ["click", "keydown", "scroll", "mousemove"];
    events.forEach(event => window.addEventListener(event, updateActivity));

    const interval = setInterval(checkInactivity, 60 * 1000); // check every 10s

    return () => {
      clearInterval(interval);
      events.forEach(event => window.removeEventListener(event, updateActivity));
    };
  }, [navigate, INACTIVITY_LIMIT]);

  return null;
};

export default SessionManager;
