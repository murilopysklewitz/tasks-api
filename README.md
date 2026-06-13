# Tasks API

API para gerenciamento de tarefas desenvolvida com Spring Boot.

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
* Supabase

---

## Como rodar

### Pré-requisitos

* Java 17
* Maven

Clone o repositório:

```bash
git clone https://github.com/murilopysklewitz/tasks-api.git
cd tasks-api
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

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

O relatório de cobertura fica disponível em:

```text
target/site/jacoco/index.html
```

---

## Deploy

**Plataforma da API:** Render

**URL da aplicação:**

https://tasks-api-zszh.onrender.com/

O deploy da aplicação foi realizado utilizando o Render.

O banco de dados PostgreSQL utilizado pela API está hospedado no Supabase e conectado à aplicação por meio das variáveis de ambiente configuradas no Render.

O projeto utiliza o arquivo `render.yaml` para automatizar a configuração do serviço durante o deploy.

### Passo a passo

1. Criar uma conta no Render e conectar ao GitHub.
2. Subir o projeto para o GitHub:

```bash
git init
git add .
git commit -m "primeiro commit"
git remote add origin https://github.com/murilopysklewitz/tasks-api.git
git push -u origin main
```

3. No Render, selecionar **New → Blueprint**.
4. Escolher o repositório do projeto.
5. O Render detectará automaticamente o arquivo `render.yaml`.
6. Configurar as variáveis de ambiente para conexão com o banco PostgreSQL hospedado no Supabase.
7. Aguardar a conclusão do build e acessar a URL gerada pelo serviço.

> No plano gratuito do Render, o serviço pode entrar em modo de suspensão após um período sem uso. A primeira requisição pode levar alguns segundos para responder.

---

## Divisão de tarefas

| Membro             | O que fez                        |
| ------------------ | -------------------------------- |
|      Eduardo       | Model                            |
|     Guilherme      | Repository                       |
|      Murilo        | Service e Exception              |
|      Julia         | Controller e DTO                 |
|  Murilo Pysklewitz | Testes                           |
|  Erick Ferreira    | README                           |

---


Projeto desenvolvido para fins acadêmicos na disciplina de **Programação Orientada a Objetos**, utilizando Spring Boot para construção da API e PostgreSQL para persistência dos dados.
