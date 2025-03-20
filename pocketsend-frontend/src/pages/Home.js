import React, { useState, useEffect } from "react";
import SearchBar from "../components/SearchBar";
import FileList from "../components/FileList";
import SendBox from "../components/SendBox";
import "./styles/Home.css"


export default function Home() {
  const [searchQuery, setSearchQuery] = useState("");
  const [files, setFiles] = useState([]);

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
  }, []);

  const handleSend = async (data) => {
    const formData = new FormData();
    if (data.text) {
      formData.append("text", data.text);
    }
    if (data.file) {
      formData.append("file", data.file);
    }

    console.log("📂 data.file:", data.file);
    console.log("📋 data.text:", data.text);

    // 验证文件类型是否为 File 或 Blob
    if (data.file instanceof File) {
      console.log("✅ 文件对象类型正确:", data.file.name);
    } else {
      console.error("❌ 文件对象类型错误:", typeof data.file);
    }

    formData.append("file", data.file);

    // 打印整个 FormData
    for (let pair of formData.entries()) {
      console.log("🔥 FormData key:", pair[0], "value:", pair[1]);
    }

    try {
      const response = await fetch("http://localhost:8080/api/files/upload", {
        method: "POST",
        body: formData,
      });
      if (response.ok) {
        const newFile = await response.json();
        console.log("data received", newFile);

        console.log("url:", newFile.url);

        if (!newFile.url) {
          newFile.url = `http://localhost:8080/api/files/${newFile.id}`;
        }
        setFiles(prev => [...prev, newFile]);
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
      <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />
      <FileList files={files} searchQuery={searchQuery} onDelete={handleDelete} />
      <SendBox onSend={handleSend} />
    </div>
  );
}
