# 🚀 Gerenciador de Tarefas API

Uma API RESTful robusta para gerenciamento de tarefas, desenvolvida seguindo as melhores práticas de mercado e arquitetura em camadas.

Este projeto demonstra a construção de um Backend profissional utilizando o ecossistema **Spring Boot 3** e banco de dados **PostgreSQL**, focando em qualidade de código, validações e documentação.

## 🛠️ Tecnologias Utilizadas

* **Java 17/21** - Linguagem base.
* **Spring Boot 3.3.5** - Framework principal.
* **Spring Data JPA** - Camada de persistência e ORM.
* **PostgreSQL** - Banco de dados relacional.
* **Bean Validation** - Validação de dados de entrada (`@Valid`, `@NotBlank`).
* **SpringDoc OpenAPI (Swagger)** - Documentação automática da API.
* **Maven** - Gerenciamento de dependências.

## 🏗️ Arquitetura e Padrões

O projeto foi estruturado utilizando **Layered Architecture** (Arquitetura em Camadas) para garantir a separação de responsabilidades:

* **Controller Layer:** Responsável apenas por receber as requisições HTTP e devolver as respostas (REST).
* **Service Layer:** Contém as **Regras de Negócio** (ex: não permitir deletar tarefas concluídas).
* **Repository Layer:** Interface de comunicação com o Banco de Dados.
* **DTOs & Exception Handlers:** Tratamento global de erros para devolver respostas JSON amigáveis ao cliente.

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior instalado.
* Maven instalado.
* PostgreSQL rodando na porta 5432.

### 1. Configuração do Banco de Dados
Crie um banco de dados no PostgreSQL chamado `estudos_java`:

```sql
CREATE DATABASE estudos_java;

Verifique o arquivo `src/main/resources/application.properties` e ajuste seu usuário/senha se necessário:

```properties
spring.datasource.username=postgres
spring.datasource.password=sua_senha_aqui
```

### 1. Executando a aplicação
No terminal dentro da pasta do projeto:

```
Bash

mvn spring-boot:run
```
Ou execute a classe ``` GerenciadorTarefasApiApplication``` pela sua IDE (IntelliJ/Eclipse)

📚 Documentação (Swagger UI)

Com a aplicação rodando, acesse a documentação interativa para testar os endpoints:

👉 http://localhost:8080/swagger-ui/index.html

### Rotas

```GET,/tarefas,Lista todas as tarefas cadastradas.```
```POST,/tarefas,Cria uma nova tarefa (Requer JSON).```
```PUT,/tarefas/{id},Atualiza uma tarefa existente.```
```DELETE,/tarefas/{id},Remove uma tarefa (Possui validação de negócio). ```

```
JSON

{
  "descricao": "Estudar Spring Boot e AWS",
  "concluida": false
}
```

Desenvolvido como parte de estudos avançados em Engenharia de Software.