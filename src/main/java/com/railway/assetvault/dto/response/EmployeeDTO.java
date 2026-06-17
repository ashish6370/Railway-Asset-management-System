package com.railway.assetvault.dto.response;

import com.railway.assetvault.entity.User;

public class EmployeeDTO {
    public Long id;
    public String employeeId;
    public String name;
    public String email;
    public String phone;
    public String designation;
    public String departmentName;

    public EmployeeDTO(User user) {
        this.id = user.getId();
        this.employeeId = user.getEmployeeId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.designation = user.getDesignation();
        this.departmentName = user.getDepartment() != null ? user.getDepartment().getName() : "Unassigned";
    }
}
