package com.realdomen.springmvc.services;

import com.realdomen.springmvc.models.Employee;
import com.realdomen.springmvc.models.enums.Profession;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAllEmployees();
    public int addEmployee(Employee employee);

    Employee findById(int id);

    void update(Employee employee);

    void deleteById(int id);

    List<Profession> getProfessions();
}
