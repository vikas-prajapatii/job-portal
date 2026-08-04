import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { api } from '../api/client';
import { LayoutDashboard, PlusCircle, Building, Briefcase, Eye, Ban, Trash2, CheckCircle } from 'lucide-react';

export default function EmployerDashboard() {
  const navigate = useNavigate();
  const [session, setSession] = useState(null);
  
  const [company, setCompany] = useState(null);
  const [companyLoading, setCompanyLoading] = useState(true);
  
  const [jobs, setJobs] = useState([]);
  const [jobsLoading, setJobsLoading] = useState(true);
  const [actionLoading, setActionLoading] = useState(false);

  useEffect(() => {
    const sessionData = api.auth.getCurrentSession();
    if (!sessionData) {
      navigate('/auth');
      return;
    }
    if (sessionData.user.role !== 'ROLE_EMPLOYER') {
      navigate('/profile');
      return;
    }
    setSession(sessionData);
    loadEmployerContext();
  }, [navigate]);

  const loadEmployerContext = async () => {
    setCompanyLoading(true);
    setJobsLoading(true);
    
    let currentCompany = null;
    try {
      currentCompany = await api.company.getMyCompany();
      setCompany(currentCompany);
    } catch (err) {
      console.warn('Failed to fetch company profile', err);
    } finally {
      setCompanyLoading(false);
    }

    try {
      if (currentCompany) {
        const list = await api.job.getByCompany(currentCompany.id);
        setJobs(list);
      } else {
        // Fallback for mock/test matching by employer ID
        const allJobs = await api.job.search();
        const sess = api.auth.getCurrentSession();
        const myJobs = allJobs.filter(j => j.employerId === sess?.user?.id);
        setJobs(myJobs);
      }
    } catch (err) {
      console.error('Failed to load posted jobs', err);
    } finally {
      setJobsLoading(false);
    }
  };

  const handlePublish = async (id) => {
    setActionLoading(true);
    try {
      await api.job.publish(id);
      alert('Job published successfully!');
      await loadEmployerContext();
    } catch (err) {
      alert('Failed to publish job: ' + err.message);
    } finally {
      setActionLoading(false);
    }
  };

  const handleClose = async (id) => {
    setActionLoading(true);
    try {
      await api.job.close(id);
      alert('Job closed successfully!');
      await loadEmployerContext();
    } catch (err) {
      alert('Failed to close job: ' + err.message);
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this job posting?')) return;
    setActionLoading(true);
    try {
      await api.job.delete(id);
      alert('Job posting deleted.');
      await loadEmployerContext();
    } catch (err) {
      alert('Failed to delete job: ' + err.message);
    } finally {
      setActionLoading(false);
    }
  };

  if (!session) return null;

  return (
    <div className="container" style={{ minHeight: '80vh', marginTop: '32px', display: 'flex', flexDirection: 'column', gap: '32px' }}>
      
      {/* Header banner */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '16px' }}>
        <div>
          <h1 style={{ fontSize: '2rem' }}>Employer Dashboard</h1>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem' }}>
            Manage your company profile and job advertisements
          </p>
        </div>
        
        <div style={{ display: 'flex', gap: '12px' }}>
          {company && (
            <Link to="/post-job" className="btn btn-primary">
              <PlusCircle size={18} /> Post a Job
            </Link>
          )}
          <Link to="/company-setup" className="btn btn-secondary">
            <Building size={18} /> {company ? 'Edit Company' : 'Setup Company'}
          </Link>
        </div>
      </div>

      {companyLoading ? (
        <div className="glass-panel" style={{ padding: '40px', textAlign: 'center' }}>Loading company metadata...</div>
      ) : !company ? (
        /* Prompt to Create Company Profile */
        <div className="glass-panel" style={{ padding: '60px 40px', textAlign: 'center', display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '20px' }}>
          <Building size={48} color="var(--text-muted)" />
          <h2 style={{ fontSize: '1.5rem' }}>Company Profile Required</h2>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', maxWidth: '450px', lineHeight: '1.6' }}>
            To create and manage job postings, you first need to establish a verified Company profile. Let candidates know about your organization, website, and industry focus.
          </p>
          <Link to="/company-setup" className="btn btn-primary" style={{ padding: '12px 32px' }}>
            Setup Company Profile
          </Link>
        </div>
      ) : (
        /* Dashboard Stats & Listings */
        <>
          {/* Company Summary Banner */}
          <div className="glass-panel" style={{ padding: '24px', display: 'flex', gap: '20px', alignItems: 'center', border: '1px solid var(--border-color)' }}>
            <img src={company.logoUrl || 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=100&h=100&fit=crop'} alt={company.name} style={{ width: '56px', height: '56px', borderRadius: '8px', objectFit: 'cover' }} />
            <div style={{ display: 'flex', flexDirection: 'column', gap: '4px' }}>
              <span style={{ fontSize: '1.2rem', fontWeight: 700 }}>{company.name}</span>
              <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
                {company.industry} &bull; {company.type} &bull; Status: <span className="badge badge-indigo" style={{ fontSize: '0.7rem' }}>{company.status}</span>
              </span>
            </div>
          </div>

          {/* Stats Summary row */}
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '24px' }}>
            <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
              <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', fontWeight: 600, textTransform: 'uppercase' }}>Total Jobs</span>
              <span style={{ fontSize: '2rem', fontWeight: 800 }}>{jobs.length}</span>
            </div>
            <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
              <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', fontWeight: 600, textTransform: 'uppercase' }}>Active Listings</span>
              <span style={{ fontSize: '2rem', fontWeight: 800, color: 'var(--accent-success)' }}>{jobs.filter(j => j.status === 'ACTIVE').length}</span>
            </div>
            <div className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
              <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', fontWeight: 600, textTransform: 'uppercase' }}>Draft Status</span>
              <span style={{ fontSize: '2rem', fontWeight: 800, color: 'var(--accent-warning)' }}>{jobs.filter(j => j.status === 'DRAFT').length}</span>
            </div>
          </div>

          {/* Jobs Listing Table */}
          <div className="glass-panel" style={{ padding: '32px', display: 'flex', flexDirection: 'column', gap: '24px' }}>
            <h3 style={{ fontSize: '1.3rem' }}>Your Job Advertisements</h3>
            
            {jobsLoading ? (
              <div style={{ textAlign: 'center', padding: '40px' }}>Loading postings...</div>
            ) : jobs.length === 0 ? (
              <div style={{ textAlign: 'center', padding: '40px 0', color: 'var(--text-muted)' }}>
                You haven't posted any jobs yet. Get started by posting one.
              </div>
            ) : (
              <div style={{ overflowX: 'auto' }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left', minWidth: '600px' }}>
                  <thead>
                    <tr style={{ borderBottom: '1px solid var(--border-color)', color: 'var(--text-secondary)', fontSize: '0.85rem' }}>
                      <th style={{ padding: '12px 16px' }}>Job Title</th>
                      <th style={{ padding: '12px 16px' }}>Type & Mode</th>
                      <th style={{ padding: '12px 16px' }}>Status</th>
                      <th style={{ padding: '12px 16px' }}>Created</th>
                      <th style={{ padding: '12px 16px', textAlign: 'right' }}>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {jobs.map(job => (
                      <tr key={job.id} style={{ borderBottom: '1px solid rgba(255,255,255,0.03)', fontSize: '0.95rem' }} className="table-row-hover">
                        <td style={{ padding: '16px' }}>
                          <Link to={`/jobs/${job.id}`} style={{ color: 'var(--text-primary)', fontWeight: 600, textDecoration: 'none' }}>
                            {job.title}
                          </Link>
                        </td>
                        <td style={{ padding: '16px' }}>
                          <span style={{ marginRight: '8px' }} className="badge badge-indigo">{job.jobType?.replace('_', ' ')}</span>
                          <span className="badge badge-sky">{job.workMode}</span>
                        </td>
                        <td style={{ padding: '16px' }}>
                          <span className={`badge ${job.status === 'ACTIVE' ? 'badge-emerald' : 'badge-amber'}`}>
                            {job.status}
                          </span>
                        </td>
                        <td style={{ padding: '16px', color: 'var(--text-muted)' }}>
                          {job.createdAt ? new Date(job.createdAt).toLocaleDateString() : 'Just now'}
                        </td>
                        <td style={{ padding: '16px', textAlign: 'right', display: 'flex', gap: '8px', justifyContent: 'flex-end' }}>
                          <Link to={`/jobs/${job.id}`} className="btn btn-glass" style={{ padding: '6px 12px', fontSize: '0.8rem', borderRadius: '6px' }} title="View details">
                            <Eye size={14} />
                          </Link>
                          {job.status === 'DRAFT' && (
                            <button onClick={() => handlePublish(job.id)} disabled={actionLoading} className="btn btn-glass" style={{ padding: '6px 12px', fontSize: '0.8rem', borderRadius: '6px', color: '#6ee7b7' }} title="Publish job">
                              <CheckCircle size={14} />
                            </button>
                          )}
                          {job.status === 'ACTIVE' && (
                            <button onClick={() => handleClose(job.id)} disabled={actionLoading} className="btn btn-glass" style={{ padding: '6px 12px', fontSize: '0.8rem', borderRadius: '6px', color: '#fde047' }} title="Close job">
                              <Ban size={14} />
                            </button>
                          )}
                          <button onClick={() => handleDelete(job.id)} disabled={actionLoading} className="btn btn-glass" style={{ padding: '6px 12px', fontSize: '0.8rem', borderRadius: '6px', color: '#f87171' }} title="Delete posting">
                            <Trash2 size={14} />
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </>
      )}

    </div>
  );
}
