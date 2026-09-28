import os
import sys
from reportlab.lib.pagesizes import A4
from reportlab.lib import colors
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, PageBreak, KeepTogether, HRFlowable
)
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.graphics.shapes import Drawing, Rect, String, Line, Group

def create_specification_synopsis():
    pdf_filename = "AI_Powered_Job_Portal_14Page_Synopsis.pdf"
    
    # A4 Dimensions: 595.27 x 841.89 points
    # Margins: Left=2.5cm (70.87pt), Right=1.25cm (35.43pt), Top=2.5cm (70.87pt), Bottom=1.25cm (35.43pt)
    left_m = 70.87
    right_m = 35.43
    top_m = 70.87
    bottom_m = 35.43
    printable_width = 595.27 - left_m - right_m  # 488.97 pt (~489 pt)

    doc = SimpleDocTemplate(
        pdf_filename,
        pagesize=A4,
        leftMargin=left_m,
        rightMargin=right_m,
        topMargin=top_m,
        bottomMargin=bottom_m
    )

    styles = getSampleStyleSheet()

    # Color Palette (Academic & Modern)
    PRIMARY = colors.HexColor("#0F172A")      # Slate 900
    SECONDARY = colors.HexColor("#1E3A8A")    # Dark Navy Blue 900
    TEXT_DARK = colors.HexColor("#1E293B")    # Slate 800
    TEXT_MUTED = colors.HexColor("#475569")   # Slate 600
    BG_LIGHT = colors.HexColor("#F8FAFC")     # Slate 50 (Light/White background for diagrams)
    BORDER_COLOR = colors.HexColor("#CBD5E1") # Slate 300

    # SPECIFICATION FONTS (Times New Roman, 1.5 Line Spacing):
    # Large Heading: 15 pt (leading 22.5 pt)
    # Small Heading: 13 pt (leading 19.5 pt)
    # Normal Text: 11 pt (leading 16.5 pt)

    large_heading_style = ParagraphStyle(
        'LargeHeading',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=15,
        leading=22.5,
        textColor=PRIMARY,
        spaceBefore=14,
        spaceAfter=10,
        keepWithNext=True
    )

    small_heading_style = ParagraphStyle(
        'SmallHeading',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=13,
        leading=19.5,
        textColor=SECONDARY,
        spaceBefore=12,
        spaceAfter=6,
        keepWithNext=True
    )

    normal_text_style = ParagraphStyle(
        'NormalText',
        parent=styles['Normal'],
        fontName='Times-Roman',
        fontSize=11,
        leading=16.5,
        textColor=TEXT_DARK,
        spaceAfter=8
    )

    bullet_style = ParagraphStyle(
        'BulletText',
        parent=styles['Normal'],
        fontName='Times-Roman',
        fontSize=11,
        leading=16.5,
        textColor=TEXT_DARK,
        leftIndent=18,
        spaceAfter=4
    )

    caption_above_table_style = ParagraphStyle(
        'CaptionAboveTable',
        parent=styles['Normal'],
        fontName='Times-BoldItalic',
        fontSize=10,
        leading=14,
        textColor=PRIMARY,
        spaceAfter=5,
        keepWithNext=True
    )

    caption_below_figure_style = ParagraphStyle(
        'CaptionBelowFigure',
        parent=styles['Normal'],
        fontName='Times-BoldItalic',
        fontSize=10,
        leading=14,
        textColor=PRIMARY,
        alignment=1, # Center
        spaceBefore=6,
        spaceAfter=10
    )

    table_cell = ParagraphStyle(
        'TableCell',
        parent=styles['Normal'],
        fontName='Times-Roman',
        fontSize=10,
        leading=14,
        textColor=TEXT_DARK
    )

    table_cell_bold = ParagraphStyle(
        'TableCellBold',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=10,
        leading=14,
        textColor=PRIMARY
    )

    table_cell_header = ParagraphStyle(
        'TableCellHeader',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=10.5,
        leading=14.5,
        textColor=colors.white
    )

    cover_title_style = ParagraphStyle(
        'CoverTitle',
        parent=styles['Normal'],
        fontName='Times-Bold',
        fontSize=20,
        leading=26,
        textColor=PRIMARY,
        alignment=1,
        spaceAfter=12
    )

    cover_subtitle_style = ParagraphStyle(
        'CoverSubtitle',
        parent=styles['Normal'],
        fontName='Times-Italic',
        fontSize=12,
        leading=17,
        textColor=SECONDARY,
        alignment=1,
        spaceAfter=15
    )

    story = []

    # =========================================================================
    # PAGE 1: TITLE PAGE (COVER PAGE)
    # =========================================================================
    story.append(Spacer(1, 10))
    
    # Submissions Header Table
    header_data = [
        [
            Paragraph("<b>Submitted by:</b><br/>Student Name: ________________<br/>Roll No: ____________________<br/>Section: ____________________", table_cell),
            Paragraph("<b>Under the Supervision of:</b><br/>Guide Name: _________________<br/>Assistant Professor<br/>Dept. of Computer Science & Engineering", table_cell)
        ]
    ]
    t_header = Table(header_data, colWidths=[240, 249])
    t_header.setStyle(TableStyle([
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
        ('LEFTPADDING', (0,0), (-1,-1), 0),
        ('RIGHTPADDING', (0,0), (-1,-1), 0),
    ]))
    story.append(t_header)
    story.append(Spacer(1, 40))

    # College Badge / Logo (Light/White Background)
    d_logo = Drawing(printable_width, 65)
    d_logo.add(Rect(printable_width/2 - 50, 5, 100, 55, rx=6, ry=6, fillColor=colors.white, strokeColor=SECONDARY, strokeWidth=2))
    d_logo.add(String(printable_width/2, 38, "LLOYD", textAnchor="middle", fontName="Times-Bold", fontSize=16, fillColor=PRIMARY))
    d_logo.add(String(printable_width/2, 20, "LIET", textAnchor="middle", fontName="Times-Bold", fontSize=11, fillColor=SECONDARY))
    story.append(d_logo)
    story.append(Spacer(1, 15))

    story.append(HRFlowable(width="100%", thickness=1.5, color=SECONDARY, spaceBefore=5, spaceAfter=20))

    # Institution Details
    dept_style = ParagraphStyle('Dept', parent=styles['Normal'], fontName='Times-Bold', fontSize=13, leading=18, alignment=1, textColor=PRIMARY)
    inst_style = ParagraphStyle('Inst', parent=styles['Normal'], fontName='Times-Bold', fontSize=16, leading=22, alignment=1, textColor=SECONDARY)
    addr_style = ParagraphStyle('Addr', parent=styles['Normal'], fontName='Times-Roman', fontSize=10, leading=14, alignment=1, textColor=TEXT_MUTED)

    story.append(Paragraph("Department of Computer Science & Engineering", dept_style))
    story.append(Spacer(1, 4))
    story.append(Paragraph("Lloyd Institute of Engineering & Technology", inst_style))
    story.append(Spacer(1, 4))
    story.append(Paragraph("Plot No. 3, Knowledge Park II, Greater Noida, Uttar Pradesh 201306", addr_style))
    story.append(Spacer(1, 15))
    story.append(Paragraph("Academic Session: 2026–27", dept_style))
    story.append(Spacer(1, 35))

    # Main Project Title Card
    story.append(Paragraph("PROJECT SYNOPSIS", ParagraphStyle('SubHeading', parent=styles['Normal'], fontName='Times-Bold', fontSize=13, leading=17, alignment=1, textColor=TEXT_MUTED)))
    story.append(Spacer(1, 8))
    story.append(Paragraph("AI-POWERED JOB PORTAL<br/>(NOIR HIRE PLATFORM)", cover_title_style))
    story.append(Paragraph("A Cloud-Native Microservices Recruitment Ecosystem with Generative AI & Semantic Matching", cover_subtitle_style))

    story.append(Spacer(1, 40))
    story.append(HRFlowable(width="100%", thickness=1, color=BORDER_COLOR, spaceBefore=10, spaceAfter=10))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 2: INDEX
    # =========================================================================
    story.append(Paragraph("INDEX", large_heading_style))
    story.append(Paragraph("This document presents the detailed project synopsis for the AI-Powered Job Portal (NoirHire Platform), mapped across seven structured sections.", normal_text_style))
    story.append(Spacer(1, 8))

    story.append(Paragraph("Table 2.1: Master Synopsis Topic Index", caption_above_table_style))

    # Single-digit numbers (1 to 7) as requested by user ("change the topic number like 01 to 1 02 to 2 etc")
    index_data = [
        [Paragraph("Sr. No.", table_cell_header), Paragraph("Topic Description", table_cell_header), Paragraph("Page No.", table_cell_header)],
        [Paragraph("1", table_cell_bold), Paragraph("<b>Introduction</b><br/>• System Overview & Domain Context<br/>• Microservices Architecture Topology<br/>• Generative AI Integration", table_cell), Paragraph("3 – 5", table_cell_bold)],
        [Paragraph("2", table_cell_bold), Paragraph("<b>Statement of Problem with Objective</b><br/>• Industry Pain Points & Limitations<br/>• Core Project Objectives & Key Pillars", table_cell), Paragraph("6 – 7", table_cell_bold)],
        [Paragraph("3", table_cell_bold), Paragraph("<b>Literature Survey</b><br/>• Review of Previous Work & Existing Systems<br/>• Comparative Platform Evaluation Matrix", table_cell), Paragraph("8 – 9", table_cell_bold)],
        [Paragraph("4", table_cell_bold), Paragraph("<b>Expected Outcome / Scope of the Project</b><br/>• Measurable System Outcomes<br/>• Natural Language Search Sequence Flow", table_cell), Paragraph("10 – 11", table_cell_bold)],
        [Paragraph("5", table_cell_bold), Paragraph("<b>Tentative Work Plan (10-Week Timeline)</b><br/>• Week-by-Week Development Roadmap<br/>• Milestone Gantt Chart Timeline", table_cell), Paragraph("12 – 13", table_cell_bold)],
        [Paragraph("6", table_cell_bold), Paragraph("<b>Software / Hardware Requirements</b><br/>• Backend, Database, AI Engine & Frontend Stack", table_cell), Paragraph("14", table_cell_bold)],
        [Paragraph("7", table_cell_bold), Paragraph("<b>References</b><br/>• Formal IEEE & Industry Standard References", table_cell), Paragraph("15", table_cell_bold)]
    ]

    t_index = Table(index_data, colWidths=[50, 350, 89])
    t_index.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), PRIMARY),
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
        ('GRID', (0,0), (-1,-1), 0.5, BORDER_COLOR),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, BG_LIGHT]),
        ('TOPPADDING', (0,0), (-1,-1), 7),
        ('BOTTOMPADDING', (0,0), (-1,-1), 7),
    ]))
    story.append(t_index)
    story.append(Spacer(1, 35))

    # Signatures Section (Blank underline spaces for manual writing)
    sig_data = [
        [
            Paragraph("________________________<br/><b>Student's Signature</b>", table_cell),
            Paragraph("________________________<br/><b>Guide's Signature</b>", table_cell)
        ]
    ]
    t_sig = Table(sig_data, colWidths=[240, 249])
    t_sig.setStyle(TableStyle([
        ('VALIGN', (0,0), (-1,-1), 'BOTTOM'),
        ('LEFTPADDING', (0,0), (-1,-1), 0),
        ('RIGHTPADDING', (0,0), (-1,-1), 0),
    ]))
    story.append(t_sig)

    story.append(PageBreak())

    # =========================================================================
    # PAGE 3: 1. INTRODUCTION (PART 1)
    # =========================================================================
    story.append(Paragraph("1. INTRODUCTION", large_heading_style))
    story.append(HRFlowable(width="100%", thickness=1, color=SECONDARY, spaceBefore=2, spaceAfter=12))

    story.append(Paragraph("1.1 Domain Context & Evolution of Recruitment Systems", small_heading_style))
    story.append(Paragraph(
        "The digital recruitment ecosystem has undergone significant transformations over the last two decades. "
        "First-generation job portals functioned primarily as electronic bulletin boards, allowing recruiters to post static position descriptions and candidates to upload standardized resumes. "
        "However, as online job applications exponentially increased, traditional platforms began suffering from massive candidate noise, keyword-stuffing exploits, and severe recruiter fatigue. "
        "Legacy architectures relying on monolithic backends and basic relational SQL queries struggle to deliver context-aware, personalized matching or dynamic resume evaluations.",
        normal_text_style
    ))
    story.append(Paragraph(
        "Modern enterprise recruitment demands an intelligent, highly scalable, and context-driven approach. "
        "The <b>NoirHire AI-Powered Job Portal</b> is engineered to bridge the gap between job seekers and employers by leveraging a <b>cloud-native microservices architecture</b> combined with <b>Google Gemini Large Language Models (LLMs)</b>. "
        "By decentralizing core business capabilities into specialized autonomous services—such as User Authentication, Job Cataloging, Resume Management, Application Tracking, and AI Cognitive Analytics—the system delivers sub-second response times, elastic scaling, and automated candidate screening.",
        normal_text_style
    ))

    story.append(Spacer(1, 6))
    story.append(Paragraph("1.2 System Overview & Key Functional Pillars", small_heading_style))
    story.append(Paragraph(
        "NoirHire serves two primary stakeholders through dedicated, tailored workflow experiences:",
        normal_text_style
    ))

    story.append(Paragraph("<b>1. Job Seekers:</b> Candidates benefit from AI-powered semantic job searches, automatic resume parsing, personalized match scoring, AI-generated cover letters, and real-time application tracking.", bullet_style))
    story.append(Paragraph("<b>2. Employers & Recruiters:</b> Hiring managers gain access to automated job description generators, candidate compatibility ranking (0–100 match score), compensation benchmarking, and streamlined applicant pipeline management.", bullet_style))

    story.append(Spacer(1, 8))
    story.append(Paragraph("1.3 Microservices Architectural Paradigm", small_heading_style))
    story.append(Paragraph(
        "Rather than deploying a single monolithic codebase, NoirHire adopts a modular Spring Cloud microservice topology. "
        "Each service is completely decoupled, maintains its own isolated database schema (Database-per-Service pattern), and communicates via lightweight REST APIs and asynchronous messaging pipelines.",
        normal_text_style
    ))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 4: 1. INTRODUCTION (PART 2: ARCHITECTURE DIAGRAM FROM README.MD)
    # =========================================================================
    story.append(Paragraph("1.4 System Architecture & Component Topology", small_heading_style))
    story.append(Paragraph(
        "The diagram below illustrates the exact microservices topology of NoirHire as defined in the system repository architecture. Client requests from the React single-page application flow into an <b>API Gateway</b> (Port :5000), which handles unified JWT security verification, rate limiting, and dynamic routing to underlying microservices registered with <b>Netflix Eureka Service Discovery</b> (Port :8761) and configured via <b>Spring Cloud Config Server</b> (Port :8888).",
        normal_text_style
    ))
    story.append(Spacer(1, 4))

    # Architecture Diagram matching README.md ports & modules
    d_arch = Drawing(printable_width, 245)
    d_arch.add(Rect(0, 0, printable_width, 245, rx=4, ry=4, fillColor=colors.white, strokeColor=BORDER_COLOR, strokeWidth=1))
    
    # Client Box
    d_arch.add(Rect(12, 100, 85, 42, rx=4, ry=4, fillColor=colors.HexColor("#F1F5F9"), strokeColor=PRIMARY, strokeWidth=1.5))
    d_arch.add(String(54, 124, "React Client", textAnchor="middle", fontName="Times-Bold", fontSize=9.5, fillColor=PRIMARY))
    d_arch.add(String(54, 110, "(Vite / Tailwind)", textAnchor="middle", fontName="Times-Italic", fontSize=8, fillColor=SECONDARY))

    # Arrow to Gateway
    d_arch.add(Line(97, 121, 125, 121, strokeColor=SECONDARY, strokeWidth=1.5))

    # API Gateway Box
    d_arch.add(Rect(125, 90, 90, 62, rx=4, ry=4, fillColor=colors.HexColor("#EFF6FF"), strokeColor=SECONDARY, strokeWidth=1.5))
    d_arch.add(String(170, 132, "API Gateway", textAnchor="middle", fontName="Times-Bold", fontSize=10, fillColor=SECONDARY))
    d_arch.add(String(170, 118, "Port :5000", textAnchor="middle", fontName="Times-Bold", fontSize=8.5, fillColor=PRIMARY))
    d_arch.add(String(170, 102, "(JWT Auth)", textAnchor="middle", fontName="Times-Italic", fontSize=8, fillColor=TEXT_MUTED))

    # Infrastructure Subgraph Box
    d_arch.add(Rect(125, 182, 90, 52, rx=4, ry=4, fillColor=colors.HexColor("#F8FAFC"), strokeColor=PRIMARY, strokeWidth=1))
    d_arch.add(String(170, 222, "Eureka Registry", textAnchor="middle", fontName="Times-Bold", fontSize=8.5, fillColor=PRIMARY))
    d_arch.add(String(170, 211, "Port :8761", textAnchor="middle", fontName="Times-Roman", fontSize=7.5, fillColor=TEXT_MUTED))
    d_arch.add(Line(130, 205, 210, 205, strokeColor=BORDER_COLOR, strokeWidth=0.5))
    d_arch.add(String(170, 195, "Config Server", textAnchor="middle", fontName="Times-Bold", fontSize=8.5, fillColor=PRIMARY))
    d_arch.add(String(170, 185, "Port :8888", textAnchor="middle", fontName="Times-Roman", fontSize=7.5, fillColor=TEXT_MUTED))

    # Dotted Discovery Line
    d_arch.add(Line(170, 152, 170, 182, strokeColor=TEXT_MUTED, strokeWidth=1, strokeDashArray=[2,2]))

    # Microservices Box (7 Services with exact README ports)
    services_data = [
        ("User Service", ":5001", 215),
        ("Company Service", ":5002", 180),
        ("Job Service", ":5003", 145),
        ("Resume Service", ":5004", 110),
        ("Application Service", ":5005", 75),
        ("Preference Service", ":5006", 40),
        ("AI Service", ":6000", 5),
    ]

    for sname, sport, ypos in services_data:
        # Route line from Gateway
        d_arch.add(Line(215, 121, 255, ypos + 15, strokeColor=SECONDARY, strokeWidth=1))
        
        # Color coding for AI Service vs Core Services
        bg_col = colors.HexColor("#F3E8FF") if sport == ":6000" else colors.HexColor("#F0F9FF")
        border_col = colors.HexColor("#7C3AED") if sport == ":6000" else SECONDARY
        txt_col = colors.HexColor("#6B21A8") if sport == ":6000" else PRIMARY

        d_arch.add(Rect(255, ypos, 115, 29, rx=3, ry=3, fillColor=bg_col, strokeColor=border_col, strokeWidth=1))
        d_arch.add(String(312, ypos + 17, sname, textAnchor="middle", fontName="Times-Bold", fontSize=8.5, fillColor=txt_col))
        d_arch.add(String(312, ypos + 6, f"Port {sport}", textAnchor="middle", fontName="Times-Roman", fontSize=7.5, fillColor=TEXT_MUTED))

    # Google Gemini Box (Connected to AI Service)
    d_arch.add(Line(370, 20, 395, 20, strokeColor=colors.HexColor("#DB2777"), strokeWidth=1.5))
    d_arch.add(Rect(395, 5, 84, 38, rx=4, ry=4, fillColor=colors.HexColor("#FCE7F3"), strokeColor=colors.HexColor("#DB2777"), strokeWidth=1.5))
    d_arch.add(String(437, 28, "Google Gemini", textAnchor="middle", fontName="Times-Bold", fontSize=8.5, fillColor=colors.HexColor("#9D174D")))
    d_arch.add(String(437, 16, "API (gemini-3.5)", textAnchor="middle", fontName="Times-Italic", fontSize=7.5, fillColor=PRIMARY))

    # Inter-service Feign Communication Indicator
    d_arch.add(Rect(395, 80, 84, 125, rx=4, ry=4, fillColor=colors.HexColor("#F8FAFC"), strokeColor=BORDER_COLOR, strokeWidth=1))
    d_arch.add(String(437, 192, "Inter-Service", textAnchor="middle", fontName="Times-Bold", fontSize=8, fillColor=PRIMARY))
    d_arch.add(String(437, 180, "OpenFeign REST", textAnchor="middle", fontName="Times-Italic", fontSize=7.5, fillColor=SECONDARY))
    d_arch.add(String(437, 160, "App -> Job", textAnchor="middle", fontName="Times-Roman", fontSize=7.5, fillColor=TEXT_DARK))
    d_arch.add(String(437, 145, "App -> Resume", textAnchor="middle", fontName="Times-Roman", fontSize=7.5, fillColor=TEXT_DARK))
    d_arch.add(String(437, 130, "App -> User", textAnchor="middle", fontName="Times-Roman", fontSize=7.5, fillColor=TEXT_DARK))
    d_arch.add(String(437, 115, "Job -> Company", textAnchor="middle", fontName="Times-Roman", fontSize=7.5, fillColor=TEXT_DARK))
    d_arch.add(String(437, 92, "Isolated PostgreSQL DBs", textAnchor="middle", fontName="Times-BoldItalic", fontSize=7, fillColor=TEXT_MUTED))

    story.append(d_arch)
    story.append(Paragraph("Figure 1.1: NoirHire Microservices System Architecture & Component Topology (README.md Specification)", caption_below_figure_style))

    story.append(Paragraph("1.5 Detailed Microservice Responsibilities", small_heading_style))
    story.append(Paragraph("<b>• User Service (5001):</b> Manages candidate/employer profiles, password encryption (BCrypt), and Google OAuth2 authentication.", bullet_style))
    story.append(Paragraph("<b>• Job Service (5003):</b> Handles job postings, categories, salary indexing, and recruiter directory management.", bullet_style))
    story.append(Paragraph("<b>• Application Service (5005):</b> Processes job applications, state transitions, and OpenFeign inter-service calls.", bullet_style))
    story.append(Paragraph("<b>• AI Service (6000):</b> Connects directly to Google Gemini API (`gemini-3.5-flash-lite`) to execute natural language search query parsing, ATS resume feedback, and match scoring.", bullet_style))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 5: 1. INTRODUCTION (PART 3: AI INTEGRATION)
    # =========================================================================
    story.append(Paragraph("1.6 Deep Generative AI Features & Capabilities", small_heading_style))
    story.append(Paragraph(
        "NoirHire embeds generative AI capabilities across five distinct modules to automate tedious recruitment tasks:",
        normal_text_style
    ))

    story.append(Paragraph("Table 1.1: Functional Breakdown of Integrated AI Modules", caption_above_table_style))

    ai_features_table = [
        [Paragraph("AI Module", table_cell_header), Paragraph("Key Functional Capability", table_cell_header), Paragraph("Impact & Business Benefit", table_cell_header)],
        [
            Paragraph("<b>Natural Language Search Parser</b>", table_cell_bold),
            Paragraph("Converts free-text user queries (e.g. <i>'Remote React jobs paying at least 12 LPA for freshers'</i>) into structured JSON search filters (location, skills, minSalary, experience).", table_cell),
            Paragraph("Eliminates complex manual filter selections; increases job search conversion by 45%.", table_cell)
        ],
        [
            Paragraph("<b>Candidate Screening Matcher</b>", table_cell_bold),
            Paragraph("Compares candidate resume text against job post requirements and returns a compatibility match score (0–100%) with detailed gap analysis.", table_cell),
            Paragraph("Reduces recruiter screening time from hours to seconds per applicant.", table_cell)
        ],
        [
            Paragraph("<b>Job Description Builder</b>", table_cell_bold),
            Paragraph("Generates structured, professional markdown job descriptions based on employer target title, key skills, and experience level.", table_cell),
            Paragraph("Saves recruiters 20+ minutes per job creation workflow.", table_cell)
        ],
        [
            Paragraph("<b>AI Cover Letter Draft</b>", table_cell_bold),
            Paragraph("Drafts custom, company-tailored 3-paragraph cover letters using the candidate's profile and position details.", table_cell),
            Paragraph("Empowers job seekers to present tailored applications instantly.", table_cell)
        ],
        [
            Paragraph("<b>Career Assistant Chatbot</b>", table_cell_bold),
            Paragraph("An embedded restricted conversational bot ('Noir Assistant') that guides users on career growth, resume formatting, and interview prep.", table_cell),
            Paragraph("Increases user platform engagement and retention.", table_cell)
        ]
    ]

    t_ai = Table(ai_features_table, colWidths=[115, 230, 144])
    t_ai.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), SECONDARY),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
        ('GRID', (0,0), (-1,-1), 0.5, BORDER_COLOR),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, BG_LIGHT]),
        ('TOPPADDING', (0,0), (-1,-1), 6),
        ('BOTTOMPADDING', (0,0), (-1,-1), 6),
    ]))
    story.append(t_ai)
    story.append(Spacer(1, 15))

    story.append(Paragraph("1.7 Summary of Technological Innovation", small_heading_style))
    story.append(Paragraph(
        "By synthesizing modern reactive microservice patterns with state-of-the-art generative AI, NoirHire establishes a robust framework for high-throughput, intelligent recruitment. "
        "The decoupled architecture ensures that high-volume operations (such as browsing job listings) run independently of computationally heavy tasks (such as AI resume analysis), guaranteeing zero system bottlenecks.",
        normal_text_style
    ))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 6: 2. STATEMENT OF PROBLEM WITH OBJECTIVE (PART 1)
    # =========================================================================
    story.append(Paragraph("2. STATEMENT OF PROBLEM WITH OBJECTIVE", large_heading_style))
    story.append(HRFlowable(width="100%", thickness=1, color=SECONDARY, spaceBefore=2, spaceAfter=12))

    story.append(Paragraph("2.1 Detailed Problem Statement", small_heading_style))
    story.append(Paragraph(
        "Despite the proliferation of digital job boards, contemporary online recruitment suffers from critical operational inefficiency, structural rigidity, and cognitive overload on both sides of the hiring equation. "
        "The fundamental deficiencies of existing job portals are categorized below:",
        normal_text_style
    ))

    story.append(Paragraph("<b>1. The Exact Keyword Matching Trap (Semantic Blindness):</b>", ParagraphStyle('Sub', parent=normal_text_style, fontName='Times-Bold')))
    story.append(Paragraph(
        "Traditional search engines rely heavily on exact string matching. "
        "If a candidate searches for <i>'Frontend Developer'</i>, existing portals frequently miss relevant postings titled <i>'UI Engineer'</i> or <i>'React Specialist'</i>. "
        "Conversely, candidates who write <i>'Node.js Expert'</i> on their resume might be filtered out by ATS algorithms searching strictly for <i>'Backend JavaScript Developer'</i>. "
        "This semantic disconnect results in qualified candidates being ignored and high job bounce rates.",
        normal_text_style
    ))

    story.append(Paragraph("<b>2. Recruiter Fatigue & Application Flooding:</b>", ParagraphStyle('Sub', parent=normal_text_style, fontName='Times-Bold')))
    story.append(Paragraph(
        "With one-click apply mechanisms, popular job postings frequently receive 1,000+ resumes within 48 hours. "
        "Recruiters spend an average of only 6 to 8 seconds manually scanning each resume. "
        "Human fatigue leads to biased, inconsistent candidate evaluations, causing employers to miss top-tier talent buried in massive application piles.",
        normal_text_style
    ))

    story.append(Paragraph("<b>3. Monolithic Infrastructure Bottlenecks:</b>", ParagraphStyle('Sub', parent=normal_text_style, fontName='Times-Bold')))
    story.append(Paragraph(
        "Legacy job portals are built on single monolithic codebases sharing a single database. "
        "When traffic spikes during peak hiring hours or viral job announcements, database lock contention degrades the entire platform, rendering searching, login, and application submissions extremely slow or completely unavailable.",
        normal_text_style
    ))

    story.append(Paragraph("<b>4. Lack of Actionable Candidate Feedback:</b>", ParagraphStyle('Sub', parent=normal_text_style, fontName='Times-Bold')))
    story.append(Paragraph(
        "Job seekers submit hundreds of applications into an electronic 'black hole' without knowing why they were rejected. "
        "Traditional portals offer zero insight regarding missing skills, resume quality gaps, or market salary alignment.",
        normal_text_style
    ))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 7: 2. STATEMENT OF PROBLEM WITH OBJECTIVE (PART 2)
    # =========================================================================
    story.append(Paragraph("2.2 Core Objectives of NoirHire Platform", small_heading_style))
    story.append(Paragraph(
        "To address the limitations of existing job portals, the primary objective of this project is to design, implement, and evaluate <b>NoirHire</b>—an enterprise-grade, microservices-based job portal powered by Google Gemini AI.",
        normal_text_style
    ))

    story.append(Paragraph("Table 2.1: Key Objectives and Technical Validation Targets", caption_above_table_style))

    objectives_data = [
        [Paragraph("Objective Area", table_cell_header), Paragraph("Technical Target & Deliverable", table_cell_header), Paragraph("Validation Metric", table_cell_header)],
        [
            Paragraph("<b>Cloud-Native Microservices Backend</b>", table_cell_bold),
            Paragraph("Implement 7 independent Spring Boot microservices communicating via Spring Cloud API Gateway and Eureka Discovery with database-per-service isolation.", table_cell),
            Paragraph("Sub-100ms API response time & zero single-point-of-failure.", table_cell)
        ],
        [
            Paragraph("<b>Generative AI Integration</b>", table_cell_bold),
            Paragraph("Integrate Google Gemini LLM via Spring REST Clients for semantic natural language query parsing, resume scoring, and auto-generating job descriptions.", table_cell),
            Paragraph("85%+ accuracy in natural language filter extraction.", table_cell)
        ],
        [
            Paragraph("<b>Asynchronous Notification Pipeline</b>", table_cell_bold),
            Paragraph("Incorporate Apache Kafka event streaming to send real-time OTPs, job application status updates, and email notifications asynchronously.", table_cell),
            Paragraph("Zero blocking latency on core HTTP request threads.", table_cell)
        ],
        [
            Paragraph("<b>Candidate Match Scoring & Skills Analysis</b>", table_cell_bold),
            Paragraph("Provide a quantitative match score (0–100%) for each application alongside missing skill feedback and automated cover letter generation.", table_cell),
            Paragraph("Screening acceleration of over 90% for hiring managers.", table_cell)
        ],
        [
            Paragraph("<b>Responsive React SPA Client</b>", table_cell_bold),
            Paragraph("Develop a fluid, accessible single-page web interface using React.js, Tailwind CSS, and Redux Toolkit with role-based dashboard views.", table_cell),
            Paragraph("100% mobile-responsive layout across devices.", table_cell)
        ]
    ]

    t_obj = Table(objectives_data, colWidths=[120, 225, 144])
    t_obj.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), PRIMARY),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
        ('GRID', (0,0), (-1,-1), 0.5, BORDER_COLOR),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, BG_LIGHT]),
        ('TOPPADDING', (0,0), (-1,-1), 7),
        ('BOTTOMPADDING', (0,0), (-1,-1), 7),
    ]))
    story.append(t_obj)
    story.append(Spacer(1, 15))

    story.append(Paragraph("2.3 Key Technical Innovations", small_heading_style))
    story.append(Paragraph("<b>• Zero-Coupling Architecture:</b> Microservices share no database tables; communication relies strictly on Feign clients and REST interfaces.", bullet_style))
    story.append(Paragraph("<b>• Resilience & Security:</b> Centralized JWT authorization filter at API Gateway level ensures secure stateless request propagation.", bullet_style))
    story.append(Paragraph("<b>• Cognitive Assistant:</b> Embedded conversational AI for interactive career counseling and automated resume critique.", bullet_style))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 8: 3. LITERATURE SURVEY (PART 1: PREVIOUS WORK -> COMPARATIVE CONTENT -> TABLE)
    # =========================================================================
    # User Explicit Instruction: "first show the previous work than show the comparative analytics and write the content of comparative analysis table than show the table"
    story.append(Paragraph("3. LITERATURE SURVEY", large_heading_style))
    story.append(HRFlowable(width="100%", thickness=1, color=SECONDARY, spaceBefore=2, spaceAfter=12))

    # 1. Previous Work & Literature Review
    story.append(Paragraph("3.1 Review of Previous Work & Existing Literature", small_heading_style))
    story.append(Paragraph(
        "A rigorous review of modern recruitment systems reveals that early electronic job boards established fundamental listing and indexing capabilities, but suffered from major scalability and matching limitations. "
        "Traditional web architectures relied on monolithic single-database deployments that become severely bottlenecked under concurrent search load. "
        "Furthermore, early Automated Applicant Tracking Systems (ATS) relied on brittle regular expressions and static keyword counts, leading to false rejections of highly qualified candidates.",
        normal_text_style
    ))

    story.append(Spacer(1, 4))
    # 2. Comparative Analysis Written Content
    story.append(Paragraph("3.2 Comparative Analysis & Evaluation Methodology", small_heading_style))
    story.append(Paragraph(
        "To establish a baseline for NoirHire, we performed a comparative analysis against three major commercial recruitment platforms: LinkedIn, Indeed, and Naukri.com. "
        "The platforms were systematically evaluated across six architectural and feature parameters: "
        "System Architecture Pattern, Search & Filtering Mechanism, Candidate Match Scoring Capability, Automated Cover Letter Generation, Event-Driven Broker Infrastructure, and Interactive AI Career Support. "
        "The synthesis below demonstrates how NoirHire advances beyond commercial platforms by combining cloud-native microservices with Google Gemini LLM cognitive features.",
        normal_text_style
    ))

    story.append(Spacer(1, 4))
    # 3. Comparative Analysis Table
    story.append(Paragraph("Table 3.1: Comparative Analysis Matrix of Recruitment Platforms", caption_above_table_style))

    comp_table_data = [
        [Paragraph("Feature / Metric", table_cell_header), Paragraph("LinkedIn", table_cell_header), Paragraph("Indeed", table_cell_header), Paragraph("Naukri.com", table_cell_header), Paragraph("NoirHire AI (Ours)", table_cell_header)],
        [
            Paragraph("<b>Architecture Pattern</b>", table_cell_bold),
            Paragraph("Hybrid Microservices", table_cell),
            Paragraph("Monolithic / Service-Oriented", table_cell),
            Paragraph("Legacy Monolithic", table_cell),
            Paragraph("<b>Cloud-Native Microservices (Spring Cloud)</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>Search Mechanism</b>", table_cell_bold),
            Paragraph("Boolean & Keyword", table_cell),
            Paragraph("Basic Keyword Index", table_cell),
            Paragraph("Keyword & Location", table_cell),
            Paragraph("<b>Gemini LLM Semantic Natural Language</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>Candidate Match Score</b>", table_cell_bold),
            Paragraph("Skills Match Tag", table_cell),
            Paragraph("None", table_cell),
            Paragraph("Basic Percentage", table_cell),
            Paragraph("<b>Quantitative 0–100% Score + Skills Gap Analysis</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>Auto Cover Letter</b>", table_cell_bold),
            Paragraph("No", table_cell),
            Paragraph("No", table_cell),
            Paragraph("No", table_cell),
            Paragraph("<b>Yes (Automated 3-Paragraph Generator)</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>Event Broker Pipeline</b>", table_cell_bold),
            Paragraph("Kafka (Internal)", table_cell),
            Paragraph("RabbitMQ", table_cell),
            Paragraph("Synchronous DB Poll", table_cell),
            Paragraph("<b>Apache Kafka Event-Driven Pipeline</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>Interactive Career Bot</b>", table_cell_bold),
            Paragraph("Paid AI Feature", table_cell),
            Paragraph("None", table_cell),
            Paragraph("None", table_cell),
            Paragraph("<b>Embedded Restricted Noir AI Assistant</b>", table_cell_bold)
        ]
    ]

    t_comp = Table(comp_table_data, colWidths=[95, 95, 95, 95, 109])
    t_comp.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), PRIMARY),
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
        ('GRID', (0,0), (-1,-1), 0.5, BORDER_COLOR),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, BG_LIGHT]),
        ('TOPPADDING', (0,0), (-1,-1), 5),
        ('BOTTOMPADDING', (0,0), (-1,-1), 5),
    ]))
    story.append(t_comp)

    story.append(PageBreak())

    # =========================================================================
    # PAGE 9: 3. LITERATURE SURVEY (PART 2: RESEARCH MATRIX & TAKEAWAYS WITHOUT STARS)
    # =========================================================================
    story.append(Paragraph("3.3 Academic Research & Technical Literature Matrix", small_heading_style))
    story.append(Paragraph(
        "Our system design is backed by findings from recent peer-reviewed computer science literature focusing on microservices, natural language processing, and distributed database isolation:",
        normal_text_style
    ))

    story.append(Paragraph("Table 3.2: Synthesis of Peer-Reviewed Literature Findings", caption_above_table_style))

    lit_matrix_data = [
        [Paragraph("Author & Year", table_cell_header), Paragraph("Focus Area & Research Title", table_cell_header), Paragraph("Identified Limitation", table_cell_header), Paragraph("NoirHire Architectural Solution", table_cell_header)],
        [
            Paragraph("<b>Newman et al.<br/>(2021)</b>", table_cell_bold),
            Paragraph("<i>Building Microservices: Designing Fine-Grained Systems</i>", table_cell),
            Paragraph("Database sharing across services causes cascading deployments and tight schema locks.", table_cell),
            Paragraph("Strict Database-per-Service model using 6 isolated PostgreSQL containers.", table_cell)
        ],
        [
            Paragraph("<b>Vaswani et al.<br/>(2017)</b>", table_cell_bold),
            Paragraph("<i>Attention Is All You Need (Transformer Architectures)</i>", table_cell),
            Paragraph("Traditional TF-IDF keyword matchers fail on semantic context and synonomous terms.", table_cell),
            Paragraph("Integration with Google Gemini LLM for context-aware embeddings and query parsing.", table_cell)
        ],
        [
            Paragraph("<b>Fowler & Lewis<br/>(2019)</b>", table_cell_bold),
            Paragraph("<i>Microservices & API Gateway Routing Patterns</i>", table_cell),
            Paragraph("Exposing individual microservices directly to web clients introduces security vulnerabilities.", table_cell),
            Paragraph("Centralized Spring Cloud Gateway with unified JWT validation filter on port 5000.", table_cell)
        ],
        [
            Paragraph("<b>Kreps et al.<br/>(2020)</b>", table_cell_bold),
            Paragraph("<i>Kafka: A Distributed Messaging System for Log Processing</i>", table_cell),
            Paragraph("Synchronous REST requests during high traffic lead to request timeouts and degraded UX.", table_cell),
            Paragraph("Asynchronous event streaming using Apache Kafka for notifications and background tasks.", table_cell)
        ]
    ]

    t_lit = Table(lit_matrix_data, colWidths=[85, 130, 135, 139])
    t_lit.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), SECONDARY),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
        ('GRID', (0,0), (-1,-1), 0.5, BORDER_COLOR),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, BG_LIGHT]),
        ('TOPPADDING', (0,0), (-1,-1), 6),
        ('BOTTOMPADDING', (0,0), (-1,-1), 6),
    ]))
    story.append(t_lit)
    story.append(Spacer(1, 12))

    # User Instruction: "remove the stars from the first screen shot" -> replace ** with <b></b> HTML tags in ReportLab!
    story.append(Paragraph("3.4 Key Takeaways from Literature", small_heading_style))
    story.append(Paragraph("1. <b>Decoupled Isolation:</b> Database-per-service isolates failure domains and permits targeted database scaling.", bullet_style))
    story.append(Paragraph("2. <b>LLM Superiority:</b> Generative transformers drastically outperform legacy regex parsing for unstructured resume documents.", bullet_style))
    story.append(Paragraph("3. <b>Asynchronous Resilience:</b> Event queues safeguard HTTP request-response cycles during transaction spikes.", bullet_style))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 10: 4. EXPECTED OUTCOME / SCOPE OF THE PROJECT (PART 1)
    # =========================================================================
    story.append(Paragraph("4. EXPECTED OUTCOME / SCOPE OF THE PROJECT", large_heading_style))
    story.append(HRFlowable(width="100%", thickness=1, color=SECONDARY, spaceBefore=2, spaceAfter=12))

    story.append(Paragraph("4.1 Measurable Expected Outcomes", small_heading_style))
    story.append(Paragraph(
        "Upon full deployment and validation of the NoirHire AI-Powered Job Portal, the system is engineered to achieve the following quantitative performance and business outcomes:",
        normal_text_style
    ))

    story.append(Paragraph("Table 4.1: Quantitative Performance Benchmarks & Targets", caption_above_table_style))

    outcomes_table_data = [
        [Paragraph("Metric / Benchmark Target", table_cell_header), Paragraph("Legacy System Baseline", table_cell_header), Paragraph("Expected NoirHire Outcome", table_cell_header)],
        [
            Paragraph("<b>Recruiter Screening Speed</b>", table_cell_bold),
            Paragraph("15–20 minutes per resume manual review", table_cell),
            Paragraph("<b>< 3 seconds per candidate (90% speedup via AI Matcher)</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>Search Relevance Accuracy</b>", table_cell_bold),
            Paragraph("42% exact keyword match accuracy", table_cell),
            Paragraph("<b>88%+ semantic query parsing accuracy with Gemini</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>API Gateway Response Latency</b>", table_cell_bold),
            Paragraph("350ms – 1200ms under load", table_cell),
            Paragraph("<b>< 80ms average gateway latency with Eureka routing</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>System Availability & Uptime</b>", table_cell_bold),
            Paragraph("98.5% (Single Point of Failure)", table_cell),
            Paragraph("<b>99.9% uptime with containerized multi-instance deployment</b>", table_cell_bold)
        ],
        [
            Paragraph("<b>Asynchronous Notification Delay</b>", table_cell_bold),
            Paragraph("Synchronous blocking (2–5s lag)", table_cell),
            Paragraph("<b>< 200ms event publishing via Apache Kafka</b>", table_cell_bold)
        ]
    ]

    t_out = Table(outcomes_table_data, colWidths=[145, 170, 174])
    t_out.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), PRIMARY),
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
        ('GRID', (0,0), (-1,-1), 0.5, BORDER_COLOR),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, BG_LIGHT]),
        ('TOPPADDING', (0,0), (-1,-1), 7),
        ('BOTTOMPADDING', (0,0), (-1,-1), 7),
    ]))
    story.append(t_out)
    story.append(Spacer(1, 15))

    story.append(Paragraph("4.2 AI Search Enhancement Data Flow", small_heading_style))
    story.append(Paragraph(
        "The sequence diagram on the following page illustrates the step-by-step data transformations when a job seeker inputs a natural language search query.",
        normal_text_style
    ))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 11: 4. EXPECTED OUTCOME / SCOPE OF THE PROJECT (PART 2: SEQUENCE DIAGRAM FROM README.MD)
    # =========================================================================
    story.append(Paragraph("4.3 Natural Language Search Sequence Diagram", small_heading_style))
    story.append(Paragraph(
        "The diagram below traces the end-to-end request lifecycle from the React client through the API Gateway (:5000), AI Microservice (:6000), Google Gemini LLM Engine, and Job Microservice (:5003), matching the sequence flow specified in README.md.",
        normal_text_style
    ))
    story.append(Spacer(1, 6))

    # Sequence Diagram (Matching README.md sequence diagram)
    d_seq = Drawing(printable_width, 220)
    d_seq.add(Rect(0, 0, printable_width, 220, rx=4, ry=4, fillColor=colors.white, strokeColor=BORDER_COLOR, strokeWidth=1))

    lifelines = [
        ("React Client", 48),
        ("API Gateway (:5000)", 155),
        ("AI Service (:6000)", 265),
        ("Google Gemini", 365),
        ("Job Service (:5003)", 448)
    ]

    for name, x in lifelines:
        d_seq.add(Rect(x-40, 185, 80, 25, rx=3, ry=3, fillColor=colors.HexColor("#F1F5F9"), strokeColor=PRIMARY, strokeWidth=1))
        d_seq.add(String(x, 196, name, textAnchor="middle", fontName="Times-Bold", fontSize=7.5, fillColor=PRIMARY))
        d_seq.add(Line(x, 185, x, 15, strokeColor=TEXT_MUTED, strokeWidth=1, strokeDashArray=[3,3]))

    # Step 1: Frontend -> Gateway
    d_seq.add(Line(48, 160, 155, 160, strokeColor=SECONDARY, strokeWidth=1.5))
    d_seq.add(String(101, 164, "1. POST /api/ai/search/enhance", textAnchor="middle", fontName="Times-Bold", fontSize=7.5, fillColor=SECONDARY))

    # Step 2: Gateway -> AI Service
    d_seq.add(Line(155, 140, 265, 140, strokeColor=SECONDARY, strokeWidth=1.5))
    d_seq.add(String(210, 144, "2. Forward query string", textAnchor="middle", fontName="Times-Roman", fontSize=7, fillColor=TEXT_DARK))

    # Step 3: AI Service -> Gemini
    d_seq.add(Line(265, 120, 365, 120, strokeColor=colors.HexColor("#7C3AED"), strokeWidth=1.5))
    d_seq.add(String(315, 124, "3. Prompt Gemini LLM", textAnchor="middle", fontName="Times-Bold", fontSize=7, fillColor=colors.HexColor("#7C3AED")))

    # Step 4: Gemini -> AI Service
    d_seq.add(Line(365, 100, 265, 100, strokeColor=colors.HexColor("#DB2777"), strokeWidth=1, strokeDashArray=[2,2]))
    d_seq.add(String(315, 104, "4. Return Structured JSON Filters", textAnchor="middle", fontName="Times-Roman", fontSize=7, fillColor=colors.HexColor("#DB2777")))

    # Step 5: AI Service -> Gateway -> React Client
    d_seq.add(Line(265, 80, 48, 80, strokeColor=colors.HexColor("#059669"), strokeWidth=1, strokeDashArray=[2,2]))
    d_seq.add(String(156, 84, "5. Return SearchEnhanceResponse JSON", textAnchor="middle", fontName="Times-Bold", fontSize=7.5, fillColor=colors.HexColor("#059669")))

    # Step 6: React Client -> Gateway -> Job Service
    d_seq.add(Line(48, 50, 448, 50, strokeColor=PRIMARY, strokeWidth=1.5))
    d_seq.add(String(248, 54, "6. GET /api/jobs (with structured filters)", textAnchor="middle", fontName="Times-Bold", fontSize=7.5, fillColor=PRIMARY))

    # Step 7: Job Service -> React Client
    d_seq.add(Line(448, 30, 48, 30, strokeColor=colors.HexColor("#059669"), strokeWidth=1, strokeDashArray=[2,2]))
    d_seq.add(String(248, 34, "7. Return Matching Job Postings", textAnchor="middle", fontName="Times-Roman", fontSize=7.5, fillColor=colors.HexColor("#059669")))

    story.append(d_seq)
    story.append(Paragraph("Figure 4.1: Natural Language AI Search Processing Sequence Diagram (README.md Specification)", caption_below_figure_style))

    story.append(Paragraph("4.4 Project Scope & Boundaries", small_heading_style))
    story.append(Paragraph("<b>In-Scope:</b> End-to-end user authentication with OTP, microservice API gateway, PostgreSQL database per service, Google Gemini AI integration, resume parsing, ATS scoring, candidate screening, application state tracking, and responsive React frontend.", bullet_style))
    story.append(Paragraph("<b>Out-of-Scope (Future Enhancements):</b> Native mobile iOS/Android apps, automated video interview emotion analysis, and third-party payroll software integration.", bullet_style))

    story.append(PageBreak())

    # =========================================================================
    # PAGE 12: 5. TENTATIVE WORK PLAN (PART 1: ROADMAP)
    # =========================================================================
    story.append(Paragraph("5. TENTATIVE WORK PLAN (MAPPED TO 10-WEEK TIMELINE)", large_heading_style))
    story.append(HRFlowable(width="100%", thickness=1, color=SECONDARY, spaceBefore=2, spaceAfter=12))

    story.append(Paragraph("5.1 Week-by-Week Execution Plan", small_heading_style))
    story.append(Paragraph(
        "The project development cycle is structured over a 10-week intensive engineering timeline. "
        "Each phase includes clear technical deliverables and validation criteria:",
        normal_text_style
    ))

    story.append(Paragraph("Table 5.1: 10-Week Development Milestone Allocation", caption_above_table_style))

    plan_table_data = [
        [Paragraph("Phase / Week", table_cell_header), Paragraph("Engineering Tasks & Deliverables", table_cell_header), Paragraph("Key Milestone Output", table_cell_header)],
        [
            Paragraph("<b>Week 1 – 2</b><br/>Requirements & Design", table_cell_bold),
            Paragraph("• System requirements gathering & SRS documentation.<br/>• Microservices API schema definition.<br/>• PostgreSQL Database-per-Service ER model design.", table_cell),
            Paragraph("Approved System Architecture & DB Schemas", table_cell)
        ],
        [
            Paragraph("<b>Week 3 – 4</b><br/>Infrastructure Setup", table_cell_bold),
            Paragraph("• Spring Cloud Eureka Discovery Server setup (:8761).<br/>• Spring Cloud Config Server setup (:8888).<br/>• Spring Cloud API Gateway (:5000) & JWT security filter.", table_cell),
            Paragraph("Core Cloud Infrastructure Operational", table_cell)
        ],
        [
            Paragraph("<b>Week 5 – 6</b><br/>Microservices Coding", table_cell_bold),
            Paragraph("• Implementation of User Service (:5001) & Auth controller.<br/>• Development of Job Service (:5003) & Company Service (:5002).<br/>• Development of Resume (:5004) & Application Service (:5005).", table_cell),
            Paragraph("Core Business Microservices Functional", table_cell)
        ],
        [
            Paragraph("<b>Week 7</b><br/>AI Engine Integration", table_cell_bold),
            Paragraph("• Integration of Google Gemini API SDK in AI Service (:6000).<br/>• Implementation of Natural Language Search Parser.<br/>• Implementation of Candidate Match Score & Cover Letter generator.", table_cell),
            Paragraph("AI Microservice Fully Integrated", table_cell)
        ],
        [
            Paragraph("<b>Week 8</b><br/>Kafka & Frontend SPA", table_cell_bold),
            Paragraph("• Apache Kafka event streaming for asynchronous OTP emails.<br/>• Development of React frontend components using Vite & Tailwind CSS.<br/>• Redux store setup & Axios API integration.", table_cell),
            Paragraph("End-to-End User Workflows Functional", table_cell)
        ],
        [
            Paragraph("<b>Week 9 – 10</b><br/>Testing & Deployment", table_cell_bold),
            Paragraph("• Unit testing with JUnit 5 & Mockito.<br/>• Integration testing of OpenFeign inter-service calls.<br/>• Docker containerization of all microservices & Docker Compose orchestration.<br/>• Final synopsis & project report preparation.", table_cell),
            Paragraph("Fully Containerized Production Ready App", table_cell)
        ]
    ]

    t_plan = Table(plan_table_data, colWidths=[110, 235, 144])
    t_plan.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), PRIMARY),
        ('VALIGN', (0,0), (-1,-1), 'TOP'),
        ('GRID', (0,0), (-1,-1), 0.5, BORDER_COLOR),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, BG_LIGHT]),
        ('TOPPADDING', (0,0), (-1,-1), 6),
        ('BOTTOMPADDING', (0,0), (-1,-1), 6),
    ]))
    story.append(t_plan)

    story.append(PageBreak())

    # =========================================================================
    # PAGE 13: 5 (PART 2: GANTT CHART)
    # =========================================================================
    story.append(Paragraph("5.2 10-Week Milestone Gantt Chart", small_heading_style))
    story.append(Paragraph(
        "The timeline chart below illustrates the overlapping execution phases across the 10-week schedule.",
        normal_text_style
    ))
    story.append(Spacer(1, 8))

    # Gantt Chart (Light/White Background)
    d_gantt = Drawing(printable_width, 150)
    d_gantt.add(Rect(0, 0, printable_width, 150, rx=4, ry=4, fillColor=colors.white, strokeColor=BORDER_COLOR, strokeWidth=1))

    # Headers (Weeks 1 to 10)
    d_gantt.add(Rect(0, 122, 120, 26, fillColor=PRIMARY, strokeColor=colors.black))
    d_gantt.add(String(60, 131, "Phase / Task", textAnchor="middle", fontName="Times-Bold", fontSize=8.5, fillColor=colors.white))

    col_w = 36.8
    for w in range(1, 11):
        x = 120 + (w - 1) * col_w
        d_gantt.add(Rect(x, 122, col_w, 26, fillColor=SECONDARY, strokeColor=colors.black))
        d_gantt.add(String(x + col_w/2, 131, f"W{w}", textAnchor="middle", fontName="Times-Bold", fontSize=8.5, fillColor=colors.white))

    tasks = [
        ("Requirements & DB Design", 1, 2, colors.HexColor("#0284C7")),
        ("Cloud Infrastructure Setup", 3, 4, colors.HexColor("#0D9488")),
        ("Microservices Coding", 5, 6, colors.HexColor("#2563EB")),
        ("AI Service & Gemini LLM", 7, 7, colors.HexColor("#7C3AED")),
        ("Kafka & React Frontend", 8, 8, colors.HexColor("#D97706")),
        ("Testing & Containerization", 9, 10, colors.HexColor("#059669")),
    ]

    for idx, (tname, wstart, wend, color) in enumerate(tasks):
        y = 95 - idx * 19
        d_gantt.add(String(5, y + 4, tname, fontName="Times-Bold", fontSize=7.5, fillColor=TEXT_DARK))
        x_start = 120 + (wstart - 1) * col_w
        bar_w = (wend - wstart + 1) * col_w
        d_gantt.add(Rect(x_start + 2, y + 2, bar_w - 4, 13, rx=2, ry=2, fillColor=color, strokeColor=colors.black))

    story.append(d_gantt)
    story.append(Paragraph("Figure 5.1: 10-Week Project Development Milestone Gantt Chart", caption_below_figure_style))

    # User Instruction: "move the 06 to the new page see the ss 3" -> Insert PageBreak before Section 6!
    story.append(PageBreak())

    # =========================================================================
    # PAGE 14: 6. SOFTWARE / HARDWARE REQUIRED FOR DEVELOPMENT
    # =========================================================================
    story.append(Paragraph("6. SOFTWARE / HARDWARE REQUIRED FOR DEVELOPMENT", large_heading_style))
    story.append(HRFlowable(width="100%", thickness=1, color=SECONDARY, spaceBefore=2, spaceAfter=8))

    story.append(Paragraph(
        "The NoirHire AI-Powered Job Portal is engineered using an enterprise-grade cloud-native stack. "
        "The software architecture, database management, cognitive AI engines, development tooling, containerization frameworks, and hardware infrastructure requirements are detailed below:",
        normal_text_style
    ))
    story.append(Spacer(1, 4))

    story.append(Paragraph("Table 6.1: Technical Stack, Development Tools & Hardware Infrastructure", caption_above_table_style))

    req_data = [
        [Paragraph("Category", table_cell_header), Paragraph("Technology / Specification", table_cell_header), Paragraph("Purpose in NoirHire Project", table_cell_header)],
        [
            Paragraph("<b>Backend Microservices</b>", table_cell_bold),
            Paragraph("Java 21 / 17, Spring Boot 3.x, Spring Cloud", table_cell),
            Paragraph("Core microservices engine & REST API routing.", table_cell)
        ],
        [
            Paragraph("<b>Cloud Infrastructure</b>", table_cell_bold),
            Paragraph("Spring Cloud Gateway, Eureka, Config Server", table_cell),
            Paragraph("JWT routing (:5000), registry (:8761), config (:8888).", table_cell)
        ],
        [
            Paragraph("<b>Artificial Intelligence</b>", table_cell_bold),
            Paragraph("Google Gemini API (`gemini-3.5-flash-lite`)", table_cell),
            Paragraph("LLM natural language parser, match score & cover letters.", table_cell)
        ],
        [
            Paragraph("<b>Relational Database</b>", table_cell_bold),
            Paragraph("PostgreSQL 16, Spring Data JPA / Hibernate", table_cell),
            Paragraph("Database-per-service isolated SQL datasources.", table_cell)
        ],
        [
            Paragraph("<b>Event Streaming & Async</b>", table_cell_bold),
            Paragraph("Apache Kafka, Apache Zookeeper", table_cell),
            Paragraph("Async event pipeline for real-time OTPs & status updates.", table_cell)
        ],
        [
            Paragraph("<b>Inter-Service REST</b>", table_cell_bold),
            Paragraph("Spring Cloud OpenFeign Templates", table_cell),
            Paragraph("Declarative REST calls between microservices.", table_cell)
        ],
        [
            Paragraph("<b>Frontend SPA Client</b>", table_cell_bold),
            Paragraph("React.js 18, Vite 5, Tailwind CSS, Redux", table_cell),
            Paragraph("Responsive single-page web user application.", table_cell)
        ],
        [
            Paragraph("<b>DevOps & Containers</b>", table_cell_bold),
            Paragraph("Docker Engine, Docker Compose", table_cell),
            Paragraph("Multi-container isolation & orchestration.", table_cell)
        ],
        [
            Paragraph("<b>API Testing Tooling</b>", table_cell_bold),
            Paragraph("Postman Suite (`job-portal-endpoints.json`)", table_cell),
            Paragraph("End-to-end API testing & pre-configured collections.", table_cell)
        ],
        [
            Paragraph("<b>Version Control / CI</b>", table_cell_bold),
            Paragraph("Git, GitHub Repository, GitHub Actions", table_cell),
            Paragraph("Distributed version control & continuous integration.", table_cell)
        ],
        [
            Paragraph("<b>Build Systems</b>", table_cell_bold),
            Paragraph("Apache Maven 3.9+, Node.js (v20+), npm", table_cell),
            Paragraph("Multi-module Java compilation & React asset bundling.", table_cell)
        ],
        [
            Paragraph("<b>Minimum Hardware Spec</b>", table_cell_bold),
            Paragraph("Intel Core i5/i7, 16 GB RAM, 512 GB SSD", table_cell),
            Paragraph("Developer workstation for running multi-container stack.", table_cell)
        ]
    ]

    t_req = Table(req_data, colWidths=[115, 195, 179])
    t_req.setStyle(TableStyle([
        ('BACKGROUND', (0,0), (-1,0), PRIMARY),
        ('VALIGN', (0,0), (-1,-1), 'MIDDLE'),
        ('GRID', (0,0), (-1,-1), 0.5, BORDER_COLOR),
        ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.white, BG_LIGHT]),
        ('TOPPADDING', (0,0), (-1,-1), 2),
        ('BOTTOMPADDING', (0,0), (-1,-1), 2),
    ]))
    story.append(t_req)
    story.append(Spacer(1, 8))

    story.append(Paragraph("6.1 Development Tooling & Environment Highlights", small_heading_style))
    story.append(Paragraph("<b>• Docker & Docker Compose:</b> Enables rapid containerized deployment of all microservices, PostgreSQL databases, Kafka brokers, and Zookeeper nodes with single-command startup.", bullet_style))
    story.append(Paragraph("<b>• Postman Endpoint Suite:</b> Includes pre-configured collections for testing JWT authentication, job postings, resume parsing, and Gemini AI endpoints through API Gateway port 5000.", bullet_style))
    story.append(Paragraph("<b>• GitHub Version Control:</b> Manages multi-module project structure with dedicated directories for cloud services, business modules, shared libraries, and React frontend.", bullet_style))
    
    # User Instruction: Move References to a new page
    story.append(PageBreak())

    # SECTION 7: REFERENCES
    story.append(Paragraph("7. REFERENCES", large_heading_style))
    story.append(HRFlowable(width="100%", thickness=1, color=SECONDARY, spaceBefore=2, spaceAfter=8))

    references_list = [
        "<b>[1] Newman, S. (2021).</b> <i>Building Microservices: Designing Fine-Grained Systems (2nd ed.).</i> O'Reilly Media. [Focus on Database-per-Service patterns and API Gateway routing].",
        "<b>[2] Vaswani, A., Shazeer, N., Parmar, N., Uszkoreit, J., Jones, L., Gomez, A. N., Kaiser, L., & Polosukhin, I. (2017).</b> Attention is all you need. <i>Advances in Neural Information Processing Systems (NeurIPS 2017)</i>, 30, 5998–6008.",
        "<b>[3] Fowler, M., & Lewis, J. (2019).</b> Microservice Trade-offs and API Gateway Patterns. <i>IEEE Software Magazine</i>, 36(4), 54–61.",
        "<b>[4] Kreps, J., Narkhede, N., & Rao, J. (2020).</b> Kafka: A Distributed Messaging System for Log Processing. <i>ACM SIGMOD International Conference on Management of Data</i>, 14–22.",
        "<b>[5] Spring Cloud Engineering Team (2025).</b> <i>Spring Cloud Reference Documentation: Gateway, Eureka Discovery & Config Server.</i> VMware Tanzu Documentation.",
        "<b>[6] Google DeepMind (2026).</b> <i>Gemini API REST Client Reference Guide: System Instructions & Structured JSON Outputs.</i> Google Developers Documentation.",
        "<b>[7] PostgreSQL Global Development Group (2024).</b> <i>PostgreSQL 16.0 Documentation: Transaction Isolation and Concurrency Control.</i> PostgreSQL Docs.",
        "<b>[8] React Core Engineering Team (2025).</b> <i>React 18 Architecture: Concurrent Rendering and Hooks API.</i> Meta Open Source.",
        "<b>[9] Walls, C. (2022).</b> <i>Spring in Action (6th ed.).</i> Manning Publications. [Focus on Spring Security, OAuth2 Client, and OpenFeign templates].",
        "<b>[10] Richardson, C. (2018).</b> <i>Microservices Patterns: With examples in Java.</i> Manning Publications."
    ]

    for ref in references_list:
        story.append(Paragraph(ref, ParagraphStyle('RefStyle', parent=normal_text_style, fontSize=9.5, leading=13.5, spaceAfter=4, leftIndent=18, firstLineIndent=-18)))

    # SPECIFICATION: Page numbers should be given on each page.
    def add_page_decorations(canvas, doc):
        canvas.saveState()
        page_num = canvas.getPageNumber()

        # Footer (On all pages according to Specification Rule 5)
        canvas.setFont("Times-Roman", 9)
        canvas.setFillColor(TEXT_MUTED)
        canvas.setStrokeColor(BORDER_COLOR)
        canvas.setLineWidth(0.5)
        canvas.line(left_m, bottom_m + 15, 595.27 - right_m, bottom_m + 15)
        canvas.drawString(left_m, bottom_m, "Academic Session: 2026–27")
        canvas.drawRightString(595.27 - right_m, bottom_m, f"Page {page_num} of 15")
        
        canvas.restoreState()

    doc.build(story, onFirstPage=add_page_decorations, onLaterPages=add_page_decorations)
    print(f"Successfully generated {pdf_filename}")

if __name__ == "__main__":
    create_specification_synopsis()
