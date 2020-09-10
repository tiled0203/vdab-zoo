package com.realdomen.springmvc.services;

import com.realdomen.springmvc.models.Employee;
import com.realdomen.springmvc.models.enums.Profession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service
@Qualifier(value = "hrService")
public class HrServiceImpl implements EmployeeService {
    @Override
    public List<Employee> getAllEmployees() {
        System.out.println("in hr service");
        return null;
    }

    @Override
    public int addEmployee(Employee employee) {
        throw new NotImplementedException();
    }

    @Override
    public Employee findById(int id) {
        throw new NotImplementedException();
    }

    @Override
    public void update(Employee employee) {
        throw new NotImplementedException();

    }

    @Override
    public void deleteById(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Profession> getProfessions() {
        throw new NotImplementedException();
    }
}
