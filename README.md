# Sistema Academico - CRUD Web com Spring + Thymeleaf

Projeto desenvolvido para a atividade de CRUD web da disciplina de Programacao Orientada a Objetos II.
Alunos - Matheus Pinheiro dos Santos e Erick Lino

## Tema

Sistema academico para controle de cursos, alunos e atividades. O sistema permite cadastrar, listar, editar e excluir registros, alem de apresentar um relatorio com grafico de atividades por status.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Data JPA
- H2 Database
- Bootstrap
- Chart.js

## Entidades

O sistema possui 3 entidades principais:

1. Curso
2. Aluno
3. Atividade

## Relacionamentos

- Um Curso pode possuir varios Alunos.
- Um Aluno pertence a um Curso.
- Um Aluno pode possuir varias Atividades.
- Uma Atividade pertence a um Aluno.

## Como executar

No terminal, dentro da pasta do projeto, execute:

```bash
mvn spring-boot:run
```

Depois acesse:

```text
http://localhost:8080
```

## Telas principais

- Relatorio: `http://localhost:8080/relatorios`
- Cursos: `http://localhost:8080/cursos`
- Alunos: `http://localhost:8080/alunos`
- Atividades: `http://localhost:8080/atividades`

## Banco H2

Console do H2:

```text
http://localhost:8080/h2-console
```

Configuracao:

```text
JDBC URL: jdbc:h2:mem:faculdadedb
User: sa
Password: deixar vazio
```

## Relatorio

O sistema possui um dashboard em `/relatorios` com:

- Total de cursos
- Total de alunos
- Total de atividades
- Grafico de barras com quantidade de atividades por status

## Estrutura de camadas

- `controller`: recebe as requisicoes web
- `service`: regras de negocio
- `repository`: acesso ao banco de dados
- `entity`: entidades JPA
- `templates`: paginas Thymeleaf