package com.realdomen.springmvc.doa;


import com.realdomen.springmvc.models.Employee;
import com.realdomen.springmvc.models.enums.Profession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    
    @Value(value = "${db.url}") // fetch the property value with key db.url from application.properties
    private String url;

    @Value(value = "${db.driver}")
    private String driver;

    private Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    private Connection createConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.error("Error mysql driver:", e);
        }
        return DriverManager.getConnection(url
                , "root"
                , "P@ssw0rd");
    }

    public List<Employee> getAllEmployees() {
        try (PreparedStatement preparedStatement = createConnection().prepareStatement("select * from employees")) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setFirstName(resultSet.getString("firstname"));
                employee.setLastName(resultSet.getString("lastname"));
//                becuase we don't like NullPointerExceptions we have to
                if(resultSet.getString("profession") != null){
                    employee.setProfession(Profession.valueOf(resultSet.getString("profession")));
                }
                employees.add(employee);
            }
            return employees;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    public int addEmployee(Employee employee) {
        try (PreparedStatement preparedStatement = createConnection()
                .prepareStatement("insert into employees(id, firstname, lastname, profession) values ((SELECT LAST_INSERT_ID()),?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getProfession().name());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
             return resultSet.getInt(1); // returns the newly generated id
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public Employee findById(int id) {
        try (PreparedStatement preparedStatement = createConnection().prepareStatement("select * from employees where id = ? ")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setFirstName(resultSet.getString("firstname"));
            employee.setLastName(resultSet.getString("lastname"));
            if(resultSet.getString("profession") != null){
                employee.setProfession(Profession.valueOf(resultSet.getString("profession")));
            }
            return employee;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Employee employee) {
        try (PreparedStatement preparedStatement = createConnection().prepareStatement("update employees set firstname= ? , lastname=? , profession=? where id = ? ")) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getProfession().name());
            preparedStatement.setInt(4, employee.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try (PreparedStatement preparedStatement = createConnection().prepareStatement("delete from employees where id = ? ")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Profession> getProfesions() {
        return Arrays.stream(Profession.values()).collect(Collectors.toList());
    }


}
