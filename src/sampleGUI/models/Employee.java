package sampleGUI.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee implements Serializable {
    private String codeEmployee;
    private String nameEmployee;
    private Date dateStartWorking;
    private Date dateOfBirth;
    private Department department;
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy");
    public Employee() {
    }

    public Employee(String codeEmployee, String nameEmployee, Date dateStartWorking, Date dateOfBirth) {
        this.codeEmployee = codeEmployee;
        this.nameEmployee = nameEmployee;
        this.dateStartWorking = dateStartWorking;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee(String codeEmployee, String nameEmployee, Date dateStartWorking, Date dateOfBirth, Department department) {
        this.codeEmployee = codeEmployee;
        this.nameEmployee = nameEmployee;
        this.dateStartWorking = dateStartWorking;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
    }

    public String getCodeEmployee() {
        return codeEmployee;
    }

    public void setCodeEmployee(String codeEmployee) {
        this.codeEmployee = codeEmployee;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public Date getDateStartWorking() {
        return dateStartWorking;
    }

    public void setDateStartWorking(Date dateStartWorking) {
        this.dateStartWorking = dateStartWorking;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return this.codeEmployee+": "+this.nameEmployee+" - "+sdf.format(this.dateStartWorking)+" - "+sdf.format(this.dateOfBirth);
    }
}
