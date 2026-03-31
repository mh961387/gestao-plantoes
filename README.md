
# 📌 Gestão de Plantões

Sistema backend desenvolvido com Spring Boot para gerenciamento de profissionais e plantões, com aplicação de regras de negócio, validações e tratamento global de exceções.

A aplicação permite controlar escalas de trabalho de forma organizada, garantindo integridade dos dados e consistência nas operações.



## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 Database (em memória)
- Maven
- Swagger / OpenAPI
- JUnit (testes unitários)



## 🧠 Arquitetura do Projeto

O projeto segue o padrão de arquitetura em camadas:



    controller/

        ├── rest/   -> Endpoints da API (JSON)

        └── web/    -> Controllers para páginas (MVC)

    service/        -> Regras de negócio
    repository/     -> Acesso a dados (JPA)
    model/          -> Entidades do sistema
    exception/      -> Tratamento global de erros





## 📚 Funcionalidades

### 👨‍⚕️ Profissionais
- Cadastro de profissional
- Edição de dados
- Listagem de profissionais
- Busca por ID
- Exclusão de profissional
- Validação de registro único

### 🗓️ Plantões
- Cadastro de plantão
- Associação com profissional
- Definição de data e turno (MANHA, TARDE, NOITE)
- Listagem de plantões
- Exclusão de plantão



## ⚙️ Regras de Negócio

- Não permite cadastro de profissionais com o mesmo registro
- Validação de redução de carga horária
- Um plantão deve estar obrigatoriamente vinculado a um profissional
- Validação de dados antes da persistência
- Tratamento de erros centralizado


## ❗ Tratamento de Exceções

- Exception Handler global para API REST
- Tratamento separado para camada Web (MVC)
- Respostas padronizadas com status HTTP apropriados:
  - `400 Bad Request`
  - `404 Not Found`
  - `409 Conflict`

## 🗄️ Banco de Dados

A aplicação utiliza banco em memória H2.

### Acesso ao console:

👉 http://localhost:8080/h2-console

### Configurações padrão:


JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vazio)


## ▶️ Como Executar o Projeto

### ✅ Pré-requisitos

- Java 17 instalado
- Maven instalado


### 🔽 Passo a passo

```bash
# 1. Clonar o repositório
git clone https://github.com/mh961387/gestao-plantoes.git

# 2. Acessar a pasta do projeto
cd gestao-plantoes

# 3. Limpar e compilar o projeto
mvn clean install

# 4. Executar a aplicação
mvn spring-boot:run


### 🚀 Executando via IDE (IntelliJ)

1. Abrir o projeto
2. Aguardar o Maven baixar as dependências
3. Localizar a classe:

GestaoPlantaoApplication

4. Clicar em **Run ▶**


## 🔌 Endpoints principais

### 📍 Profissionais

| Método | Endpoint                | Descrição              |
| ------ | ----------------------- | ---------------------- |
| POST   | /api/profissionais      | Cadastrar profissional |
| GET    | /api/profissionais/{id} | Buscar por ID          |
| DELETE | /api/profissionais/{id} | Remover                |


### 📍 Plantões

| Método | Endpoint           | Descrição         |
| ------ | ------------------ | ----------------- |
| POST   | /api/plantoes      | Cadastrar plantão |
| GET    | /api/plantoes/{id} | Buscar por ID     |
| DELETE | /api/plantoes/{id} | Remover           |


## 🧪 Testes

* Testes unitários implementados para services
* Foco nas regras de negócio
* Validação de cenários de erro


## 👨‍💻 Autor

Projeto desenvolvido como prática de backend com foco em boas práticas, arquitetura e organização de código.

