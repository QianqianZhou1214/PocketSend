# PocketSend
PocketSend is a full-stack file-sharing web application that allows users to upload, view, and download text, images, and files across multiple devices. It supports real-time synchronization with WebSockets, user authentication, session timeout handling, and per-user file isolation.

## 🔧 Features
✅ Core Functionality
Upload files or plain text

View uploaded content (text, image preview, or downloadable link)

Copy, download, or delete content with one click

Real-time updates using WebSockets (no manual refresh needed)

Paste images directly into the text area

## 👥 User Management
Registration and login (via email or username)

Passwords securely stored with hashing

Session-based login with 30-minute inactivity timeout

Session activity auto-refreshes with user interaction

Each user can only access their own files

## 🧠 Architecture
Frontend: React.js

Backend: Spring Boot (Java)

Database: MySQL (Dockerized)

Deployment: Docker Compose for full stack

Real-time: WebSocket support with multi-user broadcasting

## Project Structure
- **pocketsend/**
  - `pocketsend-backend/` - Backend code (Spring Boot)
  - `pocketsend-frontend/` - Frontend code (React)
  - `mysql/` - Database initialization scripts
  - `docker-compose.yml` - Multi-container orchestration

