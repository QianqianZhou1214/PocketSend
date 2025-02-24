import React, { useState } from "react";
import { Paperclip, Send } from "lucide-react";

export default function SendBox({ onSend }) {
  const [message, setMessage] = useState("");
  const [image, setImage] = useState(null);

  const handleChange = (e) => {
    setMessage(e.target.value);
  };

  const handleSend = () => {
    if (message.trim() !== "" || image) {
      onSend({ text: message, image });
      setMessage("");
      setImage(null);
    }
  };


  const handlePaste = (event) => {
    const items = event.clipboardData.items;
    for (const item of items) {
      if (item.type.startsWith("image/")) {
        const file = item.getAsFile();
        const reader = new FileReader();
        reader.onload = () => {
          setImage(reader.result);
        };
        reader.readAsDataURL(file);
      }
    }
  };

  const handleFileSelect = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        setImage(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div className="sendbox">
      <label htmlFor="file-upload" className="add-file-btn">
        <Paperclip size={24} />
      </label>
      <input id="file-upload" type="file" accept="image/*" onChange={handleFileSelect} hidden />

      <div className="input-area">
        <input
          type="text"
          className="sendbox-input"
          placeholder="Type messages or paste images here..."
          value={message}
          onChange={handleChange}
          onPaste={handlePaste}
        />

        {image && <img src={image} alt="Pasted" className="preview-image" />}

      </div>

      <button className="sendbox-btn" onClick={handleSend}>
        <Send size={24} />
      </button>

    </div>
  );
  


}