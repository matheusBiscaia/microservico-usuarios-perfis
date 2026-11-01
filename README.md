<div align="center">

# 👥 Microserviço de Usuários e Perfis

**API REST em Java 17 e Spring Boot 3 para gerenciamento de usuários em um cenário acadêmico de microsserviços.**

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) ![H2](https://img.shields.io/badge/Database-H2-blue?style=for-the-badge)

</div>

## 📌 Sobre o projeto

Projeto acadêmico que implementa o serviço de **Usuários e Perfis** de uma arquitetura baseada em microsserviços. Gerencia cadastro, login, dados de contato, perfis e status de usuários. Mantém identificadores de escola, curso e matriz sem criar relacionamentos JPA com entidades pertencentes a outros serviços.

### Funcionalidades

- Cadastro e consulta de usuários
- Login por e-mail e senha
- Atualização de dados
- Perfis: `ADMINISTRADOR`, `PROFESSOR` e `ALUNO`
- Status: `ATIVO` e `INATIVO`
- Inativação de aluno ao sair ou trancar a instituição
- Integração com **Eureka Service Discovery**
- Validação de dados e tratamento de erros

## 🛠️ Tecnologias

- Java 17, Spring Boot 3.3.5, Spring Web
- Spring Data JPA e Bean Validation
- Spring Cloud Netflix Eureka Client
- H2 Database (em memória)
- Maven e Spring Boot Actuator

## 🔗 Endpoints

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/usuarios` | Cadastrar usuário |
| `POST` | `/auth/login` | Autenticar usuário |
| `GET` | `/usuarios` | Listar usuários (filtro opcional `?perfil=ALUNO`) |
| `GET` | `/usuarios/{id}` | Consultar usuário por ID |
| `PUT` | `/usuarios/{id}` | Atualizar usuário |
| `PUT` | `/usuarios/{id}/inativar-aluno` | Inativar aluno |

## ▶️ Como executar

**Pré-requisitos:** JDK 17 e Maven.

1. Clone o repositório e entre na pasta:

   ```bash
   git clone https://github.com/matheusBiscaia/microservico-usuarios-perfis.git
   cd microservico-usuarios-perfis
   ```

2. Inicie um servidor **Eureka** em `http://localhost:8761/eureka/` para que o serviço possa se registrar. O servidor Eureka **não está incluído neste repositório**.

3. Execute:

   ```bash
   mvn spring-boot:run
   ```

4. A API estará em `http://localhost:8081`.

### Exemplo de cadastro

```bash
curl -X POST http://localhost:8081/usuarios \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Maria Souza","email":"maria@example.com","senha":"123456","telefone":"71999999999","perfil":"PROFESSOR","escolaId":"11111111-1111-1111-1111-111111111111"}'
```

### Exemplo de login

```bash
curl -X POST http://localhost:8081/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"maria@example.com","senha":"123456"}'
```

## 🗃️ Banco de dados

O projeto usa **H2 em memória** para desenvolvimento e demonstração. Os dados são perdidos quando a aplicação é encerrada.

- Console: `http://localhost:8081/h2-console`
- JDBC: `jdbc:h2:mem:usuarios_perfis_db`
- Usuário: `sa`
- Senha: em branco

## 🧱 Estrutura

```text
src/main/java/br/ucsal/usuarioperfis/
├── controller/
├── dto/
├── exception/
├── model/
├── repository/
├── service/
└── UsuarioPerfisApplication.java
src/main/resources/application.yml
pom.xml
```

## ⚠️ Observações

Este repositório contém **apenas o microserviço de usuários e perfis**, não o sistema acadêmico completo. O banco H2, o console de desenvolvimento e a configuração local não são adequados, sem ajustes, para produção. A rota de login não deve ser confundida com uma solução completa de segurança JWT.

---

Projeto acadêmico de Engenharia de Software — UCSAL.
