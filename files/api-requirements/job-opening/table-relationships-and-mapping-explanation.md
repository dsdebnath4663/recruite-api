### Table Relationships and Mapping Explanation

Below is a concise summary of the relationships and their mappings between tables, along with an explanation of how they connect.


<img src="https://github.com/meghnadsaha/recruite-api/blob/feature/user-group-management/files/images/job-opening/job-opening-schema-diagram.png?raw=true" alt="Logo" title="job-opening-schema-diagram" >

---

#### **1. Employee and Department Relationship**

- **Tables Involved:** `employee`, `department`
- **Relationship Type:** Many-to-One
    - **One Department** can have **many Employees**.
    - **One Employee** belongs to **one Department**.

| Table        | Column              | Type            | Relationship      |
|--------------|---------------------|-----------------|-------------------|
| **employee** | `id`                | Primary Key     |                   |
|              | `name`              | String          |                   |
|              | `department_id`     | Foreign Key     | References `department.id` |
| **department** | `id`              | Primary Key     |                   |
|              | `name`              | String          |                   |

---

#### **2. Employee and Address (Embedded)**

- **Tables Involved:** `employee`
- **Relationship Type:** Embedded
    - Address fields (e.g., city, country) are stored directly in the `employee` table.

| Table        | Column              | Type            | Relationship      |
|--------------|---------------------|-----------------|-------------------|
| **employee** | `id`                | Primary Key     |                   |
|              | `name`              | String          |                   |
|              | `address_city`      | String          | Embedded Field    |
|              | `address_country`   | String          | Embedded Field    |

---

#### **3. Job Opening and Department Relationship**

- **Tables Involved:** `job_opening`, `department`
- **Relationship Type:** Many-to-One
    - **One Department** can have **many Job Openings**.
    - **One Job Opening** belongs to **one Department**.

| Table        | Column              | Type            | Relationship      |
|--------------|---------------------|-----------------|-------------------|
| **job_opening** | `id`             | Primary Key     |                   |
|              | `posting_title`     | String          |                   |
|              | `department_id`     | Foreign Key     | References `department.id` |
| **department** | `id`              | Primary Key     |                   |
|              | `name`              | String          |                   |

---

#### **4. Employee and Role Relationship**

- **Tables Involved:** `employee`, `role`
- **Relationship Type:** Many-to-One
    - **One Role** can be assigned to **many Employees**.
    - **One Employee** can have **one Role**.

| Table        | Column              | Type            | Relationship      |
|--------------|---------------------|-----------------|-------------------|
| **employee** | `id`                | Primary Key     |                   |
|              | `name`              | String          |                   |
|              | `role_id`           | Foreign Key     | References `role.id` |
| **role**     | `id`                | Primary Key     |                   |
|              | `name`              | String          |                   |

---

#### **5. Employee and Projects Relationship**

- **Tables Involved:** `employee`, `project`, `employee_project` (Join Table)
- **Relationship Type:** Many-to-Many
    - **One Employee** can work on **many Projects**.
    - **One Project** can have **many Employees**.

| Table              | Column              | Type          | Relationship            |
|--------------------|---------------------|---------------|-------------------------|
| **employee**       | `id`               | Primary Key   |                         |
|                    | `name`             | String        |                         |
| **project**        | `id`               | Primary Key   |                         |
|                    | `title`            | String        |                         |
| **employee_project** | `employee_id`    | Foreign Key   | References `employee.id` |
|                    | `project_id`       | Foreign Key   | References `project.id`  |

---

#### **6. Department and Parent Department Relationship**

- **Tables Involved:** `department`
- **Relationship Type:** Self-Referencing (One-to-Many)
    - **One Department** can have **many Child Departments**.
    - **One Child Department** belongs to **one Parent Department**.

| Table         | Column              | Type          | Relationship      |
|---------------|---------------------|---------------|-------------------|
| **department**| `id`               | Primary Key   |                   |
|               | `name`             | String        |                   |
|               | `parent_department_id` | Foreign Key | References `department.id` |

---

#### **7. User and Profile Relationship**

- **Tables Involved:** `user`, `user_profile`
- **Relationship Type:** Many-to-One
    - **One Profile** can be assigned to **many Users**.
    - **One User** has **one Profile**.

| Table           | Column            | Type          | Relationship            |
|-----------------|-------------------|---------------|-------------------------|
| **user**        | `id`             | Primary Key   |                         |
|                 | `name`           | String        |                         |
|                 | `profile_id`     | Foreign Key   | References `user_profile.id` |
| **user_profile**| `id`             | Primary Key   |                         |
|                 | `permissions`    | JSON          |                         |

---
#### **8. Job Opening and Attachments Relationship**

- **Tables Involved:** `job_opening`, `job_attachment`
- **Relationship Type:** One-to-Many
    - **One Job Opening** can have **many Attachments**.
    - **One Attachment** belongs to **one Job Opening**.

| Table             | Column             | Type          | Relationship            |
|-------------------|--------------------|---------------|-------------------------|
| **job_opening**   | `id`              | Primary Key   |                         |
|                   | `posting_title`   | String        |                         |
| **job_attachment**| `id`              | Primary Key   |                         |
|                   | `job_id`          | Foreign Key   | References `job_opening.id` |
|                   | `file_path`       | String        |                         |

---

These table relationships demonstrate how various entities in the system are connected and how fields map to represent these connections. Each mapping ensures the database and application are in sync.
