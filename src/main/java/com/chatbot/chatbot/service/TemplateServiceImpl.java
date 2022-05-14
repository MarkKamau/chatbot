package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Template;
import com.chatbot.chatbot.model.TemplateRequest;
import com.chatbot.chatbot.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateServiceImpl implements  TemplateService{

    final TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public void save(TemplateRequest templateRequest) {
        templateRepository.save(new Template(templateRequest.getName(),templateRequest.getValue()));
    }

    @Override
    public void update(Long templateId, String value) throws Exception {
        Template template = templateRepository.findById(templateId).orElseThrow(() -> new Exception("Template not found"));
        template.setValue(value);
        templateRepository.save(template);
    }

    public Optional<Template> findTemplateById(Long templateId){
        return templateRepository.findById(templateId);
    }
}
