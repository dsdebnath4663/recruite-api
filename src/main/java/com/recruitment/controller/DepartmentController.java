package com.recruitment.controller;


import com.recruitment.dto.DepartmentDTO;
import com.recruitment.dto.DepartmentMapper;
import com.recruitment.model.Department;
import com.recruitment.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Department Management", description = "Endpoints for managing departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Create a new department", description = "Adds a new department to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created department"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
//    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
//        return ResponseEntity.ok(departmentService.createDepartment(department));
//    }
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department department = departmentService.createDepartment(departmentDTO);

        return ResponseEntity.ok(DepartmentMapper.toDTO(department));
    }

    @Operation(summary = "Get all departments", description = "Retrieves a list of all departments")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved department list"),
    })
//    @GetMapping
//    public ResponseEntity<List<Department>> getAllDepartments() {
//        return ResponseEntity.ok(departmentService.getAllDepartments());
//    }
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentDTO> departmentDTOs = departments.stream()
                                                        .map(DepartmentMapper::toDTO)
                                                        .toList();
        return ResponseEntity.ok(departmentDTOs);
    }

    @Operation(summary = "Get department by ID", description = "Retrieves a department by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved department"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @GetMapping("/{id}")
//    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
//        return ResponseEntity.ok(departmentService.getDepartmentById(id));
//    }
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(DepartmentMapper.toDTO(department));
    }

    @Operation(summary = "Update a department", description = "Updates an existing department by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated department"),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
    }

    @Operation(summary = "Delete a department", description = "Deletes a department by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted department"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department with ID " + id + " deleted successfully.");
    }
}
