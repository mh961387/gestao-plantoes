# 📌 Gestão de Plantões

API desenvolvida em Java com Spring Boot para gerenciamento de profissionais e plantões, permitindo cadastro, consulta e exclusão de registros com aplicação de regras de negócio.

---

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

---

## 📚 Funcionalidades

### 👨‍⚕️ Profissionais
- Cadastro de profissional
- Listagem de profissionais
- Busca por ID
- Exclusão de profissional
- Validação de registro duplicado

### 🗓️ Plantões
- Cadastro de plantão
- Associação com profissional
- Definição de data e turno
- Listagem de plantões
- Exclusão de plantão

---

## ⚙️ Regras de negócio

- Não permite cadastro de profissionais com registro duplicado
- Controle de carga horária ao atualizar profissional
- Associação obrigatória de plantão a um profissional
- Validações de dados antes de persistir

---

## ❗ Tratamento de erros

- Exception Handler global para API REST
- Tratamento centralizado para aplicação Web
- Retorno de mensagens claras para o usuário
- Status HTTP padronizados

---
