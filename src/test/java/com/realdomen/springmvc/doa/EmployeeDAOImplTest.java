package com.realdomen.springmvc.doa;

import com.realdomen.springmvc.models.Employee;
import com.realdomen.springmvc.models.enums.Profession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeDAOImplTest {
    @Autowired
    private EmployeeDAO employeeDAO;
    private int id;


    @BeforeEach
    void setUp() {
        Employee employee = new Employee();
        employee.setProfession(Profession.DEVELOPER);
        employee.setFirstName("mr");
        employee.setLastName("Spock");
        id = employeeDAO.addEmployee(employee);
    }

    @AfterEach
    void tearDown() {
        employeeDAO.deleteById(id);
    }

    private Connection createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error mysql driver:" + e.getMessage());
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/companyDb"
                , "root"
                , "P@ssw0rd");
    }

    @Test
    void getAllEmployees() {
        List<Employee> allEmployees = employeeDAO.getAllEmployees();
        Employee employee1 = allEmployees.stream().filter(employee -> employee.getId() == id).findAny().get();
        assertEquals(employee1.getFirstName(), "mr");
        assertEquals(employee1.getLastName(), "Spock");
        assertEquals(employee1.getId(), id);
        assertEquals(employee1.getProfession(), Profession.DEVELOPER);
    }
}
