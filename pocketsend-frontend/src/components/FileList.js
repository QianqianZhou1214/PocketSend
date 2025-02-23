import React from "react";
import "./styles/FileList.css"

export default function FileList ({ files }) {
  return (
    <div className="file-list">
      {files.length > 0 ? (
        files.map((file) => (
          <div key={file.id} className="file-item">
            <span>{file.name}</span>
            <span className="file-size">{file.size}</span>
          </div>
        ))
      ) : (
        <p className="no-files">No files found</p>
      )}

    </div>
  );
}