package com.recruitment.controller;


import com.recruitment.dto.Permissions;
import com.recruitment.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/module/{moduleName}")
    public ResponseEntity<Permissions.ModulePermission> getModulePermission(@PathVariable String moduleName, @RequestParam Long userId) {
        Permissions.ModulePermission modulePermission = permissionService.getModulePermission(userId, moduleName);
        return ResponseEntity.ok(modulePermission);
    }

    @GetMapping("/social")
    public ResponseEntity<Permissions.SocialPermissions> getSocialPermissions(@RequestParam Long userId) {
        Permissions.SocialPermissions socialPermissions = permissionService.getSocialPermissions(userId);
        return ResponseEntity.ok(socialPermissions);
    }
}
