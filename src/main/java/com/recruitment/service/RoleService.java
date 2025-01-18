package com.recruitment.service;


import com.recruitment.dto.RoleDTO;
import com.recruitment.model.UserRole;
import com.recruitment.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;


    public UserRole createRole ( UserRole role ) {
        // Resolve reportsTo field
        if (role.getReportsTo() != null && role.getReportsTo().getId() != null) {
            UserRole parentRole = roleRepository.findById(role.getReportsTo().getId())
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                        "Invalid reportsTo ID: " + role.getReportsTo().getId()));
            role.setReportsTo(parentRole); // Assign the parent role
            log.info("Assigned reportsTo: {}" , parentRole);
        }
        // Save the role
        log.info("Saving role: {}" , role);
        return roleRepository.save(role);
    }


    public List<UserRole> getAllRoles () {
        return roleRepository.findAll();
    }

    public UserRole updateRole ( Long id , UserRole updatedRole ) {
        UserRole role = roleRepository.findById(id)
                                      .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
        role.setName(updatedRole.getName());
        role.setDescription(updatedRole.getDescription());
        role.setReportsTo(updatedRole.getReportsTo());
        role.setShareDataWithPeers(updatedRole.isShareDataWithPeers());
        return roleRepository.save(role);
    }

    public void deleteRole ( Long id ) {
        roleRepository.deleteById(id);
    }

    public List<RoleDTO> getRoleHierarchy () {
        List<UserRole> allRoles = roleRepository.findAll(); // Fetch all roles from DB
        return buildHierarchy(null , allRoles); // Build hierarchy from the root
    }

    private List<RoleDTO> buildHierarchy ( Long parentId , List<UserRole> allRoles ) {
        // Log the parent ID being processed
        log.info("Building hierarchy for parentId: {}" , parentId);

        return allRoles.stream()
                       .filter(role -> (role.getReportsTo() == null && parentId == null) || // Root-level roles
                               (role.getReportsTo() != null && role.getReportsTo()
                                                                   .getId()
                                                                   .equals(parentId))) // Subordinate roles
                       .map(role -> {
                           // Map UserRole to RoleDTO
                           log.info("Role: {} (id: {}) reportsTo: {}" ,
                                    role.getName() ,
                                    role.getId() ,
                                    role.getReportsTo() != null?role.getReportsTo().getId():"null");

                           RoleDTO roleDTO = new RoleDTO();
                           roleDTO.setId(role.getId());
                           roleDTO.setName(role.getName());
                           roleDTO.setDescription(role.getDescription());

                           // Recursive call to get subordinates
                           List<RoleDTO> subordinates = buildHierarchy(role.getId() , allRoles);
                           log.info("Role: {} (id: {}) has {} subordinates" , role.getName() , role.getId() ,
                                    subordinates.size());

                           roleDTO.setSubordinates(subordinates);
                           return roleDTO;
                       })
                       .collect(Collectors.toList());
    }


}
