# Low-Level Design (LLD) — Job Portal System

This document outlines the detailed Low-Level Design (LLD) of the **Job Portal System**. It specifies the domain models, database schemas, design patterns, microservice interaction contracts, and core sequence flows.

---

## 1. Domain Models & Class Diagram

The system employs a decoupled domain model aligned to distinct microservices:

```mermaid
classDiagram
    class User {
        +Long id
        +String firstName
        +String lastName
        +String email
        +String password
        +Role role
        +boolean active
    }
    
    class Company {
        +Long id
        +String name
        +String description
        +String industry
        +List~String~ locations
        +String logoUrl
    }
    
    class Job {
        +Long id
        +String title
        +String description
        +List~String~ skills
        +JobType jobType
        +WorkMode workMode
        +ExperienceLevel experienceLevel
        +Long companyId
        +Double salaryMin
        +Double salaryMax
        +String currency
        +boolean active
    }
    
    class Resume {
        +Long id
        +Long userId
        +String summary
        +List~Education~ education
        +List~WorkExperience~ workExperience
        +List~String~ skills
    }

    class Application {
        +Long id
        +Long jobId
        +Long resumeId
        +Long candidateId
        +ApplicationStatus status
        +String coverLetter
        +List~String~ recruiterNotes
        +Double screeningScore
    }

    User "1" --> "0..1" Resume : owns
    Company "1" --> "0..*" Job : hosts
    Job "1" --> "0..*" Application : receives
    Resume "1" --> "0..*" Application : applies with
```

### Enums
* **`Role`**: `ROLE_CANDIDATE`, `ROLE_EMPLOYER`, `ROLE_ADMIN`
* **`JobType`**: `FULL_TIME`, `PART_TIME`, `CONTRACT`, `INTERNSHIP`, `FREELANCE`
* **`WorkMode`**: `REMOTE`, `HYBRID`, `ON_SITE`
* **`ExperienceLevel`**: `ENTRY`, `MID`, `SENIOR`, `LEAD`, `EXECUTIVE`
* **`ApplicationStatus`**: `APPLIED`, `SCREENING`, `INTERVIEWING`, `OFFERED`, `REJECTED`

---

## 2. Database Schema (Entity-Relationship Diagram)

Each microservice isolates its database schema to enforce datasource boundary security. Relations across databases are referenced logically by ID.

```mermaid
erDiagram
    USERS {
        bigint id PK
        varchar first_name
        varchar last_name
        varchar email UK
        varchar password
        varchar role
        boolean active
    }

    COMPANIES {
        bigint id PK
        varchar name
        text description
        varchar industry
        varchar logo_url
    }

    JOBS {
        bigint id PK
        varchar title
        text description
        varchar job_type
        varchar work_mode
        varchar experience_level
        bigint company_id
        double salary_min
        double salary_max
        varchar currency
        boolean active
    }

    RESUMES {
        bigint id PK
        bigint user_id UK
        text summary
    }

    WORK_EXPERIENCES {
        bigint id PK
        bigint resume_id FK
        varchar job_title
        varchar company
        text description
    }

    EDUCATIONS {
        bigint id PK
        bigint resume_id FK
        varchar degree
        varchar institution_name
        varchar field_of_study
    }

    APPLICATIONS {
        bigint id PK
        bigint job_id
        bigint resume_id
        bigint candidate_id
        varchar status
        text cover_letter
        double screening_score
    }
```

---

## 3. Design Patterns Applied

### A. Strategy Pattern (AI Output Strategy)
Used inside the `GeminiClient` to determine how models handle structuring responses (e.g., standard Markdown text vs structured JSON output).

