package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Template;
import com.chatbot.chatbot.model.TemplateRequest;

import java.util.Optional;

public interface TemplateService {
    void save(TemplateRequest templateRequest);

    void update(Long templateId,String value) throws Exception;

    Optional<Template> findTemplateById(Long templateId);
}
