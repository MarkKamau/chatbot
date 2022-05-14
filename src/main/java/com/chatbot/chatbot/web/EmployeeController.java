package com.chatbot.chatbot.web;

import com.chatbot.chatbot.model.EmployeeRequest;
import com.chatbot.chatbot.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    final EmployeeService employeeService;

    @PostMapping("create")
    public void create(@RequestBody EmployeeRequest employeeRequest){
        employeeService.save(employeeRequest);
    }


}
