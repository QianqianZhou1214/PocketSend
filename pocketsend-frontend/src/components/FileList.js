import React from "react";
import "./styles/FileList.css"
import { Copy, ArrowDownToLine, Trash2 } from "lucide-react";

export default function FileList ({ files, searchQuery }) {
  const filteredFiles = files.filter(file =>
    file.content?.toLowerCase().includes(searchQuery.toLowerCase()) || 
    file.name?.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="file-list">
      {filteredFiles.length > 0 ? (
        filteredFiles.map((file) => (
          <div key={file.id} className="file-item">
            <div className="file-header">
              <span className="upload-time">{file.uploadTime}</span>
            </div>

            <div className="file-content">
              {file.type === "text" && (
                <p className="file-text">{file.content}</p>
              )}

              {file.type.startsWith("image/") && (
                <img src={file.url} alt={file.name} className="file-thumbnail" />
              )}

              {file.type !== "text" && !file.type.startsWith("image/") && (
                <span className="file-name">{file.name}</span>
              )}
            </div>

            <div className="file-actions">
              {file.type === "text" && (
                <button className="copy-btn" onClick={() => navigator.clipboard.writeText(file.content)}>
                  <Copy size={20} color="#fff" />
                </button>
              )}

              {file.url && (
                <a href={file.url} download={file.name} className="download-btn">
                  <ArrowDownToLine size={20} color="#fff" />
                </a>
              )}

              <button className="delete-btn" onClick={() => onDelete(file.id)}>
                <Trash2 size={20} color="#fff" />
              </button>
            </div>
          </div>
        ))
      ) : (
        <p className="no-files">No files found</p>
      )}

    </div>
  );
}