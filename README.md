# mercadolibre-mutant-challenge
Mercado Libre challenge. More details in the `Examen Mercadolibre  - Mutantes.pdf` file at the root of the project.

### TECHNOLOGIES

- Java 11
- Maven 3.8.1
- Spring Boot 2.4.5
- MongoDB
### CLOUD
- AWS Elastic Beanstalk
- MongoDB Atlas

## Project Installation

1. It is required to run MongoDB in your local listening at port 27017
- To run Docker easily, run a Docker MongoDB container as shown below.
```shell
docker pull mongo
sudo docker run -it --rm --name mongo-container mongo
#to ssh into the container you can 
sudo docker exec -it mongodb bash
```
2. Compile the project
- To compile it is required to use JDK 11 
```shell
./mvnw clean package #For linux and MacOs
./mvnw.cmd clean package #Windows
```
3. To run the project, just run the following commands
```shell
java -jar ./target/mutantchallenge-0.0.1-SNAPSHOT.jar
```
### Notes
- Default spring profile is `dev`
- For a Production environment switch to the `prod` profile, but you will need to update the mongo db uri at the 
  `application-prod.yml` file.
  
## RUNNING API URLS

### isMutant Endpoint 

To consume the API run the following `curl` command
```shell
curl --location --request POST 'Mercadolibremutantchallenge-env-1.eba-i2pken3k.us-east-1.elasticbeanstalk.com/mutant' \
--header 'Content-Type: application/json' \
--data-raw '{
"dna":[
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"]
}'
```

### Stats Endpoint 

To consume the Stats API run the following command
```shell
curl --location --request GET 'Mercadolibremutantchallenge-env-1.eba-i2pken3k.us-east-1.elasticbeanstalk.com/stats'
```

### Notes
- The provided URL to check the app running are not meant to run performance test. The instances running the app are
  `t2.micro` which are too small to run a performance test.
- The instance of MongoDB is running in MongoDB Atlas in a shared cluster therefore response time may be high. 
