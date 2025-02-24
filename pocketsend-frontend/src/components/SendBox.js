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
      const reader = new FileReader();
      reader.onload = () => {
        setFile({
          name: uploadedFile.name,
          type: uploadedFile.type || "application/octet-stream",
          url: URL.createObjectURL(uploadedFile),
        });
      };
      reader.readAsDataURL(uploadedFile);
    }
  };

  return (
    <div className="sendbox">
      <label htmlFor="file-upload" className="add-file-btn">
        <Paperclip size={24} /> Add
      </label>
      <input id="file-upload" type="file" onChange={handleFileSelect} hidden />

      <div className="input-area">
        <input
          type="text"
          className="sendbox-input"
          placeholder="Type messages or paste images here..."
          value={message}
          onChange={handleChange}
          onPaste={handlePaste}
        />

        {file && (
          <div className="file-preview">
            <span>{file.name}</span>
          </div>
        )}

      </div>

      <button className="sendbox-btn" onClick={handleSend}>
        <Send size={24} /> Send
      </button>

    </div>
  );
  


}