package com.recruitment.dto;

import com.recruitment.model.Department;

public class DepartmentMapper {

    public static DepartmentDTO toDTO( Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setDepartmentName(department.getDepartmentName());


        dto.setAttachmentPath(department.getAttachmentPath());
        dto.setDepartmentLeadName(department.getDepartmentLead().getFirstName() +" "+department.getDepartmentLead().getLastName());


        if (department.getParentDepartment() != null) {
            dto.setParentDepartmentId(department.getParentDepartment().getId());
        }

        if (department.getDepartmentLead() != null) {
            dto.setDepartmentLeadId(department.getDepartmentLead().getId());
        }

        return dto;
    }


}
