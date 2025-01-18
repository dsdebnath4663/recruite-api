Here’s a structured documentation template for the table mapping shown in the diagram, tailored for freshers. The explanation includes primary keys (PK), foreign keys (FK), and other relevant details.

---

<img src="https://github.com/meghnadsaha/recruite-api/blob/feature/user-group-management/files/images/role-user-group-db-mapping.png" alt="swagger_ui_screenshot"/>
## Table Mapping Documentation

### 1. **`users` Table**
- **Description**: Contains information about the application's users.
- **Primary Key (PK)**:
    - `id`: Unique identifier for each user.
- **Foreign Keys (FK)**:
    - `profile_id`: References `user_profile(id)`.
    - `role_id`: References `user_role(id)`.
- **Columns**:
    - `address`: User's address.
    - `email`: User's email address.
    - `first_name`: User's first name.
    - `last_name`: User's last name.
    - `phone`: User's contact number.
    - `territory`: Region associated with the user.

---

### 2. **`user_profile` Table**
- **Description**: Defines profiles for users with specific permissions and roles.
- **Primary Key (PK)**:
    - `id`: Unique identifier for each profile.
- **Columns**:
    - `description`: Profile description.
    - `name`: Name of the profile.
    - `permissions`: Permissions assigned to the profile.

---

### 3. **`user_role` Table**
- **Description**: Defines roles that users can have within the application.
- **Primary Key (PK)**:
    - `id`: Unique identifier for each role.
- **Foreign Keys (FK)**:
    - `reports_to_id`: Self-referential FK pointing to another role (`user_role(id)`) for hierarchical reporting.
- **Columns**:
    - `share_data_with_peers`: Boolean flag indicating if data sharing is allowed with peers.
    - `description`: Description of the role.
    - `name`: Name of the role.

---

### 4. **`user_group` Table**
- **Description**: Groups that users can belong to.
- **Primary Key (PK)**:
    - `id`: Unique identifier for each group.
- **Columns**:
    - `description`: Group description.
    - `name`: Name of the group.

---

### 5. **`group_members` Table**
- **Description**: Maps users to groups, representing many-to-many relationships.
- **Composite Primary Key (PK)**:
    - `group_id`: Part of the composite PK, references `user_group(id)`.
    - `user_id`: Part of the composite PK, references `users(id)`.
- **Foreign Keys (FK)**:
    - `group_id`: References `user_group(id)`.
    - `user_id`: References `users(id)`.

---

## Relationships
### 1. **One-to-Many Relationships**:
- A `user_profile` can be associated with many `users` via `profile_id`.
- A `user_role` can be associated with many `users` via `role_id`.
- A `user_role` can report to another `user_role` via `reports_to_id`.

### 2. **Many-to-Many Relationships**:
- `users` and `user_group` are connected via the `group_members` table.

---

## Key Concepts for Freshers
1. **Primary Key (PK)**:
    - Uniquely identifies each record in a table.
    - Example: `id` in the `users` table.

2. **Foreign Key (FK)**:
    - Establishes a link between two tables.
    - Example: `profile_id` in the `users` table links to `id` in the `user_profile` table.

3. **Composite Key**:
    - A combination of two or more columns to form a unique identifier.
    - Example: `group_id` and `user_id` in the `group_members` table.

4. **Relationships**:
    - Tables are related through PKs and FKs, enabling data integrity and structured access.

---

