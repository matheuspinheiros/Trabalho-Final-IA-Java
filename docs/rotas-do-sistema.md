# Rotas do Sistema Web

| Funcionalidade | Caminho | Objetivo |
|---|---|---|
| Relatorio | `/relatorios` | Exibir dashboard e grafico |
| Listar cursos | `/cursos` | Mostrar todos os cursos |
| Novo curso | `/cursos/novo` | Abrir formulario de cadastro de curso |
| Editar curso | `/cursos/editar/{id}` | Abrir formulario para atualizar curso |
| Excluir curso | `/cursos/excluir/{id}` | Remover curso |
| Listar alunos | `/alunos` | Mostrar todos os alunos |
| Novo aluno | `/alunos/novo` | Abrir formulario de cadastro de aluno |
| Editar aluno | `/alunos/editar/{id}` | Abrir formulario para atualizar aluno |
| Excluir aluno | `/alunos/excluir/{id}` | Remover aluno |
| Listar atividades | `/atividades` | Mostrar todas as atividades |
| Nova atividade | `/atividades/nova` | Abrir formulario de cadastro de atividade |
| Editar atividade | `/atividades/editar/{id}` | Abrir formulario para atualizar atividade |
| Excluir atividade | `/atividades/excluir/{id}` | Remover atividade |

## Sequencia sugerida para apresentacao

1. Abrir `/relatorios` e mostrar o grafico.
2. Cadastrar um curso.
3. Cadastrar um aluno vinculado ao curso.
4. Cadastrar uma atividade vinculada ao aluno.
5. Editar uma atividade e mudar o status.
6. Voltar ao relatorio e mostrar o grafico atualizado.
7. Abrir o H2 Console e mostrar as tabelas criadas.
