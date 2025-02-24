import React from "react";
import "./styles/FileList.css"
import { Copy, ArrowDownToLine } from "lucide-react";

export default function FileList ({ files }) {
  return (
    <div className="file-list">
      {files.length > 0 ? (
        files.map((file) => (
          <div key={file.id} className="file-item">
            <div className="file-header">
              <span className="upload-time">{file.uploadTime}</span>
            </div>

            <div className="file-content">
              {file.type.startsWith("image/") ? (
                <img src={file.url} alt={file.name} className="file-thumbnail" />
              ) : (
                <span className="file-name">{file.name}</span>
              )}
            </div>

            <div className="file-actions">
              {file.type === "text" && (
                <button className="copy-btn" onClick={() => navigator.clipboard.writeText(file.content)}>
                  <Copy size={20} color="#fff" />
                </button>
              )}

              {file.type !== "text" && file.url && (
                <a href={file.url} download={file.name} className="download-btn">
                  <ArrowDownToLine size={20} color="#fff" />
                </a>
              )}
            </div>
          </div>
        ))
      ) : (
        <p className="no-files">No files found</p>
      )}

    </div>
  );
}