# DemoImageRetrieval
This demo project is a website using the library histogram-based-image-retrieval

It shows :
- creation of histograms indexes based on grey level or color level
- use of indexes created to retrieve closest images of an given image

It contain a directory with a base of 10 000 images.

Buit with Spring Boot and Bootstrap

To launch this website, please refer to the following steps :


- Adding histogram-based-image-retrieval.jar to your maven repository
```
./mvnw install:install-file -Dfile=src/main/resources/histogram-based-image-retrieval.jar -DgroupId=img-retrieval -DartifactId=com.histobasedimageretrieval -Dversion=1.0 -Dpackaging=jar
```
- Generate the jar :
```
./mvnw package
```
- Run :
```
java -jar target/demo-0.0.1-SNAPSHOT.jar
```
