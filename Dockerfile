# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app/bin

# Copy the application JAR file to the container
COPY target/ServidorJPA-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables for the database configuration
ENV DB_URL=jdbc:mysql://44.204.192.243:3306/arep_taller
ENV DB_USERNAME=SebZaUr
ENV DB_PASSWORD=Arep2024!@#

# Expose the port the application runs on
EXPOSE 80

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]