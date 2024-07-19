# URL Shortener API

Este projeto é uma API REST para encurtamento de URLs, desenvolvida usando Java e Spring Boot. Ele permite encurtar URLs, redirecionar URLs curtas para URLs completas e obter estatísticas de acesso.

## Funcionalidades

-   **Encurtar URL**: Cria uma URL curta para uma URL longa fornecida.
-   **Redirecionar URL**: Redireciona uma URL curta para a URL longa correspondente.
-   **Contar Acessos**: Obtém o número de acessos para uma URL curta.
-   **Contar Acessos por Data**: Obtém o número de acessos para uma URL curta em uma data específica.

## Tecnologias Utilizadas

-   **Java 17**
-   **Spring Boot 3.3.2**
-   **JPA/Hibernate**
-   **PostgreSQL**
-   **Lombok**

## Estrutura do Projeto

### 1. **Controladores**

Os controladores são responsáveis por expor as APIs e lidar com as solicitações HTTP. Veja os principais controladores:

-   **`UrlController`**: Manipula endpoints relacionados ao encurtamento de URLs, redirecionamentos e estatísticas.

### 2. **Serviços**

Os serviços contêm a lógica de negócios e interagem com o repositório.

-   **`UrlService`**: Gerencia operações relacionadas a URLs, como criação, redirecionamento e contagem de acessos.

### 3. **Entidades**

As entidades representam as tabelas do banco de dados.

-   **`UrlEntity`**: Representa uma URL encurtada com suas informações associadas.

### 4. **Repositórios**

Os repositórios fornecem a interface para interagir com o banco de dados.

-   **`UrlRepository`**: Interface JPA para acessar dados das URLs.
-

## Endpoints da API

### 1. **POST /url**

Cria uma nova URL curta.

-   **Request Body**: JSON com o campo `url` (URL completa).
-   **Response**: JSON com o campo `shortUrl` (URL curta).

**Exemplo de Request:**

`{
"url": "https://github.com/diogomelo09"
}`

**Exemplo de Response:**

`{
"shortUrl": "E8HJjD5e1"
}`

### 2. **GET /url/{shortUrl}**

Redireciona para a URL completa correspondente ao `shortUrl`.

-   **Path Variable**: `shortUrl` (URL curta).
-   **Response**: Redireciona para a URL completa.

**Exemplo de Request:**

`
"http://localhost:8080/url/E8HJjD5e1"
`

**Exemplo de Response:**

`
"https://github.com/diogomelo09"
`

### 3. **GET /url/access/{shortUrl}**

Obtém a contagem de acessos para a URL curta.

-   **Path Variable**: `shortUrl` (URL curta).
-   **Response**: Número de acessos.

**Exemplo de Request:**

`
"http://localhost:8080/url/access/E8HJjD5e1"
`

**Exemplo de Response:**

`
6
`


### 4. **GET /url/access/{shortUrl}/date/{localDate}**

Obtém a contagem de acessos para a URL curta em uma data específica.

-   **Path Variables**:
    -   `shortUrl` (URL curta)
    -   `localDate` (Data no formato ISO-8601)
-   **Response**: Número de acessos na data especificada.

**Exemplo de Request:**

`
"http://localhost:8080/url/access/E8HJjD5e1/date/2024-07-19"
`

**Exemplo de Response:**

`
4
`

## Configuração

### Banco de Dados

O projeto utiliza o PostgreSQL para desenvolvimento. Configure o banco de dados no arquivo `application.properties` se estiver usando um banco de dados diferente.

### Executando o Projeto

1.  **Clone o repositório:**


    `git clone https://github.com/username/url-shortener-api.git` 

2.  **Navegue para o diretório do projeto:**


    `cd url-shortener-api` 

3.  **Compile e execute o projeto:**


    `./mvnw spring-boot:run` 

4.  **Acesse a API:** A API estará disponível em

    `http://localhost:8080`.


## Testes

Os testes unitários podem ser executados usando o Maven:


`./mvnw test` 
