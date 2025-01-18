### Database Mapping in JPA with Examples for Beginners

Let’s break down database mapping with simple, **real-world relatable examples** for each case. Imagine we're building an application for managing employees, departments, and their roles in a company.

---

#### **1. `@Entity`: Mapping a Java Class to a Table**

**Example:**
```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key (unique for every employee)

    private String name; // Employee name
    private String email; // Employee email
}
```

**Explanation:**
Think of `@Entity` as a marker that says, "This class represents a table in the database." For example:
- Table name: `employee`
- Columns: `id`, `name`, `email`.

---

#### **2. `@Table`: Customizing the Table Name**

**Example:**
```java
@Entity
@Table(name = "company_employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```

**Explanation:**
If you want the table name to be `company_employees` instead of the default `Employee`, use `@Table`.

---

#### **3. `@Column`: Customizing Column Names and Attributes**

**Example:**
```java
@Column(name = "employee_name", nullable = false, unique = true)
private String name;
```

**Explanation:**
- Column name in the table: `employee_name`.
- `nullable = false`: This column cannot have empty values.
- `unique = true`: Every row must have a unique value in this column (e.g., two employees cannot have the same name).

---

#### **4. `@Id` and `@GeneratedValue`: Primary Key with Auto-Increment**

**Example:**
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

**Explanation:**
- `@Id`: Marks the `id` field as the primary key.
- `@GeneratedValue`: Automatically generates the primary key value (like auto-increment in SQL).

**Real-world analogy:** Imagine every employee has an employee ID that is automatically assigned when they join the company.

---

#### **5. Relationships (`@ManyToOne`, `@OneToMany`, `@ManyToMany`)**

##### **a. `@ManyToOne`: Many Employees Belong to One Department**
**Example:**
```java
@ManyToOne
@JoinColumn(name = "department_id")
private Department department;
```

**Explanation:**
- Each employee belongs to one department.
- `@JoinColumn`: Maps the `department_id` column as the foreign key in the `employee` table.

**Real-world analogy:** John and Sarah both belong to the `IT` department.

---

##### **b. `@OneToMany`: One Department Has Many Employees**
**Example:**
```java
@OneToMany(mappedBy = "department")
private List<Employee> employees;
```

**Explanation:**
- A department can have many employees.
- `mappedBy = "department"` links it to the `department` field in the `Employee` entity.

**Real-world analogy:** The IT department has John, Sarah, and Alice as employees.

---

##### **c. `@ManyToMany`: Many Employees Can Join Many Projects**
**Example:**
```java
@ManyToMany
@JoinTable(
    name = "employee_project",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "project_id")
)
private List<Project> projects;
```

**Explanation:**
- `@JoinTable`: Creates a join table `employee_project` with `employee_id` and `project_id` as foreign keys.
- An employee can work on multiple projects, and a project can have multiple employees.

**Real-world analogy:** John is working on the "CRM Upgrade" and "Website Redesign" projects.

---

#### **6. `@Embedded` and `@Embeddable`: Embedding a Value Object**

**Example:**
```java
@Embedded
private Address address;

@Embeddable
public class Address {
    private String city;
    private String country;
}
```

**Explanation:**
- `@Embeddable`: Marks `Address` as a reusable value object.
- `@Embedded`: Indicates that the `address` fields (e.g., city, country) are part of the `employee` table.

**Real-world analogy:** Every employee has an address with fields like city and country.

---

#### **7. `@Enumerated`: Storing Enums in the Database**

**Example:**
```java
@Enumerated(EnumType.STRING)
private JobType jobType;

public enum JobType {
    FULL_TIME, PART_TIME, CONTRACT
}
```

**Explanation:**
- `EnumType.STRING`: Stores the enum as text (e.g., "FULL_TIME").
- `EnumType.ORDINAL`: Stores the enum as a number (e.g., 0, 1).

**Real-world analogy:** An employee's job type can be full-time, part-time, or contract.

---

#### **8. Lazy Loading (`fetch = FetchType.LAZY`)**

**Example:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "department_id")
private Department department;
```

**Explanation:**
- `FetchType.LAZY`: The department data is not loaded unless explicitly accessed.
- Useful when you don't always need related data.

**Real-world analogy:** You don’t load the entire department’s details when viewing a single employee.

---

#### **9. JSON Fields with `@Transient` and `@Column`**

**Example:**
```java
@Column(columnDefinition = "TEXT")
private String permissionsJson;

@Transient
private Permissions permissions;
```

**Explanation:**
- `@Transient`: Fields are excluded from persistence (e.g., not stored in the database).
- `permissionsJson`: Stores the data as raw JSON.
- `permissions`: A runtime representation of the JSON data.

**Real-world analogy:** You store raw JSON for permissions but use an object to manipulate it in the code.

---

#### **10. Cascading and Orphan Removal**

**Example:**
```java
@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Employee> employees;
```

**Explanation:**
- `cascade = CascadeType.ALL`: Operations like save, update, and delete are cascaded to child entities.
- `orphanRemoval = true`: Automatically deletes employees removed from the department list.

**Real-world analogy:** When a department is deleted, all its employees are reassigned or removed.

---

#### Full Beginner-Friendly Example: Employee and Department
```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Embedded
    private Address address;
}

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;
}

@Embeddable
public class Address {
    private String city;
    private String country;
}
```

**Explanation:**
- Each employee belongs to a department.
- Each department has multiple employees.
- Employees have an address (city and country) embedded in the same table.

---

By following these examples, you can map your application’s data models to a relational database effectively and understand how annotations simplify these mappings.