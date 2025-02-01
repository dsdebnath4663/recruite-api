package com.recruitment.initializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.recruitment.dto.DepartmentDTO;
import com.recruitment.dto.UserResponseDTO;
import com.recruitment.model.*;
import com.recruitment.repository.ProfileRepository;
import com.recruitment.repository.RoleRepository;
import com.recruitment.repository.UserRepository;
import com.recruitment.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FileService fileService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final PermissionsBuilder permissionsBuilder;
    private final RoleService roleService;

    private final UserService userService;

    private final DepartmentService departmentService;

    private final JobOpeningService jobOpeningService;

    private String token;

    // Inject the server port or any other property
    @Value("${server.port}")
    private int serverPort;

    public void setToken ( String token ) {
        this.token = token;
        log.info("Token has been set for authentication.");
    }

    public void authenticateAndLoadData () throws Exception {
        log.info("Starting data loading process...");

        insertAdminUser();
        authenticate();
        createRoles();
        createProfiles();
//        createUsersUsingRestTemplate();
        createUsers();
        createDepartment();
//        createJobOpening();

        log.info("\n" +
                         "  ____        _        _         _                                      \n" +
                         " |  _ \\  __ _| |_ __ _| |__     (_) ___  ___ ___  ___  _ __             \n" +
                         " | | | |/ _` | __/ _` | '_ \\    | |/ _ \\/ __/ __|/ _ \\| '_ \\       \n" +
                         " | |_| | (_| | || (_| | |_) |   | |  __/\\__ \\__ \\ (_) | | | |         \n" +
                         " |____/ \\__,_|\\__\\__,_|_.__/    |_|\\___||___/___/\\___/|_| |_|     \n" +
                         "                                                                       \n" +
                         "                 Data imported successfully. ");
    }

    public void insertAdminUser () {
        log.info("Checking and initializing admin user...");

        try {
            // 1. Create the Top-Level Role (Recruiter Admin)
            log.info("Checking for 'Recruiter Admin' role...");
            UserRole adminRole = roleRepository.findByName("Recruiter Admin")
                                               .orElseGet(() -> {
                                                   log.info("'Recruiter Admin' role not found. Creating...");
                                                   UserRole role = new UserRole();
                                                   role.setName("Recruiter Admin");
                                                   role.setDescription(
                                                           "Top-level admin overseeing all recruitment activities");
                                                   UserRole savedRole = roleRepository.save(role);
                                                   log.info("'Recruiter Admin' role created.");
                                                   return savedRole;
                                               });
            // Print the adminRole
            log.info("Admin Role: {}" , adminRole);


            // 2. Create Profile for Administrator
            log.info("Checking for 'Administrator' profile...");
            UserProfile userProfile = profileRepository.findByName("Administrator")
                                                       .orElseGet(() -> {
                                                           log.info("'Administrator' profile not found. Creating...");
                                                           UserProfile profile = new UserProfile();
                                                           profile.setName("Administrator");
                                                           profile.setDescription(
                                                                   "This profile will have all the permissions");
                                                           profile.setPermissions(
                                                                   PermissionsBuilder.buildAdministratorPermissions());
                                                           profile.setPermissionsJson(
                                                                   "{\"modulePermissions\":[{\"entity\":\"Home\",\"tabVisible\":true,\"view\":true,\"create\":true,\"edit\":true,\"delete\":true}],\"socialPermissions\":{\"socialAdmin\":true,\"managePosts\":true,\"viewAnalytics\":true}}");
                                                           UserProfile savedProfile = profileRepository.save(profile);
                                                           log.info("'Administrator' profile created.");
                                                           return savedProfile;
                                                       });
            log.info("User Profile: {}" , userProfile);


            // Delay before creating the admin user
            log.info("Waiting for 2 seconds before creating the admin user...");
            

            // 3. Check if admin user exists, and create if necessary
            log.info("Checking for admin user...");
            if (!userRepository.existsByEmail("admin@example.com")) {
                log.info("Admin user not found. Creating...");
                User admin = User.builder()
                                 .firstName("Recruiter Admin")
                                 .lastName("User")
                                 .email("admin@example.com")
                                 .password(passwordEncoder.encode("admin123"))
                                 .role(adminRole)
                                 .profile(userProfile)
                                 .enabled(true)
                                 .address(
                                         "851, Kalikapur Rd, Purbachal Kalitala, Kalikapur, Haltu, Kolkata, West Bengal 700099")
                                 .phone("09836268960")
                                 .territory("West Bengal")
                                 .build();
                userRepository.save(admin);

                log.info("User : {}" , admin);

                log.info("Admin user created with email: admin@example.com and password: admin123");
            } else {
                log.info("Admin user already exists.");
            }
        } catch (Exception e) {
            log.error("Error initializing admin user: {}" , e.getMessage() , e);
        }
    }

    private void authenticate () {
        log.info("Authenticating admin user...");

        String url = "http://localhost:"+serverPort+"/api/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = Map.of(
                "username" , "admin@example.com" ,
                "password" , "admin123"
        );

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody , headers);

        try {
            ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                    url ,
                    HttpMethod.POST ,
                    entity ,
                    new ParameterizedTypeReference<>() {
                    }
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                UserResponseDTO responseBody = response.getBody();
                if (responseBody != null && responseBody.getToken() != null) {
                    token = responseBody.getToken();
                    log.info("Authentication successful. Token retrieved for user: {} {}" ,
                             responseBody.getFirstName() , responseBody.getLastName());
                } else {
                    throw new RuntimeException("Token not found in response");
                }
            } else {
                throw new RuntimeException("Authentication failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Authentication failed: {}" , e.getMessage() , e);
            throw new RuntimeException("Authentication failed");
        }
    }

    public void createRoles () {
        String[] cases = {
//                "admin-role-definition.json" ,
                "recruiter-role-definition.json" , "hiring-manager-role-definition.json" , "employee-role-definition.json"};

        for (String fileName : cases) {
            log.info("Processing role file: {}" , fileName);

            try {
                // Load and parse file content into a UserRole object
                String fileContent = fileService.getFileContent(fileName); // JSON content
                if (fileContent == null || fileContent.isBlank()) {
                    log.warn("Skipping empty or null file: {}" , fileName);
                    continue;
                }

                ObjectMapper objectMapper = new ObjectMapper();
                UserRole role = objectMapper.readValue(fileContent , UserRole.class);

                // Use createRole method to process the UserRole object
                UserRole createdRole = roleService.createRole(role);

                log.info("Successfully created role: {}" , createdRole);
            } catch (Exception e) {
                log.error("Error occurred while creating role for file: {}. Error: {}" , fileName , e.getMessage() , e);
            }
        }

        log.info("Completed processing all role files.");
    }

    public void createRolesUsingRestTemplate () throws Exception {
        log.info("Starting creation of roles...");

        String url = "http://localhost:8080/api/roles";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        String[] cases = {"recruiter-role-definition.json" , "hiring-manager-role-definition.json" , "employee-role-definition.json"};

        for (String fileName : cases) {
            log.info("Processing role file: {}" , fileName);

            try {
                String body = fileService.getFileContent(fileName);
                HttpEntity<String> entity = new HttpEntity<>(body , headers);

                ResponseEntity<UserRole> response = restTemplate.exchange(url , HttpMethod.POST , entity ,
                                                                          UserRole.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("Successfully created role: {}" , response.getBody());
                } else {
                    log.error("Failed to create role for file: {}. Response status: {}" , fileName ,
                              response.getStatusCode());
                }
            } catch (Exception e) {
                log.error("Error occurred while creating role from file {}: {}" , fileName , e.getMessage() , e);
            }

            log.info("Delaying for 2 seconds before processing the next file...");
            
        }

        log.info("Finished processing all role files.");
    }

    private void createProfiles () throws Exception {
        String[] cases = {
                "administrator-profile-definition.json" ,
                "standard-profile-definition.json" ,
                "hiring-manager-profile-definition.json" ,
                "employee-profile-definition.json"
        };

        for (String fileName : cases) {
            log.info("Processing file: {}" , fileName);

            try {
                // Load and parse the file content
                String fileContent = fileService.getFileContent(fileName); // JSON content
                if (fileContent == null || fileContent.isBlank()) {
                    log.warn("Skipping empty or null file: {}" , fileName);
                    continue;
                }

                ObjectMapper objectMapper = new ObjectMapper();
                UserProfile profile = objectMapper.readValue(fileContent , UserProfile.class);

                // Call the createProfile method to process the profile
                UserProfile createdProfile = profileRepository.save(profile);

                log.info("Successfully created profile: {}" , createdProfile);
            } catch (Exception e) {
                log.error("Error occurred while creating profile from file {}: {}" , fileName , e.getMessage() , e);
            }

            
            log.info("Adding a delay of 2 seconds before processing the next file...");
        }

        log.info("Completed processing all files for profiles.");
    }

    private void createProfilesUsingRestTemplate () throws Exception {
        log.info("Starting creation of profiles...");

        String url = "http://localhost:8080/api/profiles";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        String[] cases = {"administrator-profile-definition.json" , "standard-profile-definition.json" , "hiring-manager-profile-definition.json" , "employee-profile-definition.json"};

        for (String fileName : cases) {
            log.info("Processing profile file: {}" , fileName);

            try {
                String body = fileService.getFileContent(fileName);
                HttpEntity<String> entity = new HttpEntity<>(body , headers);

                ResponseEntity<UserProfile> response = restTemplate.exchange(url , HttpMethod.POST , entity ,
                                                                             UserProfile.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("Successfully created profile: {}" , response.getBody());
                } else {
                    log.error("Failed to create profile for file: {}. Status code: {}" , fileName ,
                              response.getStatusCode());
                }
            } catch (Exception e) {
                log.error("Error occurred while creating profile from file {}: {}" , fileName , e.getMessage() , e);
            }

            log.info("Delaying for 2 seconds before processing the next file...");
            
        }

        log.info("Finished processing all profile files.");
    }

    private void createUsersUsingRestTemplate () throws Exception {
        log.info("Starting creation of users...");

        String url = "http://localhost:8080/api/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        String[] cases = {"admin-user.json" , "user1.json" , "user2.json" , "user3.json" , "user4.json"};

        for (String fileName : cases) {
            log.info("Processing user file: {}" , fileName);

            try {
                String body = fileService.getFileContent(fileName);
                HttpEntity<String> entity = new HttpEntity<>(body , headers);

                ResponseEntity<User> response = restTemplate.exchange(url , HttpMethod.POST , entity , User.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("Successfully created user: {}" , response.getBody());
                } else {
                    log.error("Failed to create user from file: {}. Status code: {}" , fileName ,
                              response.getStatusCode());
                }
            } catch (Exception e) {
                log.error("Error occurred while creating user from file {}: {}" , fileName , e.getMessage() , e);
            }

            log.info("Delaying for 2 seconds before processing the next file...");
            
        }

        log.info("Finished processing all user files.");
    }

    private void createUsers () throws Exception {
        log.info("Starting creation of users...");

        String[] cases = {
//                "admin.json" ,
                "admin-user.json" , "user1.json" , "user2.json" , "user3.json" , "user4.json" ,
                "user5.json" , "user6.json" , "user7.json" , "user8.json" ,
                "user9.json" , "user10.json" , "user11.json" , "user12.json"};

        for (String fileName : cases) {
            log.info("Processing user file: {}" , fileName);

            try {
                // Load and parse the file content
                String fileContent = fileService.getFileContent(fileName);
                if (fileContent == null || fileContent.isBlank()) {
                    log.warn("Skipping empty or null file: {}" , fileName);
                    continue;
                }

                // Deserialize JSON into User object
                ObjectMapper objectMapper = new ObjectMapper();
                User user = objectMapper.readValue(fileContent , User.class);

                // Use the userService to create the user
                User createdUser = userService.createUser(user);

                log.info("Successfully created user: {}" , createdUser);
            } catch (Exception e) {
                log.error("Error occurred while creating user from file {}: {}" , fileName , e.getMessage() , e);
            }

            log.info("Delaying for 2 seconds before processing the next file...");
        }

        log.info("Finished processing all user files.");
    }



    private void createDepartment() throws Exception {
        log.info("Starting creation of departments...");

        String[] departmentFiles = {
                "engineering_department.json",
                "human_resources_department.json",
                "marketing_department.json",
                "sales_department.json",
                "finance_department.json",
                "product_department.json",
                "support_department.json",
                "legal_department.json",
                "it_department.json",
                "operations_department.json"
        };

        for (String departmentFile : departmentFiles) {
            log.info("Processing department file: {}", departmentFile);

            try {
                // Load and parse the file content
                String fileContent = fileService.getFileContent(departmentFile);

                if (fileContent == null || fileContent.isBlank()) {
                    log.warn("Skipping empty or null file: {}", departmentFile);
                    continue;
                }

                // Log file content for debugging
                log.debug("File content for {}: {}", departmentFile, fileContent);

                // Deserialize JSON into DepartmentDTO object
                ObjectMapper objectMapper = new ObjectMapper();
                DepartmentDTO departmentDTO = objectMapper.readValue(fileContent, DepartmentDTO.class);

                // Validate the parsed object
                log.debug("Parsed DepartmentDTO: {}", departmentDTO);

                // Use the departmentService to create the Department
                Department createdDepartment = departmentService.createDepartment(departmentDTO);

                log.info("Successfully created department: {}", createdDepartment.getId());
            } catch (Exception e) {
                log.error("Error occurred while creating department from file {}: {}",
                          departmentFile, e.getMessage(), e);
            }

            
            log.info("Delaying for 2 seconds before processing the next file...");
        }

        log.info("Finished processing all department files.");
    }
    private void createJobOpening() throws Exception {
        log.info("Starting creation of job openings...");

        String[] jobOpeningFiles = {
                "java_developer_job_opening.json",
                "software_engineer_job_opening.json", "marketing_manager_job_opening.json",  "sales_executive_job_opening.json",
                "hr_specialist_job_opening.json",  "finance_analyst_job_opening.json",  "product_specialist_job_opening.json", "customer_support_specialist_job_opening.json",
                "corporate_lawyer_job_opening.json",  "network_engineer_job_opening.json"
//                , "business_operations_specialist_job_opening.json"
        };

        for (String jobOpeningFile : jobOpeningFiles) {
            log.info("Starting processing for job opening file: {}", jobOpeningFile);

            try {
                // Load and parse the file content
                log.debug("Attempting to retrieve content for file: {}", jobOpeningFile);
                String fileContent = fileService.getFileContent(jobOpeningFile);

                if (fileContent == null || fileContent.isBlank()) {
                    log.warn("File {} is empty or null. Skipping processing.", jobOpeningFile);
                    continue;
                }

                log.debug("Successfully retrieved content for file: {}. Content length: {} characters.",
                          jobOpeningFile, fileContent.length());

                // Deserialize JSON into JobOpening object
                log.debug("Deserializing content of file: {}", jobOpeningFile);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());


                // Optional: Configure the ObjectMapper for ISO-8601 formatting
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                JobOpening jobOpeningPayload = objectMapper.readValue(fileContent, JobOpening.class);

                // Validate the parsed object
                log.debug("Deserialized JobOpening object for file {}: {}", jobOpeningFile, jobOpeningPayload);

                // Create the job opening
                log.info("Creating job opening from file: {}", jobOpeningFile);
                JobOpening jobOpeningResponse = jobOpeningService.createJobOpening(jobOpeningPayload);

                log.info("Successfully created job opening with ID: {} from file: {}",
                         jobOpeningResponse.getId(), jobOpeningFile);

            } catch (JsonProcessingException e) {
                log.error("JSON parsing error while processing file {}: {}", jobOpeningFile, e.getMessage(), e);
            } catch (IOException e) {
                log.error("I/O error while processing file {}: {}", jobOpeningFile, e.getMessage(), e);
            } catch (Exception e) {
                log.error("Unexpected error occurred while processing file {}: {}", jobOpeningFile, e.getMessage(), e);
            }
        }

        log.info("Finished processing all job opening files.");
    }

}
