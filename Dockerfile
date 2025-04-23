FROM payara/server-full:6.2023.1-jdk17

# Copiar el archivo WAR de la aplicación al servidor de Payara
COPY target/punto-venta-1.0.war /opt/payara/deployments/

# Exponer el puerto 8080
EXPOSE 8080

# Iniciar el dominio de Payara
CMD ["asadmin", "start-domain", "domain1"]