package com.chatbot.chatbot.service;


import com.chatbot.chatbot.entity.Employee;
import com.chatbot.chatbot.model.EmployeeRequest;

import java.util.Optional;

public interface EmployeeService {

    void save(EmployeeRequest employeeRequest);

    Optional<Employee> findEmployeeById(Long employeeId);
}
