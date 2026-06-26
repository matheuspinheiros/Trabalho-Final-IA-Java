# Sistema Academico - CRUD Web com Spring Boot, Thymeleaf e IA

Projeto academico desenvolvido para a disciplina de Programacao Orientada a Objetos II. A aplicacao oferece um CRUD web para cursos, alunos e atividades, com dashboard gerencial e recursos de IA integrados via Mistral.

## Integrantes

- Matheus Pinheiro dos Santos
- Erick Lino

## Visao geral

O sistema foi construido para apoiar o controle academico em um ambiente web simples, com foco em:

- cadastro, edicao, listagem e exclusao de cursos
- cadastro, edicao, listagem e exclusao de alunos
- cadastro, edicao, listagem e exclusao de atividades
- dashboard com indicadores consolidados
- geracao assistida por IA para resumo do dashboard, massa de dados e atividades academicas

## Funcionalidades

### CRUD principal

- `Cursos`: nome e turno
- `Alunos`: nome, email e curso vinculado
- `Atividades`: titulo, descricao, data de entrega, status e aluno vinculado

### Dashboard

Tela em `/relatorios` com:

- total de cursos
- total de alunos
- total de atividades
- grafico de barras com atividades por status
- botao para gerar resumo textual com IA
- botao para gerar cursos e alunos ficticios com IA
- botao para gerar atividades contextuais com IA

### Recursos de IA

Integracao implementada no servico `MistralIaService` com apoio do Spring AI.

Recursos disponiveis:

1. `GET /api/ia/resumo`
   Gera um resumo curto do dashboard com base nos totais atuais.

2. `POST /api/ia/popular-dados`
   Gera massa inicial com exatamente 3 cursos ficticios e 2 alunos por curso.

3. `POST /api/ia/gerar-atividades`
   Gera atividades academicas contextuais para os alunos cadastrados.

Observacoes da geracao de atividades:

- o processo usa ate 5 alunos por execucao
- a IA gera exatamente 2 atividades por aluno
- as datas devem estar no formato `YYYY-MM-DD`
- o status retornado deve ser `PENDENTE`
- a descricao foi ajustada para suportar ate 1000 caracteres no banco
- o prompt orienta a IA a manter a descricao com no maximo 150 caracteres

## Modelo de dados

Entidades principais:

1. `Curso`
2. `Aluno`
3. `Atividade`

Relacionamentos:

- um curso possui varios alunos
- um aluno pertence a um curso
- um aluno possui varias atividades
- uma atividade pertence a um aluno

## Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring MVC
- Thymeleaf
- Spring Data JPA
- Spring Validation
- Spring AI
- Mistral AI
- H2 Database
- Bootstrap 5
- Chart.js
- Maven

## Estrutura do projeto

- `controller`: controladores web e endpoints REST
- `service`: regras de negocio e integracao com IA
- `repository`: acesso ao banco com Spring Data JPA
- `entity`: entidades JPA
- `templates`: paginas Thymeleaf
- `static`: arquivos estaticos, como CSS

## Como executar

### Pre-requisitos

- Java 17
- Maven 3.9 ou superior

### Configuracao

Defina uma chave valida da Mistral para a propriedade abaixo antes de executar o projeto:

```properties
spring.ai.mistralai.api-key=SUA_CHAVE_AQUI
```

### Execucao

No terminal, dentro da pasta do projeto:

```bash
mvn spring-boot:run
```

Aplicacao:

```text
http://localhost:8080
```

## Rotas principais

- `/` redireciona para o dashboard
- `/relatorios`
- `/cursos`
- `/alunos`
- `/atividades`

## Banco H2

Console:

```text
http://localhost:8080/h2-console
```

Configuracao padrao:

```text
JDBC URL: jdbc:h2:mem:faculdadedb
User: sa
Password: deixar vazio
```

## Observacoes tecnicas

- `spring.jpa.hibernate.ddl-auto=update` esta habilitado para evoluir o schema automaticamente no H2
- a coluna `descricao` da entidade `Atividade` foi ampliada para suportar textos maiores
- o frontend foi mantido em Thymeleaf com Bootstrap e JavaScript simples

## Status do projeto

Projeto funcional para demonstracao academica, cobrindo CRUD completo, dashboard com indicadores e automacoes com IA integradas ao fluxo principal da aplicacao.
