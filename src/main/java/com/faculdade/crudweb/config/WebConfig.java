package com.faculdade.crudweb.config;

import com.faculdade.crudweb.entity.Aluno;
import com.faculdade.crudweb.entity.Curso;
import com.faculdade.crudweb.repository.AlunoRepository;
import com.faculdade.crudweb.repository.CursoRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;

    public WebConfig(CursoRepository cursoRepository, AlunoRepository alunoRepository) {
        this.cursoRepository = cursoRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCursoConverter(cursoRepository));
        registry.addConverter(new StringToAlunoConverter(alunoRepository));
    }

    private static class StringToCursoConverter implements Converter<String, Curso> {
        private final CursoRepository cursoRepository;

        private StringToCursoConverter(CursoRepository cursoRepository) {
            this.cursoRepository = cursoRepository;
        }

        @Override
        public Curso convert(String source) {
            if (source == null || source.isBlank()) {
                return null;
            }
            return cursoRepository.findById(Long.valueOf(source)).orElse(null);
        }
    }

    private static class StringToAlunoConverter implements Converter<String, Aluno> {
        private final AlunoRepository alunoRepository;

        private StringToAlunoConverter(AlunoRepository alunoRepository) {
            this.alunoRepository = alunoRepository;
        }

        @Override
        public Aluno convert(String source) {
            if (source == null || source.isBlank()) {
                return null;
            }
            return alunoRepository.findById(Long.valueOf(source)).orElse(null);
        }
    }
}
