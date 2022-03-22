package sampleGUI.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {
    private String codeDepartment;
    private String nameDepartment;
    private ObservableList<Employee> employees= FXCollections.observableArrayList();

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setDepartment(this);
    }
    public Department() {
    }

    public Department(String codeDepartment, String nameDepartment) {
        this.codeDepartment = codeDepartment;
        this.nameDepartment = nameDepartment;
    }

    public Department(String codeDepartment, String nameDepartment, ObservableList<Employee> employees) {
        this.codeDepartment = codeDepartment;
        this.nameDepartment = nameDepartment;
        this.employees = employees;
    }

    public String getCodeDepartment() {
        return codeDepartment;
    }

    public void setCodeDepartment(String codeDepartment) {
        this.codeDepartment = codeDepartment;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ObservableList<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return this.nameDepartment;
    }
}
