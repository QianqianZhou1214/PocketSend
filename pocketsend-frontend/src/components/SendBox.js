import React, { useState } from "react";

export default function SendBox({ onSend }) {
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setMessage(e.target.value);
  };
  


}