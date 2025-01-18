---

# **API Testing Sequence for Role, Profile, User, and Group Management**

## **1. Create a Role**

```bash
curl -X POST http://localhost:8080/api/roles \
-H "Content-Type: application/json" \
-d '{
  "name": "Hiring Manager",
  "description": "Manages hiring process",
  "reportsTo": null,
  "shareDataWithPeers": true
}'
```

**Explanation**:  
This command creates a new role named **Hiring Manager** with the specified description.

---

## **2. Create a Profile**

```bash
curl -X POST http://localhost:8080/api/profiles \
-H "Content-Type: application/json" \
-d '{
  "name": "Standard Profile",
  "description": "Standard permissions for a user",
  "permissions": "{\"modulePermissions\":{\"view\":true,\"create\":true,\"edit\":false,\"delete\":false}}"
}'

```

**Explanation**:  
Creates a profile named **Standard Profile** with specific permissions (view, create, no edit, no delete).

---

## **3. Create a User**

```bash
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-d '{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "role": {
    "id": 1,
    "name": "Hiring Manager"
  },
  "profile": {
    "id": 1,
    "name": "Standard Profile"
  },
  "phone": "1234567890",
  "address": "123 Main St",
  "territory": "East"
}'

```

**Explanation**:  
Creates a **User** with the first name John, last name Doe, and links the newly created **Role** (ID=1) and **
Profile** (ID=1).

---

## **4. Get All Users**

```bash
curl -X GET http://localhost:8080/api/users
```

**Explanation**:  
Retrieves a list of all existing users.

---

## **5. Create a Group**

```bash
curl -X POST http://localhost:8080/api/groups \
-H "Content-Type: application/json" \
-d '{
  "name": "Recruitment Team",
  "description": "Team for managing recruitment process",
  "members": [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe"
    }
  ]
}'
```

**Explanation**:  
Creates a **Group** called **Recruitment Team** and assigns the user with ID=1 (John Doe) as a member.

---

## **6. Add Users to a Group**

```bash
curl -X PUT http://localhost:8080/api/groups/1 \
-H "Content-Type: application/json" \
-d '{
  "name": "Updated Recruitment Team",
  "description": "Updated description for the recruitment team",
  "members": [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe"
    },
    {
      "id": 2,
      "firstName": "Jane",
      "lastName": "Smith"
    }
  ]
}'


```

**Explanation**:  
Updates the group with ID=1 to include two members, John Doe and Jane Smith.

---

## **7. Update a Role**

```bash
curl -X PUT http://localhost:8080/api/roles/1 \
-H "Content-Type: application/json" \
-d '{
  "name": "Senior Hiring Manager",
  "description": "Handles advanced hiring processes",
  "reportsTo": null,
  "shareDataWithPeers": false
}'

```

**Explanation**:  
Updates the existing role (ID=1) to **Senior Hiring Manager** with a new description.

---

## **8. Update a Profile**

```bash
curl -X PUT http://localhost:8080/api/profiles/1 \
-H "Content-Type: application/json" \
-d '{
  "name": "Updated Standard Profile",
  "description": "Updated permissions for a standard user",
  "permissions": "{\"modulePermissions\":{\"view\":true,\"create\":true,\"edit\":true,\"delete\":true}}"
}'

```

**Explanation**:  
Updates the **Profile** (ID=1) to add edit and delete permissions.

---

## **9. Delete a Group**

```bash
curl -X DELETE http://localhost:8080/api/groups/1
```

**Explanation**:  
Deletes the **Group** with ID=1.

---

## **10. Delete a User**

```bash
curl -X DELETE http://localhost:8080/api/users/1
```

**Explanation**:  
Deletes the **User** with ID=1.

---

## **11. Delete a Role**

```bash
curl -X DELETE http://localhost:8080/api/roles/1
```

**Explanation**:  
Deletes the **Role** with ID=1.

---

## **12. Delete a Profile**

```bash
curl -X DELETE http://localhost:8080/api/profiles/1
```

**Explanation**:  
Deletes the **Profile** with ID=1.

---

# **Overall Sequence of Operations**

1. **Create a Role**: `/api/roles` (POST)
2. **Create a Profile**: `/api/profiles` (POST)
3. **Create a User**: `/api/users` (POST)
4. **Get All Users**: `/api/users` (GET)
5. **Create a Group**: `/api/groups` (POST)
6. **Add Users to a Group**: `/api/groups/{id}` (PUT)
7. **Update a Role**: `/api/roles/{id}` (PUT)
8. **Update a Profile**: `/api/profiles/{id}` (PUT)
9. **Delete a Group**: `/api/groups/{id}` (DELETE)
10. **Delete a User**: `/api/users/{id}` (DELETE)
11. **Delete a Role**: `/api/roles/{id}` (DELETE)
12. **Delete a Profile**: `/api/profiles/{id}` (DELETE)

This sequence ensures the complete workflow:

1. Roles and profiles are created.
2. A user is assigned a role and profile.
3. Users are grouped.
4. Updates are made to roles, profiles, and groups.
5. Finally, deletions clean up these entities.

---

**Note**: Make sure to adjust the **IDs** in the `curl` commands as needed (for example, if the role or profile created
does not automatically get the ID=1). The exact IDs will depend on the response from your API.

