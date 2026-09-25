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
        self.setFont("Helvetica", 9)
        self.setFillColor(colors.HexColor("#64748B"))
        
        # Suppress headers/footers on cover/title page if desired, but here page 1 is title page
        if self._pageNumber > 1:
            # Header
            self.drawString(54, 750, "JobPortal.AI — Microservices Architecture & Technical Project Synopsis")
            self.setStrokeColor(colors.HexColor("#CBD5E1"))
            self.setLineWidth(0.5)
            self.line(54, 742, 558, 742)
            
            # Footer
            self.line(54, 45, 558, 45)
            self.drawString(54, 32, "Confidential — For Academic & Technical Review")
            page_text = f"Page {self._pageNumber} of {page_count}"
            self.drawRightString(558, 32, page_text)
        self.restoreState()

def create_synopsis_pdf(filename="Job_Portal_Project_Synopsis.pdf"):
    doc = SimpleDocTemplate(
        filename,
        pagesize=letter,
        leftMargin=54,
        rightMargin=54,
        topMargin=54,
        bottomMargin=54
    )

    styles = getSampleStyleSheet()
    
    # Custom Styles
    primary_color = colors.HexColor("#1E293B")   # Slate 800
    brand_color = colors.HexColor("#4F46E5")     # Indigo 600
    text_dark = colors.HexColor("#0F172A")       # Slate 900
    text_muted = colors.HexColor("#475569")      # Slate 600
    bg_light = colors.HexColor("#F8FAFC")        # Slate 50

    title_style = ParagraphStyle(
        'CoverTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=24,
        leading=28,
        textColor=brand_color,
        spaceAfter=6
    )

    subtitle_style = ParagraphStyle(
        'CoverSubtitle',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=12,
        leading=16,
        textColor=text_muted,
        spaceAfter=15
    )

    h1_style = ParagraphStyle(
        'Heading1_Custom',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=15,
        leading=19,
        textColor=primary_color,
        spaceBefore=14,
        spaceAfter=8,
        keepWithNext=True
    )

    h2_style = ParagraphStyle(
        'Heading2_Custom',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=11,
        leading=15,
        textColor=brand_color,
        spaceBefore=10,
        spaceAfter=4,
        keepWithNext=True
    )

    body_style = ParagraphStyle(
        'Body_Custom',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9.5,
        leading=13.5,
        textColor=text_dark,
        spaceAfter=6
    )

    bullet_style = ParagraphStyle(
        'Bullet_Custom',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9.5,
        leading=13.5,
        textColor=text_dark,
        leftIndent=12,
        firstLineIndent=-8,
        spaceAfter=4
    )

    table_cell = ParagraphStyle(
        'TableCell',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=8.5,
        leading=11.5,
        textColor=text_dark
    )

    table_cell_bold = ParagraphStyle(
        'TableCellBold',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=8.5,
        leading=11.5,
        textColor=primary_color
    )

    table_header = ParagraphStyle(
        'TableHeader',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=9,
        leading=12,
        textColor=colors.white
    )

    story = []

    # Banner Title Block
    story.append(Paragraph("PROJECT SYNOPSIS", ParagraphStyle('SubHeader', fontName='Helvetica-Bold', fontSize=10, leading=12, textColor=text_muted, spaceAfter=4)))
    story.append(Paragraph("JobPortal.AI — AI-Powered Microservices Job Portal", title_style))
    story.append(Paragraph("An Enterprise-Grade Distributed Recruitment Platform featuring Google OAuth2, OTP Email Verification, AI Matching Engine, Dynamic Resume Builder & Real-Time Candidate Screening.", subtitle_style))
    story.append(HRFlowable(width="100%", thickness=1.5, color=brand_color, spaceBefore=0, spaceAfter=15))

    # Project Overview Metadata Table
    meta_data = [
        [Paragraph("Project Name", table_cell_bold), Paragraph("JobPortal.AI (ZoshHire)", table_cell),
         Paragraph("Architecture", table_cell_bold), Paragraph("Distributed Microservices (Spring Cloud)", table_cell)],
        [Paragraph("Frontend Tech Stack", table_cell_bold), Paragraph("React 18, Vite, Redux Toolkit, Tailwind CSS", table_cell),
         Paragraph("Backend Tech Stack", table_cell_bold), Paragraph("Spring Boot 3.x, Java 17, Spring Cloud", table_cell)],
        [Paragraph("Databases", table_cell_bold), Paragraph("PostgreSQL 16 (Database per Microservice)", table_cell),
         Paragraph("Messaging / Queue", table_cell_bold), Paragraph("Apache Kafka, Apache Zookeeper", table_cell)],
        [Paragraph("Containerization", table_cell_bold), Paragraph("Docker, Docker Compose, Google Jib", table_cell),
         Paragraph("Security & Auth", table_cell_bold), Paragraph("OAuth2, JWT, Google SMTP OTP, RBAC", table_cell)]
    ]
    meta_table = Table(meta_data, colWidths=[1.1*inch, 2.3*inch, 1.1*inch, 2.3*inch])
    meta_table.setStyle(TableStyle([
        ('BACKGROUND', (0, 0), (-1, -1), bg_light),
        ('BOX', (0, 0), (-1, -1), 0.5, colors.HexColor("#CBD5E1")),
        ('INNERGRID', (0, 0), (-1, -1), 0.5, colors.HexColor("#E2E8F0")),
        ('VALIGN', (0, 0), (-1, -1), 'MIDDLE'),
        ('TOPPADDING', (0, 0), (-1, -1), 5),
        ('BOTTOMPADDING', (0, 0), (-1, -1), 5),
    ]))
    story.append(meta_table)
    story.append(Spacer(1, 15))

    # Section 1: Executive Summary
    story.append(Paragraph("1. Executive Summary", h1_style))
    story.append(Paragraph(
        "<b>JobPortal.AI</b> is an advanced, enterprise-grade job recruitment platform engineered to connect job seekers with top-tier employers through AI-driven algorithms and a decoupled, distributed microservices architecture. Traditional monolithic job portals struggle with scalability bottlenecks, single points of failure, and monolithic database locks. JobPortal.AI resolves these challenges by decoupling core domains into 10+ autonomous Spring Boot microservices orchestrated via Spring Cloud Gateway, Netflix Eureka Service Discovery, and Spring Cloud Config Server.",
        body_style
    ))
    story.append(Paragraph(
        "The system incorporates state-of-the-art authentication featuring <b>Google OAuth2 integration</b>, <b>OTP-based email verification via Gmail SMTP</b>, and <b>JWT (JSON Web Token) stateless security</b> with 7-day refresh tokens. Candidates benefit from an AI-powered Resume Builder and Resume-Job Matcher, while employers gain access to automated AI candidate screening, applicant tracking pipelines, and detailed dashboard analytics.",
        body_style
    ))

    # Section 2: Problem Statement & System Objectives
    story.append(Paragraph("2. Problem Statement & Core Objectives", h1_style))
    story.append(Paragraph("<b>2.1 Problem Statement:</b> Modern hiring portals face latency issues during peak traffic, rigid database schemas, high friction during registration, and manual overhead in filtering hundreds of resumes per job posting.", body_style))
    story.append(Paragraph("<b>2.2 Project Objectives:</b>", h2_style))
    story.append(Paragraph("• <b>High Availability & Scalability:</b> Implement a microservice per business domain with dynamic load balancing via Spring Cloud Gateway and Netflix Eureka.", bullet_style))
    story.append(Paragraph("• <b>Multi-Factor Authentication & Verification:</b> Support seamless Google OAuth2 single-sign-on alongside mandatory OTP email verification prior to account activation.", bullet_style))
    story.append(Paragraph("• <b>AI Candidate Matching & Screening:</b> Leverage intelligent parsing and screening algorithms to score applicant fit against job requirements in real time.", bullet_style))
    story.append(Paragraph("• <b>Isolated Persistence:</b> Maintain strict database-per-service isolation utilizing dedicated PostgreSQL databases to eliminate cross-domain lock contention.", bullet_style))
    story.append(Paragraph("• <b>Containerized Deployment:</b> Automate container packaging with Google Jib and manage multi-container stack orchestration using Docker Compose.", bullet_style))

    story.append(Spacer(1, 10))

    # Section 3: Microservices System Architecture
    story.append(Paragraph("3. Microservices Architecture & Module Breakdown", h1_style))
    story.append(Paragraph(
        "The platform architecture adheres to the domain-driven microservice paradigm. Every microservice operates independently on dedicated ports with isolated persistence layers, communicating via RESTful APIs and asynchronous Kafka event streams.",
        body_style
    ))

    services_data = [
        [Paragraph("Service Name", table_header), Paragraph("Port", table_header), Paragraph("Database / Tech", table_header), Paragraph("Key Functional Scope", table_header)],
        [Paragraph("API Gateway", table_cell_bold), Paragraph("9000", table_cell), Paragraph("Spring Cloud Gateway", table_cell), Paragraph("Central entrance, reverse proxy, CORS handling, dynamic routing.", table_cell)],
        [Paragraph("User Service", table_cell_bold), Paragraph("9001", table_cell), Paragraph("PostgreSQL (userdb:5433)", table_cell), Paragraph("User auth, Google OAuth2, OTP mail verification, JWT, RBAC.", table_cell)],
        [Paragraph("Company Service", table_cell_bold), Paragraph("9002", table_cell), Paragraph("PostgreSQL (companydb:5434)", table_cell), Paragraph("Employer company profile setup, verification, brand assets.", table_cell)],
        [Paragraph("Job Service", table_cell_bold), Paragraph("9003", table_cell), Paragraph("PostgreSQL (jobdb:5435)", table_cell), Paragraph("Job posting lifecycle, full-text search, filtering & bookmarking.", table_cell)],
        [Paragraph("Application Service", table_cell_bold), Paragraph("9004", table_cell), Paragraph("PostgreSQL (applicationdb:5436)", table_cell), Paragraph("Job application submission, tracking, screening status workflow.", table_cell)],
        [Paragraph("Notification Service", table_cell_bold), Paragraph("9005", table_cell), Paragraph("Apache Kafka Consumer", table_cell), Paragraph("Asynchronous email dispatches & real-time system alerts.", table_cell)],
        [Paragraph("Preference Service", table_cell_bold), Paragraph("9008", table_cell), Paragraph("PostgreSQL (preferencedb:5439)", table_cell), Paragraph("Job seeker preference vectors & recommendation parameters.", table_cell)],
        [Paragraph("Resume Service", table_cell_bold), Paragraph("9009", table_cell), Paragraph("PostgreSQL (resumedb:5440)", table_cell), Paragraph("Live drag-and-drop resume builder, PDF exports, resume parsing.", table_cell)],
        [Paragraph("AI Service", table_cell_bold), Paragraph("9010", table_cell), Paragraph("AI Scoring Engine", table_cell), Paragraph("AI resume-job scoring, candidate match rating, feedback generation.", table_cell)],
        [Paragraph("Config Server", table_cell_bold), Paragraph("8888", table_cell), Paragraph("Git Configuration Repo", table_cell), Paragraph("Centralized environment configuration management (`config-repo`).", table_cell)],
        [Paragraph("Discovery Server", table_cell_bold), Paragraph("8761", table_cell), Paragraph("Netflix Eureka", table_cell), Paragraph("Dynamic service registry, health heartbeats, microservice discovery.", table_cell)]
    ]

    services_table = Table(services_data, colWidths=[1.3*inch, 0.5*inch, 1.8*inch, 3.2*inch])
    services_table.setStyle(TableStyle([
        ('BACKGROUND', (0, 0), (-1, 0), brand_color),
        ('ALIGN', (1, 0), (1, -1), 'CENTER'),
        ('GRID', (0, 0), (-1, -1), 0.5, colors.HexColor("#CBD5E1")),
        ('ROWBACKGROUNDS', (0, 1), (-1, -1), [colors.white, bg_light]),
        ('TOPPADDING', (0, 0), (-1, -1), 4),
        ('BOTTOMPADDING', (0, 0), (-1, -1), 4),
        ('VALIGN', (0, 0), (-1, -1), 'MIDDLE'),
    ]))
    story.append(services_table)

    story.append(PageBreak()) # Clean transition to next page

    # Section 4: Key Technical Innovations & Workflows
    story.append(Paragraph("4. Key Technical Innovations & Workflows", h1_style))
    
    story.append(Paragraph("4.1 Dual Authentication & OTP Security Flow", h2_style))
    story.append(Paragraph(
        "The security layer integrates standard credential-based signup alongside Google OAuth2 Single Sign-On. To prevent fake registrations, newly created Google OAuth2 accounts are assigned an `INACTIVE` status with `verified = false`. The backend instantly generates a 6-digit OTP, mails it via JavaMailSender (Gmail SMTP), and redirects the client browser to a custom frontend verification page (`/verify-otp?email=...`). Upon successful OTP validation, an <b>Access Token (15 mins)</b> and a <b>Refresh Token (7 days)</b> are issued.",
        body_style
    ))

    story.append(Paragraph("4.2 AI-Powered Candidate Matching & Screening", h2_style))
    story.append(Paragraph(
        "The platform features an intelligent AI Service that parses candidate resumes against employer job descriptions. It calculates a compatibility percentage score, identifies skill gaps, and recommends top candidates directly on the employer screening dashboard.",
        body_style
    ))

    story.append(Paragraph("4.3 Live Interactive Resume Builder", h2_style))
    story.append(Paragraph(
        "Candidates can build, edit, and preview professional resumes in real-time. The Resume Service exposes APIs for dynamic section updates (Experience, Education, Skills, Projects) and instant PDF rendering.",
        body_style
    ))

    story.append(Paragraph("4.4 Role-Based Access Control (RBAC) & Router Guards", h2_style))
    story.append(Paragraph(
        "The frontend enforces granular role-based authorization using Redux state and React Router guards (`ProtectedRoute` & `RoleBasedRoute`). Three primary user roles are supported:",
        body_style
    ))
    story.append(Paragraph("• <b>ROLE_JOB_SEEKER:</b> Search jobs, apply, build resumes, track applications, save jobs.", bullet_style))
    story.append(Paragraph("• <b>ROLE_EMPLOYER:</b> Manage company profiles, post/edit jobs, screen applicants, view AI match scores.", bullet_style))
    story.append(Paragraph("• <b>ROLE_ADMIN:</b> Manage users, verify company profiles, monitor platform metrics and job metadata.", bullet_style))

    story.append(Spacer(1, 10))

    # Section 5: Software & Hardware Requirements
    story.append(Paragraph("5. Hardware & Software Requirements", h1_style))

    req_data = [
        [Paragraph("Category", table_header), Paragraph("Specification / Tool Details", table_header)],
        [Paragraph("Operating System", table_cell_bold), Paragraph("Windows 10/11, macOS, or Linux (Ubuntu 22.04 LTS)", table_cell)],
        [Paragraph("Backend Framework", table_cell_bold), Paragraph("Java 17 / JDK 17, Spring Boot 3.4+, Spring Cloud 2023.x", table_cell)],
        [Paragraph("Frontend Framework", table_cell_bold), Paragraph("React 18, Vite, JavaScript (ES6+), Redux Toolkit, Tailwind CSS", table_cell)],
        [Paragraph("Database Engines", table_cell_bold), Paragraph("PostgreSQL 16 (Multi-database setup: 6 independent DB instances)", table_cell)],
        [Paragraph("Message Broker", table_cell_bold), Paragraph("Apache Kafka 7.6.1 & Apache Zookeeper 7.6.1", table_cell)],
        [Paragraph("Containerization", table_cell_bold), Paragraph("Docker Desktop v24+, Docker Compose v2.20+, Google Jib Plugin", table_cell)],
        [Paragraph("Development Tools", table_cell_bold), Paragraph("IntelliJ IDEA / VS Code, Postman, Git, Google Cloud Console (OAuth2 API)", table_cell)],
        [Paragraph("Hardware Requirements", table_cell_bold), Paragraph("Min: 8 GB RAM (16 GB Recommended for Docker Stack), Dual-Core i5/Ryzen 5 CPU", table_cell)]
    ]

    req_table = Table(req_data, colWidths=[1.8*inch, 5.0*inch])
    req_table.setStyle(TableStyle([
        ('BACKGROUND', (0, 0), (-1, 0), primary_color),
        ('GRID', (0, 0), (-1, -1), 0.5, colors.HexColor("#CBD5E1")),
        ('ROWBACKGROUNDS', (0, 1), (-1, -1), [colors.white, bg_light]),
        ('TOPPADDING', (0, 0), (-1, -1), 4),
        ('BOTTOMPADDING', (0, 0), (-1, -1), 4),
        ('VALIGN', (0, 0), (-1, -1), 'MIDDLE'),
    ]))
    story.append(req_table)

    story.append(Spacer(1, 10))

    # Section 6: Future Scope & Conclusion
    story.append(Paragraph("6. Future Scope & Conclusion", h1_style))
    story.append(Paragraph(
        "<b>6.1 Future Enhancements:</b>", h2_style
    ))
    story.append(Paragraph("• <b>Video Interview Integration:</b> WebRTC-based automated video screening and interview scheduling.", bullet_style))
    story.append(Paragraph("• <b>Elasticsearch Job Indexing:</b> Sub-millisecond full-text job search and geo-location distance filtering.", bullet_style))
    story.append(Paragraph("• <b>Kubernetes Deployment (K8s):</b> Helm chart deployment with autoscaling HPA for enterprise scale.", bullet_style))
    
    story.append(Paragraph(
        "<b>6.2 Conclusion:</b><br/>"
        "JobPortal.AI demonstrates a modern, production-grade implementation of a microservices architecture. By decoupling user authentication, job discovery, application management, and AI matching into containerized services, the system guarantees superior uptime, domain isolation, and rapid scalability. The seamless integration of Google OAuth2 and OTP verification delivers high security without sacrificing user experience.",
        body_style
    ))

    # Signature Block
    story.append(Spacer(1, 20))
    sig_data = [
        [Paragraph("<b>Prepared By:</b>", table_cell_bold), Paragraph("<b>Approved By:</b>", table_cell_bold)],
        [Paragraph("Vikas Prajapati<br/>Lead Software Engineer / Student", table_cell), Paragraph("Department of Computer Science & Engineering<br/>Project Evaluation Committee", table_cell)]
    ]
    sig_table = Table(sig_data, colWidths=[3.4*inch, 3.4*inch])
    sig_table.setStyle(TableStyle([
        ('LINEABOVE', (0, 0), (-1, 0), 1, colors.HexColor("#CBD5E1")),
        ('TOPPADDING', (0, 0), (-1, -1), 6),
    ]))
    story.append(sig_table)

    doc.build(story, canvasmaker=NumberedCanvas)
    print(f"Synopsis PDF successfully generated at: {os.path.abspath(filename)}")

if __name__ == "__main__":
    create_synopsis_pdf()
