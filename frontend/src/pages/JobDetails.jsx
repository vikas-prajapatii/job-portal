import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { api } from '../api/client';
import { MapPin, DollarSign, Briefcase, Calendar, Users, ArrowLeft, Send } from 'lucide-react';

export default function JobDetails() {
  const { id } = useParams();
  const [job, setJob] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [applied, setApplied] = useState(false);

  useEffect(() => {
    const fetchJob = async () => {
      setLoading(true);
      try {
        const data = await api.job.getById(id);
        setJob(data);
      } catch (err) {
        setError(err.message || 'Failed to load job details.');
      } finally {
        setLoading(false);
      }
    };
    fetchJob();
  }, [id]);

  const handleApply = () => {
    setApplied(true);
    alert('Application submitted successfully!');
  };

  if (loading) return <div className="container" style={{ textAlign: 'center', padding: '100px' }}>Loading job details...</div>;
  if (error) return <div className="container" style={{ textAlign: 'center', padding: '100px', color: '#fca5a5' }}>{error}</div>;
  if (!job) return <div className="container" style={{ textAlign: 'center', padding: '100px' }}>Job listing not found.</div>;

  const companyLogo = job.company?.logoUrl || 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=150&h=150&fit=crop';
  const companyName = job.company?.name || 'Noir Technologies';

  return (
    <div className="container" style={{ minHeight: '80vh', marginTop: '24px', display: 'flex', flexDirection: 'column', gap: '24px' }}>
      
      {/* Back link */}
      <Link to="/jobs" style={{
        display: 'inline-flex',
        alignItems: 'center',
        gap: '8px',
        color: 'var(--text-secondary)',
        textDecoration: 'none',
        fontSize: '0.9rem',
        fontWeight: 600,
        width: 'fit-content',
      }} onMouseOver={e => e.target.style.color = 'var(--text-primary)'}
         onMouseOut={e => e.target.style.color = 'var(--text-secondary)'}>
        <ArrowLeft size={16} /> Back to Job Search
      </Link>

      {/* Main Header glass panel */}
      <div className="glass-panel" style={{
        padding: '32px',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        flexWrap: 'wrap',
        gap: '24px',
        border: '1px solid var(--border-color)',
      }}>
        <div style={{ display: 'flex', gap: '24px', flexWrap: 'wrap', alignItems: 'center' }}>
          <img src={companyLogo} alt={companyName} style={{
            width: '80px',
            height: '80px',
            borderRadius: '16px',
            objectFit: 'cover',
            border: '1px solid var(--border-color)',
          }} />
          <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
            <h1 style={{ fontSize: '2rem' }}>{job.title}</h1>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '16px', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
              <span style={{ fontWeight: 600, color: 'var(--text-primary)' }}>{companyName}</span>
              <span style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
                <MapPin size={14} /> {job.location?.city}, {job.location?.country}
              </span>
              <span className="badge badge-sky">{job.workMode}</span>
              <span className="badge badge-indigo">{job.jobType?.replace('_', ' ')}</span>
            </div>
          </div>
        </div>

        <button
          onClick={handleApply}
          disabled={applied}
          className="btn btn-primary"
          style={{ padding: '14px 32px', fontSize: '1rem' }}
        >
          <Send size={18} />
          {applied ? 'Applied' : 'Apply Now'}
        </button>
      </div>

      {/* Content Columns */}
      <div className="dashboard-grid" style={{ gridTemplateColumns: '2fr 1fr' }}>
        
        {/* Main descriptions */}
        <main style={{ display: 'flex', flexDirection: 'column', gap: '32px' }}>
          {/* Description */}
          <div className="glass-panel" style={{ padding: '32px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
            <h2 style={{ fontSize: '1.4rem' }}>Job Description</h2>
            <p style={{ color: 'var(--text-secondary)', lineHeight: '1.7', whiteSpace: 'pre-line' }}>
              {job.description}
            </p>
          </div>

          {/* Requirements */}
          <div className="glass-panel" style={{ padding: '32px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
            <h2 style={{ fontSize: '1.4rem' }}>Requirements & Qualifications</h2>
            <p style={{ color: 'var(--text-secondary)', lineHeight: '1.7', whiteSpace: 'pre-line' }}>
              {job.requirements}
            </p>
          </div>

          {/* Responsibilities */}
          {job.responsibilities && (
            <div className="glass-panel" style={{ padding: '32px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
              <h2 style={{ fontSize: '1.4rem' }}>Core Responsibilities</h2>
              <p style={{ color: 'var(--text-secondary)', lineHeight: '1.7', whiteSpace: 'pre-line' }}>
                {job.responsibilities}
              </p>
            </div>
          )}

          {/* Benefits */}
          {job.benefits && (
            <div className="glass-panel" style={{ padding: '32px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
              <h2 style={{ fontSize: '1.4rem' }}>Perks & Benefits</h2>
              <p style={{ color: 'var(--text-secondary)', lineHeight: '1.7', whiteSpace: 'pre-line' }}>
                {job.benefits}
              </p>
            </div>
          )}
        </main>

        {/* Sidebar Info */}
        <aside style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
          <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '20px' }}>
            <h3 style={{ fontSize: '1.2rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '12px' }}>
              Overview
            </h3>
            
            {/* Salary */}
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
              <DollarSign size={20} className="text-gradient-primary" />
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                  Salary Period
                </span>
                <span style={{ fontSize: '0.95rem', fontWeight: 600 }}>
                  {job.salaryRange?.minSalary ? `$${job.salaryRange.minSalary.toLocaleString()} - $${job.salaryRange.maxSalary.toLocaleString()} / Yr` : 'Disclosed on application'}
                </span>
              </div>
            </div>

            {/* Level */}
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
              <Briefcase size={20} className="text-gradient-secondary" />
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                  Experience Level
                </span>
                <span style={{ fontSize: '0.95rem', fontWeight: 600 }}>
                  {job.experienceLevel}
                </span>
              </div>
            </div>

            {/* Openings */}
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
              <Users size={20} className="text-gradient-primary" />
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                  Open Positions
                </span>
                <span style={{ fontSize: '0.95rem', fontWeight: 600 }}>
                  {job.openings} Openings
                </span>
              </div>
            </div>

            {/* Created At */}
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
              <Calendar size={20} className="text-gradient-secondary" />
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                  Date Posted
                </span>
                <span style={{ fontSize: '0.95rem', fontWeight: 600 }}>
                  {job.createdAt ? new Date(job.createdAt).toLocaleDateString() : 'Just now'}
                </span>
              </div>
            </div>
          </div>

          {/* Required Skills */}
          {job.skills && job.skills.length > 0 && (
            <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
              <h3 style={{ fontSize: '1.2rem' }}>Required Skills</h3>
              <div style={{ display: 'flex', flexWrap: 'wrap', gap: '8px' }}>
                {job.skills.map(skill => (
                  <span key={skill.id} className="badge badge-indigo">
                    {skill.name}
                  </span>
                ))}
              </div>
            </div>
          )}

          {/* Tags */}
          {job.tags && job.tags.length > 0 && (
            <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
              <h3 style={{ fontSize: '1.2rem' }}>Tags</h3>
              <div style={{ display: 'flex', flexWrap: 'wrap', gap: '8px' }}>
                {job.tags.map(tag => (
                  <span key={tag.id} className="badge badge-sky">
                    {tag.name}
                  </span>
                ))}
              </div>
            </div>
          )}
        </aside>

      </div>

    </div>
  );
}
