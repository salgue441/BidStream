# Multi-stage build for optimal image size
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline -B

# Copy source code & build application
COPY src ./src
RUN ./mvnw clean package -DskipTests -B
RUN java -Djarmode=layertools -jar target/*.jar extract

# =====================
# Production Stage
# =====================
FROM eclipse-temurin:21-jre-alpine AS production
RUN addgroup -g 1001 -S appgroup && \
  adduser -S appuser -u 1001 -G appgroup

# Install required packages
RUN apk add --no-cache \
  curl \
  tzdata \
  && rm -rf /var/cache/apk/*

# Environment configuration
ENV TZ=UTC
WORKDIR /app

COPY --from=builder --chown=appuser:appgroup /app/dependencies/ ./
COPY --from=builder --chown=appuser:appgroup /app/spring-boot-loader/ ./
COPY --from=builder --chown=appuser:appgroup /app/snapshot-dependencies/ ./
COPY --from=builder --chown=appuser:appgroup /app/application/ ./

USER appuser
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/api/actuator/health || exit 1

# JVM options for production & run app
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport -XX:MaxRAMPercentage=70.0"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]