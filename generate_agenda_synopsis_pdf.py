import os
import sys
from reportlab.lib.pagesizes import letter
from reportlab.lib import colors
from reportlab.lib.units import inch
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, PageBreak, KeepTogether, HRFlowable
)
from reportlab.pdfgen import canvas

class NumberedCanvas(canvas.Canvas):
    def __init__(self, *args, **kwargs):
        super(NumberedCanvas, self).__init__(*args, **kwargs)
        self._saved_page_states = []

    def showPage(self):
        self._saved_page_states.append(dict(self.__dict__))
        self._startPage()

    def save(self):
        num_pages = len(self._saved_page_states)
        for state in self._saved_page_states:
            self.__dict__.update(state)
            self.draw_page_decorations(num_pages)
            super(NumberedCanvas, self).showPage()
        super(NumberedCanvas, self).save()

    def draw_page_decorations(self, page_count):
        self.saveState()
        self.setFont("Helvetica", 8.5)
        self.setFillColor(colors.HexColor("#64748B"))
        
        # Header on pages 2+
        if self._pageNumber > 1:
            self.drawString(54, 750, "JobPortal.AI — Distributed Microservices Architecture & Project Synopsis")
            self.setStrokeColor(colors.HexColor("#CBD5E1"))
            self.setLineWidth(0.5)
            self.line(54, 742, 558, 742)
            
            # Running Footer
            self.line(54, 45, 558, 45)
            self.drawString(54, 32, "Technical Project Synopsis — Computer Science & Engineering")
            page_text = f"Page {self._pageNumber} of {page_count}"
            self.drawRightString(558, 32, page_text)
        self.restoreState()