```mermaid
classDiagram
    class ContentGenerationStrategy {
        <<interface>>
        +generate(String systemInstruction, String prompt) Object
    }
    class TextGenerationStrategy {
        +generate(String systemInstruction, String prompt) String
    }
    class JsonGenerationStrategy~T~ {
        -Class~T~ responseType
        -ObjectMapper mapper
        +generate(String systemInstruction, String prompt) T
    }
    ContentGenerationStrategy <|.. TextGenerationStrategy
    ContentGenerationStrategy <|.. JsonGenerationStrategy
```

### B. Builder Pattern (Fluent Request Payloads)
Applied across all response wrappers (e.g., `AiTextResponse`) to build payloads fluently.
```java
AiTextResponse response = AiTextResponse.builder()
        .content(rawText)
        .build();
```

### C. Observer Pattern (Async Notifications)
Recruiters and candidates are notified of status changes asynchronously (e.g., when a candidate gets a `ScreeningScore` computed, a notification event is emitted).

---

## 4. Microservice Sequence Flows

### Sequence A: Candidate Application Auto-Screening
Calculates candidate match compatibility using application-specific criteria.

```mermaid
sequenceDiagram
    participant App as Application Service (5005)
    participant Gateway as API Gateway (5000)
    participant AI as AI Service (6000)
    participant Gemini as Google Gemini API

    App->>Gateway: POST /api/ai/application/screening-score
    Gateway->>AI: Routing & JWT Authentication check
    AI->>Gemini: generateJson(ScreeningScoreRequest)
    Gemini-->>AI: returns ScreeningScoreResponse JSON
    AI-->>Gateway: Forward Match Assessment & Score
    Gateway-->>App: Return Score (e.g., Match: 85%)
    App->>App: Update application.screening_score
```

### Sequence B: Declarative Inter-Service Fetching (OpenFeign)
Shows how OpenFeign abstracts inter-service synchronization.

```mermaid
sequenceDiagram
    participant Client as Client Request
    participant App as Application Service
    participant Feign as Feign Client Wrapper
    participant Job as Job Service

    Client->>App: GET /api/applications/1
    App->>Feign: getJobById(jobId)
    Feign->>Job: GET /api/jobs/{id}
    Job-->>Feign: return Job details
    Feign-->>App: return JobDTO
    App-->>Client: Assemble & Return Application Detail
```

---

## 5. API Interface Contracts

### A. AI Resume Controller (`/api/ai/resume`)
* **`POST /summary`**
  * **Request**: `ResumeSummaryRequest` (targetJobTitle, yearOfExperience, skills, workExperience, education)
  * **Response**: `AiTextResponse` (content)
* **`POST /experience-bullets`**
  * **Request**: `WorkExperienceBulletRequest` (jobTitle, company, rawDescription, achievementsHint)
  * **Response**: `WorkExperienceBulletsResponse` (bullets: List\<String\>)

### B. AI Job Controller (`/api/ai/job`)
* **`POST /describe`**
  * **Request**: `JobDescriptionRequest` (title, skill, experienceLevel, jobType, workMode, category, additionalContext)
  * **Response**: `AiTextResponse` (content)
* **`POST /salary-suggestion`**
  * **Request**: `SalaryRangeRequest` (title, skills, experienceLevel, jobType, location)
  * **Response**: `SalaryRangeResponse` (minSalary, maxSalary, currency, period, marketInsight)

### C. AI Application Controller (`/api/ai/application`)
* **`POST /screening-score`**
  * **Request**: `ScreeningScoreRequest` (jobTitle, experienceLevel, requiredSkills, responsibilities, candidateSummary, candidateSkills, candidateExperience, candidateEducation)
  * **Response**: `ScreeningScoreResponse` (score, skillsMatchScore, experienceMatchScore, educationMatchScore, matchedSkills, missingSkills, strengths, concerns, summary)

### D. AI Search Controller (`/api/ai/search`)
* **`POST /enhance`**
  * **Request**: `SearchEnhanceRequest` (query)
  * **Response**: `SearchEnhanceResponse` (keywords, locations, jobTypes, workModes, experienceLevels, minSalary, skills)
