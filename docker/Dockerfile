FROM adoptopenjdk/openjdk8
EXPOSE 8080
ADD docker-1.0-SNAPSHOT.jar japp.jar
CMD ["java", "-jar", "japp.jar"]

# docker build -t japp .
# docker run -d -p 8080:8080 -it --rm japp
# 浏览器访问 http://192.168.56.150:8080/health