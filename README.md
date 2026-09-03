# GYM

Sistema de monitoramento financeiro desenvolvido com **Java e Spring Boot**, criado para acompanhar ganhos, gastos e movimentações financeiras de usuários de forma simples e segura.

O projeto possui autenticação com **JWT**, gerenciamento de usuários e visualização de dados financeiros por meio de gráficos.

## Funcionalidades

* Cadastro de usuários
* Login e autenticação
* Autorização utilizando JWT
* Controle de acesso às rotas
* Registro de ganhos
* Registro de gastos
* Consulta de movimentações financeiras
* Cálculo de saldo
* Dashboard financeiro
* Visualização de ganhos e gastos em gráfico
* Dados financeiros separados por usuário

## Tecnologias

### Back-end

* Java
* Spring Boot
* Spring Web
* Spring Security
* JWT
* JPA / Hibernate
* Maven

### Banco de dados

* PostgreSQL

### Front-end

* React.js
* JavaScript
* HTML
* CSS

## Arquitetura

O projeto segue uma organização em camadas para separar as responsabilidades da aplicação:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Principais responsabilidades:

* **Controller:** recebe e responde às requisições HTTP
* **Service:** concentra as regras de negócio
* **Repository:** realiza o acesso ao banco de dados
* **Entity:** representa as entidades persistidas
* **DTO:** realiza a transferência de dados entre as camadas
* **Security:** autenticação, autorização e gerenciamento JWT

## Autenticação

A autenticação da aplicação é realizada utilizando **Spring Security e JWT**.

Fluxo básico:

```text
Usuário realiza login
        ↓
Servidor valida as credenciais
        ↓
JWT é gerado
        ↓
Cliente recebe o token
        ↓
Token é enviado nas próximas requisições
        ↓
Spring Security valida o acesso
```

As rotas protegidas exigem o envio do token:

```http
Authorization: Bearer <token>
```

## Dashboard

O dashboard apresenta um resumo da situação financeira do usuário, incluindo:

* Total de ganhos
* Total de gastos
* Saldo disponível
* Histórico de movimentações
* Comparação entre receitas e despesas
* Gráfico financeiro

Exemplo:

```text
Ganhos: R$ 5.000,00
Gastos: R$ 3.200,00
Saldo:  R$ 1.800,00
```

## Estrutura de dados

### Usuário

```text
id
nome
email
senha
```

### Movimentação

```text
id
descricao
valor
tipo
data
usuario
```

O tipo da movimentação pode ser:

```text
RECEITA
DESPESA
```

## Principais endpoints

### Autenticação

```http
POST /auth/register
POST /auth/login
```

### Usuários

```http
GET /users/{id}
PUT /users/{id}
DELETE /users/{id}
```

### Movimentações

```http
GET    /transactions
POST   /transactions
GET    /transactions/{id}
PUT    /transactions/{id}
DELETE /transactions/{id}
```

### Dashboard

```http
GET /dashboard
```

## Executando o projeto

### Pré-requisitos

Tenha instalado:

* Java 17+
* Maven
* PostgreSQL
* Git

Clone o projeto:

```bash
git clone <URL_DO_REPOSITORIO>
```

Entre na pasta:

```bash
cd GYM
```

Configure o banco de dados no arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gym
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

Execute:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

## Segurança

O projeto utiliza:

* Spring Security
* Autenticação JWT
* Senhas criptografadas
* Proteção de endpoints
* Separação de dados por usuário
* Validação de requisições

## Objetivo do projeto

O GYM foi desenvolvido com o objetivo de aplicar conceitos utilizados no desenvolvimento de aplicações back-end modernas com Java, incluindo:

* Orientação a Objetos
* APIs RESTful
* Spring Boot
* Spring Security
* JWT
* JPA e Hibernate
* Banco de dados relacional
* Arquitetura em camadas
* Autenticação e autorização
* Integração entre front-end e back-end

## Autor

**Gustavo Tagliatti Sampaio**

GitHub: github.com/Gustavo-ts
Portfólio: gustavots-portfolio.vercel.app
LinkedIn: linkedin.com/in/gustavo-tagliatti-sampaio-8989aa323
