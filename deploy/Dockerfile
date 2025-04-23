# Usa una imagen base con Tomcat y Java 17
FROM tomcat:9.0-jdk17

# Borra las apps por defecto
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia el WAR renombrado a ROOT.war
COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war

# Expón el puerto 8080
EXPOSE 8080

# Inicia Tomcat
CMD ["catalina.sh", "run"]