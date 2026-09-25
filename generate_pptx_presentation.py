import os
import sys
from pptx import Presentation
from pptx.util import Inches, Pt
from pptx.enum.text import PP_ALIGN
from pptx.dml.color import RGBColor
from pptx.enum.shapes import MSO_SHAPE

def create_presentation(filename="AI_Powered_Job_Portal_Noir_Hire_Presentation.pptx"):
    prs = Presentation()
    prs.slide_width = Inches(13.333)
    prs.slide_height = Inches(7.5)
    blank_layout = prs.slide_layouts[6]  # Blank slide

    # Theme Colors
    DARK_BG = RGBColor(15, 23, 42)      # Slate 900
    LIGHT_BG = RGBColor(248, 250, 252)  # Slate 50
    CARD_BG = RGBColor(255, 255, 255)   # White
    SUBTLE_BG = RGBColor(241, 245, 249) # Slate 100
    
    INDIGO = RGBColor(79, 70, 229)      # Indigo 600
    SKY_BLUE = RGBColor(14, 165, 233)   # Sky 500
    TEXT_DARK = RGBColor(30, 41, 59)    # Slate 800
    TEXT_MUTED = RGBColor(100, 116, 139)# Slate 500
    BORDER_COLOR = RGBColor(226, 232, 240) # Slate 200

    def add_header(slide, title_text, category_text="AI POWERED JOB PORTAL (NOIR HIRE)"):
        # Header background banner
        header_box = slide.shapes.add_shape(MSO_SHAPE.RECTANGLE, Inches(0), Inches(0), Inches(13.333), Inches(1.1))
        header_box.fill.solid()
        header_box.fill.fore_color.rgb = DARK_BG
        header_box.line.color.rgb = DARK_BG
        
        # Category tag
        tx = slide.shapes.add_textbox(Inches(0.8), Inches(0.12), Inches(11), Inches(0.3))
        tf = tx.text_frame
        tf.word_wrap = True
        p = tf.paragraphs[0]
        p.text = category_text.upper()
        p.font.name = "Arial"
        p.font.size = Pt(9)
        p.font.bold = True
        p.font.color.rgb = SKY_BLUE

        # Main Title
        tx2 = slide.shapes.add_textbox(Inches(0.8), Inches(0.4), Inches(11), Inches(0.6))
        tf2 = tx2.text_frame
        tf2.word_wrap = True
        p2 = tf2.paragraphs[0]
        p2.text = title_text
        p2.font.name = "Arial"
        p2.font.size = Pt(20)
        p2.font.bold = True
        p2.font.color.rgb = RGBColor(255, 255, 255)

    def add_card(slide, left, top, width, height, bg_color=CARD_BG, border_col=BORDER_COLOR):
        card = slide.shapes.add_shape(MSO_SHAPE.ROUNDED_RECTANGLE, Inches(left), Inches(top), Inches(width), Inches(height))
        card.fill.solid()
        card.fill.fore_color.rgb = bg_color
        card.line.color.rgb = border_col
        card.line.width = Pt(1)
        return card

    # ==========================================
    # SLIDE 1: TITLE SLIDE (COVER)
    # ==========================================
    slide1 = prs.slides.add_slide(blank_layout)
    bg1 = slide1.shapes.add_shape(MSO_SHAPE.RECTANGLE, Inches(0), Inches(0), Inches(13.333), Inches(7.5))
    bg1.fill.solid()
    bg1.fill.fore_color.rgb = DARK_BG
    bg1.line.color.rgb = DARK_BG

    # Accent decorative bar
    bar = slide1.shapes.add_shape(MSO_SHAPE.RECTANGLE, Inches(0.8), Inches(1.8), Inches(0.15), Inches(3.8))
    bar.fill.solid()
    bar.fill.fore_color.rgb = INDIGO
    bar.line.color.rgb = INDIGO

    tx_cover = slide1.shapes.add_textbox(Inches(1.2), Inches(1.8), Inches(11), Inches(3.8))
    tf_cover = tx_cover.text_frame
    tf_cover.word_wrap = True

    p0 = tf_cover.paragraphs[0]
    p0.text = "PROJECT SYNOPSIS & PRESENTATION"
    p0.font.name = "Arial"
    p0.font.size = Pt(12)
    p0.font.bold = True
    p0.font.color.rgb = RGBColor(255, 255, 255)
    p0.space_after = Pt(10)

    p1 = tf_cover.add_paragraph()
    p1.text = "AI Powered Job Portal (Noir Hire)"
    p1.font.name = "Arial"
    p1.font.size = Pt(34)
    p1.font.bold = True
    p1.font.color.rgb = RGBColor(255, 255, 255)
    p1.space_after = Pt(14)

    p2 = tf_cover.add_paragraph()
    p2.text = "Enterprise Distributed Microservices Architecture featuring Google OAuth2 SSO, Gmail SMTP 6-Digit OTP Verification, AI Candidate Skill Match Scoring Engine, and Live Dynamic Resume Builder."
    p2.font.name = "Arial"
    p2.font.size = Pt(13.5)
    p2.font.color.rgb = RGBColor(255, 255, 255)
    p2.space_after = Pt(24)

    p3 = tf_cover.add_paragraph()
    p3.text = "DEVELOPMENT TEAM:  Vikas  |  Vineet  |  Shiv  |  Ajay"
    p3.font.name = "Arial"
    p3.font.size = Pt(13.5)
    p3.font.bold = True
    p3.font.color.rgb = RGBColor(255, 255, 255)

    # ==========================================
    # SLIDE 2: PRESENTATION AGENDA
    # ==========================================
    slide2 = prs.slides.add_slide(blank_layout)
    add_header(slide2, "Presentation & Synopsis Agenda")

    agenda_items = [
        ("01", "Problem Statement", "Monolithic limits, DB lock contention, SPOF, bot spam, screening fatigue."),
        ("02", "Objectives", "10+ Microservices, DB-per-Service, Google OAuth2 + OTP, AI Matcher, Kafka."),
        ("03", "Technology Stack", "Java 17, Spring Boot 3.4+, React 18, PostgreSQL, Kafka, Docker, Jib."),
        ("04", "Literature Survey", "Recruitment evolution, comparison matrix (Indeed/LinkedIn), industry gaps."),
        ("05", "System Workflow / Architecture", "Topology, Gateway routes, 6 DB Schemas, OAuth2+OTP sequence, React UI."),
        ("06", "Expected Outcome", "Sub-100ms SLAs, 99.9% uptime, zero DB locks, societal impact, K8s roadmap."),
        ("07", "References", "Academic papers, OAuth2 RFC 6749, JWT RFC 7519, software specifications.")
    ]

    for idx, (num, title, desc) in enumerate(agenda_items):
        row = idx // 4
        col = idx % 4
        left = 0.8 + col * 2.95
        top = 1.5 + row * 2.7
        
        card = add_card(slide2, left, top, 2.75, 2.4, SUBTLE_BG, BORDER_COLOR)
        
        tx = slide2.shapes.add_textbox(Inches(left + 0.15), Inches(top + 0.15), Inches(2.45), Inches(2.1))
        tf = tx.text_frame
        tf.word_wrap = True
        
        p_num = tf.paragraphs[0]
        p_num.text = num
        p_num.font.name = "Arial"
        p_num.font.size = Pt(20)
        p_num.font.bold = True
        p_num.font.color.rgb = INDIGO
        
        p_t = tf.add_paragraph()
        p_t.text = title
        p_t.font.name = "Arial"
        p_t.font.size = Pt(13)
        p_t.font.bold = True
        p_t.font.color.rgb = TEXT_DARK
        p_t.space_before = Pt(4)
        p_t.space_after = Pt(6)
        
        p_d = tf.add_paragraph()
        p_d.text = desc
        p_d.font.name = "Arial"
        p_d.font.size = Pt(9.5)
        p_d.font.color.rgb = TEXT_MUTED

    # ==========================================
    # SLIDE 3: PROBLEM STATEMENT
    # ==========================================
    slide3 = prs.slides.add_slide(blank_layout)
    add_header(slide3, "1. Problem Statement — Industry Challenges & Monolithic Limitations")

    probs = [
        ("Monolithic DB Lock Contention", "Concurrent transactions across job postings, applications, and search indexing contend for identical PostgreSQL table locks, leading to deadlocks and latency spikes."),
        ("Single Point of Failure (SPOF)", "Uncaught exceptions or memory leaks in secondary components (e.g. mail dispatch) crash the entire monolithic backend, halting login and job applications."),
        ("Unverified Bot Registrations", "Basic single-factor registration allows automated bot signups, polluting recruiter applicant pipelines with fake profiles and consuming storage."),
        ("Manual Screening Fatigue", "Recruiters waste over 70% of initial sourcing cycles manually scanning non-standardized resumes, leading to screening fatigue and lost hiring opportunities."),
        ("Resource Waste & Inflexible Scaling", "Monolithic systems force scaling the entire backend image even if only job search indexing faces peak load, multiplying cloud infrastructure expenses."),
        ("Synchronous Mail Dispatch Delays", "Sending transactional emails synchronously inside HTTP request-response loops introduces 2 to 5 seconds of latency per application submission.")
    ]

    for idx, (title, desc) in enumerate(probs):
        row = idx // 3
        col = idx % 3
        left = 0.8 + col * 3.9
        top = 1.5 + row * 2.7
        
        add_card(slide3, left, top, 3.7, 2.4, CARD_BG, BORDER_COLOR)
        
        tx = slide3.shapes.add_textbox(Inches(left + 0.2), Inches(top + 0.2), Inches(3.3), Inches(2.0))
        tf = tx.text_frame
        tf.word_wrap = True
        
        p_t = tf.paragraphs[0]
        p_t.text = f"• {title}"
        p_t.font.name = "Arial"
        p_t.font.size = Pt(13)
        p_t.font.bold = True
        p_t.font.color.rgb = INDIGO
        p_t.space_after = Pt(8)
        
        p_d = tf.add_paragraph()
        p_d.text = desc
        p_d.font.name = "Arial"
        p_d.font.size = Pt(10)
        p_d.font.color.rgb = TEXT_DARK

    # ==========================================
    # SLIDE 4: OBJECTIVES
    # ==========================================
    slide4 = prs.slides.add_slide(blank_layout)
    add_header(slide4, "2. Objectives — Technical Core Goals of AI Powered Job Portal (Noir Hire)")

    objs = [
        ("10+ Domain Microservices", "Deconstruct recruitment workflows into autonomous Spring Boot microservices (Gateway, User, Job, Company, Application, Resume, Preference, AI, Notification, Eureka, Config)."),
        ("Database-per-Service Isolation", "Provision 6 isolated PostgreSQL 16 database containers (userdb, companydb, jobdb, applicationdb, preferencedb, resumedb) to eliminate database locks."),
        ("Hybrid OAuth2 + Mandatory OTP", "Integrate Google OAuth2 SSO with a mandatory Gmail SMTP 6-digit OTP verification pipeline, assigning INACTIVE status until email is verified."),
        ("AI Candidate Screening Engine", "Develop an automated AI matching engine that evaluates resume skill compatibility and calculates real-time match percentage scores."),
        ("Live Interactive Resume Builder", "Deliver a React drag-and-drop resume builder with instant section updates and dynamic PDF export capability."),
        ("Asynchronous Kafka Pipeline", "Integrate Apache Kafka 7.6.1 and Zookeeper for event-driven notification queuing without blocking HTTP client threads.")
    ]

    for idx, (title, desc) in enumerate(objs):
        row = idx // 3
        col = idx % 3
        left = 0.8 + col * 3.9
        top = 1.5 + row * 2.7
        
        add_card(slide4, left, top, 3.7, 2.4, SUBTLE_BG, BORDER_COLOR)
        
        tx = slide4.shapes.add_textbox(Inches(left + 0.2), Inches(top + 0.2), Inches(3.3), Inches(2.0))
        tf = tx.text_frame
        tf.word_wrap = True
        
        p_t = tf.paragraphs[0]
        p_t.text = title
        p_t.font.name = "Arial"
        p_t.font.size = Pt(13)
        p_t.font.bold = True
        p_t.font.color.rgb = DARK_BG
        p_t.space_after = Pt(8)
        
        p_d = tf.add_paragraph()
        p_d.text = desc
        p_d.font.name = "Arial"
        p_d.font.size = Pt(10)
        p_d.font.color.rgb = TEXT_DARK

    # ==========================================
    # SLIDE 5: TECHNOLOGY STACK (BACKEND & INFRA)
    # ==========================================
    slide5 = prs.slides.add_slide(blank_layout)
    add_header(slide5, "3. Technology Stack — Backend, Database & Infrastructure Architecture")

    tech_backend = [
        ("Java 17 LTS", "Backend Core JDK", "Enterprise LTS execution environment for all microservices."),
        ("Spring Boot 3.4+", "Microservice Framework", "REST controllers, dependency injection, service components."),
        ("Spring Cloud 2023.0", "Cloud Ecosystem", "API Gateway (9000), Eureka Registry (8761), Config Server (8888)."),
        ("Spring Security / OAuth2", "Security Engine", "Google OAuth2 SSO, JWT parsing filter, Security Filter Chain."),
        ("PostgreSQL 16", "Relational Persistence", "6 Dedicated DB containers (userdb, companydb, jobdb, appdb, etc.)."),
        ("Apache Kafka 7.6.1", "Event Streaming", "Asynchronous event topics (user-registered, application-submitted)."),
        ("Docker & Google Jib", "Containerization", "Multi-container Docker Compose stack with daemonless Jib packaging.")
    ]

    for idx, (title, category, desc) in enumerate(tech_backend):
        row = idx // 4
        col = idx % 4
        left = 0.8 + col * 2.95
        top = 1.5 + (row * 2.7)
        
        add_card(slide5, left, top, 2.75, 2.4, CARD_BG, BORDER_COLOR)
        
        tx = slide5.shapes.add_textbox(Inches(left + 0.15), Inches(top + 0.15), Inches(2.45), Inches(2.1))
        tf = tx.text_frame
        tf.word_wrap = True
        
        p_cat = tf.paragraphs[0]
        p_cat.text = category.upper()
        p_cat.font.name = "Arial"
        p_cat.font.size = Pt(8.5)
        p_cat.font.bold = True
        p_cat.font.color.rgb = SKY_BLUE
        
        p_t = tf.add_paragraph()
        p_t.text = title
        p_t.font.name = "Arial"
        p_t.font.size = Pt(13)
        p_t.font.bold = True
        p_t.font.color.rgb = INDIGO
        p_t.space_before = Pt(3)
        p_t.space_after = Pt(6)
        
        p_d = tf.add_paragraph()
        p_d.text = desc
        p_d.font.name = "Arial"
        p_d.font.size = Pt(9.5)
        p_d.font.color.rgb = TEXT_DARK

    # ==========================================
    # SLIDE 6: TECHNOLOGY STACK (FRONTEND & CLIENT)
    # ==========================================
    slide6 = prs.slides.add_slide(blank_layout)
    add_header(slide6, "3. Technology Stack — Frontend React Application & Validation Engine")

    tech_frontend = [
        ("React 18.3", "UI Library", "Modern component hierarchy with dynamic rendering and hooks."),
        ("Vite 5.4", "Build Bundler", "Sub-second Hot Module Replacement (HMR) and optimized build bundles."),
        ("Redux Toolkit 2.2", "State Management", "Global application state, async Thunks (userThunk, jobSlice, appSlice)."),
        ("Tailwind CSS 3.4", "Styling Engine", "Utility-first responsive design, modern dark/light themes."),
        ("Shadcn UI & Lucide", "UI Primitives", "Accessible UI components, dialogs, forms, and custom icons."),
        ("React Hook Form + Zod", "Form Validation", "Schema enforcement for signup, login, OTP verification, password reset."),
        ("React Router v6", "Client Routing", "Role-based route guards (ProtectedRoute, RoleBasedRoute for JobSeeker/Employer/Admin).")
    ]

    for idx, (title, category, desc) in enumerate(tech_frontend):
        row = idx // 4
        col = idx % 4
        left = 0.8 + col * 2.95
        top = 1.5 + (row * 2.7)
        
        add_card(slide6, left, top, 2.75, 2.4, SUBTLE_BG, BORDER_COLOR)
        
        tx = slide6.shapes.add_textbox(Inches(left + 0.15), Inches(top + 0.15), Inches(2.45), Inches(2.1))
        tf = tx.text_frame
        tf.word_wrap = True
        
        p_cat = tf.paragraphs[0]
        p_cat.text = category.upper()
        p_cat.font.name = "Arial"
        p_cat.font.size = Pt(8.5)
        p_cat.font.bold = True
        p_cat.font.color.rgb = INDIGO
        
        p_t = tf.add_paragraph()
        p_t.text = title
        p_t.font.name = "Arial"
        p_t.font.size = Pt(13)
        p_t.font.bold = True
        p_t.font.color.rgb = DARK_BG
        p_t.space_before = Pt(3)
        p_t.space_after = Pt(6)
        
        p_d = tf.add_paragraph()
        p_d.text = desc
        p_d.font.name = "Arial"
        p_d.font.size = Pt(9.5)
        p_d.font.color.rgb = TEXT_DARK

    # ==========================================
    # SLIDE 7: LITERATURE SURVEY — EVOLUTION & MATRIX
    # ==========================================
    slide7 = prs.slides.add_slide(blank_layout)
    add_header(slide7, "4. Literature Survey — Comparative Evaluation Matrix")

    # Paragraph background text
    tx_lit = slide7.shapes.add_textbox(Inches(0.8), Inches(1.3), Inches(11.733), Inches(0.8))
    tf_lit = tx_lit.text_frame
    tf_lit.word_wrap = True
    p_lit = tf_lit.paragraphs[0]
    p_lit.text = "Research literature (Fowler & Lewis, 2014; Newman, 2021) proves that decomposing monolithic software into autonomous microservices eliminates single points of failure. Comparative evaluation against commercial portals highlights key technical advantages:"
    p_lit.font.name = "Arial"
    p_lit.font.size = Pt(10.5)
    p_lit.font.color.rgb = TEXT_DARK

    # Comparative Table
    rows = 7
    cols = 5
    left = Inches(0.8)
    top = Inches(2.2)
    width = Inches(11.733)
    height = Inches(4.8)

    table_shape = slide7.shapes.add_table(rows, cols, left, top, width, height)
    table = table_shape.table

    headers = ["Technical Dimension", "Indeed", "LinkedIn", "Monster", "AI Powered Job Portal (Noir Hire)"]
    for i, h in enumerate(headers):
        cell = table.cell(0, i)
        cell.fill.solid()
        cell.fill.fore_color.rgb = DARK_BG
        p = cell.text_frame.paragraphs[0]
        p.text = h
        p.font.name = "Arial"
        p.font.size = Pt(10)
        p.font.bold = True
        p.font.color.rgb = RGBColor(255, 255, 255)

    matrix_data = [
        ["Architecture Model", "Hybrid Legacy Core", "Microservices Stack", "Monolithic Core", "Microservices (Spring Cloud Framework)"],
        ["Database Pattern", "Centralized Sharded", "Distributed Graph/Doc", "Monolithic Relational", "Database-per-Service (PostgreSQL 16)"],
        ["Authentication", "Standard OAuth2", "Custom SSO / OAuth2", "Basic OAuth", "Google OAuth2 + Mandatory OTP Verification"],
        ["Account Verification", "Email Click Link", "Email / SMS Code", "Basic Link", "Gmail SMTP 6-Digit OTP Verification"],
        ["AI Match Engine", "Keyword Search Based", "ML Vector Embeddings", "Keyword Filtering", "Integrated AI Skill Matcher Engine"],
        ["Resume Creation", "Static File Upload", "Profile Export Only", "Basic Parser", "Live Interactive Drag & Drop Builder + PDF Export"]
    ]

    for row_idx, row_data in enumerate(matrix_data):
        for col_idx, text in enumerate(row_data):
            cell = table.cell(row_idx + 1, col_idx)
            cell.fill.solid()
            if col_idx == 4:
                cell.fill.fore_color.rgb = RGBColor(238, 242, 255) # Light Indigo
            elif row_idx % 2 == 0:
                cell.fill.fore_color.rgb = CARD_BG
            else:
                cell.fill.fore_color.rgb = SUBTLE_BG
                
            p = cell.text_frame.paragraphs[0]
            p.text = text
            p.font.name = "Arial"
            p.font.size = Pt(9.5)
            if col_idx == 0 or col_idx == 4:
                p.font.bold = True
                p.font.color.rgb = INDIGO if col_idx == 4 else DARK_BG
            else:
                p.font.color.rgb = TEXT_DARK

    # ==========================================
    # SLIDE 8: SYSTEM WORKFLOW — TOPOLOGY & CATALOG
    # ==========================================
    slide8 = prs.slides.add_slide(blank_layout)
    add_header(slide8, "5. System Workflow / Architecture — Distributed Topology & Microservices")

    # Topology card
    add_card(slide8, 0.8, 1.4, 11.733, 5.5, CARD_BG, BORDER_COLOR)
    
    tx_top = slide8.shapes.add_textbox(Inches(1.0), Inches(1.5), Inches(11.333), Inches(5.3))
    tf_top = tx_top.text_frame
    tf_top.word_wrap = True

    p = tf_top.paragraphs[0]
    p.text = "MICROSERVICES DOMAIN CATALOG & PORT SPECIFICATIONS"
    p.font.name = "Arial"
    p.font.size = Pt(11)
    p.font.bold = True
    p.font.color.rgb = INDIGO
    p.space_after = Pt(10)

    services_catalog = [
        ("API Gateway (Port 9000)", "Central entry point, reverse proxy, CORS filter, route predicates, JWT routing."),
        ("User Service (Port 9001)", "User auth, Google OAuth2 SSO, Gmail SMTP 6-digit OTP verification, JWT, RBAC."),
        ("Company Service (Port 9002)", "Employer company profile registration, verification status, corporate assets."),
        ("Job Service (Port 9003)", "Job posting lifecycle, full-text search, filtering (skills, location, salary), saved jobs."),
        ("Application Service (Port 9004)", "Job application pipeline, candidate submission tracking, screening status workflow."),
        ("Notification Service (Port 9005)", "Kafka consumer listening to registration/application events, sending emails via SMTP."),
        ("Preference Service (Port 9008)", "Candidate job preference vectors & recommendation criteria."),
        ("Resume Service (Port 9009)", "Live drag & drop resume builder state, section updates, dynamic PDF export engine."),
        ("AI Service (Port 9010)", "AI candidate skill compatibility scoring engine & automated applicant screening."),
        ("Eureka Server (Port 8761) & Config Server (Port 8888)", "Dynamic microservice discovery & centralized Git configuration repository.")
    ]

    for name, desc in services_catalog:
        p_item = tf_top.add_paragraph()
        p_item.text = f"• {name}: {desc}"
        p_item.font.name = "Arial"
        p_item.font.size = Pt(9.5)
        p_item.font.color.rgb = TEXT_DARK
        p_item.space_after = Pt(4)

    # ==========================================
    # SLIDE 9: SYSTEM WORKFLOW — OAUTH2 & OTP FLOW
    # ==========================================
    slide9 = prs.slides.add_slide(blank_layout)
    add_header(slide9, "5. System Workflow / Architecture — Hybrid OAuth2 + Mandatory OTP Verification Flow")

    steps_data = [
        ("1. OAuth2 Request", "User clicks 'Sign up with Google' on Register.jsx. Gateway routes to user-service (9001). User consents on Google."),
        ("2. OAuth2 Callback", "Google redirects back to callback endpoint: /login/oauth2/code/google. OAuth2SuccessHandler intercepts request."),
        ("3. Account Creation", "OAuth2Service extracts email, name, sub. If new user, creates entity with verified = false and status = INACTIVE."),
        ("4. Gmail SMTP OTP", "MailServiceImpl generates 6-digit OTP entity, saves to database, and emails code via Gmail SMTP (MimeMessageHelper)."),
        ("5. Frontend Redirect", "Backend redirects browser to frontend OTP page: http://localhost:5173/verify-otp?email=user@gmail.com."),
        ("6. OTP Validation", "Candidate enters 6-digit code. VerifyOtp.jsx sends POST to /auth/verify-otp. Backend validates code."),
        ("7. JWT Issuance", "JwtProvider sets verified = true, status = ACTIVE, generates 15-min Access & 7-day Refresh Tokens, and logs in user.")
    ]

    for idx, (title, desc) in enumerate(steps_data):
        top = 1.4 + idx * 0.78
        add_card(slide9, 0.8, top, 11.733, 0.7, SUBTLE_BG, BORDER_COLOR)
        
        tx = slide9.shapes.add_textbox(Inches(1.0), Inches(top + 0.1), Inches(11.333), Inches(0.5))
        tf = tx.text_frame
        tf.word_wrap = True
        
        p = tf.paragraphs[0]
        p.text = f"{title}:  {desc}"
        p.font.name = "Arial"
        p.font.size = Pt(10)
        p.font.bold = True
        p.font.color.rgb = DARK_BG

    # ==========================================
    # SLIDE 10: SYSTEM WORKFLOW — LOGICAL AI MATCH BREAKDOWN (Replacing LaTeX formula)
    # ==========================================
    slide10 = prs.slides.add_slide(blank_layout)
    add_header(slide10, "5. System Workflow / Architecture — AI Candidate Skill Matching Engine")

    # Card 1: AI Match Logic Breakdown
    add_card(slide10, 0.8, 1.4, 11.733, 2.5, CARD_BG, BORDER_COLOR)
    tx_ai = slide10.shapes.add_textbox(Inches(1.0), Inches(1.5), Inches(11.333), Inches(2.3))
    tf_ai = tx_ai.text_frame
    tf_ai.word_wrap = True

    p = tf_ai.paragraphs[0]
    p.text = "LOGICAL AI CANDIDATE MATCH SCORING BREAKDOWN"
    p.font.name = "Arial"
    p.font.size = Pt(12)
    p.font.bold = True
    p.font.color.rgb = INDIGO
    p.space_after = Pt(10)

    p1 = tf_ai.add_paragraph()
    p1.text = "The AI Service (Port 9010) evaluates candidate resumes against employer job descriptions using a 3-tier weighted compatibility score:"
    p1.font.name = "Arial"
    p1.font.size = Pt(10.5)
    p1.font.color.rgb = TEXT_DARK
    p1.space_after = Pt(8)

    p2 = tf_ai.add_paragraph()
    p2.text = "• Technical Skill Overlap (60% Weight): Measures exact matching between candidate resume skills and mandatory job requirement skills."
    p2.font.name = "Arial"
    p2.font.size = Pt(10)
    p2.font.color.rgb = TEXT_DARK
    p2.space_after = Pt(4)

    p3 = tf_ai.add_paragraph()
    p3.text = "• Experience Alignment (25% Weight): Compares candidate's total years of experience against the required experience range."
    p3.font.name = "Arial"
    p3.font.size = Pt(10)
    p3.font.color.rgb = TEXT_DARK
    p3.space_after = Pt(4)

    p4 = tf_ai.add_paragraph()
    p4.text = "• Preference Alignment (15% Weight): Evaluates candidate's preferred location, job type (Remote/Onsite), and salary expectations."
    p4.font.name = "Arial"
    p4.font.size = Pt(10)
    p4.font.color.rgb = TEXT_DARK

    # Card 2: Composite Score Output
    add_card(slide10, 0.8, 4.2, 11.733, 2.7, SUBTLE_BG, BORDER_COLOR)
    tx_out = slide10.shapes.add_textbox(Inches(1.0), Inches(4.3), Inches(11.333), Inches(2.5))
    tf_out = tx_out.text_frame
    tf_out.word_wrap = True

    p = tf_out.paragraphs[0]
    p.text = "AUTOMATED SCREENING & RECRUITER DASHBOARD OUTPUT"
    p.font.name = "Arial"
    p.font.size = Pt(12)
    p.font.bold = True
    p.font.color.rgb = DARK_BG
    p.space_after = Pt(10)

    p1 = tf_out.add_paragraph()
    p1.text = "• Real-Time Match Rating: Computes a final 0% to 100% score for each applicant directly upon job submission."
    p1.font.name = "Arial"
    p1.font.size = Pt(10)
    p1.font.color.rgb = TEXT_DARK
    p1.space_after = Pt(4)

    p2 = tf_out.add_paragraph()
    p2.text = "• Employer Candidate Ranking: Recruiter screening page (AIScreening.jsx) automatically ranks applicants by match score."
    p2.font.name = "Arial"
    p2.font.size = Pt(10)
    p2.font.color.rgb = TEXT_DARK
    p2.space_after = Pt(4)

    p3 = tf_out.add_paragraph()
    p3.text = "• Candidate Skill Gap Feedback: Provides job seekers with actionable feedback on missing skills required for their target roles."
    p3.font.name = "Arial"
    p3.font.size = Pt(10)
    p3.font.color.rgb = TEXT_DARK

    # ==========================================
    # SLIDE 11: EXPECTED OUTCOME — SLAS & IMPACT
    # ==========================================
    slide11 = prs.slides.add_slide(blank_layout)
    add_header(slide11, "6. Expected Outcome — Performance SLAs & Societal Impact")

    slas = [
        ("API Gateway Latency", "Sub-100ms average response time via Spring Cloud Gateway dynamic routing."),
        ("Zero DB Deadlocks", "Database-per-service isolation completely eliminates cross-domain table locks."),
        ("99.9% Uptime SLA", "Fault-tolerant microservices prevent cascading single-point-of-failure outages."),
        ("Instant Candidate Ranking", "AI service ranks applicants in real-time (under 2 seconds) upon application submit."),
        ("0ms Client Email Blocking", "Asynchronous Kafka messaging dispatches notifications without HTTP thread delay."),
        ("Bias-Free Sourcing", "Objective skill-vector evaluation mitigates human demographic bias during initial screening.")
    ]

    for idx, (title, desc) in enumerate(slas):
        row = idx // 3
        col = idx % 3
        left = 0.8 + col * 3.9
        top = 1.5 + row * 2.7
        
        add_card(slide11, left, top, 3.7, 2.4, CARD_BG, BORDER_COLOR)
        
        tx = slide11.shapes.add_textbox(Inches(left + 0.2), Inches(top + 0.2), Inches(3.3), Inches(2.0))
        tf = tx.text_frame
        tf.word_wrap = True
        
        p_t = tf.paragraphs[0]
        p_t.text = title
        p_t.font.name = "Arial"
        p_t.font.size = Pt(13)
        p_t.font.bold = True
        p_t.font.color.rgb = INDIGO
        p_t.space_after = Pt(8)
        
        p_d = tf.add_paragraph()
        p_d.text = desc
        p_d.font.name = "Arial"
        p_d.font.size = Pt(10)
        p_d.font.color.rgb = TEXT_DARK

    # ==========================================
    # SLIDE 12: REFERENCES & CONCLUSION
    # ==========================================
    slide12 = prs.slides.add_slide(blank_layout)
    add_header(slide12, "7. References & Academic Citations")

    add_card(slide12, 0.8, 1.4, 11.733, 5.5, SUBTLE_BG, BORDER_COLOR)
    
    tx_ref = slide12.shapes.add_textbox(Inches(1.0), Inches(1.5), Inches(11.333), Inches(5.3))
    tf_ref = tx_ref.text_frame
    tf_ref.word_wrap = True

    p = tf_ref.paragraphs[0]
    p.text = "ACADEMIC PUBLICATIONS & TECHNICAL STANDARDS"
    p.font.name = "Arial"
    p.font.size = Pt(12)
    p.font.bold = True
    p.font.color.rgb = DARK_BG
    p.space_after = Pt(12)

    refs = [
        "1. Fowler, M., & Lewis, J. (2014). Microservices: a definition of this new architectural term. ThoughtWorks Insights.",
        "2. Newman, S. (2021). Building Microservices: Designing Fine-Grained Systems (2nd ed.). O'Reilly Media.",
        "3. Garg, L., et al. (2022). Artificial Intelligence in Human Resource Management: Automated Candidate Screening Algorithms. Journal of Systems Software, 184, 111-125.",
        "4. Hardt, D. (2012). The OAuth 2.0 Authorization Framework. IETF RFC 6749.",
        "5. Jones, M., Bradley, J., & Sakimura, N. (2015). JSON Web Token (JWT). IETF RFC 7519.",
        "6. Spring Cloud Engineering Team. (2024). Spring Cloud Gateway & Netflix Eureka Reference Guide. VMware Tanzu.",
        "7. Apache Software Foundation. (2024). Apache Kafka Distributed Event Streaming Documentation."
    ]

    for r in refs:
        p_r = tf_ref.add_paragraph()
        p_r.text = r
        p_r.font.name = "Arial"
        p_r.font.size = Pt(10.5)
        p_r.font.color.rgb = TEXT_DARK
        p_r.space_after = Pt(8)

    prs.save(filename)
    print(f"Presentation successfully generated at: {os.path.abspath(filename)}")

if __name__ == "__main__":
    create_presentation()
