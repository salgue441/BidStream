#!/bin/bash

set -e

echo "🚀 Starting BidStream Development Environment"

# Check if docker is running
if ! docker info >/dev/null 2>&1; then
  echo "Docker is not running. Please start docker first"
  exit 1
fi

# Start infra services with clean state
echo "Starting infrastructure services with clean state"
docker-compose down -v 
docker-compose up -d postgres redis

# Wait for services to be ready
echo "Waiting for services to be ready"
sleep 5

# Check if PostgreSQL is ready
echo "Checking PostgreSQL connection"
until docker-compose exec postgres pg_isready -U bidstream >/dev/null 2>&1; do
  echo "Waiting for PostgreSQL"
  sleep 2
done

# Check if redis is ready
echo "Checking Redis connection"
until docker-compose exec redis redis-cli ping >/dev/null 2>&1; do
  echo "Waiting for Redis"
  sleep 2
done

echo "Infrastructure services are ready!"
echo ""
echo "🔧 Starting backend application..."
echo "Backend will be available at: http://localhost:8080"
echo "API Documentation: http://localhost:8080/swagger-ui.html"
echo ""

# Start the spring boot application
cd apps/backend
./mvnw spring-boot:run
