package com.realdomen.springmvc.services;

import com.realdomen.springmvc.doa.EmployeeDAO;
import com.realdomen.springmvc.models.Employee;
import com.realdomen.springmvc.models.enums.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier(value = "empService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDAO dao;

    public List<Employee> getAllEmployees(){
        return dao.getAllEmployees();
    }

    @Override
    public int addEmployee(Employee employee) {
       return dao.addEmployee(employee);
    }

    @Override
    public Employee findById(int id) {
        return dao.findById(id);
    }


    @Override
    public void update(Employee employee) {
        dao.update(employee);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    @Override
    public List<Profession> getProfessions() {
        return dao.getProfesions();
    }

}
