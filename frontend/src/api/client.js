// Microservice base URLs
const SERVICES = {
  USER: 'http://localhost:5001',
  COMPANY: 'http://localhost:5002',
  JOB: 'http://localhost:5003',
  RESUME: 'http://localhost:5004',
};

// Local storage keys
const AUTH_KEY = 'noir_job_auth';

// Helper to get auth headers
const getHeaders = (contentType = 'application/json') => {
  const headers = {};
  if (contentType) {
    headers['Content-Type'] = contentType;
  }
  
  try {
    const authData = localStorage.getItem(AUTH_KEY);
    if (authData) {
      const session = JSON.parse(authData);
      if (session.user) {
        headers['X-User-Id'] = session.user.id.toString();
        headers['X-User-Email'] = session.user.email;
        if (session.jwt) {
          headers['Authorization'] = `Bearer ${session.jwt}`;
        }
      }
    }
  } catch (e) {
    console.error('Failed to parse auth data', e);
  }
  
  return headers;
};

// Generic fetch wrapper with automatic mock fallback
async function request(service, path, options = {}, mockFallback = null) {
  const url = `${SERVICES[service]}${path}`;
  const defaultHeaders = getHeaders(
    options.body instanceof FormData ? null : 'application/json'
  );
  
  const config = {
    ...options,
    headers: {
      ...defaultHeaders,
      ...options.headers,
    },
  };

  try {
    const response = await fetch(url, config);
    if (!response.ok) {
      const errText = await response.text();
      let errMsg = `Request failed with status ${response.status}`;
      try {
        const errJson = JSON.parse(errText);
        errMsg = errJson.message || errMsg;
      } catch (_) {}
      throw new Error(errMsg);
    }
    return await response.json();
  } catch (error) {
    console.warn(`API call failed to ${url}. Error: ${error.message}. Checking mock fallback...`);
    if (mockFallback) {
      // Add slight delay to simulate network latency
      await new Promise(r => setTimeout(r, 400));
      return typeof mockFallback === 'function' ? mockFallback() : mockFallback;
    }
    throw error;
  }
}

