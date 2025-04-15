<repositories>
        <repository>
            <id>prime-repo</id>
            <name>PrimeFaces Maven Repository</name>
            <url>http://repository.primefaces.org</url>
        </repository>
</repositories>


<dependency>
     <groupId>org.primefaces</groupId>
     <artifactId>primefaces</artifactId>
     <version>11.0.0</version>
     <classifier>jakarta</classifier>
</dependency>


<dependency>
      <groupId>org.primefaces.themes</groupId>
      <artifactId>omega-theme</artifactId>
      <version>4.1.0</version>
      <classifier>jakarta</classifier>
</dependency>



1.- se puede hacer asi o usar la alternativa 2.-

mkdir .m2/repository/org/primefaces/themes/omega-theme/4.1.0/

copy omega-theme-4.1.0-jakarta.jar .m2/repository/org/primefaces/themes/omega-theme/4.1.0/

copy omega-theme-4.1.0.jar .m2/repository/org/primefaces/themes/omega-theme/4.1.0/


2.- cp -r omega-theme /home/hsalinas/.m2/repository/org/primefaces/themes
