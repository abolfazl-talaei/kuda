FROM openjdk:11
VOLUME /tmp
COPY target/app-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/app.jar
RUN sh -c 'touch /usr/local/tomcat/webapps/app.jar'
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=main -jar /usr/local/tomcat/webapps/app.jar"]