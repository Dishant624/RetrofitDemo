package com.example.retrofitdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EmployeeList {
    @SerializedName("employeeList")
    private ArrayList<Employee> employeeArrayList;

    public ArrayList<Employee> getEmployeeArrayList() {
        return employeeArrayList;
    }

    public void setEmployeeArrayList(ArrayList<Employee> employeeArrayList) {
        this.employeeArrayList = employeeArrayList;
    }
}
