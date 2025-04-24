#!/bin/bash
echo "Descargando Payara Micro..."
mkdir -p server
curl -L https://repo1.maven.org/maven2/fish/payara/distributions/payara-web/6.2024.12/payara-web-6.2024.12-sources.jar -o server/payara-micro-latest.jar
echo "Payara Micro descargado correctamente."