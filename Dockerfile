FROM tomcat:9.0-jdk17

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR to webapps folder
COPY target/erpsoft-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/erpsoft.war

# Expose port
EXPOSE 8080
