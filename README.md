# POC Kafka

Serviços desenvolvidos para aplicar os conceitos do Apache Kafka.

- `api`: Utilizado como [_producer_](https://docs.confluent.io/platform/current/clients/producer.html) do projeto.
- `worker`: Utilizado como [_consumer_](https://docs.confluent.io/platform/current/clients/consumer.html) do projeto.

---

## Dependências

- Necessário ter o `docker-compose -v` funcionando corretamente.

## Passo a passo

- Clone o repositório (`$ git clone git@github.com:gustavo-flor/poc-kafka.git`);

- Acesse a pasta clonada (`$ cd poc-kafka`) 🗃️;

- Execute o `docker-compose up -d` para subir o broker do Kafka com Zookeeper;

- Acesse o subprojeto `api` e execute o comando (`$ ./mvnw spring-boot:run`), **necessário ter a porta 8080 disponível**;

- Acesse o subprojeto `worker` e execute o comando (`$ ./mvnw spring-boot:run`); 

- Estamos prontos para utilizar a aplicação 🎉.

## Endpoints

> POST | http://localhost:8080/login

````json5
{
    "name": "String"
}
````

Este endpoint pode retornar randomicamente um dos dois status `200` ou `400`, cada um deles gerando uma mensagem diferente para o Kafka respectivamente nos tópicos `poc-kafka_login-success` e `poc-kafka_login-failure`.

---

> POST | http://localhost:8080/logout

Este endpoint retorna status `200` e gera uma mensagem para o Kafka no tópico `poc-kafka_logout`.

## Chegamos ao fim 🎉

Fique a vontade para contribuir com essa POC implementando novos conceitos, até maisss!!
