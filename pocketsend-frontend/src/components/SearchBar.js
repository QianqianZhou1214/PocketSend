import React from "react";
import "./styles/SearchBar.css"

export default function SearchBar({ searchQuery, setSearchQuery }) {
  return (
    <div>
      <input
      type="text"
      className="search-bar"
      placeholder="Search files..."
      value={searchQuery}
      onChange={(e) => setSearchQuery(e.target.value)}
    />
    </div>
  );
}