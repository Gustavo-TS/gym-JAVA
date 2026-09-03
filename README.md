# GYM

API REST para controle financeiro pessoal, desenvolvida com Java e Spring Boot. A aplicação permite cadastrar usuários, organizar categorias e registrar transações de receitas e despesas. As rotas protegidas usam autenticação stateless com JWT.

## Tecnologias

- Java 17
- Spring Boot 3.5.7
- Spring Web
- Spring Security
- Spring Data JPA / Hibernate
- JWT (JJWT)
- MySQL
- Gradle
- H2 para execução de testes

## Funcionalidades

- Cadastro e login de usuários
- Senhas armazenadas com BCrypt
- Autenticação e autorização com JWT
- Cadastro e consulta de categorias
- Criação, atualização, consulta e exclusão de transações
- Separação das transações por usuário autenticado
- Resumo de receitas, despesas e saldo

## Estrutura do projeto

```text
src/main/java/com/gym
├── controller   # Endpoints HTTP
├── dto          # Objetos de entrada e resposta
├── model        # Entidades JPA
├── repository   # Acesso ao banco de dados
└── security     # Configuração do Spring Security e JWT
```

## Configuração

### Pré-requisitos

- JDK 17 ou superior
- MySQL 8 ou superior
- Git

Crie um banco chamado `gym` no MySQL e ajuste as credenciais em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gym
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

O Hibernate está configurado com `ddl-auto=update`, portanto as tabelas são criadas ou atualizadas automaticamente ao iniciar a aplicação. Em ambientes compartilhados ou de produção, substitua as credenciais de exemplo e prefira variáveis de ambiente ou um gerenciador de segredos.

## Executando

No Windows:

```powershell
.\gradlew.bat bootRun
```

No Linux ou macOS:

```bash
./gradlew bootRun
```

A API ficará disponível em `http://localhost:8080`.

Para executar os testes:

```bash
./gradlew test
```

No Windows, use `.\gradlew.bat test` no PowerShell.

## Autenticação

As rotas `/auth/**` são públicas. Todas as outras rotas exigem um token JWT no cabeçalho:

```http
Authorization: Bearer <token>
```

### Cadastrar usuário

```bash
curl -X POST http://localhost:8080/auth/register \
        -H "Content-Type: application/json" \
        -d '{"nome":"Maria","email":"maria@example.com","senha":"senha123"}'
```

### Fazer login

```bash
curl -X POST http://localhost:8080/auth/login \
        -H "Content-Type: application/json" \
        -d '{"email":"maria@example.com","senha":"senha123"}'
```

O login retorna um objeto com o token. Use o valor de `token` nas requisições protegidas.

## Endpoints

### Usuários

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/usuarios` | Lista os usuários |
| `GET` | `/usuarios/{id}` | Busca um usuário por ID |
| `POST` | `/usuarios` | Cria um usuário |
| `PUT` | `/usuarios/{id}` | Atualiza nome e e-mail |
| `DELETE` | `/usuarios/{id}` | Remove um usuário |

### Categorias

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/categorias` | Lista as categorias |
| `POST` | `/categorias` | Cria uma categoria |

Uma categoria possui `descricao` e `tipo`, sendo o tipo normalmente `ganho` ou `gasto`.

### Transações

Todas as rotas abaixo exigem autenticação. O usuário da transação é definido pelo token, e não pelo corpo da requisição.

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/transacoes` | Lista as transações do usuário autenticado |
| `POST` | `/transacoes` | Cria uma transação |
| `PUT` | `/transacoes/{id}` | Atualiza uma transação do usuário |
| `DELETE` | `/transacoes/{id}` | Remove uma transação do usuário |
| `GET` | `/transacoes/resumo` | Retorna receitas, despesas e saldo |

Exemplo de criação de transação:

```bash
curl -X POST http://localhost:8080/transacoes \
        -H "Content-Type: application/json" \
        -H "Authorization: Bearer <token>" \
        -d '{"valor":150.50,"descricao":"Mensalidade","data":"2026-09-03","categoria":{"id":1}}'
```

O resumo retorna os campos `nome`, `income`, `expense` e `balance`.

## Observações

- Não existe frontend neste repositório; o projeto contém somente a API backend.
- O JWT tem validade de 24 horas.
- A aplicação permite CORS para qualquer origem na configuração atual. Restrinja `allowedOrigins` antes de publicar em produção.
- O projeto ainda não possui documentação OpenAPI/Swagger.

