package com.recruitment.service;


import com.recruitment.dto.DepartmentDTO;
import com.recruitment.model.Department;
import com.recruitment.model.User;
import com.recruitment.repository.DepartmentRepository;
import com.recruitment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class DepartmentService {

    @Autowired
    private  DepartmentRepository departmentRepository;
    @Autowired
    private  UserRepository userRepository;


    public Department createDepartment(DepartmentDTO departmentDTO) {
        validateDepartmentDTO(departmentDTO);

        Department department = mapToEntity(departmentDTO);

        // Log the creation process
        log.info("Creating department: {}", department.getDepartmentName());

        // Save and return
        return departmentRepository.save(department);
    }

    private void validateDepartmentDTO(DepartmentDTO departmentDTO) {
        if (departmentDTO.getDepartmentName() == null || departmentDTO.getDepartmentName().isEmpty()) {
            throw new IllegalArgumentException("Department name is required");
        }
        if (departmentDTO.getParentDepartmentId() != null && departmentDTO.getParentDepartmentId().equals(departmentDTO.getId())) {
            throw new IllegalArgumentException("A department cannot be its own parent.");
        }
    }

    private Department mapToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setDepartmentName(departmentDTO.getDepartmentName());

        if (departmentDTO.getParentDepartmentId() != null) {
            department.setParentDepartment(
                    departmentRepository.findById(departmentDTO.getParentDepartmentId())
                                        .orElseThrow(() -> new IllegalArgumentException("Parent department not found with ID: " + departmentDTO.getParentDepartmentId()))
            );
        }

        if (departmentDTO.getDepartmentLeadId() != null) {
            department.setDepartmentLead(
                    userRepository.findById(departmentDTO.getDepartmentLeadId())
                                  .orElseThrow(() -> new IllegalArgumentException("Department lead not found with ID: " + departmentDTO.getDepartmentLeadId()))
            );
        }

        department.setAttachmentPath(departmentDTO.getAttachmentPath());
        return department;
    }



    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                                   .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
    }

    public Department updateDepartment(Long id, Department department) {
        Department existingDepartment = getDepartmentById(id);
        existingDepartment.setDepartmentName(department.getDepartmentName());
        existingDepartment.setParentDepartment(department.getParentDepartment());
        existingDepartment.setDepartmentLead(department.getDepartmentLead());
        existingDepartment.setAttachmentPath(department.getAttachmentPath());
        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(Long id) {
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
    }
}
