import React, { useState } from "react";
import SearchBar from "../components/SearchBar";
import FileList from "../components/FileList";
import SendBox from "../components/SendBox";
import "./styles/Home.css"


export default function Home() {
  const [searchQuery, setSearchQuery] = useState("");
  const [files, setFiles] = useState([]);

  const handleSend = (data) => {
    setFiles(prev => [...prev, {
      id: prev.length + 1,
      name: data.file ? data.file.name : "Text Message",
      type: data.file ? data.file.type : "text",
      content: data.text,
      url: data.file ? data.file.url : null,
      uploadTime: new Date().toLocaleString()
    }]);
  };

  return (
    <div className="home">
      <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
      <FileList files={files} searchQuery={searchQuery} />
      <SendBox onSend={handleSend} />
    </div>
  );
}
