# Tasks API

API para gerenciar tarefas feita com Spring Boot.

---

## Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* Swagger/OpenAPI
* JUnit
* Mockito
* JaCoCo
* Render

---

## Como rodar

Pré-requisitos:

* Java 17
* Maven

```bash
git clone <URL_DO_REPO>
cd tasks-api
mvn spring-boot:run
```

A API vai rodar em:

```text
http://localhost:8080
```

Documentação Swagger:

```text
http://localhost:8080/swagger-ui.html
```

---

## Rotas

| Método   | Rota                    | O que faz                 |
| -------- | ----------------------- | ------------------------- |
| `POST`   | `/tasks`                | Cria uma tarefa           |
| `GET`    | `/tasks`                | Lista todas as tarefas    |
| `GET`    | `/tasks/{id}`           | Busca uma tarefa pelo ID  |
| `PUT`    | `/tasks/{id}`           | Atualiza uma tarefa       |
| `PATCH`  | `/tasks/{id}/status`    | Atualiza apenas o status  |
| `DELETE` | `/tasks/{id}`           | Deleta uma tarefa         |
| `GET`    | `/tasks/filter?status=` | Filtra tarefas por status |

Os status possíveis são:

* `PENDING`
* `IN_PROGRESS`
* `DONE`

---

## Exemplos

### Criar tarefa

```http
POST http://localhost:8080/tasks
```

Body:

```json
{
  "title": "Estudar Spring Boot",
  "description": "Capítulos 1 a 5"
}
```

### Listar todas

```http
GET http://localhost:8080/tasks
```

### Atualizar status

```http
PATCH http://localhost:8080/tasks/1/status?status=DONE
```

### Deletar

```http
DELETE http://localhost:8080/tasks/1
```

---

## Testes

Executar todos os testes:

```bash
mvn test
```

O relatório de cobertura fica em:

```text
target/site/jacoco/index.html
```

---

## Deploy

**Plataforma:** Render

**URL:** `https://tasks-api.onrender.com`
*(atualizar após o deploy)*

O projeto utiliza o arquivo `render.yaml` para configurar automaticamente a aplicação e o banco PostgreSQL.

### Passo a passo

1. Criar uma conta no Render e conectar ao GitHub.
2. Subir o projeto para o GitHub:

```bash
git init
git add .
git commit -m "primeiro commit"
git remote add origin <URL_DO_REPO>
git push -u origin main
```

3. No Render, clicar em **New → Blueprint**.
4. Selecionar o repositório.
5. O Render detectará o arquivo `render.yaml` e configurará os recursos automaticamente.
6. Aguardar o término do build e acessar a URL gerada.

> No plano gratuito, o serviço pode entrar em modo de suspensão após um período sem uso. A primeira requisição pode levar alguns segundos para responder.

---

## Divisão de tarefas

| Membro             | O que fez                        |
| ------------------ | -------------------------------- |
| Nome do integrante | Model, entidade JPA e Repository |
| Nome do integrante | Service e regras de negócio      |
| Nome do integrante | Controller e rotas               |
| Nome do integrante | DTOs e Deploy                    |
| Nome do integrante | Testes de Service                |
| Nome do integrante | Testes de Controller e README    |

---

## Autor(es)

Projeto desenvolvido para fins acadêmicos na disciplina de Desenvolvimento Web utilizando Spring Boot.
