package com.elhefny.askdoctor.DepartmentsRecyclerClasses;

import java.io.Serializable;

public class DepartmentDetails implements Serializable {
    private int departmentImageID;
    private String departmentName;
    private String departmentDescription;

    public DepartmentDetails(int departmentImageID, String departmentName, String departmentDescription) {
        this.departmentImageID = departmentImageID;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
    }

    public int getDepartmentImageID() {
        return departmentImageID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }
}
