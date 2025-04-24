#!/bin/bash

# Ruta al archivo JAR de Payara Micro
PAYARA_JAR="server/payara-micro-6.2025.4.jar"

# Comando para iniciar Payara Micro con el WAR desplegado
java -jar $PAYARA_JAR --deploy target/punto-venta-1.0.war --port 8080