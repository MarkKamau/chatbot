package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Employee;
import com.chatbot.chatbot.model.EmployeeRequest;
import com.chatbot.chatbot.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements  EmployeeService{

    final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void save(EmployeeRequest employeeRequest) {
            employeeRepository.save(new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName()));
    }

    public Optional<Employee> findEmployeeById(Long employeeId){
        return  employeeRepository.findById(employeeId);
    }
}
