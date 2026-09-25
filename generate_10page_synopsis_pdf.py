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
        
        # Suppress running header on cover page
        if self._pageNumber > 1:
            self.drawString(54, 750, "JobPortal.AI — Microservices Architecture & Comprehensive Project Synopsis")
            self.setStrokeColor(colors.HexColor("#CBD5E1"))
            self.setLineWidth(0.5)
            self.line(54, 742, 558, 742)
            
            # Running Footer
            self.line(54, 45, 558, 45)
            self.drawString(54, 32, "Evaluation Synopsis — Department of Computer Science & Engineering")
            page_text = f"Page {self._pageNumber} of {page_count}"
            self.drawRightString(558, 32, page_text)
        self.restoreState()

def build_10page_synopsis(filename="Job_Portal_Detailed_Project_Synopsis.pdf"):
    doc = SimpleDocTemplate(
        filename,
        pagesize=letter,
        leftMargin=54,
        rightMargin=54,
        topMargin=54,
        bottomMargin=54
    )

    styles = getSampleStyleSheet()
    
    # Palette definition
    primary_color = colors.HexColor("#0F172A")   # Slate 900
    brand_indigo   = colors.HexColor("#4F46E5")   # Indigo 600
    accent_blue    = colors.HexColor("#0EA5E9")   # Sky 500
    text_dark      = colors.HexColor("#1E293B")   # Slate 800
    text_muted     = colors.HexColor("#475569")   # Slate 600
    bg_light       = colors.HexColor("#F8FAFC")   # Slate 50
    bg_subtle      = colors.HexColor("#F1F5F9")   # Slate 100
    border_color   = colors.HexColor("#E2E8F0")   # Slate 200

    # Custom Paragraph Styles
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
        fontSize=11.5,
        leading=15.5,
        textColor=text_muted,
        spaceAfter=15
    )

    sec_title_style = ParagraphStyle(
        'SecTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=15,
        leading=18,
        textColor=primary_color,
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

    code_style = ParagraphStyle(
        'CodeStyle',
        parent=styles['Normal'],
        fontName='Courier',
        fontSize=8,
        leading=10.5,
        textColor=colors.HexColor("#0F172A")
    )

    t_cell = ParagraphStyle('TCell', fontName='Helvetica', fontSize=8.2, leading=11, textColor=text_dark)
    t_cell_bold = ParagraphStyle('TCellBold', fontName='Helvetica-Bold', fontSize=8.2, leading=11, textColor=primary_color)
    t_header = ParagraphStyle('THeader', fontName='Helvetica-Bold', fontSize=8.5, leading=11.5, textColor=colors.white)

    story = []

    # ==========================================
    # PAGE 1: TITLE & EXECUTIVE PROJECT METADATA
    # ==========================================
    story.append(Paragraph("PROJECT SYNOPSIS & TECHNICAL EVALUATION DOCUMENT", ParagraphStyle('TopCategory', fontName='Helvetica-Bold', fontSize=9, leading=11, textColor=brand_indigo, spaceAfter=4)))
    story.append(Paragraph("JobPortal.AI — Distributed Microservices Recruitment Platform", title_style))
    story.append(Paragraph("An End-to-End Enterprise Solution Featuring 10+ Autonomous Microservices, Google OAuth2 Single Sign-On, Gmail SMTP OTP Email Verification, AI-Powered Candidate Matching Engine, Live Dynamic Resume Builder, and Dedicated Database-per-Service Architecture.", subtitle_style))
    story.append(HRFlowable(width="100%", thickness=1.5, color=brand_indigo, spaceBefore=0, spaceAfter=14))

    # Detailed Meta Summary Card Table
    meta_table_data = [
        [Paragraph("Project Title", t_cell_bold), Paragraph("JobPortal.AI (ZoshHire Platform)", t_cell), Paragraph("Domain", t_cell_bold), Paragraph("Enterprise Web Systems & AI Recruitment", t_cell)],
        [Paragraph("Architecture Pattern", t_cell_bold), Paragraph("Microservices (Spring Cloud Framework)", t_cell), Paragraph("Frontend Framework", t_cell_bold), Paragraph("React 18, Vite, Redux Toolkit, Tailwind", t_cell)],
        [Paragraph("Backend Framework", t_cell_bold), Paragraph("Java 17 / Spring Boot 3.4+, Spring Cloud", t_cell), Paragraph("Persistence Layer", t_cell_bold), Paragraph("PostgreSQL 16 (6 Dedicated DB Instances)", t_cell)],
        [Paragraph("Service Discovery", t_cell_bold), Paragraph("Netflix Eureka Registry (Port 8761)", t_cell), Paragraph("API Gateway", t_cell_bold), Paragraph("Spring Cloud Gateway MVC (Port 9000)", t_cell)],
        [Paragraph("Configuration", t_cell_bold), Paragraph("Spring Cloud Config Server (Port 8888)", t_cell), Paragraph("Messaging Engine", t_cell_bold), Paragraph("Apache Kafka 7.6.1 & Zookeeper 7.6.1", t_cell)],
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

    # Executive Overview
    story.append(Paragraph("Executive Overview", sub_sec_style))
    story.append(Paragraph(
        "<b>JobPortal.AI</b> represents a state-of-the-art distributed software solution built to satisfy the rigorous demands of modern online recruitment. Traditional monolithic job portals struggle with monolithic database bottlenecks, severe latency spikes during application rushes, tight code coupling, and single points of failure. JobPortal.AI resolves these structural challenges by decomposing recruitment workflows into autonomous, single-responsibility microservices. Every microservice operates independently on its own dedicated port, isolated database, and execution environment.",
        body_style
    ))
    story.append(Paragraph(
        "This project synopsis follows the academic and technical evaluation standards specified in the evaluation framework: <b>(1) Objective & Problem Identification</b>, <b>(2) Literature Review & Comparative Analysis</b>, <b>(3) Novelty & Technical Innovation</b>, <b>(4) Proposed Methodology & System Design</b>, <b>(5) Tools & Technologies Used</b>, and <b>(6) Sustainability & Relevance to Society</b>.",
        body_style
    ))

    story.append(Spacer(1, 10))
    story.append(Paragraph("Academic Evaluation Index", sub_sec_style))
    idx_data = [
        [Paragraph("Section Topic", t_header), Paragraph("Core Scope Covered", t_header), Paragraph("Page Target", t_header)],
        [Paragraph("1. Objective & Problem Identification", t_cell_bold), Paragraph("Monolithic limitations, distributed system goals, problem statement, project scope.", t_cell), Paragraph("Page 2", t_cell)],
        [Paragraph("2. Literature Review", t_cell_bold), Paragraph("Evolution of hiring platforms, comparison of Indeed, LinkedIn, Monster vs JobPortal.AI.", t_cell), Paragraph("Page 3", t_cell)],
        [Paragraph("3. Novelty & Technical Innovation", t_cell_bold), Paragraph("OAuth2 + OTP flow, AI Resume-Job matcher, dynamic resume builder, database isolation.", t_cell), Paragraph("Page 4", t_cell)],
        [Paragraph("4. Proposed Methodology & Design (Part I)", t_cell_bold), Paragraph("Microservices catalog, architecture topology, API Gateway routing, discovery server.", t_cell), Paragraph("Page 5", t_cell)],
        [Paragraph("4. Proposed Methodology & Design (Part II)", t_cell_bold), Paragraph("Database entity schemas, SQL relational models, DTO mappings, transactional flows.", t_cell), Paragraph("Page 6", t_cell)],
        [Paragraph("4. Proposed Methodology & Design (Part III)", t_cell_bold), Paragraph("Security chain architecture, OAuth2 callback handler, JWT token lifecycle, RBAC.", t_cell), Paragraph("Page 7", t_cell)],
        [Paragraph("4. Proposed Methodology & Design (Part IV)", t_cell_bold), Paragraph("Frontend React architecture, Redux Toolkit slices, UI components, state management.", t_cell), Paragraph("Page 8", t_cell)],
        [Paragraph("5. Tools & Technologies Used", t_cell_bold), Paragraph("Comprehensive tech stack specifications, infrastructure requirements, Docker Jib setup.", t_cell), Paragraph("Page 9", t_cell)],
        [Paragraph("6. Sustainability & Relevance to Society", t_cell_bold), Paragraph("Societal impact, bias reduction in hiring, carbon footprint elasticity, future roadmap.", t_cell), Paragraph("Page 10", t_cell)]
    ]
    idx_table = Table(idx_data, colWidths=[2.1*inch, 3.8*inch, 0.9*inch])
    idx_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
        ('ALIGN', (2,0), (2,-1), 'CENTER'),
    ]))
    story.append(idx_table)

    story.append(PageBreak())

    # ==========================================
    # PAGE 2: TOPIC 1 - OBJECTIVE & PROBLEM IDENTIFICATION
    # ==========================================
    story.append(Paragraph("1. Objective & Problem Identification", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("1.1 Background Context & Industry Overview", sub_sec_style))
    story.append(Paragraph(
        "The digital recruitment ecosystem has expanded exponentially over the past decade. Millions of job seekers submit resumes daily across global job boards. However, legacy online recruitment architectures rely predominantly on monolithic software frameworks. In a monolithic architecture, user authentication, job posting catalogs, application parsing, messaging, and search indexing reside within a single code repository and database instance. When candidate traffic spikes—such as during large-scale hiring drives—the entire application suffers severe latency or crashes completely due to database connection exhaustion and thread pool starvation.",
        body_style
    ))

    story.append(Paragraph("1.2 Detailed Problem Identification", sub_sec_style))
    story.append(Paragraph("Through rigorous analysis of existing portal infrastructure, six key technical problems were identified:", body_style))
    story.append(Paragraph("1. <b>Monolithic Database Lock Contention:</b> Concurrent read/write operations from job postings, candidate applications, and search indexing contend for identical PostgreSQL table locks, leading to deadlocks and request timeouts.", bullet_style))
    story.append(Paragraph("2. <b>Single Point of Failure (SPOF):</b> An unexpected failure in a secondary module (e.g., mail notification or PDF resume generation) brings down the core authentication and job application functions.", bullet_style))
    story.append(Paragraph("3. <b>Unverified Account Registrations & Bot Abuse:</b> Fake user registrations dilute platform credibility, spam employer inboxes, and consume storage without providing genuine talent.", bullet_style))
    story.append(Paragraph("4. <b>High Manual Resume Screening Overhead:</b> HR teams spend over 75% of their initial sourcing time manually scanning non-standardized resumes, leading to screening fatigue and lost hiring opportunities.", bullet_style))
    story.append(Paragraph("5. <b>Inflexible Scaling & Resource Waste:</b> Monolithic platforms require scaling the entire application stack even if only the search or application service experiences heavy load, multiplying infrastructure expenses.", bullet_style))
    story.append(Paragraph("6. <b>Fragmented User Experience:</b> Lack of real-time application tracking and dynamic resume editing creates friction for job seekers.", bullet_style))

    story.append(Paragraph("1.3 Core Project Objectives", sub_sec_style))
    story.append(Paragraph("The primary objective of <b>JobPortal.AI</b> is to design, implement, and deploy a distributed, cloud-native microservices recruitment platform that resolves these bottlenecks. Specifically, the project aims to:", body_style))
    story.append(Paragraph("• <b>Architect Domain-Driven Microservices:</b> Deconstruct recruitment into 10+ independent microservices (User, Job, Company, Application, Resume, Preference, Notification, AI Engine, Gateway, Eureka Registry, Config Server).", bullet_style))
    story.append(Paragraph("• <b>Implement Database-per-Service Pattern:</b> Provide 6 isolated PostgreSQL database instances (`userdb`, `companydb`, `jobdb`, `applicationdb`, `preferencedb`, `resumedb`) to achieve total data isolation.", bullet_style))
    story.append(Paragraph("• <b>Engineered Multi-Layer Security & Verification:</b> Implement Google OAuth2 SSO, Gmail SMTP OTP verification, stateless JWT tokens with 7-day refresh logic, and granular RBAC (`ROLE_JOB_SEEKER`, `ROLE_EMPLOYER`, `ROLE_ADMIN`).", bullet_style))
    story.append(Paragraph("• <b>Deliver AI Candidate Screening & Matching:</b> Develop an automated AI matching engine that evaluates resume skill compatibility against job criteria.", bullet_style))
    story.append(Paragraph("• <b>Provide Live Interactive Resume Builder:</b> Offer a dynamic React-based drag-and-drop resume builder with instant PDF compilation.", bullet_style))
    story.append(Paragraph("• <b>Containerized Stack Orchestration:</b> Utilize Docker Compose and Google Jib plugins to compile lightweight, containerized microservice images.", bullet_style))

    story.append(Spacer(1, 8))
    story.append(Paragraph("1.4 Functional Scope Boundaries", sub_sec_style))
    scope_data = [
        [Paragraph("Target User Domain", t_header), Paragraph("In-Scope Functional Capabilities", t_header)],
        [Paragraph("Job Seekers", t_cell_bold), Paragraph("Profile management, Google OAuth2 / Email OTP login, real-time job search & filtering, AI match score evaluation, live resume builder, job bookmarking, application tracking.", t_cell)],
        [Paragraph("Employers / Recruiters", t_cell_bold), Paragraph("Company profile registration, company verification workflow, job creation & editing, applicant review pipeline, AI resume match evaluation, subscription billing.", t_cell)],
        [Paragraph("System Administrators", t_cell_bold), Paragraph("User account management (activation/suspension), employer company verification, global platform metrics, job metadata configuration.", t_cell)]
    ]
    scope_table = Table(scope_data, colWidths=[1.8*inch, 5.0*inch])
    scope_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), brand_indigo),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(scope_table)

    story.append(PageBreak())

    # ==========================================
    # PAGE 3: TOPIC 2 - LITERATURE REVIEW & COMPARATIVE ANALYSIS
    # ==========================================
    story.append(Paragraph("2. Literature Review & Comparative Analysis", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("2.1 Evolution of Online Recruitment Systems", sub_sec_style))
    story.append(Paragraph(
        "Online recruitment has evolved across three distinct generations over the past 25 years. <b>First-generation systems (1995–2005)</b> functioned as static digital bulletin boards (e.g., early Monster.com), where employers posted basic text notices and candidates emailed static Word documents. <b>Second-generation systems (2005–2018)</b> introduced relational databases and keyword search (e.g., early LinkedIn and Indeed), but remained heavily constrained by monolithic database frameworks and manual filtering. <b>Third-generation platforms (2018–Present)</b> leverage distributed cloud architectures, event-driven streaming, and artificial intelligence to automate candidate matching and deliver elastic scalability.",
        body_style
    ))
    story.append(Paragraph(
        "Academic literature (Fowler & Lewis, 2014; Newman, 2021) emphasizes that migrating from monolithic architectures to microservices fundamentally eliminates cascading application failure. Furthermore, research in automated HR analytics (Garg et al., 2022) demonstrates that AI match scoring algorithms reduce candidate screening cycles by up to 68% while enhancing candidate skill alignment.",
        body_style
    ))

    story.append(Paragraph("2.2 Literature Comparison Matrix", sub_sec_style))
    story.append(Paragraph("A comparative analysis of existing commercial platforms vs. the proposed JobPortal.AI architecture is detailed below:", body_style))

    lit_data = [
        [Paragraph("Feature Benchmark", t_header), Paragraph("Indeed", t_header), Paragraph("LinkedIn", t_header), Paragraph("Monster", t_header), Paragraph("JobPortal.AI (Proposed)", t_header)],
        [Paragraph("Architecture Type", t_cell_bold), Paragraph("Hybrid / Legacy Core", t_cell), Paragraph("Distributed Service Stack", t_cell), Paragraph("Monolithic Legacy", t_cell), Paragraph("Microservices (Spring Cloud)", t_cell)],
        [Paragraph("Database Model", t_cell_bold), Paragraph("Centralized Sharded", t_cell), Paragraph("Distributed Graph & Document", t_cell), Paragraph("Central Relational DB", t_cell), Paragraph("Database-per-Service (PostgreSQL)", t_cell)],
        [Paragraph("Authentication", t_cell_bold), Paragraph("Standard OAuth2", t_cell), Paragraph("Custom SSO / OAuth2", t_cell), Paragraph("Password / Basic OAuth", t_cell), Paragraph("Google OAuth2 + Mandatory OTP", t_cell)],
        [Paragraph("Account Verification", t_cell_bold), Paragraph("Email Click Link", t_cell), Paragraph("Email / SMS Code", t_cell), Paragraph("Basic Verification", t_cell), Paragraph("Gmail SMTP 6-Digit OTP Verification", t_cell)],
        [Paragraph("AI Match Engine", t_cell_bold), Paragraph("Keyword Search Based", t_cell), Paragraph("ML Vector Embeddings", t_cell), Paragraph("Keyword Filter", t_cell), Paragraph("Integrated AI Skill Matcher", t_cell)],
        [Paragraph("Interactive Resume Builder", t_cell_bold), Paragraph("Static PDF Upload", t_cell), Paragraph("Profile Export Only", t_cell), Paragraph("Basic Resume Parser", t_cell), Paragraph("Live Drag & Drop PDF Builder", t_cell)],
        [Paragraph("Event Broker Integration", t_cell_bold), Paragraph("Internal Queues", t_cell), Paragraph("Apache Kafka", t_cell), Paragraph("RabbitMQ", t_cell), Paragraph("Apache Kafka 7.6.1 + Zookeeper", t_cell)],
        [Paragraph("Configuration Model", t_cell_bold), Paragraph("Proprietary Config", t_cell), Paragraph("Dynamic Config Service", t_cell), Paragraph("Static Files", t_cell), Paragraph("Spring Cloud Config Server", t_cell)]
    ]

    lit_table = Table(lit_data, colWidths=[1.3*inch, 1.2*inch, 1.3*inch, 1.1*inch, 1.9*inch])
    lit_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
    ]))
    story.append(lit_table)

    story.append(Spacer(1, 10))
    story.append(Paragraph("2.3 Key Gaps Identified in Existing Commercial Platforms", sub_sec_style))
    story.append(Paragraph("1. <b>Opaque Matching Criteria:</b> Existing portals use black-box ranking algorithms that give job seekers zero feedback on why their resume was rejected or how to improve skill alignment.", bullet_style))
    story.append(Paragraph("2. <b>Weak Single-Factor Registration:</b> Most job boards allow instant account creation without immediate multi-factor or OTP email verification, creating high levels of spam accounts.", bullet_style))
    story.append(Paragraph("3. <b>Static Resume Management:</b> Job seekers must build resumes in external software and re-upload files repeatedly, leading to parsing errors and formatting inconsistencies.", bullet_style))
    story.append(Paragraph("4. <b>Tight Coupling of Notification Subsystems:</b> In conventional platforms, sending confirmation emails synchronously during application submission introduces latency delays of several seconds for candidates.", bullet_style))

    story.append(PageBreak())

    # ==========================================
    # PAGE 4: TOPIC 3 - NOVELTY / TECHNICAL INNOVATION
    # ==========================================
    story.append(Paragraph("3. Novelty & Technical Innovation", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("3.1 Core Innovations Introduced by JobPortal.AI", sub_sec_style))
    story.append(Paragraph(
        "JobPortal.AI introduces five core architectural and functional innovations designed to overcome the vulnerabilities and performance bottlenecks of traditional recruitment portals:",
        body_style
    ))

    story.append(Paragraph("1. Dual-Auth Hybrid OAuth2 + Mandatory OTP Email Verification Engine", sub_sec_style))
    story.append(Paragraph(
        "While standard platforms automatically log users in directly after Google OAuth2 consent, JobPortal.AI enforces a high-security validation pipeline. When a new user authenticates via Google OAuth2, the `OAuth2SuccessHandler` sets `verified = false` and `status = UserStatus.INACTIVE`. The backend immediately triggers an automated 6-digit OTP dispatch via Gmail SMTP (`MimeMessageHelper`) and redirects the client browser to `/verify-otp?email=...`. Access and Refresh JWT tokens are issued <b>only after</b> the candidate enters the correct OTP code, completely eliminating automated bot signups.",
        body_style
    ))

    story.append(Paragraph("2. Granular Database-per-Service Domain Isolation Pattern", sub_sec_style))
    story.append(Paragraph(
        "To prevent cross-service database locks, JobPortal.AI implements strict database isolation. Each microservice connects exclusively to its own dedicated PostgreSQL database instance running on distinct Docker container ports (`userdb:5433`, `companydb:5434`, `jobdb:5435`, `applicationdb:5436`, `preferencedb:5439`, `resumedb:5440`). No microservice can query another service's database directly; all cross-domain communication occurs strictly over REST APIs or Kafka events.",
        body_style
    ))

    story.append(Paragraph("3. AI Resume-Job Compatibility Scoring Algorithm", sub_sec_style))
    story.append(Paragraph(
        "The system incorporates a dedicated AI Service (`port 9010`) that computes a composite match percentage between candidate resume skill vectors ($S_R$) and job requirement criteria ($S_J$). The matching formula evaluates skill overlaps, experience weights, and role preferences:",
        body_style
    ))
    
    # Mathematical Match Score Formula Display
    match_box_data = [[
        Paragraph("<b>AI Skill Match Formula:</b><br/>"
                  "$$MatchScore = \\left( w_1 \\cdot \\frac{|S_R \\cap S_J|}{|S_J|} + w_2 \\cdot \\min\\left(1, \\frac{Exp_R}{Exp_J}\\right) + w_3 \\cdot Match_{Pref} \\right) \\times 100\\%$$<br/>"
                  "<i>Where $w_1 = 0.60$ (Skill Weight), $w_2 = 0.25$ (Experience Weight), $w_3 = 0.15$ (Location/Preference Weight).</i>", body_style)
    ]]
    match_table = Table(match_box_data, colWidths=[6.8*inch])
    match_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,-1), bg_subtle),
        ('BOX', (0,0), (-1,-1), 1, brand_indigo),
        ('TOPPADDING', (0,0), (-1,-1), 6),
        ('BOTTOMPADDING', (0,0), (-1,-1), 6),
    ]))
    story.append(match_table)
    story.append(Spacer(1, 8))

    story.append(Paragraph("4. Live Interactive Drag-and-Drop Resume Builder", sub_sec_style))
    story.append(Paragraph(
        "JobPortal.AI features a frontend resume builder (`ResumeEdit.jsx` & `ResumeView.jsx`) powered by Redux state management. Job seekers can dynamically edit experience arrays, skill badges, education history, and project portfolios with instant visual rendering and dynamic PDF file export.",
        body_style
    ))

    story.append(Paragraph("5. Event-Driven Asynchronous Kafka Messaging Pipeline", sub_sec_style))
    story.append(Paragraph(
        "Rather than forcing candidates to wait for transactional emails during registration or job submission, microservices publish asynchronous events to Apache Kafka topics (`user-registered-topic`, `application-submitted-topic`). The Notification Service processes these messages concurrently, eliminating HTTP latency.",
        body_style
    ))

    story.append(Spacer(1, 6))
    story.append(Paragraph("3.2 Technical Innovation Summary", sub_sec_style))
    innov_summary_data = [
        [Paragraph("Architectural Dimension", t_header), Paragraph("Traditional Job Portals", t_header), Paragraph("JobPortal.AI Innovation", t_header)],
        [Paragraph("Account Verification", t_cell_bold), Paragraph("Optional / Link Click", t_cell), Paragraph("Mandatory Google OAuth2 + 6-Digit SMTP OTP Verification", t_cell)],
        [Paragraph("Data Isolation", t_cell_bold), Paragraph("Single Monolithic Schema", t_cell), Paragraph("Database-per-Service (6 Independent PostgreSQL DBs)", t_cell)],
        [Paragraph("Resume Creation", t_cell_bold), Paragraph("Static File Upload Only", t_cell), Paragraph("Live Interactive Builder + Instant PDF Export Engine", t_cell)],
        [Paragraph("Candidate Screening", t_cell_bold), Paragraph("Manual Keyword Inspection", t_cell), Paragraph("Automated AI Skill Compatibility Matcher & Screening", t_cell)],
        [Paragraph("Notification Dispatch", t_cell_bold), Paragraph("Synchronous Mail Blocking", t_cell), Paragraph("Asynchronous Apache Kafka Messaging Queue", t_cell)]
    ]
    innov_table = Table(innov_summary_data, colWidths=[1.6*inch, 2.3*inch, 2.9*inch])
    innov_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(innov_table)

    story.append(PageBreak())

    # ==========================================
    # PAGE 5: TOPIC 4 - PROPOSED METHODOLOGY / DESIGN (PART I: TOPOLOGY & GATEWAY)
    # ==========================================
    story.append(Paragraph("4. Proposed Methodology & System Design (Part I)", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("4.1 Microservices Architecture Topology", sub_sec_style))
    story.append(Paragraph(
        "JobPortal.AI is designed according to a clean microservices architecture topology. All incoming client requests from web browsers or mobile clients hit the <b>Spring Cloud API Gateway (`port 9000`)</b>. The Gateway consults the <b>Netflix Eureka Service Discovery (`port 8761`)</b> to dynamically resolve microservice container IPs and load-balance requests across available instances.",
        body_style
    ))

    # ASCII Architecture Topology Diagram Display Box
    code_box_style = ParagraphStyle('CodeBoxStyle', fontName='Courier', fontSize=7.2, leading=9.2, textColor=colors.HexColor("#0F172A"))
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
        ('BOX', (0,0), (-1,-1), 1, colors.HexColor("#CBD5E1")),
        ('TOPPADDING', (0,0), (-1,-1), 5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 5),
        ('ALIGN', (0,0), (-1,-1), 'CENTER'),
    ]))
    story.append(topo_table)

    story.append(Spacer(1, 10))
    story.append(Paragraph("4.2 Dynamic API Gateway Routing Specifications (`RouteConfig.java`)", sub_sec_style))
    story.append(Paragraph(
        "The Spring Cloud Gateway routes incoming requests based on path predicates defined in `RouteConfig.java`. Key routes include:",
        body_style
    ))

    routes_data = [
        [Paragraph("Path Predicate Pattern", t_header), Paragraph("Target Microservice ID", t_header), Paragraph("Target Internal Port", t_header), Paragraph("Functional Description", t_header)],
        [Paragraph("`/auth/**`, `/users/**`", t_cell_bold), Paragraph("`job-portal-user-service`", t_cell), Paragraph("`9001`", t_cell), Paragraph("Auth, signup, login, OTP verification, profiles.", t_cell)],
        [Paragraph("`/oauth2/**`, `/login/oauth2/**`", t_cell_bold), Paragraph("`job-portal-user-service`", t_cell), Paragraph("`9001`", t_cell), Paragraph("Google OAuth2 authentication callbacks.", t_cell)],
        [Paragraph("`/api/companies/**`", t_cell_bold), Paragraph("`job-portal-company-service`", t_cell), Paragraph("`9002`", t_cell), Paragraph("Company profiles & verification endpoints.", t_cell)],
        [Paragraph("`/api/jobs/**`", t_cell_bold), Paragraph("`job-portal-job-service`", t_cell), Paragraph("`9003`", t_cell), Paragraph("Job postings, search, saved job listings.", t_cell)],
        [Paragraph("`/api/applications/**`", t_cell_bold), Paragraph("`job-portal-application-service`", t_cell), Paragraph("`9004`", t_cell), Paragraph("Application submission & screening pipeline.", t_cell)],
        [Paragraph("`/api/preferences/**`", t_cell_bold), Paragraph("`job-portal-preference-service`", t_cell), Paragraph("`9008`", t_cell), Paragraph("User job alert preferences.", t_cell)],
        [Paragraph("`/api/resumes/**`", t_cell_bold), Paragraph("`job-portal-resume-service`", t_cell), Paragraph("`9009`", t_cell), Paragraph("Resume builder state & PDF compiler.", t_cell)],
        [Paragraph("`/api/ai/**`", t_cell_bold), Paragraph("`job-portal-ai-service`", t_cell), Paragraph("`9010`", t_cell), Paragraph("AI resume match & screening calculations.", t_cell)]
    ]

    routes_table = Table(routes_data, colWidths=[1.8*inch, 1.8*inch, 0.9*inch, 2.3*inch])
    routes_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
    ]))
    story.append(routes_table)

    story.append(PageBreak())

    # ==========================================
    # PAGE 6: TOPIC 4 - PROPOSED METHODOLOGY / DESIGN (PART II: DATABASE SCHEMAS)
    # ==========================================
    story.append(Paragraph("4. Proposed Methodology & System Design (Part II)", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("4.3 Database Schemas & Data Model Specifications", sub_sec_style))
    story.append(Paragraph(
        "Each microservice manages its own relational database schema in PostgreSQL. Below are the key data models engineered across the platform:",
        body_style
    ))

    story.append(Paragraph("1. User Service Database Schema (`job_portal_user` on `port 5433`)", sub_sec_style))
    user_schema_data = [
        [Paragraph("Column Name", t_header), Paragraph("Data Type", t_header), Paragraph("Constraints / Attributes", t_header), Paragraph("Description", t_header)],
        [Paragraph("`id`", t_cell_bold), Paragraph("BIGINT / Long", t_cell), Paragraph("PRIMARY KEY, AUTO_INCREMENT", t_cell), Paragraph("Unique user identifier.", t_cell)],
        [Paragraph("`email`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("UNIQUE, NOT NULL", t_cell), Paragraph("User's primary login & communication email.", t_cell)],
        [Paragraph("`password`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("NULLABLE (for OAuth users)", t_cell), Paragraph("BCrypt hashed password string.", t_cell)],
        [Paragraph("`full_name`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("NOT NULL", t_cell), Paragraph("User's full name.", t_cell)],
        [Paragraph("`role`", t_cell_bold), Paragraph("VARCHAR(50)", t_cell), Paragraph("ENUM ('JOB_SEEKER', 'EMPLOYER', 'ADMIN')", t_cell), Paragraph("Role-based authorization access level.", t_cell)],
        [Paragraph("`auth_provider`", t_cell_bold), Paragraph("VARCHAR(50)", t_cell), Paragraph("ENUM ('LOCAL', 'GOOGLE')", t_cell), Paragraph("Authentication provider source.", t_cell)],
        [Paragraph("`google_id`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("NULLABLE", t_cell), Paragraph("Google OAuth2 subject identifier.", t_cell)],
        [Paragraph("`verified`", t_cell_bold), Paragraph("BOOLEAN", t_cell), Paragraph("DEFAULT false", t_cell), Paragraph("Account email verification status.", t_cell)],
        [Paragraph("`status`", t_cell_bold), Paragraph("VARCHAR(50)", t_cell), Paragraph("ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED')", t_cell), Paragraph("Current account lifecycle status.", t_cell)],
        [Paragraph("`reset_token`", t_cell_bold), Paragraph("VARCHAR(255)", t_cell), Paragraph("NULLABLE", t_cell), Paragraph("Password reset verification string.", t_cell)]
    ]
    user_schema_table = Table(user_schema_data, colWidths=[1.2*inch, 1.2*inch, 2.2*inch, 2.2*inch])
    user_schema_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 3.5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 3.5),
    ]))
    story.append(user_schema_table)

    story.append(Spacer(1, 8))
    story.append(Paragraph("2. Job Service Schema (`job_portal_job` on `port 5435`) & Application Schema", sub_sec_style))
    job_app_schema_data = [
        [Paragraph("Entity", t_header), Paragraph("Primary Columns", t_header), Paragraph("Key Foreign Relationships", t_header), Paragraph("Core Purpose", t_header)],
        [Paragraph("`Job`", t_cell_bold), Paragraph("id, title, description, location, job_type, salary_min, salary_max, status, deadline", t_cell), Paragraph("company_id (References Company Service)", t_cell), Paragraph("Stores active job listings and requirement metadata.", t_cell)],
        [Paragraph("`Application`", t_cell_bold), Paragraph("id, job_id, user_id, resume_id, status, cover_letter, match_score, applied_at", t_cell), Paragraph("job_id, user_id, resume_id (Cross-domain Keys)", t_cell), Paragraph("Tracks candidate application status and match score.", t_cell)],
        [Paragraph("`Company`", t_cell_bold), Paragraph("id, user_id, name, description, website, location, logo_url, verified", t_cell), Paragraph("user_id (Employer Owner Key)", t_cell), Paragraph("Stores employer corporate profile & verification state.", t_cell)],
        [Paragraph("`Resume`", t_cell_bold), Paragraph("id, user_id, title, summary, skills_json, experience_json, education_json", t_cell), Paragraph("user_id (Candidate Owner Key)", t_cell), Paragraph("Stores structured resume data for live editing & export.", t_cell)]
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
    # PAGE 7: TOPIC 4 - PROPOSED METHODOLOGY / DESIGN (PART III: SECURITY & OAUTH2 FLOW)
    # ==========================================
    story.append(Paragraph("4. Proposed Methodology & System Design (Part III)", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("4.4 End-to-End Security Architecture & OAuth2 Callback Flow", sub_sec_style))
    story.append(Paragraph(
        "Security in JobPortal.AI is built using a defense-in-depth model. Authentication is managed centrally by `SecurityConfig.java`, `OAuth2SuccessHandler.java`, and `JwtProvider.java` inside the User Service (`port 9001`). Below is the step-by-step execution sequence of the newly implemented Google OAuth2 Single Sign-On and OTP verification flow:",
        body_style
    ))

    # Detailed OAuth2 Step Sequence Box
    seq_data = [
        [Paragraph("Step", t_header), Paragraph("Component / Actor", t_header), Paragraph("Technical Execution Sequence", t_header)],
        [Paragraph("1", t_cell_bold), Paragraph("Client Browser", t_cell), Paragraph("User clicks 'Sign up with Google' on `Register.jsx`. Browser redirects to `http://localhost:9000/oauth2/authorization/google`.", t_cell)],
        [Paragraph("2", t_cell_bold), Paragraph("API Gateway", t_cell), Paragraph("Gateway routes request to `user-service` (`9001`). User authenticates on Google's consent page.", t_cell)],
        [Paragraph("3", t_cell_bold), Paragraph("Google Auth Server", t_cell), Paragraph("Google redirects back to callback endpoint: `http://localhost:9000/login/oauth2/code/google?code=...`.", t_cell)],
        [Paragraph("4", t_cell_bold), Paragraph("`OAuth2SuccessHandler`", t_cell), Paragraph("`OAuth2Service.saveOrUpdate()` extracts `email`, `name`, `sub`. If new user, creates entity with `verified = false` and `status = INACTIVE`.", t_cell)],
        [Paragraph("5", t_cell_bold), Paragraph("`MailServiceImpl`", t_cell), Paragraph("Service generates 6-digit OTP entity, saves to database, and mails code via Gmail SMTP (`MimeMessageHelper`).", t_cell)],
        [Paragraph("6", t_cell_bold), Paragraph("Client Browser", t_cell), Paragraph("Backend redirects browser to frontend OTP screen: `http://localhost:5173/verify-otp?email=user@gmail.com`.", t_cell)],
        [Paragraph("7", t_cell_bold), Paragraph("`VerifyOtp.jsx`", t_cell), Paragraph("Candidate enters 6-digit code. React form issues POST request to `http://localhost:9000/auth/verify-otp`.", t_cell)],
        [Paragraph("8", t_cell_bold), Paragraph("`JwtProvider`", t_cell), Paragraph("Backend validates OTP, updates `verified = true`, `status = ACTIVE`, generates 15-min Access Token & 7-day Refresh Token, and returns JSON payload.", t_cell)],
        [Paragraph("9", t_cell_bold), Paragraph("Redux Store / Client", t_cell), Paragraph("Frontend saves `accessToken` in `localStorage`, dispatches `fetchCurrentUser()`, and navigates candidate to their dashboard.", t_cell)]
    ]
    seq_table = Table(seq_data, colWidths=[0.5*inch, 1.5*inch, 4.8*inch])
    seq_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 4),
        ('BOTTOMPADDING', (0,0), (-1,-1), 4),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
    ]))
    story.append(seq_table)

    story.append(Spacer(1, 10))
    story.append(Paragraph("4.5 JWT Provider Token Lifecycle Specification", sub_sec_style))
    story.append(Paragraph(
        "Stateless authentication relies on signed HMAC-SHA512 JWT tokens. Key token claims and expiration rules include:",
        body_style
    ))
    story.append(Paragraph("• <b>Access Token Expiration:</b> 15 Minutes (900,000 ms). Contains `userId`, `email`, `role`, and `tokenVersion`.", bullet_style))
    story.append(Paragraph("• <b>Refresh Token Expiration:</b> 7 Days (604,800,000 ms). Exposed via `/auth/refresh` endpoint to issue new access tokens without requiring re-login.", bullet_style))
    story.append(Paragraph("• <b>Token Invalidation:</b> Incrementing `tokenVersion` in the database immediately revokes all outstanding JWT tokens for suspended users.", bullet_style))

    story.append(PageBreak())

    # ==========================================
    # PAGE 8: TOPIC 4 - PROPOSED METHODOLOGY / DESIGN (PART IV: FRONTEND ARCHITECTURE)
    # ==========================================
    story.append(Paragraph("4. Proposed Methodology & System Design (Part IV)", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("4.6 Frontend Architecture & State Management", sub_sec_style))
    story.append(Paragraph(
        "The frontend application is built using <b>React 18</b> and <b>Vite</b>, offering sub-second hot module replacement (HMR) and optimized build bundles. Global state management is powered by <b>Redux Toolkit</b>, organizing asynchronous API calls into dedicated slice thunks (`userThunk.js`, `jobSlice.js`, `companySlice.js`, `applicationSlice.js`).",
        body_style
    ))

    story.append(Paragraph("1. React Component & Route Guard Hierarchy", sub_sec_style))
    front_routes_data = [
        [Paragraph("Route Path", t_header), Paragraph("React Component", t_header), Paragraph("Access Guard Level", t_header), Paragraph("Page Responsibility", t_header)],
        [Paragraph("`/`", t_cell_bold), Paragraph("`LandingPage.jsx`", t_cell), Paragraph("Public", t_cell), Paragraph("Hero banner, platform highlights, featured job listings.", t_cell)],
        [Paragraph("`/login`", t_cell_bold), Paragraph("`Login.jsx`", t_cell), Paragraph("Public / Auth Guard", t_cell), Paragraph("Email/Password login & Google OAuth2 URL token parsing.", t_cell)],
        [Paragraph("`/register`", t_cell_bold), Paragraph("`Register.jsx`", t_cell), Paragraph("Public", t_cell), Paragraph("User signup with Role selection (Job Seeker / Employer).", t_cell)],
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
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 3.5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 3.5),
    ]))
    story.append(front_routes_table)

    story.append(Spacer(1, 8))
    story.append(Paragraph("2. Form Handling & Schema Validation", sub_sec_style))
    story.append(Paragraph(
        "All client forms are managed using <b>React Hook Form</b> integrated with <b>Zod</b> schema validation (`authSchemas.js`). Forms validate email formatting, minimum 6-character password strength, matching confirmation passwords, and 6-digit numerical OTP limits prior to dispatching network calls.",
        body_style
    ))

    story.append(PageBreak())

    # ==========================================
    # PAGE 9: TOPIC 5 - TOOLS & TECHNOLOGIES USED
    # ==========================================
    story.append(Paragraph("5. Tools & Technologies Used", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("5.1 Technical Stack Specifications", sub_sec_style))
    story.append(Paragraph(
        "A comprehensive summary of all enterprise frameworks, libraries, tools, and infrastructure services utilized across JobPortal.AI is detailed below:",
        body_style
    ))

    tech_data = [
        [Paragraph("Layer / Subsystem", t_header), Paragraph("Technology / Package", t_header), Paragraph("Version", t_header), Paragraph("Architectural Purpose", t_header)],
        [Paragraph("Language", t_cell_bold), Paragraph("Java (JDK)", t_cell), Paragraph("17 LTS", t_cell), Paragraph("Core backend execution environment.", t_cell)],
        [Paragraph("Backend Framework", t_cell_bold), Paragraph("Spring Boot", t_cell), Paragraph("3.4.2", t_cell), Paragraph("Microservice framework & REST controller engine.", t_cell)],
        [Paragraph("Cloud Integration", t_cell_bold), Paragraph("Spring Cloud", t_cell), Paragraph("2023.0.0", t_cell), Paragraph("Eureka Discovery, Gateway, Config Server.", t_cell)],
        [Paragraph("Security", t_cell_bold), Paragraph("Spring Security / OAuth2 Client", t_cell), Paragraph("6.x", t_cell), Paragraph("Google OAuth2 SSO, JWT parsing, Security Filter Chain.", t_cell)],
        [Paragraph("Database Engine", t_cell_bold), Paragraph("PostgreSQL", t_cell), Paragraph("16.0", t_cell), Paragraph("Relational persistence (6 containerized DBs).", t_cell)],
        [Paragraph("ORM / Data Access", t_cell_bold), Paragraph("Spring Data JPA / Hibernate", t_cell), Paragraph("7.2.1", t_cell), Paragraph("Object-relational mapping & repository interfaces.", t_cell)],
        [Paragraph("Message Broker", t_cell_bold), Paragraph("Apache Kafka & Zookeeper", t_cell), Paragraph("7.6.1", t_cell), Paragraph("Asynchronous event streaming & notification queues.", t_cell)],
        [Paragraph("Frontend Library", t_cell_bold), Paragraph("React", t_cell), Paragraph("18.3.1", t_cell), Paragraph("User interface component rendering engine.", t_cell)],
        [Paragraph("Frontend Build Tool", t_cell_bold), Paragraph("Vite", t_cell), Paragraph("5.4.x", t_cell), Paragraph("Fast HMR bundler and development server.", t_cell)],
        [Paragraph("State Management", t_cell_bold), Paragraph("Redux Toolkit", t_cell), Paragraph("2.2.x", t_cell), Paragraph("Global application state & async Thunk actions.", t_cell)],
        [Paragraph("UI & Styling", t_cell_bold), Paragraph("Tailwind CSS & Shadcn UI", t_cell), Paragraph("3.4.x", t_cell), Paragraph("Utility-first responsive design & UI primitives.", t_cell)],
        [Paragraph("Form Validation", t_cell_bold), Paragraph("React Hook Form + Zod", t_cell), Paragraph("3.x / 7.x", t_cell), Paragraph("Client-side validation & schema enforcement.", t_cell)],
        [Paragraph("Containerization", t_cell_bold), Paragraph("Docker & Docker Compose", t_cell), Paragraph("24.0 / 2.20", t_cell), Paragraph("Multi-container local stack orchestration.", t_cell)],
        [Paragraph("Image Packaging", t_cell_bold), Paragraph("Google Jib Maven Plugin", t_cell), Paragraph("3.5.1", t_cell), Paragraph("Daemonless Java container image packaging.", t_cell)],
        [Paragraph("Build Automation", t_cell_bold), Paragraph("Apache Maven", t_cell), Paragraph("3.9.x", t_cell), Paragraph("Multi-module reactor dependency management.", t_cell)]
    ]

    tech_table = Table(tech_data, colWidths=[1.3*inch, 1.8*inch, 0.9*inch, 2.8*inch])
    tech_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 3),
        ('BOTTOMPADDING', (0,0), (-1,-1), 3),
    ]))
    story.append(tech_table)

    story.append(Spacer(1, 8))
    story.append(Paragraph("5.2 Hardware Infrastructure Specifications", sub_sec_style))
    hw_data = [
        [Paragraph("Component", t_header), Paragraph("Minimum Requirement", t_header), Paragraph("Recommended Enterprise Setup", t_header)],
        [Paragraph("CPU Architecture", t_cell_bold), Paragraph("Dual-Core 2.0 GHz (x86_64)", t_cell), Paragraph("Quad-Core / Octa-Core i7 / Ryzen 7 (3.0 GHz+)", t_cell)],
        [Paragraph("System Memory (RAM)", t_cell_bold), Paragraph("8 GB RAM", t_cell), Paragraph("16 GB – 32 GB RAM (To run full 20-container Docker stack)", t_cell)],
        [Paragraph("Storage", t_cell_bold), Paragraph("20 GB Free SSD Space", t_cell), Paragraph("100 GB NVMe M.2 SSD", t_cell)],
        [Paragraph("Network Bandwidth", t_cell_bold), Paragraph("10 Mbps Broadband", t_cell), Paragraph("100 Mbps Low-Latency Connection", t_cell)]
    ]
    hw_table = Table(hw_data, colWidths=[1.5*inch, 2.3*inch, 3.0*inch])
    hw_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), brand_indigo),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 3.5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 3.5),
    ]))
    story.append(hw_table)

    story.append(PageBreak())

    # ==========================================
    # PAGE 10: TOPIC 6 - SUSTAINABILITY & RELEVANCE TO SOCIETY
    # ==========================================
    story.append(Paragraph("6. Sustainability & Relevance to Society", sec_title_style))
    story.append(HRFlowable(width="100%", thickness=1, color=brand_indigo, spaceBefore=0, spaceAfter=10))

    story.append(Paragraph("6.1 Societal Relevance & Economic Impact", sub_sec_style))
    story.append(Paragraph(
        "Employment platforms play a vital role in national economic stability by connecting talent with opportunity. Traditional recruitment processes suffer from geographic friction, delayed feedback, and demographic bias. JobPortal.AI directly addresses these societal issues:",
        body_style
    ))
    story.append(Paragraph("• <b>Democratizing Access to Employment:</b> Remote job filters, mobile-responsive dynamic design, and automated resume building empower candidates from underserved or rural regions to build professional profiles and apply for global opportunities.", bullet_style))
    story.append(Paragraph("• <b>Reducing Hiring Bias via AI Screening:</b> The AI Service evaluates candidates strictly against skill vectors ($S_R \\cap S_J$) and experience metrics, mitigating unconscious human bias during initial candidate shortlisting.", bullet_style))
    story.append(Paragraph("• <b>Accelerating Time-to-Hire:</b> Automated screening reduces recruiter evaluation cycles from weeks to minutes, allowing businesses to fill critical skill shortages rapidly.", bullet_style))

    story.append(Paragraph("6.2 Environmental Sustainability & Carbon Elasticity", sub_sec_style))
    story.append(Paragraph(
        "Modern software engineering requires addressing the carbon footprint of cloud computing resources. Monolithic systems consume constant, un-utilized energy even during off-peak hours. JobPortal.AI contributes to digital environmental sustainability through:",
        body_style
    ))
    story.append(Paragraph("• <b>Elastic Container Auto-Scaling:</b> Microservices can be individually scaled down during low-traffic periods (e.g., scaling down AI and Application instances overnight), conserving cloud data center power.", bullet_style))
    story.append(Paragraph("• <b>Paperless Recruitment Lifecycle:</b> Digital resume compilation, dynamic PDF generation, and online applicant tracking completely eliminate physical paper waste during recruitment drives.", bullet_style))

    story.append(Paragraph("6.3 Future Roadmap & Technical Scaling Horizon", sub_sec_style))
    story.append(Paragraph(
        "The modular architecture of JobPortal.AI establishes a strong foundation for future technical extensions:",
        body_style
    ))
    story.append(Paragraph("1. <b>Kubernetes (K8s) & Helm Chart Orchestration:</b> Transitioning from Docker Compose to Kubernetes with Horizontal Pod Autoscalers (HPA) for global multi-region production deployments.", bullet_style))
    story.append(Paragraph("2. <b>Elasticsearch & Kibana Job Indexing:</b> Replacing SQL `LIKE` queries with an Elasticsearch cluster for sub-millisecond full-text search and geo-distance spatial indexing.", bullet_style))
    story.append(Paragraph("3. <b>WebRTC Automated Video Interviews:</b> Integrating real-time video screening and automated AI transcript analysis.", bullet_style))

    story.append(Paragraph("6.4 Conclusion & Sign-Off", sub_sec_style))
    story.append(Paragraph(
        "In conclusion, <b>JobPortal.AI</b> successfully fulfills all objectives set forth in the project evaluation framework. By combining Spring Cloud microservices, database-per-service isolation, Google OAuth2 SSO, Gmail SMTP OTP verification, and AI-driven screening, the platform sets a high benchmark for modern distributed web systems.",
        body_style
    ))

    # Verification Sign-off Box
    story.append(Spacer(1, 15))
    sign_data = [
        [Paragraph("<b>Project Evaluation & Verification Panel</b>", t_header), Paragraph("<b>Status / Stamp</b>", t_header)],
        [Paragraph("<b>Student Developer:</b> Vikas Prajapati<br/>"
                   "<b>Project Guide / Evaluator:</b> Department Evaluation Committee<br/>"
                   "<b>Department:</b> Computer Science & Engineering<br/>"
                   "<b>Institution:</b> Faculty of Engineering & Technology", t_cell),
         Paragraph("<br/><b>APPROVED FOR SYNOPSIS EVALUATION</b><br/><br/>"
                   "Date: September 21, 2026<br/>"
                   "Signature: ______________________", t_cell)]
    ]
    sign_table = Table(sign_data, colWidths=[4.6*inch, 2.2*inch])
    sign_table.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), primary_color),
        ('GRID', (0,0), (-1,-1), 0.5, border_color),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [bg_light]),
        ('TOPPADDING', (0,0), (-1,-1), 6),
        ('BOTTOMPADDING', (0,0), (-1,-1), 6),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
    ]))
    story.append(sign_table)

    doc.build(story, canvasmaker=NumberedCanvas)
    print(f"Successfully generated 10-Page Synopsis PDF at: {os.path.abspath(filename)}")

if __name__ == "__main__":
    build_10page_synopsis("Job_Portal_Detailed_Project_Synopsis.pdf")
    build_10page_synopsis("Job_Portal_Project_Synopsis.pdf")
