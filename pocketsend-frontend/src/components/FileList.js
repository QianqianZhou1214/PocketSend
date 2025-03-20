import React from "react";
import "./styles/FileList.css"
import { Copy, ArrowDownToLine, Trash2 } from "lucide-react";

export default function FileList ({ files, searchQuery, onDelete }) {
  const filteredFiles = files.filter(file =>
    file.content?.toLowerCase().includes(searchQuery.toLowerCase()) || 
    file.filename?.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="file-list">
      {filteredFiles.length > 0 ? (
        filteredFiles.map((file) => (
          <div key={file.id} className="file-item">
            <div className="file-header">
              <span className="upload-time">{file.uploadedAt}</span>
            </div>

            <div className="file-content">
              {file.filetype === "text" && (
                <p className="file-text">{file.content}</p>
              )}

              {file.filetype.startsWith("image/") && (
                <img src={file.url} alt={file.filename} className="file-thumbnail" />
              )}

              {file.filetype !== "text" && !file.filetype.startsWith("image/") && (
                <span className="file-name">{file.filename}</span>
              )}
            </div>

            <div className="file-actions">
              {file.filetype === "text" && (
                <button className="copy-btn" onClick={() => navigator.clipboard.writeText(file.content)}>
                  <Copy size={20} color="#fff" />
                </button>
              )}

              {file.url && (
                <a href={file.url} download={file.filename} className="download-btn">
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