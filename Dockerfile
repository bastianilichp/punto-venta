# Usa una imagen base de Tomcat (o el servidor que prefieras)
FROM tomcat:9.0

# Copia tu archivo WAR al directorio webapps de Tomcat
COPY target/punto-venta-1.0.war /usr/local/tomcat/webapps/

# Expón el puerto 8080 (usualmente usado por Tomcat)
EXPOSE 8080

# Comando para ejecutar Tomcat
CMD ["catalina.sh", "run"]