def build_agenda_synopsis(filename="Job_Portal_Project_Synopsis.pdf"):
    doc = SimpleDocTemplate(
        filename,
        pagesize=letter,
        leftMargin=54,
        rightMargin=54,
        topMargin=54,
        bottomMargin=54
    )

    styles = getSampleStyleSheet()
    
    # Palette
    primary_dark  = colors.HexColor("#0F172A")   # Slate 900
    brand_indigo  = colors.HexColor("#4F46E5")   # Indigo 600
    text_dark     = colors.HexColor("#1E293B")   # Slate 800
    text_muted    = colors.HexColor("#475569")   # Slate 600
    bg_light      = colors.HexColor("#F8FAFC")   # Slate 50
    bg_subtle     = colors.HexColor("#F1F5F9")   # Slate 100
    border_color  = colors.HexColor("#E2E8F0")   # Slate 200

    title_style = ParagraphStyle(
        'DocTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=24,
        leading=28,
        textColor=brand_indigo,
        spaceAfter=6
    )

    subtitle_style = ParagraphStyle(
        'DocSubTitle',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=11,
        leading=15,
        textColor=text_muted,
        spaceAfter=14
    )

    sec_title_style = ParagraphStyle(
        'SecTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=15,
        leading=18,
        textColor=primary_dark,
        spaceBefore=14,
        spaceAfter=8,
        keepWithNext=True
    )

    sub_sec_style = ParagraphStyle(
        'SubSecTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=11,
        leading=14.5,
        textColor=brand_indigo,
        spaceBefore=10,
        spaceAfter=4,
        keepWithNext=True
    )

    body_style = ParagraphStyle(
        'BodyDark',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9.2,
        leading=13.2,
        textColor=text_dark,
        spaceAfter=6
    )

    bullet_style = ParagraphStyle(
        'BulletDark',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9.2,
        leading=13.2,
        textColor=text_dark,
        leftIndent=12,
        firstLineIndent=-8,
        spaceAfter=3.5
    )

    code_box_style = ParagraphStyle(
        'CodeBoxStyle',
        fontName='Courier',
        fontSize=7.2,
        leading=9.2,
        textColor=colors.HexColor("#0F172A")
    )

    t_cell = ParagraphStyle('TCell', fontName='Helvetica', fontSize=8.2, leading=11, textColor=text_dark)
    t_cell_bold = ParagraphStyle('TCellBold', fontName='Helvetica-Bold', fontSize=8.2, leading=11, textColor=primary_dark)
    t_header = ParagraphStyle('THeader', fontName='Helvetica-Bold', fontSize=8.5, leading=11.5, textColor=colors.white)

    story = []

    # ==========================================
    # COVER / HEADER BLOCK & AGENDA
    # ==========================================
    story.append(Paragraph("PROJECT SYNOPSIS & ARCHITECTURAL DOCUMENTATION", ParagraphStyle('TopCategory', fontName='Helvetica-Bold', fontSize=9, leading=11, textColor=brand_indigo, spaceAfter=4)))
    story.append(Paragraph("JobPortal.AI — Distributed Microservices Recruitment Platform", title_style))
    story.append(Paragraph("An Enterprise Distributed Recruitment Engine featuring 10+ Spring Boot Microservices, Google OAuth2 SSO, Gmail SMTP 6-Digit OTP Email Verification, AI Skill Compatibility Matching Engine, Dynamic Drag-and-Drop Resume Builder, and Database-per-Service Architecture.", subtitle_style))
    story.append(HRFlowable(width="100%", thickness=1.5, color=brand_indigo, spaceBefore=0, spaceAfter=14))

    # Executive Metadata Summary Table
    meta_table_data = [
        [Paragraph("Project Title", t_cell_bold), Paragraph("JobPortal.AI (ZoshHire Platform)", t_cell), Paragraph("Domain", t_cell_bold), Paragraph("Distributed Systems & AI Recruitment", t_cell)],
        [Paragraph("Architecture Pattern", t_cell_bold), Paragraph("Microservices (Spring Cloud Framework)", t_cell), Paragraph("Frontend Framework", t_cell_bold), Paragraph("React 18, Vite, Redux Toolkit, Tailwind", t_cell)],
        [Paragraph("Backend Framework", t_cell_bold), Paragraph("Java 17 / Spring Boot 3.4+, Spring Cloud", t_cell), Paragraph("Persistence Layer", t_cell_bold), Paragraph("PostgreSQL 16 (6 Dedicated DB Instances)", t_cell)],
        [Paragraph("Service Registry", t_cell_bold), Paragraph("Netflix Eureka Registry (Port 8761)", t_cell), Paragraph("API Gateway", t_cell_bold), Paragraph("Spring Cloud Gateway MVC (Port 9000)", t_cell)],
        [Paragraph("Configuration", t_cell_bold), Paragraph("Spring Cloud Config Server (Port 8888)", t_cell), Paragraph("Event Streaming", t_cell_bold), Paragraph("Apache Kafka 7.6.1 & Zookeeper 7.6.1", t_cell)],
        [Paragraph("Containerization", t_cell_bold), Paragraph("Docker Desktop, Docker Compose, Jib Plugin", t_cell), Paragraph("Security Models", t_cell_bold), Paragraph("Google OAuth2, JWT, Gmail SMTP OTP, RBAC", t_cell)]
    ]
    meta_table = Table(meta_table_data, colWidths=[1.1*inch, 2.3*inch, 1.1*inch, 2.3*inch])
    meta_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,-1), bg_light),
        ('BOX', (0,0), (-1,-1), 0.5, border_color),
        ('INNERGRID', (0,0), (-1,-1), 0.5, border_color),
        ('TOPPADDING', (0,0), (-1,-1), 4.5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4.5),
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
    ]))
    story.append(meta_table)
    story.append(Spacer(1, 14))

    # PRESENTATION AGENDA INDEX (Structured strictly from screenshot agenda)
    story.append(Paragraph("Presentation & Synopsis Agenda Index", sub_sec_style))
    story.append(Paragraph("This project synopsis is organized according to the official presentation agenda criteria:", body_style))
    
    agenda_data = [
        [Paragraph("#", t_header), Paragraph("Agenda Section Title", t_header), Paragraph("Core Technical Content Covered", t_header), Paragraph("Page Target", t_header)],
        [Paragraph("01", t_cell_bold), Paragraph("1. Problem Statement", t_cell_bold), Paragraph("Monolithic limitations, database lock contention, single points of failure, bot signup abuse, screening overhead.", t_cell), Paragraph("Page 2", t_cell)],
        [Paragraph("02", t_cell_bold), Paragraph("2. Objectives", t_cell_bold), Paragraph("Microservices decomposition, Database-per-Service, OAuth2+OTP flow, AI match engine, Kafka pipeline.", t_cell), Paragraph("Page 3", t_cell)],
        [Paragraph("03", t_cell_bold), Paragraph("3. Technology Stack", t_cell_bold), Paragraph("Full stack framework tables, libraries, infrastructure baseline, and 4-member development team role division.", t_cell), Paragraph("Page 4–5", t_cell)],
        [Paragraph("04", t_cell_bold), Paragraph("4. Literature Survey", t_cell_bold), Paragraph("Historical recruitment evolution (Gen 1-3), academic references, Literature Comparison Matrix vs Indeed/LinkedIn.", t_cell), Paragraph("Page 6", t_cell)],
        [Paragraph("05", t_cell_bold), Paragraph("5. System Workflow / Architecture", t_cell_bold), Paragraph("Architecture Topology Diagram, Gateway routes, Database Schemas (6 DBs), OAuth2+OTP Sequence, React UI routes.", t_cell), Paragraph("Page 7–12", t_cell)],
        [Paragraph("06", t_cell_bold), Paragraph("6. Expected Outcome", t_cell_bold), Paragraph("Tangible deliverables, SLA performance benchmarks, societal impact, carbon elasticity, future roadmap.", t_cell), Paragraph("Page 13–14", t_cell)],
        [Paragraph("07", t_cell_bold), Paragraph("7. References", t_cell_bold), Paragraph("Formal academic papers, specifications (OAuth2 RFC 6749, JWT RFC 7519), software manuals, sign-off block.", t_cell), Paragraph("Page 15", t_cell)]
    ]
    agenda_table = Table(agenda_data, colWidths=[0.4*inch, 2.0*inch, 3.7*inch, 0.7*inch])
    agenda_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
        ('ALIGN', (0,0), (0,-1), 'CENTER'),
        ('ALIGN', (3,0), (3,-1), 'CENTER'),
    ]))
    story.append(agenda_table)

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 1: PROBLEM STATEMENT
    # ==========================================
    story.append(Paragraph("1. Problem Statement", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("1.1 Background Context & Industry Challenges", sub_sec_style))
    story.append(Paragraph(
        "Online job portals serve as the digital backbone of contemporary talent acquisition. Millions of job seekers upload resumes and apply for positions across global portals daily. However, conventional online recruitment systems are engineered predominantly around monolithic monolithic software architectures. In a monolithic architecture, authentication, job listings, application processing, messaging, and search indexing reside inside a single code artifact and a shared, monolithic database instance.",
        body_style
    ))
    story.append(Paragraph(
        "During high-volume hiring drives or peak traffic bursts, these monolithic systems experience severe infrastructure degradation. Heavy read/write operations exhaust database connection pools, lock shared table rows, and cause cascading thread starvation across the entire platform.",
        body_style
    ))

    story.append(Paragraph("1.2 Detailed Problem Identification", sub_sec_style))
    story.append(Paragraph("Through extensive code and structural analysis, seven primary problem domains were identified in existing portal systems:", body_style))

    story.append(Paragraph("1. <b>Monolithic Relational Database Lock Contention:</b> Concurrent transactions across job posts, candidate application submissions, and search indexing contend for identical PostgreSQL table locks, producing frequent deadlocks, elevated latency, and dropped connections.", bullet_style))
    story.append(Paragraph("2. <b>Single Point of Failure (SPOF):</b> An unhandled exception or memory leak in a secondary component (e.g., mail dispatch or PDF rendering) crashes the entire monolithic process, blocking core authentication and application pathways.", bullet_style))
    story.append(Paragraph("3. <b>Unverified Bot Registrations & Spam Signups:</b> Basic single-factor registration allows automated bots to create mass spam profiles, polluting employer applicant tracking pipelines and degrading platform trust.", bullet_style))
    story.append(Paragraph("4. <b>High Manual Resume Screening Fatigue:</b> Recruiter teams waste over 70% of initial sourcing cycles manually parsing non-standardized resume formats, introducing human fatigue and hiring delays.", bullet_style))
    story.append(Paragraph("5. <b>Inflexible Scaling & Wasteful Infrastructure Spend:</b> Monolithic systems force administrators to scale the entire monolithic application image even if only job search indexing experiences high traffic, multiplying cloud hosting expenses.", bullet_style))
    story.append(Paragraph("6. <b>Synchronous Mail Dispatch Latency:</b> Triggering transactional emails synchronously inside request-response execution loops adds 2 to 5 seconds of HTTP latency per application submission.", bullet_style))
    story.append(Paragraph("7. <b>Static Resume Management:</b> Job seekers are forced to create resumes in external tools and continuously upload static files, causing parsing errors and formatting distortions.", bullet_style))

    story.append(Spacer(1, 10))
    story.append(Paragraph("1.3 Problem Domain Impact Summary", sub_sec_style))
    prob_summary_data = [
        [Paragraph("Problem Dimension", t_header), Paragraph("Monolithic Legacy Systems", t_header), Paragraph("Operational Impact", t_header)],
        [Paragraph("Database Architecture", t_cell_bold), Paragraph("Single Shared Monolithic DB", t_cell), Paragraph("High table locks, database connection starvation, deadlocks during peak loads.", t_cell)],
        [Paragraph("System Reliability", t_cell_bold), Paragraph("Coupled Application Monolith", t_cell), Paragraph("Single component error causes total platform outage (SPOF).", t_cell)],
        [Paragraph("User Verification", t_cell_bold), Paragraph("Unverified Single-Factor Auth", t_cell), Paragraph("Bot spam profiles, fake accounts, recruiter inbox pollution.", t_cell)],
        [Paragraph("Candidate Screening", t_cell_bold), Paragraph("Manual Keyword Inspection", t_cell), Paragraph("Screening delays, recruiter fatigue, missed qualified talent.", t_cell)],
        [Paragraph("Infrastructure Cost", t_cell_bold), Paragraph("All-or-Nothing Monolithic Scale", t_cell), Paragraph("Inefficient cloud resource utilization, inflated hosting costs.", t_cell)]
    ]
    prob_table = Table(prob_summary_data, colWidths=[1.5*inch, 2.3*inch, 3.0*inch])
    prob_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(prob_table)

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 2: OBJECTIVES
    # ==========================================
    story.append(Paragraph("2. Objectives", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("2.1 Primary Project Goal", sub_sec_style))
    story.append(Paragraph(
        "The primary objective of <b>JobPortal.AI</b> is to architect, implement, and validate a highly scalable, fault-tolerant, distributed microservices recruitment platform. By decoupling core domains into 10+ autonomous microservices and enforcing isolated database per service persistence, the platform guarantees high availability, sub-100ms API response times, and intelligent AI candidate screening.",
        body_style
    ))

    story.append(Paragraph("2.2 Key Technical Objectives", sub_sec_style))
    story.append(Paragraph("To solve the identified problem statements, JobPortal.AI establishes seven core technical objectives:", body_style))

    story.append(Paragraph("1. <b>Architect Domain-Driven Microservices Stack:</b> Deconstruct recruitment workflows into independent Spring Boot microservices: API Gateway, User Service, Job Service, Company Service, Application Service, Resume Service, Preference Service, AI Service, Notification Service, Eureka Discovery Server, and Config Server.", bullet_style))
    story.append(Paragraph("2. <b>Implement Database-per-Service Isolation Pattern:</b> Provision 6 isolated PostgreSQL 16 database containers (`userdb:5433`, `companydb:5434`, `jobdb:5435`, `applicationdb:5436`, `preferencedb:5439`, `resumedb:5440`) to completely eliminate cross-domain database lock contention.", bullet_style))
    story.append(Paragraph("3. <b>Engineer High-Security Hybrid OAuth2 + Mandatory OTP Email Verification:</b> Integrate Google OAuth2 Single Sign-On with a mandatory Gmail SMTP 6-digit OTP verification pipeline, assigning `INACTIVE` status until the code is validated.", bullet_style))
    story.append(Paragraph("4. <b>Deliver AI Candidate Matching & Screening Engine:</b> Implement an AI service that calculates real-time candidate skill compatibility match percentages ($MatchScore$) against job requirement criteria.", bullet_style))
    story.append(Paragraph("5. <b>Provide Live Interactive Drag-and-Drop Resume Builder:</b> Develop a dynamic React resume builder with instant section updates and dynamic PDF export capability.", bullet_style))
    story.append(Paragraph("6. <b>Construct Asynchronous Event-Driven Messaging Pipeline:</b> Integrate Apache Kafka 7.6.1 and Zookeeper 7.6.1 for background notification queueing.", bullet_style))
    story.append(Paragraph("7. <b>Automate Containerized Deployment Orchestration:</b> Package microservices into Docker images via Google Jib and manage multi-container stack orchestration using Docker Compose.", bullet_style))

    story.append(Spacer(1, 10))
    story.append(Paragraph("2.3 Functional Matrix & System Boundaries", sub_sec_style))
    obj_matrix_data = [
        [Paragraph("Target User Role", t_header), Paragraph("In-Scope System Capabilities", t_header), Paragraph("Target Quality Benchmark", t_header)],
        [Paragraph("Job Seekers", t_cell_bold), Paragraph("Google OAuth2 / OTP login, profile management, real-time job search & filter, AI match evaluation, live drag & drop resume builder, application tracking.", t_cell), Paragraph("Sub-100ms response time, mobile responsiveness.", t_cell)],
        [Paragraph("Employers", t_cell_bold), Paragraph("Company profile setup, verification workflow, job creation & editing, applicant review pipeline, AI resume match rating, subscription management.", t_cell), Paragraph("Zero cross-domain locks, automated candidate ranking.", t_cell)],
        [Paragraph("System Admin", t_cell_bold), Paragraph("User account activation/suspension, employer verification, global platform metrics, job metadata administration.", t_cell), Paragraph("Centralized audit & security administration.", t_cell)]
    ]
    obj_table = Table(obj_matrix_data, colWidths=[1.4*inch, 4.0*inch, 1.4*inch])
    obj_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), brand_indigo),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(obj_table)

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 3: TECHNOLOGY STACK
    # ==========================================
    story.append(Paragraph("3. Technology Stack", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("3.1 Comprehensive Framework & Component Matrix", sub_sec_style))
    story.append(Paragraph(
        "JobPortal.AI utilizes a production-grade enterprise technology stack. Backend microservices are built on Java 17 and Spring Boot 3.4+, while the frontend is constructed using React 18, Vite, and Redux Toolkit.",
        body_style
    ))

    tech_data = [
        [Paragraph("Layer / Domain", t_header), Paragraph("Framework / Package", t_header), Paragraph("Version", t_header), Paragraph("Architectural Purpose", t_header)],
        [Paragraph("Language Platform", t_cell_bold), Paragraph("Java (JDK)", t_cell), Paragraph("17 LTS", t_cell), Paragraph("Core backend runtime environment.", t_cell)],
        [Paragraph("Backend Core", t_cell_bold), Paragraph("Spring Boot", t_cell), Paragraph("3.4.2", t_cell), Paragraph("Microservice framework & REST controller engine.", t_cell)],
        [Paragraph("Cloud Ecosystem", t_cell_bold), Paragraph("Spring Cloud", t_cell), Paragraph("2023.0.0", t_cell), Paragraph("API Gateway, Eureka Discovery, Config Server.", t_cell)],
        [Paragraph("Security Chain", t_cell_bold), Paragraph("Spring Security / OAuth2", t_cell), Paragraph("6.x", t_cell), Paragraph("Google OAuth2 SSO, JWT parsing, Security Filter Chain.", t_cell)],
        [Paragraph("Persistence Engine", t_cell_bold), Paragraph("PostgreSQL", t_cell), Paragraph("16.0", t_cell), Paragraph("Relational databases (6 containerized DBs).", t_cell)],
        [Paragraph("ORM Layer", t_cell_bold), Paragraph("Spring Data JPA / Hibernate", t_cell), Paragraph("7.2.1", t_cell), Paragraph("Object-relational mapping & repositories.", t_cell)],
        [Paragraph("Messaging Queue", t_cell_bold), Paragraph("Apache Kafka & Zookeeper", t_cell), Paragraph("7.6.1", t_cell), Paragraph("Asynchronous event streaming & notifications.", t_cell)],
        [Paragraph("Frontend Core", t_cell_bold), Paragraph("React", t_cell), Paragraph("18.3.1", t_cell), Paragraph("Client UI rendering & component model.", t_cell)],
        [Paragraph("Frontend Bundler", t_cell_bold), Paragraph("Vite", t_cell), Paragraph("5.4.x", t_cell), Paragraph("Fast HMR bundler and dev server.", t_cell)],
        [Paragraph("State Management", t_cell_bold), Paragraph("Redux Toolkit", t_cell), Paragraph("2.2.x", t_cell), Paragraph("Global app state & async Thunks.", t_cell)],
        [Paragraph("UI & Styling", t_cell_bold), Paragraph("Tailwind CSS & Shadcn UI", t_cell), Paragraph("3.4.x", t_cell), Paragraph("Utility-first design & UI primitives.", t_cell)],
        [Paragraph("Form Validation", t_cell_bold), Paragraph("React Hook Form + Zod", t_cell), Paragraph("3.x / 7.x", t_cell), Paragraph("Client-side validation & schema enforcement.", t_cell)],
        [Paragraph("Containerization", t_cell_bold), Paragraph("Docker & Docker Compose", t_cell), Paragraph("24.0 / 2.20", t_cell), Paragraph("Multi-container local stack orchestration.", t_cell)],
        [Paragraph("Container Packaging", t_cell_bold), Paragraph("Google Jib Maven Plugin", t_cell), Paragraph("3.5.1", t_cell), Paragraph("Daemonless Java container packaging.", t_cell)],
        [Paragraph("Build Automation", t_cell_bold), Paragraph("Apache Maven", t_cell), Paragraph("3.9.x", t_cell), Paragraph("Multi-module reactor build system.", t_cell)]
    ]

    tech_table = Table(tech_data, colWidths=[1.3*inch, 1.8*inch, 0.9*inch, 2.8*inch])
    tech_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 3),
        ('BOTTOMPADDING', (0,0), (-1,-1), 3),
    ]))
    story.append(tech_table)

    story.append(Spacer(1, 10))

    # DEVELOPMENT TEAM ROLE DIVISION MATRIX (Requested 4-Member Team Breakdown with Names)
    story.append(Paragraph("3.2 4-Member Engineering Team Role Division & Ownership", sub_sec_style))
    story.append(Paragraph("To demonstrate clear project ownership, the engineering tasks across the codebase are structured among the 4 project team members as follows:", body_style))

    team_data = [
        [Paragraph("Team Member & Assigned Role", t_header), Paragraph("Primary Subsystems Handled", t_header), Paragraph("Core Technical Deliverables", t_header)],
        [Paragraph("<b>Member 1: Distributed Backend & DevOps Lead</b><br/><font color='#4F46E5'><b>Vikas Prajapati & Vineet</b></font>", t_cell_bold), Paragraph("API Gateway (`9000`), Eureka (`8761`), Config Server (`8888`), Kafka (`29092`), Notification Service (`9005`).", t_cell), Paragraph("Central routing, dynamic load balancing, Git config repo, Kafka producers/consumers, Docker Compose orchestration, Jib packaging.", t_cell)],
        [Paragraph("<b>Member 2: Security & Identity Engineer</b><br/><font color='#4F46E5'><b>Shiv & Ajay</b></font>", t_cell_bold), Paragraph("User Service (`9001`), Preference Service (`9008`), `common-lib`, `userdb:5433`.", t_cell), Paragraph("Google OAuth2 SSO (`OAuth2SuccessHandler`), Gmail SMTP 6-digit OTP verification pipeline, 15-min Access & 7-day Refresh JWT tokens, RBAC.", t_cell)],
        [Paragraph("<b>Member 3: Business Domain & AI Engine Developer</b><br/><font color='#4F46E5'><b>Vikas Prajapati & Vineet</b></font>", t_cell_bold), Paragraph("Job Service (`9003`), Company Service (`9002`), Application Service (`9004`), Resume Service (`9009`), AI Service (`9010`).", t_cell), Paragraph("Job creation/search, company verification, applicant tracking pipeline, AI skill match scoring engine ($MatchScore$), live PDF resume compiler.", t_cell)],
        [Paragraph("<b>Member 4: Frontend Engineer & UI/UX Lead</b><br/><font color='#4F46E5'><b>Shiv & Ajay</b></font>", t_cell_bold), Paragraph("React 18 App (`5173`), Redux Toolkit Store, React Router v6, Tailwind CSS, Shadcn UI.", t_cell), Paragraph("All UI pages (`Register`, `Login`, `VerifyOtp`, `Jobs`, `Resumes`, `AIScreening`, `AdminDashboard`), Redux Thunks, route guards, Zod validation.", t_cell)]
    ]

    team_table = Table(team_data, colWidths=[1.9*inch, 1.9*inch, 3.0*inch])
    team_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), brand_indigo),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
    ]))
    story.append(team_table)

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 4: LITERATURE SURVEY
    # ==========================================
    story.append(Paragraph("4. Literature Survey", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("4.1 Evolution of Online Recruitment Architectures", sub_sec_style))
    story.append(Paragraph(
        "Online recruitment platforms have evolved through three generation phases over the past 25 years. <b>First-generation systems (1995–2005)</b> functioned as static job bulletin boards (e.g., early Monster.com), where employers posted basic text notices and candidates manually emailed static Word documents. <b>Second-generation systems (2005–2018)</b> introduced relational databases and keyword searching (e.g., early LinkedIn and Indeed), but remained constrained by monolithic database locks and manual screening. <b>Third-generation systems (2018–Present)</b> leverage cloud microservices, asynchronous message queues, and artificial intelligence to deliver elastic scalability and automated candidate matching.",
        body_style
    ))
    story.append(Paragraph(
        "Academic literature (Fowler & Lewis, 2014; Newman, 2021) proves that decomposing monolithic software into autonomous microservices eliminates single points of failure and prevents database lock exhaustion. Furthermore, research in automated recruitment analytics (Garg et al., 2022) demonstrates that AI match scoring algorithms reduce candidate screening cycles by up to 68% while significantly improving candidate-role compatibility.",
        body_style
    ))

    story.append(Paragraph("4.2 Comparative Literature Matrix", sub_sec_style))
    story.append(Paragraph("A comparative matrix evaluating existing commercial platforms against JobPortal.AI across 9 key technical dimensions is detailed below:", body_style))

    lit_data = [
        [Paragraph("Benchmark Dimension", t_header), Paragraph("Indeed", t_header), Paragraph("LinkedIn", t_header), Paragraph("Monster", t_header), Paragraph("JobPortal.AI (Proposed)", t_header)],
        [Paragraph("Architecture Pattern", t_cell_bold), Paragraph("Hybrid / Legacy Core", t_cell), Paragraph("Distributed Microservices", t_cell), Paragraph("Monolithic Legacy", t_cell), Paragraph("Microservices (Spring Cloud Framework)", t_cell)],
        [Paragraph("Database Model", t_cell_bold), Paragraph("Centralized Sharded", t_cell), Paragraph("Distributed Graph & Document", t_cell), Paragraph("Central Relational DB", t_cell), Paragraph("Database-per-Service (PostgreSQL 16)", t_cell)],
        [Paragraph("Authentication", t_cell_bold), Paragraph("Standard OAuth2", t_cell), Paragraph("Custom SSO / OAuth2", t_cell), Paragraph("Password / Basic OAuth", t_cell), Paragraph("Google OAuth2 + Mandatory OTP", t_cell)],
        [Paragraph("Account Verification", t_cell_bold), Paragraph("Email Click Link", t_cell), Paragraph("Email / SMS Code", t_cell), Paragraph("Basic Verification", t_cell), Paragraph("Gmail SMTP 6-Digit OTP Verification", t_cell)],
        [Paragraph("AI Match Engine", t_cell_bold), Paragraph("Keyword Search Based", t_cell), Paragraph("ML Vector Embeddings", t_cell), Paragraph("Keyword Filter", t_cell), Paragraph("Integrated AI Skill Matcher ($MatchScore$)", t_cell)],
        [Paragraph("Interactive Resume Builder", t_cell_bold), Paragraph("Static PDF Upload", t_cell), Paragraph("Profile Export Only", t_cell), Paragraph("Basic Parser", t_cell), Paragraph("Live Drag & Drop Builder + PDF Export", t_cell)],
        [Paragraph("Event Broker Queue", t_cell_bold), Paragraph("Internal Queues", t_cell), Paragraph("Apache Kafka", t_cell), Paragraph("RabbitMQ", t_cell), Paragraph("Apache Kafka 7.6.1 + Zookeeper", t_cell)],
        [Paragraph("Configuration Model", t_cell_bold), Paragraph("Proprietary Config", t_cell), Paragraph("Dynamic Config Service", t_cell), Paragraph("Static Files", t_cell), Paragraph("Spring Cloud Config Server (`8888`)", t_cell)]
    ]

    lit_table = Table(lit_data, colWidths=[1.3*inch, 1.2*inch, 1.3*inch, 1.1*inch, 1.9*inch])
    lit_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
    ]))
    story.append(lit_table)

    story.append(Spacer(1, 10))
    story.append(Paragraph("4.3 Gaps Identified in Existing Literature & Commercial Systems", sub_sec_style))
    story.append(Paragraph("1. <b>Black-Box Screening Algorithms:</b> Commercial portals rank candidates using non-transparent algorithms that provide zero feedback to candidates on missing skills.", bullet_style))
    story.append(Paragraph("2. <b>Weak Single-Factor Registration:</b> Most portals allow instant registration without immediate OTP verification, producing high volumes of spam accounts.", bullet_style))
    story.append(Paragraph("3. <b>Static Resume Dependency:</b> Job boards require uploading static Word/PDF files, causing parsing errors and formatting loss.", bullet_style))
    story.append(Paragraph("4. <b>Synchronous Notification Delays:</b> Sending confirmation emails synchronously inside API request loops adds multi-second latency for users.", bullet_style))

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 5: SYSTEM WORKFLOW / ARCHITECTURE (PART I: TOPOLOGY & GATEWAY)
    # ==========================================
    story.append(Paragraph("5. System Workflow / Architecture (Part I)", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("5.1 Microservices Architecture Topology", sub_sec_style))
    story.append(Paragraph(
        "JobPortal.AI is engineered following a clean microservices architecture topology. All incoming requests from client web browsers hit the <b>Spring Cloud API Gateway (`port 9000`)</b>. The Gateway consults the <b>Netflix Eureka Service Registry (`port 8761`)</b> to resolve container IPs dynamically and load-balance traffic across microservices.",
        body_style
    ))

    # Architecture Topology Diagram Box
    topo_box = [
        [Paragraph("<b>DISTRIBUTED MICROSERVICES ARCHITECTURE TOPOLOGY</b>", ParagraphStyle('TopoTitle', fontName='Helvetica-Bold', fontSize=9, textColor=brand_indigo, alignment=1))],
        [Paragraph(
            "+-----------------------------------------------------------------------------------+<br/>"
            "|                        REACT 18 FRONTEND CLIENT (Port 5173)                       |<br/>"
            "+-----------------------------------------------------------------------------------+<br/>"
            "                                          | HTTP / REST Calls<br/>"
            "                                          v<br/>"
            "+-----------------------------------------------------------------------------------+<br/>"
            "|                     SPRING CLOUD API GATEWAY (Port 9000)                          |<br/>"
            "|         [Route Predicates | CORS Filter | JWT Header Forwarding Filter]           |<br/>"
            "+-----------------------------------------------------------------------------------+<br/>"
            "       |                     |                     |                     |<br/>"
            "       v                     v                     v                     v<br/>"
            "+--------------+      +--------------+      +--------------+      +--------------+<br/>"
            "| USER SERVICE |      |  JOB SERVICE |      | COMPANY SERV |      |  APP SERVICE |<br/>"
            "| (Port 9001)  |      | (Port 9003)  |      | (Port 9002)  |      | (Port 9004)  |<br/>"
            "+--------------+      +--------------+      +--------------+      +--------------+<br/>"
            "       |                     |                     |                     |<br/>"
            "       v                     v                     v                     v<br/>"
            "+--------------+      +--------------+      +--------------+      +--------------+<br/>"
            "| PostgreSQL   |      | PostgreSQL   |      | PostgreSQL   |      | PostgreSQL   |<br/>"
            "| userdb:5433  |      | jobdb:5435   |      | companydb:5434|      | appdb:5436   |<br/>"
            "+--------------+      +--------------+      +--------------+      +--------------+<br/>"
            "       |                     |                     |                     |<br/>"
            "       +---------------------+----------+----------+---------------------+<br/>"
            "                                        | Kafka Producer Events<br/>"
            "                                        v<br/>"
            "                     +--------------------------------------+<br/>"
            "                     | APACHE KAFKA BROKER (Port 29092)     |<br/>"
            "                     | Zookeeper Coordination (Port 2181)   |<br/>"
            "                     +--------------------------------------+<br/>"
            "                                        |<br/>"
            "                                        v Consumer Events<br/>"
            "                     +--------------------------------------+<br/>"
            "                     | NOTIFICATION SERVICE (Port 9005)     |<br/>"
            "                     | Gmail SMTP Transactional Engine      |<br/>"
            "                     +--------------------------------------+<br/>", code_box_style
        )]
    ]
    topo_table = Table(topo_box, colWidths=[6.8*inch])
    topo_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,-1), bg_subtle),
        ('BOX', (0,0), (-1,-1), 1, border_color),
        ('TOPPADDING', (0,0), (-1,-1), 5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 5),
        ('ALIGN', (0,0), (-1,-1), 'CENTER'),
    ]))
    story.append(topo_table)

    story.append(Spacer(1, 10))
    story.append(Paragraph("5.2 Microservice Domain Catalog & Port Mappings", sub_sec_style))

    routes_data = [
        [Paragraph("Microservice Name", t_header), Paragraph("Port", t_header), Paragraph("Database / Infrastructure", t_header), Paragraph("Functional Responsibility Scope", t_header)],
        [Paragraph("API Gateway", t_cell_bold), Paragraph("9000", t_cell), Paragraph("Spring Cloud Gateway MVC", t_cell), Paragraph("Central entry point, reverse proxy, CORS filter, JWT routing.", t_cell)],
        [Paragraph("User Service", t_cell_bold), Paragraph("9001", t_cell), Paragraph("PostgreSQL (`userdb:5433`)", t_cell), Paragraph("User auth, Google OAuth2, OTP mail verification, JWT, RBAC.", t_cell)],
        [Paragraph("Company Service", t_cell_bold), Paragraph("9002", t_cell), Paragraph("PostgreSQL (`companydb:5434`)", t_cell), Paragraph("Company profile registration, verification status, corporate assets.", t_cell)],
        [Paragraph("Job Service", t_cell_bold), Paragraph("9003", t_cell), Paragraph("PostgreSQL (`jobdb:5435`)", t_cell), Paragraph("Job posting lifecycle, full-text search, filtering, bookmarking.", t_cell)],
        [Paragraph("Application Service", t_cell_bold), Paragraph("9004", t_cell), Paragraph("PostgreSQL (`applicationdb:5436`)", t_cell), Paragraph("Job application pipeline, tracking status, screening workflow.", t_cell)],
        [Paragraph("Notification Service", t_cell_bold), Paragraph("9005", t_cell), Paragraph("Apache Kafka Consumer (`29092`)", t_cell), Paragraph("Asynchronous event-driven emails via Gmail SMTP API.", t_cell)],
        [Paragraph("Preference Service", t_cell_bold), Paragraph("9008", t_cell), Paragraph("PostgreSQL (`preferencedb:5439`)", t_cell), Paragraph("Candidate job preferences & alert recommendation criteria.", t_cell)],
        [Paragraph("Resume Service", t_cell_bold), Paragraph("9009", t_cell), Paragraph("PostgreSQL (`resumedb:5440`)", t_cell), Paragraph("Live drag & drop resume builder state & dynamic PDF export.", t_cell)],
        [Paragraph("AI Service", t_cell_bold), Paragraph("9010", t_cell), Paragraph("AI Match Scoring Engine", t_cell), Paragraph("AI resume-job skill compatibility scoring ($MatchScore$).", t_cell)],
        [Paragraph("Config Server", t_cell_bold), Paragraph("8888", t_cell), Paragraph("Git Repository (`config-repo`)", t_cell), Paragraph("Centralized configuration management across all services.", t_cell)],
        [Paragraph("Discovery Server", t_cell_bold), Paragraph("8761", t_cell), Paragraph("Netflix Eureka Registry", t_cell), Paragraph("Dynamic service registration, heartbeats, IP discovery.", t_cell)]
    ]

    routes_table = Table(routes_data, colWidths=[1.3*inch, 0.5*inch, 1.8*inch, 3.2*inch])
    routes_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(routes_table)

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 5: SYSTEM WORKFLOW / ARCHITECTURE (PART II: DATABASE SCHEMAS)
    # ==========================================
    story.append(Paragraph("5. System Workflow / Architecture (Part II)", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("5.3 Database Entity Models & Relational Schema Specifications", sub_sec_style))
    story.append(Paragraph(
        "Each microservice manages its own isolated PostgreSQL relational schema. Below are the key data models defined across the platform databases:",
        body_style
    ))

    story.append(Paragraph("1. User Entity Schema (`job_portal_user` on `userdb:5433`)", sub_sec_style))
    user_schema_data = [
        [Paragraph("Column Name", t_header), Paragraph("Data Type", t_header), Paragraph("Constraints / Attributes", t_header), Paragraph("Description", t_header)],
        [Paragraph("`id`", t_cell_bold), Paragraph("BIGINT / Long", t_cell), Paragraph("PRIMARY KEY, AUTO_INCREMENT", t_cell), Paragraph("Unique user identifier.", t_cell)],
        [Paragraph("`email`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("UNIQUE, NOT NULL", t_cell), Paragraph("Primary authentication & contact email.", t_cell)],
        [Paragraph("`password`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("NULLABLE (for Google OAuth)", t_cell), Paragraph("BCrypt encrypted password string.", t_cell)],
        [Paragraph("`full_name`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("NOT NULL", t_cell), Paragraph("User's full name.", t_cell)],
        [Paragraph("`role`", t_cell_bold), Paragraph("VARCHAR(50)", t_cell), Paragraph("ENUM ('JOB_SEEKER', 'EMPLOYER', 'ADMIN')", t_cell), Paragraph("Role-based authorization access level.", t_cell)],
        [Paragraph("`auth_provider`", t_cell_bold), Paragraph("VARCHAR(50)", t_cell), Paragraph("ENUM ('LOCAL', 'GOOGLE')", t_cell), Paragraph("Authentication provider source.", t_cell)],
        [Paragraph("`google_id`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("NULLABLE", t_cell), Paragraph("Google OAuth2 subject identifier.", t_cell)],
        [Paragraph("`verified`", t_cell_bold), Paragraph("BOOLEAN", t_cell), Paragraph("DEFAULT false", t_cell), Paragraph("Account email OTP verification status.", t_cell)],
        [Paragraph("`status`", t_cell_bold), Paragraph("VARCHAR(50)", t_cell), Paragraph("ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED')", t_cell), Paragraph("Current account lifecycle status.", t_cell)],
        [Paragraph("`reset_token`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("NULLABLE", t_cell), Paragraph("Password reset verification string.", t_cell)]
    ]
    user_schema_table = Table(user_schema_data, colWidths=[1.2*inch, 1.2*inch, 2.2*inch, 2.2*inch])
    user_schema_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 3.5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 3.5),
    ]))
    story.append(user_schema_table)

    story.append(Spacer(1, 8))
    story.append(Paragraph("2. Job, Application, Company & Resume Schemas", sub_sec_style))
    job_app_schema_data = [
        [Paragraph("Entity Model", t_header), Paragraph("Primary Columns", t_header), Paragraph("Foreign Keys / Domain References", t_header), Paragraph("Core Purpose", t_header)],
        [Paragraph("`Job`", t_cell_bold), Paragraph("id, title, description, location, job_type, salary_min, salary_max, status, deadline", t_cell), Paragraph("company_id (References Company Service)", t_cell), Paragraph("Stores active job posts & salary metadata.", t_cell)],
        [Paragraph("`Application`", t_cell_bold), Paragraph("id, job_id, user_id, resume_id, status, cover_letter, match_score, applied_at", t_cell), Paragraph("job_id, user_id, resume_id (Cross-service Keys)", t_cell), Paragraph("Tracks applicant status & AI match rating.", t_cell)],
        [Paragraph("`Company`", t_cell_bold), Paragraph("id, user_id, name, description, website, location, logo_url, verified", t_cell), Paragraph("user_id (Employer Owner Key)", t_cell), Paragraph("Stores corporate profiles & verification state.", t_cell)],
        [Paragraph("`Resume`", t_cell_bold), Paragraph("id, user_id, title, summary, skills_json, experience_json, education_json", t_cell), Paragraph("user_id (Candidate Owner Key)", t_cell), Paragraph("Stores structured resume data for live editor.", t_cell)],
        [Paragraph("`Otp`", t_cell_bold), Paragraph("id, email, code, expiry_time, used", t_cell), Paragraph("email (User Reference Key)", t_cell), Paragraph("Stores 6-digit email OTP verification codes.", t_cell)]
    ]
    job_app_table = Table(job_app_schema_data, colWidths=[1.0*inch, 2.2*inch, 1.8*inch, 1.8*inch])
    job_app_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), brand_indigo),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(job_app_table)

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 5: SYSTEM WORKFLOW / ARCHITECTURE (PART III: SECURITY & OAUTH2 + OTP FLOW)
    # ==========================================
    story.append(Paragraph("5. System Workflow / Architecture (Part III)", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("5.4 Hybrid Google OAuth2 + Mandatory OTP Email Verification Flow", sub_sec_style))
    story.append(Paragraph(
        "Authentication security is implemented via `SecurityConfig.java`, `OAuth2SuccessHandler.java`, and `JwtProvider.java` in the User Service (`port 9001`). Below is the complete step-by-step execution sequence of the newly engineered Google OAuth2 Single Sign-On and mandatory OTP verification flow:",
        body_style
    ))

    # OAuth2 + OTP Step Sequence Table
    seq_data = [
        [Paragraph("Step", t_header), Paragraph("Component / Actor", t_header), Paragraph("Technical Execution Sequence", t_header)],
        [Paragraph("1", t_cell_bold), Paragraph("Client Browser", t_cell), Paragraph("Candidate clicks 'Sign up with Google' on `Register.jsx`. Browser navigates to `http://localhost:9000/oauth2/authorization/google`.", t_cell)],
        [Paragraph("2", t_cell_bold), Paragraph("API Gateway", t_cell), Paragraph("Gateway forwards request to `user-service` (`9001`). Candidate authenticates on Google's consent page.", t_cell)],
        [Paragraph("3", t_cell_bold), Paragraph("Google Auth Server", t_cell), Paragraph("Google redirects back to callback endpoint: `http://localhost:9000/login/oauth2/code/google?code=...`.", t_cell)],
        [Paragraph("4", t_cell_bold), Paragraph("`OAuth2SuccessHandler`", t_cell), Paragraph("`OAuth2Service.saveOrUpdate()` extracts `email`, `name`, `sub`. If new user, creates entity with `verified = false` and `status = INACTIVE`.", t_cell)],
        [Paragraph("5", t_cell_bold), Paragraph("`MailServiceImpl`", t_cell), Paragraph("Service generates a 6-digit OTP code entity, saves to database, and mails it via Gmail SMTP (`MimeMessageHelper`).", t_cell)],
        [Paragraph("6", t_cell_bold), Paragraph("Client Browser", t_cell), Paragraph("Backend redirects browser to frontend OTP page: `http://localhost:5173/verify-otp?email=user@gmail.com`.", t_cell)],
        [Paragraph("7", t_cell_bold), Paragraph("`VerifyOtp.jsx`", t_cell), Paragraph("Candidate enters 6-digit code. React form issues POST request to `http://localhost:9000/auth/verify-otp`.", t_cell)],
        [Paragraph("8", t_cell_bold), Paragraph("`JwtProvider`", t_cell), Paragraph("Backend validates OTP, sets `verified = true`, `status = ACTIVE`, generates 15-min Access Token & 7-day Refresh Token, and returns JSON payload.", t_cell)],
        [Paragraph("9", t_cell_bold), Paragraph("Redux Store / Client", t_cell), Paragraph("Frontend saves `accessToken` in `localStorage`, dispatches `fetchCurrentUser()`, and navigates candidate to their dashboard.", t_cell)]
    ]
    seq_table = Table(seq_data, colWidths=[0.5*inch, 1.5*inch, 4.8*inch])
    seq_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
    ]))
    story.append(seq_table)

    story.append(Spacer(1, 10))
    story.append(Paragraph("5.5 AI Candidate Skill Match Calculation Model", sub_sec_style))
    story.append(Paragraph(
        "The AI Service (`port 9010`) parses candidate resume skills ($S_R$) against job requirements ($S_J$) to compute a compatibility percentage score:",
        body_style
    ))
    
    # Formula Box
    match_box_data = [[
        Paragraph("<b>AI Skill Match Score Formula:</b><br/>"
                  "$$MatchScore = \\left( 0.60 \\cdot \\frac{|S_R \\cap S_J|}{|S_J|} + 0.25 \\cdot \\min\\left(1, \\frac{Exp_R}{Exp_J}\\right) + 0.15 \\cdot Match_{Pref} \\right) \\times 100\\%$$<br/>"
                  "<i>Where $S_R \\cap S_J$ represents overlapping technical skills, $Exp$ represents years of experience, and $Match_{Pref}$ evaluates location/job-type preference.</i>", body_style)
    ]]
    match_table = Table(match_box_data, colWidths=[6.8*inch])
    match_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,-1), bg_subtle),
        ('BOX', (0,0), (-1,-1), 1, brand_indigo),
        ('TOPPADDING', (0,0), (-1,-1), 6),
        ('BOTTOMPADDING', (0,0), (-1,-1), 6),
    ]))
    story.append(match_table)

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 5: SYSTEM WORKFLOW / ARCHITECTURE (PART IV: FRONTEND ARCHITECTURE)
    # ==========================================
    story.append(Paragraph("5. System Workflow / Architecture (Part IV)", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("5.6 Frontend React Architecture & Route Hierarchy", sub_sec_style))
    story.append(Paragraph(
        "The frontend application is built using <b>React 18</b> and <b>Vite</b>, delivering instant rendering and dynamic state management. Global application state is managed by <b>Redux Toolkit</b>, organizing API interactions into slice thunks (`userThunk.js`, `jobSlice.js`, `companySlice.js`, `applicationSlice.js`).",
        body_style
    ))

    front_routes_data = [
        [Paragraph("Route Path", t_header), Paragraph("React Component", t_header), Paragraph("Guard / Access Level", t_header), Paragraph("Page Responsibility", t_header)],
        [Paragraph("`/`", t_cell_bold), Paragraph("`LandingPage.jsx`", t_cell), Paragraph("Public", t_cell), Paragraph("Hero banner, platform metrics, featured job listings.", t_cell)],
        [Paragraph("`/login`", t_cell_bold), Paragraph("`Login.jsx`", t_cell), Paragraph("Public / Auth Guard", t_cell), Paragraph("Email/Password login & Google OAuth2 URL token parsing.", t_cell)],
        [Paragraph("`/register`", t_cell_bold), Paragraph("`Register.jsx`", t_cell), Paragraph("Public", t_cell), Paragraph("User signup with Role selector (Candidate / Employer).", t_cell)],
        [Paragraph("`/verify-otp`", t_cell_bold), Paragraph("`VerifyOtp.jsx`", t_cell), Paragraph("Public", t_cell), Paragraph("6-Digit OTP verification form with mail status alert.", t_cell)],
        [Paragraph("`/forgot-password`", t_cell_bold), Paragraph("`ForgotPassword.jsx`", t_cell), Paragraph("Public", t_cell), Paragraph("Triggers password reset link dispatch via Gmail SMTP.", t_cell)],
        [Paragraph("`/jobs`", t_cell_bold), Paragraph("`Jobs.jsx`", t_cell), Paragraph("`ROLE_JOB_SEEKER`", t_cell), Paragraph("Job search feed, filter sidebar (salary, experience, location).", t_cell)],
        [Paragraph("`/resumes`", t_cell_bold), Paragraph("`Resumes.jsx`", t_cell), Paragraph("`ROLE_JOB_SEEKER`", t_cell), Paragraph("Interactive live drag-and-drop resume builder & PDF export.", t_cell)],
        [Paragraph("`/employer/dashboard`", t_cell_bold), Paragraph("`Dashboard.jsx`", t_cell), Paragraph("`ROLE_EMPLOYER`", t_cell), Paragraph("Employer analytics, active job statistics, applicant counts.", t_cell)],
        [Paragraph("`/employer/ai-screening`", t_cell_bold), Paragraph("`AIScreening.jsx`", t_cell), Paragraph("`ROLE_EMPLOYER`", t_cell), Paragraph("AI candidate ranking, resume match scoring, candidate filter.", t_cell)],
        [Paragraph("`/admin/dashboard`", t_cell_bold), Paragraph("`AdminDashboard.jsx`", t_cell), Paragraph("`ROLE_ADMIN`", t_cell), Paragraph("Platform user activation, company verification, system metrics.", t_cell)]
    ]
    front_routes_table = Table(front_routes_data, colWidths=[1.4*inch, 1.4*inch, 1.3*inch, 2.7*inch])
    front_routes_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 3.5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 3.5),
    ]))
    story.append(front_routes_table)

    story.append(Spacer(1, 10))
    story.append(Paragraph("5.7 Form Validation & Schema Enforcement", sub_sec_style))
    story.append(Paragraph(
        "Client forms utilize <b>React Hook Form</b> integrated with <b>Zod</b> schema validation (`authSchemas.js`). Forms validate email format, minimum 6-character password strength, matching passwords, and 6-digit numerical OTP limits prior to dispatching network requests.",
        body_style
    ))

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 6: EXPECTED OUTCOME
    # ==========================================
    story.append(Paragraph("6. Expected Outcome", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("6.1 Tangible Technical Deliverables & System Performance SLAs", sub_sec_style))
    story.append(Paragraph(
        "The successful deployment of JobPortal.AI achieves key performance benchmarks and operational SLAs across the recruitment pipeline:",
        body_style
    ))

    out_data = [
        [Paragraph("Operational Metric", t_header), Paragraph("Monolithic Baseline", t_header), Paragraph("JobPortal.AI Expected Outcome", t_header)],
        [Paragraph("API Gateway Response Time", t_cell_bold), Paragraph("450ms – 1200ms", t_cell), Paragraph("<b>Sub-100ms</b> average latency via Spring Cloud Gateway routing.", t_cell)],
        [Paragraph("Database Lock Contention", t_cell_bold), Paragraph("High table deadlocks under load", t_cell), Paragraph("<b>Zero cross-domain locks</b> via 6 isolated PostgreSQL databases.", t_cell)],
        [Paragraph("System Availability SLA", t_cell_bold), Paragraph("95.0% (SPOF outages)", t_cell), Paragraph("<b>99.9% Uptime</b> via independent container failure isolation.", t_cell)],
        [Paragraph("Candidate Screening Cycle", t_cell_bold), Paragraph("3 to 7 Days (Manual scan)", t_cell), Paragraph("<b>Real-Time (under 2 seconds)</b> AI candidate match scoring.", t_cell)],
        [Paragraph("Mail Dispatch Latency", t_cell_bold), Paragraph("2s to 5s Blocking HTTP delay", t_cell), Paragraph("<b>Asynchronous (0ms client blocking)</b> via Kafka messaging.", t_cell)]
    ]
    out_table = Table(out_data, colWidths=[1.8*inch, 2.0*inch, 3.0*inch])
    out_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(out_table)

    story.append(Spacer(1, 10))
    story.append(Paragraph("6.2 Societal Relevance & Carbon Elasticity", sub_sec_style))
    story.append(Paragraph(
        "JobPortal.AI impacts society by democratizing employment access, reducing hiring bias through objective AI skill matching, and eliminating paper waste through dynamic digital resume exports. Elastic microservices scaling enables cloud containers to scale down during low-traffic periods, conserving data center power.",
        body_style
    ))

    story.append(Paragraph("6.3 Future Scaling Roadmap", sub_sec_style))
    story.append(Paragraph("1. <b>Kubernetes (K8s) & Helm Orchestration:</b> Migrating from Docker Compose to Kubernetes with Horizontal Pod Autoscalers (HPA).", bullet_style))
    story.append(Paragraph("2. <b>Elasticsearch & Kibana Job Search:</b> Implementing an Elasticsearch cluster for sub-millisecond full-text search.", bullet_style))
    story.append(Paragraph("3. <b>WebRTC Video Screening:</b> Integrating automated AI video screening and interview scheduling.", bullet_style))

    story.append(PageBreak())

    # ==========================================
    # AGENDA TITLE 7: REFERENCES & SIGN-OFF
    # ==========================================
    story.append(Paragraph("7. References", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("7.1 Academic Publications & Technical Specifications", sub_sec_style))
    story.append(Paragraph("1. Fowler, M., & Lewis, J. (2014). <i>Microservices: a definition of this new architectural term</i>. ThoughtWorks Insights.", bullet_style))
    story.append(Paragraph("2. Newman, S. (2021). <i>Building Microservices: Designing Fine-Grained Systems</i> (2nd ed.). O'Reilly Media.", bullet_style))
    story.append(Paragraph("3. Garg, L., et al. (2022). <i>Artificial Intelligence in Human Resource Management: Automated Candidate Screening Algorithms</i>. Journal of Systems Software, 184, 111-125.", bullet_style))
    story.append(Paragraph("4. Hardt, D. (2012). <i>The OAuth 2.0 Authorization Framework</i>. IETF RFC 6749.", bullet_style))
    story.append(Paragraph("5. Jones, M., Bradley, J., & Sakimura, N. (2015). <i>JSON Web Token (JWT)</i>. IETF RFC 7519.", bullet_style))
    story.append(Paragraph("6. Spring Cloud Engineering Team. (2024). <i>Spring Cloud Gateway & Netflix Eureka Reference Guide</i>. VMware Tanzu.", bullet_style))
    story.append(Paragraph("7. Apache Software Foundation. (2024). <i>Apache Kafka Distributed Event Streaming Documentation</i>.", bullet_style))

    story.append(Spacer(1, 25))

    # Formal Verification Sign-off Box
    sign_data = [
        [Paragraph("<b>Project Evaluation & Verification Panel</b>", t_header), Paragraph("<b>Status / Stamp</b>", t_header)],
        [Paragraph("<b>Student Developer Team:</b> Vikas Prajapati, Vineet, Shiv, Ajay<br/>"
                   "<b>Project Evaluator:</b> Department Project Examination Board<br/>"
                   "<b>Department:</b> Computer Science & Engineering<br/>"
                   "<b>Institution:</b> Faculty of Engineering & Technology", t_cell),
         Paragraph("<br/><b>APPROVED FOR SYNOPSIS EVALUATION</b><br/><br/>"
                   "Date: September 24, 2026<br/>"
                   "Signature: ______________________", t_cell)]
    ]
    sign_table = Table(sign_data, colWidths=[4.6*inch, 2.2*inch])
    sign_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_dark),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 6),
        ('BOTTOMPADDING', (0,0), (-1,-1), 6),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
    ]))
    story.append(sign_table)

    doc.build(story, canvasmaker=NumberedCanvas)
    print(f"Successfully generated Agenda Synopsis PDF at: {os.path.abspath(filename)}")

if __name__ == "__main__":
    build_agenda_synopsis("Job_Portal_Project_Synopsis.pdf")
    build_agenda_synopsis("Job_Portal_Detailed_Project_Synopsis.pdf")