// ==========================================
// MOCK DATA STORE (For fallback mode)
// ==========================================
const mockStore = {
  users: [
    { id: 1, fullName: 'John Doe', email: 'john@example.com', role: 'ROLE_CANDIDATE', phone: '1234567890' },
    { id: 2, fullName: 'Employer Dave', email: 'employer@company.com', role: 'ROLE_EMPLOYER', phone: '9876543210' }
  ],
  companies: [
    {
      id: 1,
      name: 'Noir Technologies',
      description: 'AI-driven product and software solutions company working on state-of-the-art agentic tools.',
      website: 'https://noirtech.io',
      logoUrl: 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=200&h=200&fit=crop',
      type: 'PRODUCT',
      industry: 'IT',
      status: 'VERIFIED',
      ownerId: 2,
    }
  ],
  jobs: [
    {
      id: 1,
      title: 'Senior AI Engineer',
      description: 'We are seeking a talented Senior AI Engineer to join our Noir Labs team to build autonomous agentic workflows and design vector database architectures.',
      requirements: 'Experience with LLMs, prompt engineering, LangChain, vector stores (PGVector, Pinecone), and Python.',
      responsibilities: 'Design and run code execution environments, tune models for low-latency coding tasks, and design API systems.',
      benefits: 'Competitive equity, remote work flexibility, premium workspace, health insurance.',
      companyId: 1,
      company: { id: 1, name: 'Noir Technologies', logoUrl: 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=200&h=200&fit=crop' },
      employerId: 2,
      category: { id: 1, name: 'Artificial Intelligence' },
      skills: [{ id: 1, name: 'Python' }, { id: 2, name: 'LLMs' }, { id: 3, name: 'Vector DBs' }],
      tags: [{ id: 1, name: 'Remote' }, { id: 2, name: 'Hot Job' }],
      location: { address: 'Connaught Place', city: 'New Delhi', state: 'Delhi', country: 'India', zip: '110001' },
      salaryRange: { minSalary: 120000, maxSalary: 180000 },
      jobType: 'FULL_TIME',
      workMode: 'REMOTE',
      experienceLevel: 'SENIOR',
      status: 'ACTIVE',
      openings: 3,
      createdAt: new Date().toISOString(),
    },
    {
      id: 2,
      title: 'React JS Developer',
      description: 'Join us to construct state-of-the-art visual experiences for our AI platforms. You will design glassmorphic dashboards and high-speed search filters.',
      requirements: '3+ years of React development, master of HTML/CSS/JS, and knowledge of state management patterns.',
      responsibilities: 'Collaborate with AI designers, construct fluid micro-animations, and connect REST API services.',
      benefits: 'Flexible work hours, hybrid model, learning allowance.',
      companyId: 1,
      company: { id: 1, name: 'Noir Technologies', logoUrl: 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=200&h=200&fit=crop' },
      employerId: 2,
      category: { id: 2, name: 'Frontend Engineering' },
      skills: [{ id: 4, name: 'React' }, { id: 5, name: 'JavaScript' }, { id: 6, name: 'CSS' }],
      tags: [{ id: 3, name: 'Hybrid' }],
      location: { address: 'Sector 62', city: 'Noida', state: 'Uttar Pradesh', country: 'India', zip: '201301' },
      salaryRange: { minSalary: 60000, maxSalary: 95000 },
      jobType: 'FULL_TIME',
      workMode: 'HYBRID',
      experienceLevel: 'MID_LEVEL',
      status: 'ACTIVE',
      openings: 2,
      createdAt: new Date().toISOString(),
    }
  ],
  resumes: [
    {
      id: 1,
      candidateId: 1,
      title: 'Main Professional Resume',
      template: 'PROFESSIONAL',
      visibility: 'PUBLIC',
      isDefault: true,
      summary: 'Passionate software engineer specializing in interactive frontend experiences and AI-driven interfaces. Over 3 years of experience writing clean, scalable code.',
      personalInfo: {
        firstName: 'John',
        lastName: 'Doe',
        headLine: 'Full Stack Engineer | React Specialist',
        email: 'john@example.com',
        phone: '1234567890',
        city: 'New Delhi',
        country: 'India',
        linkedinUrl: 'https://linkedin.com/in/johndoe',
        githubUrl: 'https://github.com/johndoe',
        portfolioUrl: 'https://johndoe.dev',
      },
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    }
  ],
  categories: [
    { id: 1, name: 'Artificial Intelligence' },
    { id: 2, name: 'Frontend Engineering' },
    { id: 3, name: 'Backend Engineering' },
    { id: 4, name: 'DevOps & Cloud' }
  ],
  skills: [
    { id: 1, name: 'Python' },
    { id: 2, name: 'LLMs' },
    { id: 3, name: 'Vector DBs' },
    { id: 4, name: 'React' },
    { id: 5, name: 'JavaScript' },
    { id: 6, name: 'CSS' }
  ],
  tags: [
    { id: 1, name: 'Remote' },
    { id: 2, name: 'Hot Job' },
    { id: 3, name: 'Hybrid' },
    { id: 4, name: 'Immediate Joiner' }
  ]
};

// ==========================================
// API CLIENT IMPLEMENTATION
// ==========================================
export const api = {
  // Auth & User endpoints
  auth: {
    signup: async (fullName, email, password, role, phone) => {
      return request('USER', '/auth/signup', {
        method: 'POST',
        body: JSON.stringify({ fullName, email, password, role, phone }),
      }, () => {
        const newUser = { id: Date.now(), fullName, email, role, phone };
        mockStore.users.push(newUser);
        const jwt = 'mock-jwt-token-string-' + Date.now();
        const session = { jwt, user: newUser };
        localStorage.setItem(AUTH_KEY, JSON.stringify(session));
        return { title: `Welcome: ${fullName}`, message: 'Register successfully', jwt, user: newUser };
      });
    },
    
    login: async (email, password) => {
      return request('USER', '/auth/login', {
        method: 'POST',
        body: JSON.stringify({ email, password }),
      }, () => {
        const user = mockStore.users.find(u => u.email === email);
        if (!user) {
          throw new Error('User not found with email: ' + email);
        }
        const jwt = 'mock-jwt-token-string-' + Date.now();
        const session = { jwt, user };
        localStorage.setItem(AUTH_KEY, JSON.stringify(session));
        return { title: `Welcome back: ${user.fullName}`, message: 'Login successfully', jwt, user };
      });
    },

    logout: () => {
      localStorage.removeItem(AUTH_KEY);
    },

    getCurrentSession: () => {
      try {
        const data = localStorage.getItem(AUTH_KEY);
        return data ? JSON.parse(data) : null;
      } catch (e) {
        return null;
      }
    }
  },

  user: {
    getProfile: async (email) => {
      return request('USER', '/api/users/profile', {
        headers: { 'X-User-Email': email }
      }, () => {
        const user = mockStore.users.find(u => u.email === email) || mockStore.users[0];
        return user;
      });
    },

    updateProfile: async (email, data) => {
      return request('USER', '/api/users/profile', {
        method: 'PUT',
        headers: { 'X-User-Email': email },
        body: JSON.stringify(data),
      }, () => {
        const idx = mockStore.users.findIndex(u => u.email === email);
        if (idx !== -1) {
          mockStore.users[idx] = { ...mockStore.users[idx], ...data };
          // Update local session
          const session = api.auth.getCurrentSession();
          if (session && session.user.email === email) {
            session.user = mockStore.users[idx];
            localStorage.setItem(AUTH_KEY, JSON.stringify(session));
          }
          return mockStore.users[idx];
        }
        throw new Error('User not found');
      });
    }
  },

  // Company endpoints
  company: {
    create: async (data) => {
      return request('COMPANY', '/api/companies', {
        method: 'POST',
        body: JSON.stringify(data),
      }, () => {
        const owner = api.auth.getCurrentSession()?.user;
        const newCompany = {
          id: mockStore.companies.length + 1,
          ...data,
          status: 'PENDING',
          ownerId: owner?.id || 1,
        };
        mockStore.companies.push(newCompany);
        return newCompany;
      });
    },

    getById: async (id) => {
      return request('COMPANY', `/api/companies/${id}`, {}, () => {
        const company = mockStore.companies.find(c => c.id === parseInt(id));
        if (!company) throw new Error('Company not found');
        return company;
      });
    },

    getMyCompany: async () => {
      return request('COMPANY', '/api/companies/my', {}, () => {
        const owner = api.auth.getCurrentSession()?.user;
        const company = mockStore.companies.find(c => c.ownerId === owner?.id);
        if (!company) throw new Error('You do not own a company profile yet');
        return company;
      });
    },

    getAll: async (params = {}) => {
      const query = new URLSearchParams(params).toString();
      return request('COMPANY', `/api/companies?${query}`, {}, () => {
        return mockStore.companies;
      });
    },

    update: async (id, data) => {
      return request('COMPANY', `/api/companies/${id}`, {
        method: 'PUT',
        body: JSON.stringify(data),
      }, () => {
        const idx = mockStore.companies.findIndex(c => c.id === parseInt(id));
        if (idx !== -1) {
          mockStore.companies[idx] = { ...mockStore.companies[idx], ...data };
          return mockStore.companies[idx];
        }
        throw new Error('Company not found');
      });
    }
  },

  // Job endpoints
  job: {
    create: async (data) => {
      return request('JOB', '/api/jobs', {
        method: 'POST',
        body: JSON.stringify(data),
      }, () => {
        const owner = api.auth.getCurrentSession()?.user;
        const company = mockStore.companies.find(c => c.ownerId === owner?.id) || mockStore.companies[0];
        
        const cat = mockStore.categories.find(c => c.id === data.categoryId) || { id: 1, name: 'Technology' };
        const reqSkills = data.skillIds ? mockStore.skills.filter(s => data.skillIds.includes(s.id)) : [];
        const reqTags = data.tagIds ? mockStore.tags.filter(t => data.tagIds.includes(t.id)) : [];

        const newJob = {
          id: mockStore.jobs.length + 1,
          title: data.title,
          description: data.description,
          requirements: data.requirements,
          responsibilities: data.responsibilities || 'Build clean services.',
          benefits: data.benefits,
          companyId: company.id,
          company: company,
          employerId: owner?.id || 2,
          category: cat,
          skills: reqSkills,
          tags: reqTags,
          location: {
            address: data.address,
            city: data.city,
            state: data.state,
            country: data.country,
            zip: data.zipCode
          },
          salaryRange: {
            minSalary: data.minSalary,
            maxSalary: data.maxSalary
          },
          jobType: data.jobType,
          workMode: data.workMode,
          experienceLevel: data.experienceLevel,
          status: 'DRAFT',
          openings: data.openings || 1,
          createdAt: new Date().toISOString(),
        };
        mockStore.jobs.push(newJob);
        return newJob;
      });
    },

    getById: async (id) => {
      return request('JOB', `/api/jobs/${id}`, {}, () => {
        const job = mockStore.jobs.find(j => j.id === parseInt(id));
        if (!job) throw new Error('Job not found');
        return job;
      });
    },

    search: async (params = {}) => {
      return request('JOB', '/api/jobs', {}, () => {
        let list = [...mockStore.jobs];
        
        if (params.keyword) {
          const kw = params.keyword.toLowerCase();
          list = list.filter(j => 
            j.title.toLowerCase().includes(kw) || 
            j.description.toLowerCase().includes(kw)
          );
        }
        
        if (params.location) {
          const loc = params.location.toLowerCase();
          list = list.filter(j => 
            j.location.city.toLowerCase().includes(loc) ||
            j.location.country.toLowerCase().includes(loc)
          );
        }
        
        if (params.jobType) {
          list = list.filter(j => j.jobType === params.jobType);
        }
        
        if (params.workMode) {
          list = list.filter(j => j.workMode === params.workMode);
        }
        
        return list;
      });
    },

    getByCompany: async (companyId) => {
      return request('JOB', `/api/jobs/company/${companyId}`, {}, () => {
        return mockStore.jobs.filter(j => j.companyId === parseInt(companyId));
      });
    },

    publish: async (jobId) => {
      return request('JOB', `/api/jobs/${jobId}/publish`, { method: 'PATCH' }, () => {
        const job = mockStore.jobs.find(j => j.id === parseInt(jobId));
        if (job) {
          job.status = 'ACTIVE';
          return job;
        }
        throw new Error('Job not found');
      });
    },

    close: async (jobId) => {
      return request('JOB', `/api/jobs/${jobId}/close`, { method: 'PATCH' }, () => {
        const job = mockStore.jobs.find(j => j.id === parseInt(jobId));
        if (job) {
          job.status = 'CLOSED';
          return job;
        }
        throw new Error('Job not found');
      });
    },

    delete: async (jobId) => {
      return request('JOB', `/api/jobs/${jobId}`, { method: 'DELETE' }, () => {
        const idx = mockStore.jobs.findIndex(j => j.id === parseInt(jobId));
        if (idx !== -1) {
          mockStore.jobs.splice(idx, 1);
          return { message: 'Job deleted successfully', success: true };
        }
        throw new Error('Job not found');
      });
    },

    // Auxiliary resources
    getCategories: async () => {
      return request('JOB', '/api/jobs/categories', {}, () => mockStore.categories);
    },
    getSkills: async () => {
      return request('JOB', '/api/jobs/skills', {}, () => mockStore.skills);
    },
    getTags: async () => {
      return request('JOB', '/api/jobs/tags', {}, () => mockStore.tags);
    }
  },

  // Resume endpoints
  resume: {
    create: async (title, template = 'PROFESSIONAL', visibility = 'PRIVATE') => {
      return request('RESUME', '/api/resumes', {
        method: 'POST',
        body: JSON.stringify({ title, template, visibility }),
      }, () => {
        const candidate = api.auth.getCurrentSession()?.user;
        const newResume = {
          id: mockStore.resumes.length + 1,
          candidateId: candidate?.id || 1,
          title,
          template,
          visibility,
          isDefault: mockStore.resumes.length === 0,
          summary: '',
          personalInfo: {
            firstName: candidate?.fullName.split(' ')[0] || '',
            lastName: candidate?.fullName.split(' ')[1] || '',
            email: candidate?.email || '',
            phone: candidate?.phone || '',
          },
          createdAt: new Date().toISOString(),
          updatedAt: new Date().toISOString(),
        };
        mockStore.resumes.push(newResume);
        return newResume;
      });
    },

    getById: async (id) => {
      return request('RESUME', `/api/resumes/${id}`, {}, () => {
        const resume = mockStore.resumes.find(r => r.id === parseInt(id));
        if (!resume) throw new Error('Resume not found');
        return resume;
      });
    },

    getMyResumes: async () => {
      return request('RESUME', '/api/resumes/my', {}, () => {
        const candidate = api.auth.getCurrentSession()?.user;
        return mockStore.resumes.filter(r => r.candidateId === candidate?.id);
      });
    },

    updatePersonalInfo: async (resumeId, info) => {
      return request('RESUME', `/api/resumes/${resumeId}/personal-info`, {
        method: 'PUT',
        body: JSON.stringify(info),
      }, () => {
        const resume = mockStore.resumes.find(r => r.id === parseInt(resumeId));
        if (resume) {
          resume.personalInfo = { ...resume.personalInfo, ...info };
          resume.updatedAt = new Date().toISOString();
          return resume;
        }
        throw new Error('Resume not found');
      });
    },

    updateSummary: async (resumeId, summary) => {
      // Endpoint is PATCH /api/resumes/{resumeId}/summary?summary={summary}
      const query = new URLSearchParams({ summary }).toString();
      return request('RESUME', `/api/resumes/${resumeId}/summary?${query}`, {
        method: 'PATCH',
      }, () => {
        const resume = mockStore.resumes.find(r => r.id === parseInt(resumeId));
        if (resume) {
          resume.summary = summary;
          resume.updatedAt = new Date().toISOString();
          return resume;
        }
        throw new Error('Resume not found');
      });
    },

    setDefault: async (resumeId) => {
      return request('RESUME', `/api/resumes/${resumeId}/set-default`, {
        method: 'PATCH',
      }, () => {
        mockStore.resumes.forEach(r => {
          r.isDefault = (r.id === parseInt(resumeId));
        });
        return mockStore.resumes.find(r => r.id === parseInt(resumeId));
      });
    },

    delete: async (resumeId) => {
      return request('RESUME', `/api/resumes/${resumeId}`, {
        method: 'DELETE',
      }, () => {
        const idx = mockStore.resumes.findIndex(r => r.id === parseInt(resumeId));
        if (idx !== -1) {
          mockStore.resumes.splice(idx, 1);
          return { message: 'Resume deleted successfully', success: true };
        }
        throw new Error('Resume not found');
      });
    }
  }
};
