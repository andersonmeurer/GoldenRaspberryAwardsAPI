# Golden Raspberry Awards API
## Como executar o projeto
1. Clone o repositório:
https://github.com/andersonmeurer/filmsOutsera.git
2. Execute o projeto:
./mvnw spring-boot:run
3. Acesse a API:
- Endpoint para intervalos de prêmios:
[http://localhost:8080/producers/intervals](http://localhost:8080/producers/intervals)
```
http://localhost:8080/producers/intervals
```

## Como executar os testes
 ./mvnw test
----
# Extras
### Comando curl para post do arquivo CSV
```
curl -X POST "http://localhost:8080/import" -d "filePath=./src/main/resources/movielist.csv" -H "Content-Type: application/x-www-form-urlencoded"
```
----
### View para o banco de dados 
[Clique aqui para cessar](http://localhost:8080/h2-console)
````
http://localhost:8080/h2-console
````
![Login H2](src/main/resources/loginH2.png)