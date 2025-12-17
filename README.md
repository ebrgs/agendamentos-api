# 📅 Agendamentos API

Uma API RESTful robusta para gerenciamento de agendamentos de serviços, desenvolvida com **Spring Boot 3**, **PostgreSQL** e **Docker**.

O projeto foca em regras de negócio temporais, validações complexas e uma arquitetura preparada para ambientes corporativos.

## 🚀 Tecnologias Utilizadas

* **Java 21** - Linguagem base.

* **Spring Boot 3.3.5** - Framework principal.

* **Spring Data JPA** - Persistência de dados.

* **PostgreSQL** - Banco de dados relacional.

* **Docker & Docker Compose** - Containerização completa da aplicação e banco.

* **Bean Validation** - Validações (`@Future`, `@NotBlank`).

* **JUnit 5 & Mockito** - Testes Unitários e de Integração.

* **SpringDoc OpenAPI (Swagger)** - Documentação automática.

## 🧠 Regras de Negócio

O sistema implementa regras estritas para garantir a integridade da agenda:

1. **Validação Temporal:** Não é possível criar agendamentos no passado (`@Future`).

2. **Detecção de Conflitos:** O sistema impede que dois clientes agendem exatamente o mesmo horário.

3. **Cancelamento Seguro:** Regras para impedir cancelamentos indevidos (ex: agendamentos passados).

## ⚙️ Como Rodar o Projeto

### Opção A: Via Docker (Recomendado 🐳)

Se tiver o Docker instalado, você não precisa configurar banco de dados nem Java manualmente.

1. Na raiz do projeto, execute:
``` 
docker-compose up --build
```
2. A aplicação subirá na porta **8080** e o banco na **5432**.

### Opção B: Manualmente (Via Maven)

Pré-requisitos: Java 17+ e PostgreSQL instalado.

1. Crie o banco de dados:
``` 
CREATE DATABASE estudos_java;
```
2. Configure o `application.properties` com sua senha local.

3. Execute:
```
mvn spring-boot:run
```
## 📚 Documentação Interativa (Swagger)

Com a aplicação rodando, acesse:
👉 [**http://localhost:8080/swagger-ui/index.html**](http://localhost:8080/swagger-ui/index.html)

## 🔌 Endpoints Principais

| Método | Rota | Descrição | 
 | ----- | ----- | ----- | 
| `GET` | `/agendamentos` | Lista todos os horários agendados. | 
| `POST` | `/agendamentos` | Cria um novo agendamento (Valida horário e conflitos). | 
| `DELETE` | `/agendamentos/{id}` | Cancela um agendamento. | 

### Exemplo de JSON (POST):

```
{ 
  "cliente": "Maria Silva", 
  "servico": "Consultoria Técnica", 
  "dataHora": "2025-12-25T14:30:00" 
}
```
## ✅ Testes

O projeto possui cobertura de testes para garantir a segurança das regras de negócio.

Para rodar os testes:
```
mvn test
```

Desenvolvido como parte de estudos avançados em Engenharia de Software Backend.
