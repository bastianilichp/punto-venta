echo "Descargando Payara Micro..."
mkdir -p server
curl -L https://search.maven.org/remotecontent?filepath=fish/payara/distributions/payara-micro/6.2024.2/payara-micro-6.2024.2.jar -o server/payara-micro.jar
echo "Payara Micro descargado correctamente."