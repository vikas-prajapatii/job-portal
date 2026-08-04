import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { api } from '../api/client';
import { ArrowLeft, Sparkles } from 'lucide-react';

export default function CreateJob() {
  const navigate = useNavigate();
  
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [requirements, setRequirements] = useState('');
  const [responsibilities, setResponsibilities] = useState('');
  const [benefits, setBenefits] = useState('');
  
  const [categoryId, setCategoryId] = useState(1);
  const [jobType, setJobType] = useState('FULL_TIME');
  const [workMode, setWorkMode] = useState('REMOTE');
  const [experienceLevel, setExperienceLevel] = useState('MID_LEVEL');
  const [openings, setOpenings] = useState(1);
  
  // Location
  const [address, setAddress] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [country, setCountry] = useState('');
  const [zipCode, setZipCode] = useState('');

  // Salary
  const [minSalary, setMinSalary] = useState('');
  const [maxSalary, setMaxSalary] = useState('');

  // Categories list
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Validate session
    const session = api.auth.getCurrentSession();
    if (!session || session.user.role !== 'ROLE_EMPLOYER') {
      navigate('/auth');
      return;
    }
    
    // Load categories
    const loadCategories = async () => {
      try {
        const list = await api.job.getCategories();
        setCategories(list);
        if (list.length > 0) setCategoryId(list[0].id);
      } catch (err) {
        console.error('Failed to load categories', err);
      }
    };
    loadCategories();
  }, [navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const payload = {
        title, description, requirements, responsibilities, benefits,
        categoryId: parseInt(categoryId), jobType, workMode, experienceLevel,
        openings: parseInt(openings),
        address, city, state, country, zipCode,
        minSalary: parseFloat(minSalary), maxSalary: parseFloat(maxSalary),
        skillIds: [1], // Default placeholder skills for simplicity
        tagIds: [1],   // Default placeholder tags
      };

      await api.job.create(payload);
      alert('Job listing created successfully as DRAFT. You can publish it from your dashboard.');
      navigate('/employer');
    } catch (err) {
      alert('Failed to post job: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container" style={{ minHeight: '80vh', marginTop: '24px', maxWidth: '800px' }}>
      
      {/* Back to dashboard */}
      <Link to="/employer" style={{
        display: 'inline-flex',
        alignItems: 'center',
        gap: '8px',
        color: 'var(--text-secondary)',
        textDecoration: 'none',
        fontSize: '0.9rem',
        fontWeight: 600,
        marginBottom: '24px',
      }} onMouseOver={e => e.target.style.color = 'var(--text-primary)'}
         onMouseOut={e => e.target.style.color = 'var(--text-secondary)'}>
        <ArrowLeft size={16} /> Back to Dashboard
      </Link>

      <div className="glass-panel" style={{ padding: '40px', display: 'flex', flexDirection: 'column', gap: '32px' }}>
        <div>
          <h1 style={{ fontSize: '2rem' }}>Post a New Job Advertisement</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', marginTop: '4px' }}>
            List description, requirements, benefits, and salary parameters for candidates.
          </p>
        </div>

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
          
          <h3 style={{ fontSize: '1.2rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '8px' }}>
            Job Info
          </h3>
          
          <div className="form-group">
            <label className="form-label">Job Title</label>
            <input type="text" required placeholder="e.g. Senior Backend Engineer (Spring Boot)" className="form-input" value={title} onChange={e => setTitle(e.target.value)} />
          </div>

          <div className="form-group">
            <label className="form-label">Description</label>
            <textarea rows={6} required placeholder="Detailed job description..." className="form-input" value={description} onChange={e => setDescription(e.target.value)} style={{ resize: 'vertical' }} />
          </div>

          <div className="form-group">
            <label className="form-label">Requirements</label>
            <textarea rows={4} required placeholder="Job qualifications and technical requirements..." className="form-input" value={requirements} onChange={e => setRequirements(e.target.value)} style={{ resize: 'vertical' }} />
          </div>

          <div className="form-group">
            <label className="form-label">Core Responsibilities</label>
            <textarea rows={4} required placeholder="List daily tasks and responsibilities..." className="form-input" value={responsibilities} onChange={e => setResponsibilities(e.target.value)} style={{ resize: 'vertical' }} />
          </div>

          <div className="form-group">
            <label className="form-label">Perks & Benefits</label>
            <textarea rows={3} placeholder="Flexible hybrid remote, health insurance, equity options..." className="form-input" value={benefits} onChange={e => setBenefits(e.target.value)} style={{ resize: 'vertical' }} />
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(180px, 1fr))', gap: '20px' }}>
            <div className="form-group">
              <span className="form-label">Category</span>
              <select className="form-input form-select" value={categoryId} onChange={e => setCategoryId(e.target.value)}>
                {categories.map(c => (
                  <option key={c.id} value={c.id}>{c.name}</option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <span className="form-label">Job Type</span>
              <select className="form-input form-select" value={jobType} onChange={e => setJobType(e.target.value)}>
                <option value="FULL_TIME">Full Time</option>
                <option value="PART_TIME">Part Time</option>
                <option value="CONTRACT">Contract</option>
                <option value="INTERNSHIP">Internship</option>
              </select>
            </div>
            <div className="form-group">
              <span className="form-label">Work Mode</span>
              <select className="form-input form-select" value={workMode} onChange={e => setWorkMode(e.target.value)}>
                <option value="REMOTE">Remote</option>
                <option value="HYBRID">Hybrid</option>
                <option value="ON_SITE">On Site</option>
              </select>
            </div>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
            <div className="form-group">
              <span className="form-label">Experience Level</span>
              <select className="form-input form-select" value={experienceLevel} onChange={e => setExperienceLevel(e.target.value)}>
                <option value="JUNIOR">Junior Level</option>
                <option value="MID_LEVEL">Mid Level</option>
                <option value="SENIOR">Senior Level</option>
                <option value="LEAD">Lead / Architect</option>
                <option value="EXECUTIVE">Executive / Manager</option>
              </select>
            </div>
            <div className="form-group">
              <label className="form-label">Open Positions</label>
              <input type="number" required min={1} className="form-input" value={openings} onChange={e => setOpenings(e.target.value)} />
            </div>
          </div>

          <h3 style={{ fontSize: '1.2rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '8px', marginTop: '16px' }}>
            Location Details
          </h3>

          <div className="form-group">
            <label className="form-label">Address</label>
            <input type="text" placeholder="e.g. Sector 62, Building A" className="form-input" value={address} onChange={e => setAddress(e.target.value)} />
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(140px, 1fr))', gap: '20px' }}>
            <div className="form-group">
              <label className="form-label">City</label>
              <input type="text" placeholder="Noida" className="form-input" value={city} onChange={e => setCity(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-label">State</label>
              <input type="text" placeholder="Uttar Pradesh" className="form-input" value={state} onChange={e => setState(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-label">Country</label>
              <input type="text" placeholder="India" className="form-input" value={country} onChange={e => setCountry(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-label">Zip Code</label>
              <input type="text" placeholder="201301" className="form-input" value={zipCode} onChange={e => setZipCode(e.target.value)} />
            </div>
          </div>

          <h3 style={{ fontSize: '1.2rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '8px', marginTop: '16px' }}>
            Compensation Ranges ($ USD / Year)
          </h3>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
            <div className="form-group">
              <label className="form-label">Minimum Salary</label>
              <input type="number" required placeholder="e.g. 50000" className="form-input" value={minSalary} onChange={e => setMinSalary(e.target.value)} />
            </div>
            <div className="form-group">
              <label className="form-label">Maximum Salary</label>
              <input type="number" required placeholder="e.g. 90000" className="form-input" value={maxSalary} onChange={e => setMaxSalary(e.target.value)} />
            </div>
          </div>

          <button type="submit" className="btn btn-primary" disabled={loading} style={{ padding: '12px 32px', width: 'fit-content', marginTop: '16px' }}>
            {loading ? 'Creating Posting...' : 'Post Job Advertisement'}
          </button>

        </form>
      </div>

    </div>
  );
}
