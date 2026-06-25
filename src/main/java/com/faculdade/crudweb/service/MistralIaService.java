package com.faculdade.crudweb.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

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

        try {
            return this.chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            return "Nao foi possivel gerar o resumo com IA no momento. "
                    + "Verifique a chave da Mistral e tente novamente.";
        }
    }
}
