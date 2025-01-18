package com.recruitment.initializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitment.dto.Permissions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PermissionsBuilder {

    /**
     * 1.Administrator: All permissions for all entities and social features.
     */
    public static Permissions buildAdministratorPermissions() {
        // Administrator: All permissions
        List<Permissions.ModulePermission> modulePermissions = createAllModulePermissions(true, true, true, true, true);
        Permissions.SocialPermissions socialPermissions = Permissions.SocialPermissions.builder()
                                                                                       .socialAdmin(true)
                                                                                       .managePosts(true)
                                                                                       .viewAnalytics(true)
                                                                                       .build();

        return Permissions.builder()
                          .modulePermissions(modulePermissions)
                          .socialPermissions(socialPermissions)
                          .build();
    }

    /**
     * 2.Standard: Full permissions for all entities, but no administrative social permissions.
     */
    public static Permissions buildStandardPermissions() {
        // Standard: No administrative permissions
        List<Permissions.ModulePermission> modulePermissions = createAllModulePermissions(true, true, true, true, true);
        Permissions.SocialPermissions socialPermissions = Permissions.SocialPermissions.builder()
                                                                                       .socialAdmin(false) // No administrative permissions
                                                                                       .managePosts(false)
                                                                                       .viewAnalytics(false)
                                                                                       .build();

        return Permissions.builder()
                          .modulePermissions(modulePermissions)
                          .socialPermissions(socialPermissions)
                          .build();
    }
    /**
     * 3.Hiring Manager: Specific permissions to create jobs, review candidates, and add notes.
     */
    public static Permissions buildHiringManagerPermissions() {
        // Hiring Manager: Can create jobs, review candidates, and add notes
        List<Permissions.ModulePermission> modulePermissions = Arrays.asList(
                Permissions.ModulePermission.builder().entity("Job Openings").tabVisible(true).view(true).create(true).edit(true).delete(true).build(),
                Permissions.ModulePermission.builder().entity("Candidates").tabVisible(true).view(true).create(false).edit(false).delete(false).build(),
                Permissions.ModulePermission.builder().entity("Notes").tabVisible(true).view(true).create(true).edit(true).delete(true).build()
        );
        Permissions.SocialPermissions socialPermissions = Permissions.SocialPermissions.builder()
                                                                                       .socialAdmin(false)
                                                                                       .managePosts(false)
                                                                                       .viewAnalytics(false)
                                                                                       .build();

        return Permissions.builder()
                          .modulePermissions(modulePermissions)
                          .socialPermissions(socialPermissions)
                          .build();
    }
    /**
     * 4.Employee: Permissions to create referrals and give interview feedback.
     */
    public static Permissions buildEmployeePermissions() {
        // Employee: Can create referrals and give interview feedback
        List<Permissions.ModulePermission> modulePermissions = Arrays.asList(
                Permissions.ModulePermission.builder().entity("Referrals").tabVisible(true).view(true).create(true).edit(false).delete(false).build(),
                Permissions.ModulePermission.builder().entity("Interviews").tabVisible(true).view(true).create(true).edit(false).delete(false).build()
        );
        Permissions.SocialPermissions socialPermissions = Permissions.SocialPermissions.builder()
                                                                                       .socialAdmin(false)
                                                                                       .managePosts(false)
                                                                                       .viewAnalytics(false)
                                                                                       .build();

        return Permissions.builder()
                          .modulePermissions(modulePermissions)
                          .socialPermissions(socialPermissions)
                          .build();
    }

    private static List<Permissions.ModulePermission> createAllModulePermissions(
            boolean tabVisible, boolean view, boolean create, boolean edit, boolean delete) {
        return Arrays.asList(
                Permissions.ModulePermission.builder().entity("Home").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Job Openings").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Applications").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Candidates").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Referrals").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Interviews").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Departments").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Analytics").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Metrics").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Dashboards").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Reports").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Campaigns").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Assessments").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("To-Dos").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Vendors").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Notes").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Recruiter Inbox").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("My Actions").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Emails").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Documents").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Submissions").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build(),
                Permissions.ModulePermission.builder().entity("Offers").tabVisible(tabVisible).view(view).create(create).edit(edit).delete(delete).build()
        );
    }

    public static String buildAdministratorPermissionsAsJson() {

        // Convert the Permissions object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(buildAdministratorPermissions());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // Return an empty JSON object in case of an error
        }
    }

}
