import React, { useState, useEffect, useRef } from "react";
import SearchBar from "../components/SearchBar";
import FileList from "../components/FileList";
import SendBox from "../components/SendBox";
import "./styles/Home.css"
import SessionManager from "../components/SessionManager";


export default function Home() {
  const [searchQuery, setSearchQuery] = useState("");
  const [files, setFiles] = useState([]);
  const wsRef = useRef(null);

  const fetchFiles = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/files");
      if (response.ok) {
        const data = await response.json();
        setFiles(data);
      } else {
        console.error("Fetching files failed: ", response.statusText);
      }
    } catch (error) {
      console.error("Fetching files failed: ", error);
    }
  };

  useEffect(() => {
    fetchFiles();

    const socket = new WebSocket("ws://localhost:8080/ws");
    wsRef.current = socket;

    socket.onopen = () => {
      console.log("WebSocket connection established");
    };

    socket.onmessage = (event) => {
      console.log("WebSocket message received: ", event.data);
      fetchFiles();
    };

    socket.onclose = () => {
      console.log("WebSocket connection closed");
    };

    return () => {
      socket.close();
    };
  }, []);

  const handleSend = async (data) => {
    const formData = new FormData();
    if (data.text && data.text.trim() !== "") {
      formData.append("text", data.text);
    }
    if (data.file) {
      formData.append("file", data.file);
    }

    try {
      const response = await fetch("http://localhost:8080/api/files/upload", {
        method: "POST",
        body: formData,
      });
      console.log("Response status:", response.status);

      if (response.ok) {
        await fetchFiles();
      } else {
        console.error("Uploading failed: ", response.statusText);
      }
    } catch (error) {
      console.error("Uploading failed: ", error);
    }
  };

  const handleDelete = async (fileId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/files/${fileId}`, {
        method: "DELETE",
      });
      if (response.ok) {
        setFiles(prevFiles => prevFiles.filter(file => file.id !== fileId));
      } else {
        console.error("Deleting failed: ", response.statusText)
      }
    } catch (error) {
      console.error("Deleting failed: ", error);
    }
  };

  return (
    <div className="home">
      <SessionManager />
      <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
      <FileList files={files} searchQuery={searchQuery} onDelete={handleDelete} />
      <SendBox onSend={handleSend} />
    </div>
  );
}
