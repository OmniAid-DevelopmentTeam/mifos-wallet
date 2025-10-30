# =============================================================================
# Production Dockerfile for Mifos Wallet Web Application
# =============================================================================
# This multi-stage Dockerfile builds a production-optimized container for the
# Kotlin/JS Compose Multiplatform web application.
#
# Build context MUST be the project root directory:
#   docker build -f cmp-web/Dockerfile -t mifos-wallet-web:latest .
#
# Run:
#   docker run -p 8080:80 mifos-wallet-web:latest
# =============================================================================

# -----------------------------------------------------------------------------
# Stage 1: Build Stage
# -----------------------------------------------------------------------------
FROM gradle:8.14-jdk21 AS builder

# Set build arguments for Gradle configuration
ARG GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.parallel=true -Dorg.gradle.caching=true"
ARG BUILD_THREADS=4

# Set working directory
WORKDIR /build

# Copy Gradle wrapper and build files first (for better layer caching)
COPY gradle gradle
COPY gradlew gradlew.bat gradle.properties settings.gradle.kts build.gradle.kts ./
COPY build-logic build-logic

# Copy version catalog and other config files
COPY gradle/libs.versions.toml gradle/libs.versions.toml

# Download dependencies (this layer will be cached if dependencies don't change)
RUN ./gradlew dependencies --no-daemon || true

# Copy the entire source code
COPY . .

# Build the production JavaScript bundle
# This creates optimized, minified JS in cmp-web/build/dist/js/productionExecutable/
RUN ./gradlew :cmp-web:jsBrowserProductionWebpack \
    --no-daemon \
    --parallel \
    --max-workers=${BUILD_THREADS} \
    --console=plain

# -----------------------------------------------------------------------------
# Stage 2: Production Stage
# -----------------------------------------------------------------------------
FROM nginx:1.27-alpine

# Copy JavaScript bundles
COPY --from=builder /build/cmp-web/build/kotlin-webpack/js/productionExecutable/*.js \
                    /build/cmp-web/build/kotlin-webpack/js/productionExecutable/*.txt \
                    /usr/share/nginx/html/

# Copy static assets (HTML, CSS, favicon)
COPY --from=builder /build/cmp-web/build/processedResources/js/main/index.html \
                    /build/cmp-web/build/processedResources/js/main/styles.css \
                    /build/cmp-web/build/processedResources/js/main/favicon.ico \
                    /usr/share/nginx/html/

# Copy WebAssembly and Skiko runtime
COPY --from=builder /build/cmp-web/build/processedResources/js/main/skiko.wasm \
                    /build/cmp-web/build/processedResources/js/main/skiko.js \
                    /build/cmp-web/build/processedResources/js/main/skiko.mjs \
                    /usr/share/nginx/html/

# Copy Compose resources
COPY --from=builder /build/cmp-web/build/processedResources/js/main/composeResources/ \
                    /usr/share/nginx/html/composeResources/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
