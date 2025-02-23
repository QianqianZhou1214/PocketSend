import React from "react";

export default function SearchBar({ searchQuery, setSearchQuery }) {
  return (
    <input
      type="text"
      className="search-bar"
      placeholder="Search files..."
      value={searchQuery}
      onChange={(e) => setSearchQuery(e.target.value)}
    />
  );
}