
FROM amd64/maven:3.8.6-openjdk-11
WORKDIR usr/app
#first dot means take all code from the root and second dot means paste usr/app
COPY . .
#how to run maven through spring boot, this is Syntax.
ENTRYPOINT ["mvn", "spring-boot:run"]