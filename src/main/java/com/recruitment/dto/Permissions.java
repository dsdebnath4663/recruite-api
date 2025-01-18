package com.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor
public class Permissions {

    private List<ModulePermission> modulePermissions;
    private SocialPermissions socialPermissions;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Jacksonized // Makes the class compatible with Jackson deserialization
    public static class ModulePermission {
        private String entity;
        private boolean tabVisible;
        private boolean view;
        private boolean create;
        private boolean edit;
        private boolean delete;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialPermissions {
        private boolean socialAdmin;
        private boolean managePosts;
        private boolean viewAnalytics;
    }


}
