package com.faculdade.crudweb.service;

import com.faculdade.crudweb.entity.Aluno;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MistralIaService {

    private final ChatClient chatClient;

    public MistralIaService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String gerarResumoDashboard(int totalCursos, int totalAlunos, int totalAtividades) {
        String prompt = """
            Você é um assistente acadêmico de um sistema CRUD universitário.
            Gere um resumo curto, claro e profissional em português do Brasil.
            Use no máximo 4 frases.
            Destaque rapidamente o cenário geral e dê uma recomendação objetiva de acompanhamento.
            REGRAS: Retorne APENAS texto puro. NÃO utilize formatação Markdown, asteriscos ou negrito.

            Dados atuais do dashboard:
            - Total de cursos: %d
            - Total de alunos: %d
            - Total de atividades: %d
            """.formatted(totalCursos, totalAlunos, totalAtividades);

        long inicio = System.currentTimeMillis();
        System.out.println("[MistralIaService] Iniciando geração do resumo do dashboard.");
        System.out.println("[MistralIaService] Prompt enviado:\n" + prompt);

        try {
            String resposta = this.chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            long duracaoMs = System.currentTimeMillis() - inicio;
            System.out.println("[MistralIaService] Resposta recebida com sucesso em " + duracaoMs + " ms.");
            return resposta;
        } catch (Exception e) {
            long duracaoMs = System.currentTimeMillis() - inicio;
            System.out.println("[MistralIaService] Falha ao gerar resumo em " + duracaoMs + " ms: " + e.getMessage());
            return "Nao foi possivel gerar o resumo com IA no momento. "
                    + "Verifique a chave da Mistral e tente novamente.";
        }
    }

    public String gerarMassaDadosAcademica() {
        String prompt = """
            Você é um gerador de massa de dados para um sistema acadêmico.
            Gere um JSON VÁLIDO com exatamente 3 cursos fictícios.
            Para cada curso, gere exatamente 2 alunos brasileiros com nomes completos e e-mails fictícios válidos.

            REGRAS OBRIGATÓRIAS:
            1. Retorne APENAS o JSON puro.
            2. NÃO use markdown.
            3. NÃO use blocos de código como ```json.
            4. NÃO escreva explicações antes ou depois do JSON.
            5. Use exatamente esta estrutura:
            {
              "cursos": [
                {
                  "nome": "Nome do curso",
                  "turno": "Matutino ou Vespertino ou Noturno",
                  "alunos": [
                    {
                      "nome": "Nome completo",
                      "email": "email@exemplo.com"
                    }
                  ]
                }
              ]
            }
            6. Garanta que os e-mails sejam únicos.
            """;

        long inicio = System.currentTimeMillis();
        System.out.println("[MistralIaService] Iniciando geração da massa de dados acadêmica.");
        System.out.println("[MistralIaService] Prompt enviado para massa acadêmica:\n" + prompt);

        try {
            String resposta = this.chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            long duracaoMs = System.currentTimeMillis() - inicio;
            System.out.println("[MistralIaService] Massa acadêmica recebida com sucesso em " + duracaoMs + " ms.");
            return resposta;
        } catch (Exception e) {
            long duracaoMs = System.currentTimeMillis() - inicio;
            System.out.println("[MistralIaService] Falha ao gerar massa acadêmica em " + duracaoMs + " ms: " + e.getMessage());
            throw new IllegalStateException("Nao foi possivel gerar a massa de dados com IA.", e);
        }
    }

    public String gerarAtividadesAcademicas(List<Aluno> alunos) {
        StringBuilder alunosPrompt = new StringBuilder();
        for (Aluno aluno : alunos) {
            alunosPrompt.append("- alunoId: ")
                    .append(aluno.getId())
                    .append(", nome: ")
                    .append(aluno.getNome())
                    .append(", curso: ")
                    .append(aluno.getCurso().getNome())
                    .append("\n");
        }

        String dataAtual = LocalDate.now().toString();
        String prompt = """
                Você é um assistente acadêmico especializado em geração de atividades universitárias.
                Com base na lista de alunos abaixo, gere exatamente 2 atividades contextuais para cada aluno,
                considerando o curso de cada um.

                Se o curso for de tecnologia, gere tarefas de programação, banco de dados, modelagem ou engenharia de software.
                Se o curso for de engenharia, gere tarefas de cálculo, projetos, laboratório ou modelagem.
                As atividades devem ser coerentes com o contexto do curso informado.
                Atenção: A data de hoje é %s. Todas as datas geradas no campo dataEntrega DEVEM ser obrigatoriamente posteriores a esta data.

                LISTA DE ALUNOS:
                %s

                REGRAS OBRIGATÓRIAS:
                1. Retorne APENAS JSON puro.
                2. Não use markdown.
                3. Não use blocos ```json.
                4. Não escreva texto antes ou depois do JSON.
                5. Gere exatamente 2 atividades para cada aluno listado.
                6. Todas as datas devem estar no formato YYYY-MM-DD.
                7. Todas as datas devem ser hoje ou futuras.
                8. O status deve ser sempre "PENDENTE".
                9. Use exatamente esta estrutura:
                {
                  "alunos": [
                    {
                      "alunoId": 1,
                      "atividades": [
                        {
                          "titulo": "Titulo da atividade",
                          "descricao": "Descricao da atividade",
                          "dataEntrega": "2026-06-30",
                          "status": "PENDENTE"
                        }
                      ]
                    }
                  ]
                }
                10. NENHUM campo pode ficar em branco ou vazio ("").
                11. O campo dataEntrega DEVE ser preenchido obrigatoriamente com uma data válida YYYY-MM-DD em TODAS as atividades.
                12. O campo descricao deve ser curto, contendo NO MAXIMO 150 caracteres.
                """.formatted(dataAtual, alunosPrompt);

        long inicio = System.currentTimeMillis();
        System.out.println("[MistralIaService] Iniciando geração de atividades acadêmicas.");
        System.out.println("[MistralIaService] Prompt enviado para atividades:\n" + prompt);

        try {
            String resposta = this.chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            long duracaoMs = System.currentTimeMillis() - inicio;
            System.out.println("[MistralIaService] Atividades acadêmicas recebidas com sucesso em " + duracaoMs + " ms.");
            return resposta;
        } catch (Exception e) {
            long duracaoMs = System.currentTimeMillis() - inicio;
            System.out.println("[MistralIaService] Falha ao gerar atividades acadêmicas em " + duracaoMs + " ms: " + e.getMessage());
            throw new IllegalStateException("Nao foi possivel gerar as atividades com IA.", e);
        }
    }
}
