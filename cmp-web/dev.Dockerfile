FROM gradle:8.14-jdk21 AS dev

# Ensure consistent working directory
WORKDIR /workspace

# Environment for dev server and Gradle caching
ENV GRADLE_USER_HOME=/home/gradle/.gradle \
    HOST=0.0.0.0 \
    PORT=8080

# Copy the entire project (the context MUST be the project root)
# If you want faster rebuilds, use docker-compose volumes as shown above.
COPY . /workspace

# Expose the webpack dev server port
EXPOSE 8080

# Default command: run Kotlin/JS dev server for cmp-web
# Use shell form to expand $HOST and $PORT from env and enforce stable port
CMD ["/bin/sh", "-lc", "./gradlew :cmp-web:jsBrowserDevelopmentRun --no-daemon -Dorg.jetbrains.kotlin.js.webpack.devServer.host=${HOST:-0.0.0.0} -Dkotlin.js.webpack.devServer.host=${HOST:-0.0.0.0} -Dorg.jetbrains.kotlin.js.webpack.devServer.port=${PORT:-8080} -Dkotlin.js.webpack.devServer.port=${PORT:-8080}"]
