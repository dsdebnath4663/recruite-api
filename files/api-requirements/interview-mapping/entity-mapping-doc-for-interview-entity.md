### Entity Mapping Documentation for Interview with Beginners Examples

Below is a simplified explanation and layman's example for each case in the entity mappings provided.

---

### 1. **`Interview` Entity**
#### Mapping Explanation:
- **`@ManyToMany` (Interviewers):** An interview can involve multiple interviewers, and an interviewer can participate in multiple interviews.
- **`@ManyToOne` (Candidate):** Each interview is associated with one candidate.
- **`@ManyToOne` (Interview Owner):** Each interview has an owner responsible for its organization.
- **`@OneToMany` (Attachments):** An interview can have multiple attachments.

#### Example:
- **Interviewers (`@ManyToMany`):** Think of a panel interview where 3 interviewers (e.g., HR, Manager, Technical Lead) assess a single candidate.
- **Candidate (`@ManyToOne`):** If John Doe is scheduled for the interview, this is linked to the `Candidate` entity.
- **Attachments (`@OneToMany`):** The interview has a candidate's resume, a job description, and a feedback form as attachments.

<img src="https://github.com/rohitsunilsharma2000/recruite-api/blob/feature/user-group-management/files/images/interview-mapping/interview-mapping.png?raw=true"
alt="candidate-evaluation-db-mapping.png"/>
---

### 2. **`Attachment` Entity**
#### Mapping Explanation:
- **`@ManyToOne` (Candidate):** Attachments may belong to a candidate, like resumes or cover letters.
- **`@ManyToOne` (JobOpening):** Attachments may belong to a job opening, like job descriptions or role documents.
- **`@ManyToOne` (Interview):** Attachments may belong to an interview.

#### Example:
- A resume uploaded by a candidate (`@ManyToOne` Candidate).
- A job description document for the opening (`@ManyToOne` JobOpening).
- Interview-specific documents like feedback forms (`@ManyToOne` Interview).

---

### 3. **`JobOpening` Entity**
#### Mapping Explanation:
- **`@ManyToOne` (Assigned Recruiter):** A recruiter is assigned to manage the job opening.
- **`@ManyToOne` (Hiring Manager):** Each job opening is linked to a hiring manager.
- **`@OneToMany` (Attachments):** A job opening can have multiple documents like descriptions or requirements attached.
- **`@ElementCollection` (Skills):** A job opening requires multiple skills stored as a list.

#### Example:
- **Assigned Recruiter (`@ManyToOne`):** Sarah is the recruiter managing the "Software Engineer" position.
- **Skills (`@ElementCollection`):** Required skills include Java, Spring Boot, and AWS.
- **Attachments (`@OneToMany`):** Documents like job role PDF and company policies.

---

### 4. **`Candidate` Entity**
#### Mapping Explanation:
- **`@OneToMany` (Attachments):** A candidate can have multiple documents like resumes or certificates.
- **`@OneToMany` (EducationalDetails):** A candidate can have multiple educational qualifications.
- **`@OneToMany` (ExperienceDetails):** A candidate can have multiple prior work experiences.
- **`@ManyToOne` (Candidate Owner):** Each candidate is managed by a specific recruiter or owner.

#### Example:
- John Doe has uploaded a resume and a degree certificate (`@OneToMany` Attachments).
- He has studied at XYZ University and ABC College (`@OneToMany` EducationalDetails).
- John worked at Company A and Company B (`@OneToMany` ExperienceDetails).

---

### 5. **`User` Entity**
#### Mapping Explanation:
- **`@ManyToOne` (Role):** Each user has a role, such as "Admin" or "Recruiter."
- **`@ManyToOne` (Profile):** Users can have profiles containing additional personal information.

#### Example:
- **Role (`@ManyToOne`):** Sarah is assigned the "Recruiter" role.
- **Profile (`@ManyToOne`):** Sarah's profile contains her contact details and photo.

---

### Entity Relationship Summary:

| Entity       | Relation Type      | Associated Entity  | Description                                                                 |
|--------------|--------------------|--------------------|-----------------------------------------------------------------------------|
| **Interview** | `@ManyToMany`      | User (Interviewers)| Multiple interviewers per interview, and vice versa.                       |
|              | `@ManyToOne`       | Candidate          | One candidate per interview.                                               |
|              | `@OneToMany`       | Attachment         | Multiple attachments per interview.                                        |
| **Attachment**| `@ManyToOne`       | Candidate          | Attachments linked to a candidate.                                         |
|              | `@ManyToOne`       | JobOpening         | Attachments linked to a job opening.                                       |
|              | `@ManyToOne`       | Interview          | Attachments linked to an interview.                                        |
| **JobOpening**| `@ManyToOne`       | User (Recruiter)   | Recruiter managing the job opening.                                        |
|              | `@OneToMany`       | Attachment         | Attachments like job descriptions.                                         |
| **Candidate**| `@OneToMany`       | Attachment         | Attachments like resumes or certificates.                                  |
|              | `@OneToMany`       | EducationalDetails | Educational qualifications of the candidate.                               |
|              | `@OneToMany`       | ExperienceDetails  | Previous work experiences.                                                 |
|              | `@ManyToOne`       | User (Owner)       | Recruiter responsible for managing the candidate.                          |

---

This layout provides a clear understanding of how entities interact, with relatable real-world examples. Let me know if you need more details or changes!