package com.chatbot.chatbot.web;

import com.chatbot.chatbot.model.TemplateRequest;
import com.chatbot.chatbot.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("template")
@RequiredArgsConstructor
public class TemplateController {

    final TemplateService templateService;

    @PostMapping("create")
    public void create(@RequestBody TemplateRequest templateRequest){
        templateService.save(templateRequest);
    }

    @PostMapping("{templateId}/update")
    public void update(@PathVariable("templateId") Long templateId, @RequestParam("value") String value) throws Exception {
        templateService.update(templateId, value);
    }
}
