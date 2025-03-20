import React, { useState } from "react";
import { Paperclip, Send } from "lucide-react";
import "./styles/SendBox.css"


export default function SendBox({ onSend }) {
  const [message, setMessage] = useState("");
  const [file, setFile] = useState(null);

  const handleChange = (e) => {
    setMessage(e.target.value);
  };

  const handleSend = () => {
    if (message.trim() !== "" || file) {
      onSend({ text: message, file });
      setMessage("");
      setFile(null);
    }
  };


  const handlePaste = (event) => {
    const items = event.clipboardData.items;
    for (const item of items) {
      if (item.type.startsWith("image/")) {
        const pastedFile = item.getAsFile();
        const reader = new FileReader();
        reader.onload = () => {
          setFile({ name: "Pasted Image", type: pastedFile.type, url: reader.result });
        };
        reader.readAsDataURL(pastedFile);
      }
    }
  };

  const handleFileSelect = (event) => {
    const uploadedFile = event.target.files[0];
    if (uploadedFile) {
      setFile(uploadedFile);
    };
  };

  return (
    <div className="sendbox">
      <div className="input-container">
        <label htmlFor="file-upload" className="add-file-btn">
          <Paperclip size={24} />
        </label>
        <input id="file-upload" type="file" onChange={handleFileSelect} hidden />

        <input
          type="text"
          className="sendbox-input"
          placeholder="Type a message or paste an image here..."
          value={message}
          onChange={handleChange}
          onPaste={handlePaste}
        />

        <label className="sendbox-btn" onClick={handleSend}>
          <Send size={24} />
        </label>
      </div>   
    </div>
  );

}