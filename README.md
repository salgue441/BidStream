# 🚀 BidStream - Real-time Trading & Auction Platform

A modern, high-performance real-time trading and auction platform built with enterprise-grade architecture and cutting-edge technologies.

## ✨ Features

- **🔥 Real-time Bidding** - Live auction updates with WebSocket technology
- **🏛️ Enterprise Architecture** - Clean, scalable, and maintainable codebase
- **🔐 Secure Authentication** - JWT-based auth with Spring Security
- **📊 Portfolio Management** - Track investments and trading history
- **🚀 High Performance** - Optimized for concurrent operations
- **📱 Responsive Design** - Modern UI with Next.js 15 and Tailwind CSS
- **🐳 Containerized** - Docker-ready for easy deployment
- **🔍 Observable** - Comprehensive monitoring and logging

## 🏗️ Architecture

```plaintext
┌──────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Frontend       │    │   Backend        │    │   Database      │
│   (Next.js 15)   │◄──►│ (Spring Boot 3)  │◄──►│ (PostgreSQL)    │
│                  │    │                  │    │                 │
│ • React 18       │    │ • Java 21        │    │ • Redis Cache   │
│ • TypeScript     │    │ • WebSocket      │    │ • Full-text     │
│ • Tailwind CSS   │    │ • Spring Data    │    │   Search        │
│ • TanStack Query │    │ • Spring Security│    │                 │
└──────────────────┘    └──────────────────┘    └─────────────────┘
```

## 🚀 Quick Start

### Prerequisites

- Java 21+
- Node.js 18+
- Docker & Docker Compose
- Git

### 1. Clone the Repository

```bash
git clone git@github.com:salgue441/BidStream.git bid-stream
cd bit-stream
```

### 2. Start Infrastructure Services

```bash
# Start PostgreSQL, Redis, and monitoring stack
docker-compose up -d postgres redis prometheus grafana
```

### 3. Backend Setup

```bash
cd apps/backend

# Install dependencies and run tests
./mvnw clean install
./mvnw spring-boot:run
```

### 4. Frontend Setup

```bash
cd apps/frontend

# Install dependencies and start server
bun install
bun run dev
```

## 🚀 Deployment

### Docker Deployment

```bash
# Build and run with Docker Compose
docker-compose -f docker-compose.prod.yml up -d
```

### Kubernetes Deployment

```bash
# Deploy to Kubernetes
kubectl apply -f infrastructure/k8s/
```

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.
