import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api/client';
import { User, FileText, Settings, Sparkles, Plus, Globe, Shield, Check, Trash2, Eye } from 'lucide-react';

export default function Profile() {
  const navigate = useNavigate();
  const [session, setSession] = useState(null);
  const [activeTab, setActiveTab] = useState('resume'); // 'resume' or 'settings'
  
  // User Profile fields
  const [fullName, setFullName] = useState('');
  const [phone, setPhone] = useState('');
  const [saveLoading, setSaveLoading] = useState(false);
  const [saveMessage, setSaveMessage] = useState(null);

  // Resume fields
  const [resumes, setResumes] = useState([]);
  const [activeResume, setActiveResume] = useState(null);
  const [newResumeTitle, setNewResumeTitle] = useState('');
  const [showCreateForm, setShowCreateForm] = useState(false);

  // Resume form fields
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [headLine, setHeadLine] = useState('');
  const [resumeEmail, setResumeEmail] = useState('');
  const [resumePhone, setResumePhone] = useState('');
  const [city, setCity] = useState('');
  const [country, setCountry] = useState('');
  const [linkedinUrl, setLinkedinUrl] = useState('');
  const [githubUrl, setGithubUrl] = useState('');
  const [portfolioUrl, setPortfolioUrl] = useState('');
  const [summary, setSummary] = useState('');
  const [template, setTemplate] = useState('PROFESSIONAL');
  const [visibility, setVisibility] = useState('PRIVATE');

  // Load profile and resumes
  useEffect(() => {
    const sessionData = api.auth.getCurrentSession();
    if (!sessionData) {
      navigate('/auth');
      return;
    }
    setSession(sessionData);
    setFullName(sessionData.user.fullName);
    setPhone(sessionData.user.phone || '');

    fetchResumes();
  }, [navigate]);

  const fetchResumes = async () => {
    try {
      const list = await api.resume.getMyResumes();
      setResumes(list);
      if (list.length > 0) {
        // Find default or first resume
        const def = list.find(r => r.isDefault) || list[0];
        loadResumeIntoForm(def);
      }
    } catch (err) {
      console.error('Failed to fetch resumes', err);
    }
  };

  const loadResumeIntoForm = (res) => {
    setActiveResume(res);
    setTemplate(res.template || 'PROFESSIONAL');
    setVisibility(res.visibility || 'PRIVATE');
    setSummary(res.summary || '');
    
    const info = res.personalInfo || {};
    setFirstName(info.firstName || '');
    setLastName(info.lastName || '');
    setHeadLine(info.headLine || '');
    setResumeEmail(info.email || '');
    setResumePhone(info.phone || '');
    setCity(info.city || '');
    setCountry(info.country || '');
    setLinkedinUrl(info.linkedinUrl || '');
    setGithubUrl(info.githubUrl || '');
    setPortfolioUrl(info.portfolioUrl || '');
  };

  const handleUpdateProfile = async (e) => {
    e.preventDefault();
    setSaveLoading(true);
    setSaveMessage(null);
    try {
      const updated = await api.user.updateProfile(session.user.email, { fullName, phone });
      setSaveMessage({ text: 'Profile settings updated successfully!', type: 'success' });
      // Update local storage session
      const sess = api.auth.getCurrentSession();
      sess.user = updated;
      localStorage.setItem('noir_job_auth', JSON.stringify(sess));
      window.dispatchEvent(new Event('auth-change'));
    } catch (err) {
      setSaveMessage({ text: err.message || 'Failed to update profile.', type: 'error' });
    } finally {
      setSaveLoading(false);
    }
  };

  const handleCreateResume = async (e) => {
    e.preventDefault();
    if (!newResumeTitle) return;
    try {
      const created = await api.resume.create(newResumeTitle);
      setNewResumeTitle('');
      setShowCreateForm(false);
      await fetchResumes();
      loadResumeIntoForm(created);
    } catch (err) {
      alert('Failed to create resume: ' + err.message);
    }
  };

  const handleSaveResume = async () => {
    if (!activeResume) return;
    setSaveLoading(true);
    setSaveMessage(null);

    try {
      // Update personal info
      const personalInfo = {
        firstName, lastName, headLine, email: resumeEmail, phone: resumePhone,
        city, country, linkedinUrl, githubUrl, portfolioUrl
      };
      
      const updated = await api.resume.updatePersonalInfo(activeResume.id, personalInfo);
      
      // Update summary
      await api.resume.updateSummary(activeResume.id, summary);
      
      setSaveMessage({ text: 'Resume updated successfully!', type: 'success' });
      await fetchResumes();
    } catch (err) {
      setSaveMessage({ text: err.message || 'Failed to save resume.', type: 'error' });
    } finally {
      setSaveLoading(false);
    }
  };

  const handleDeleteResume = async (id) => {
    if (!window.confirm('Are you sure you want to delete this resume?')) return;
    try {
      await api.resume.delete(id);
      setActiveResume(null);
      await fetchResumes();
    } catch (err) {
      alert('Failed to delete: ' + err.message);
    }
  };

  const handleSetDefault = async (id) => {
    try {
      await api.resume.setDefault(id);
      await fetchResumes();
    } catch (err) {
      alert('Failed to set default: ' + err.message);
    }
  };

  if (!session) return null;

  return (
    <div className="container" style={{ minHeight: '80vh', marginTop: '32px' }}>
      
      <div style={{ display: 'flex', gap: '24px', alignItems: 'center', marginBottom: '32px' }}>
        <div style={{
          width: '64px',
          height: '64px',
          borderRadius: '50%',
          background: 'linear-gradient(135deg, var(--accent-primary) 0%, var(--accent-secondary) 100%)',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          fontSize: '1.5rem',
          fontWeight: 800,
        }}>
          {session.user.fullName[0].toUpperCase()}
        </div>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
          <h1 style={{ fontSize: '2rem' }}>{session.user.fullName}</h1>
          <span style={{ color: 'var(--text-secondary)', fontSize: '0.9rem' }}>
            {session.user.role === 'ROLE_EMPLOYER' ? 'Employer Account' : 'Candidate Account'}
          </span>
        </div>
      </div>

      {/* Tabs list */}
      <div className="glass-panel" style={{
        padding: '6px',
        display: 'flex',
        gap: '12px',
        borderRadius: '12px',
        marginBottom: '32px',
        width: 'fit-content',
      }}>
        <button
          onClick={() => setActiveTab('resume')}
          className={`btn ${activeTab === 'resume' ? 'btn-primary' : 'btn-glass'}`}
          style={{ padding: '8px 24px', fontSize: '0.9rem' }}
        >
          <FileText size={16} />
          My Resumes
        </button>
        <button
          onClick={() => setActiveTab('settings')}
          className={`btn ${activeTab === 'settings' ? 'btn-primary' : 'btn-glass'}`}
          style={{ padding: '8px 24px', fontSize: '0.9rem' }}
        >
          <Settings size={16} />
          Profile Settings
        </button>
      </div>

      {/* Messages */}
      {saveMessage && (
        <div style={{
          background: saveMessage.type === 'success' ? 'rgba(16, 185, 129, 0.1)' : 'rgba(239, 68, 68, 0.1)',
          border: saveMessage.type === 'success' ? '1px solid rgba(16, 185, 129, 0.3)' : '1px solid rgba(239, 68, 68, 0.3)',
          borderRadius: '8px',
          color: saveMessage.type === 'success' ? '#a7f3d0' : '#fca5a5',
          padding: '12px 16px',
          marginBottom: '24px',
          fontSize: '0.95rem',
          textAlign: 'center',
        }}>
          {saveMessage.text}
        </div>
      )}

      {activeTab === 'settings' ? (
        /* Settings Tab */
        <div className="glass-panel" style={{ padding: '40px', maxWidth: '600px' }}>
          <h2 style={{ fontSize: '1.4rem', marginBottom: '24px' }}>Edit Profile Settings</h2>
          <form onSubmit={handleUpdateProfile} style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
            <div className="form-group">
              <label className="form-label">Full Name</label>
              <input
                type="text"
                required
                className="form-input"
                value={fullName}
                onChange={e => setFullName(e.target.value)}
              />
            </div>
            <div className="form-group">
              <label className="form-label">Email Address (Read-only)</label>
              <input
                type="email"
                disabled
                className="form-input"
                value={session.user.email}
                style={{ opacity: 0.6, cursor: 'not-allowed' }}
              />
            </div>
            <div className="form-group">
              <label className="form-label">Phone Number</label>
              <input
                type="tel"
                required
                className="form-input"
                value={phone}
                onChange={e => setPhone(e.target.value)}
              />
            </div>
            <button type="submit" className="btn btn-primary" disabled={saveLoading} style={{ width: 'fit-content', padding: '12px 32px' }}>
              {saveLoading ? 'Saving...' : 'Update Settings'}
            </button>
          </form>
        </div>
      ) : (
        /* Resume Tab */
        <div className="dashboard-grid">
          
          {/* Left Panel: Resume List Selector */}
          <aside className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '20px', height: 'fit-content' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <h3 style={{ fontSize: '1.1rem' }}>Resume Files</h3>
              <button onClick={() => setShowCreateForm(!showCreateForm)} className="btn btn-glass" style={{ padding: '6px', borderRadius: '8px' }}>
                <Plus size={16} />
              </button>
            </div>

            {showCreateForm && (
              <form onSubmit={handleCreateResume} style={{ display: 'flex', flexDirection: 'column', gap: '10px', background: 'rgba(255,255,255,0.02)', padding: '12px', borderRadius: '8px', border: '1px solid var(--border-color)' }}>
                <input
                  type="text"
                  required
                  placeholder="Resume Title (e.g. CV 2026)"
                  className="form-input"
                  value={newResumeTitle}
                  onChange={e => setNewResumeTitle(e.target.value)}
                  style={{ fontSize: '0.85rem', padding: '8px 12px' }}
                />
                <button type="submit" className="btn btn-primary" style={{ padding: '8px', fontSize: '0.85rem' }}>Create</button>
              </form>
            )}

            <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
              {resumes.length === 0 ? (
                <span style={{ fontSize: '0.9rem', color: 'var(--text-muted)', textAlign: 'center', padding: '20px 0' }}>
                  No resumes created yet. Click "+" to start.
                </span>
              ) : (
                resumes.map(r => (
                  <div
                    key={r.id}
                    onClick={() => loadResumeIntoForm(r)}
                    style={{
                      padding: '12px 16px',
                      borderRadius: '10px',
                      background: activeResume?.id === r.id ? 'rgba(99, 102, 241, 0.12)' : 'rgba(255, 255, 255, 0.02)',
                      border: activeResume?.id === r.id ? '1px solid var(--accent-primary)' : '1px solid var(--border-color)',
                      cursor: 'pointer',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'space-between',
                      transition: 'var(--transition-fast)',
                    }}
                  >
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '4px', maxWidth: '70%' }}>
                      <span style={{ fontSize: '0.9rem', fontWeight: 600, color: 'var(--text-primary)', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                        {r.title}
                      </span>
                      {r.isDefault && <span className="badge badge-emerald" style={{ fontSize: '0.65rem', width: 'fit-content' }}>Default</span>}
                    </div>
                    
                    <div style={{ display: 'flex', gap: '8px' }}>
                      {!r.isDefault && (
                        <button onClick={(e) => { e.stopPropagation(); handleSetDefault(r.id); }} title="Set as default" style={{ background: 'none', border: 'none', color: 'var(--text-muted)', cursor: 'pointer' }} onMouseOver={e => e.target.style.color = '#34d399'}>
                          <Check size={14} />
                        </button>
                      )}
                      <button onClick={(e) => { e.stopPropagation(); handleDeleteResume(r.id); }} title="Delete resume" style={{ background: 'none', border: 'none', color: 'var(--text-muted)', cursor: 'pointer' }} onMouseOver={e => e.target.style.color = '#f87171'}>
                        <Trash2 size={14} />
                      </button>
                    </div>
                  </div>
                ))
              )}
            </div>
          </aside>

          {/* Right Panel: Resume form and interactive preview */}
          {activeResume ? (
            <main style={{ display: 'flex', flexDirection: 'column', gap: '32px' }}>
              
              {/* Settings Configuration */}
              <div className="glass-panel" style={{ padding: '24px', display: 'flex', gap: '24px', flexWrap: 'wrap' }}>
                <div className="form-group" style={{ flex: 1, minWidth: '150px' }}>
                  <span className="form-label">Template Design</span>
                  <select className="form-input form-select" value={template} onChange={e => setTemplate(e.target.value)}>
                    <option value="PROFESSIONAL">Professional Dark</option>
                    <option value="CREATIVE">Creative Glow</option>
                    <option value="MINIMAL">Minimalist</option>
                    <option value="MODERN">Modern Tech</option>
                  </select>
                </div>
                <div className="form-group" style={{ flex: 1, minWidth: '150px' }}>
                  <span className="form-label">Visibility Status</span>
                  <select className="form-input form-select" value={visibility} onChange={e => setVisibility(e.target.value)}>
                    <option value="PUBLIC">Public (Searchable)</option>
                    <option value="PRIVATE">Private (Link Only)</option>
                  </select>
                </div>
              </div>

              {/* Builder Inputs */}
              <div className="glass-panel" style={{ padding: '32px', display: 'flex', flexDirection: 'column', gap: '24px' }}>
                <h3 style={{ fontSize: '1.2rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '12px' }}>
                  Personal Information
                </h3>
                
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="form-group">
                    <label className="form-label">First Name</label>
                    <input type="text" className="form-input" value={firstName} onChange={e => setFirstName(e.target.value)} />
                  </div>
                  <div className="form-group">
                    <label className="form-label">Last Name</label>
                    <input type="text" className="form-input" value={lastName} onChange={e => setLastName(e.target.value)} />
                  </div>
                </div>

                <div className="form-group">
                  <label className="form-label">Professional Headline</label>
                  <input type="text" placeholder="e.g. Senior Java Developer & Architect" className="form-input" value={headLine} onChange={e => setHeadLine(e.target.value)} />
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="form-group">
                    <label className="form-label">Contact Email</label>
                    <input type="email" className="form-input" value={resumeEmail} onChange={e => setResumeEmail(e.target.value)} />
                  </div>
                  <div className="form-group">
                    <label className="form-label">Contact Phone</label>
                    <input type="tel" className="form-input" value={resumePhone} onChange={e => setResumePhone(e.target.value)} />
                  </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="form-group">
                    <label className="form-label">City</label>
                    <input type="text" className="form-input" value={city} onChange={e => setCity(e.target.value)} />
                  </div>
                  <div className="form-group">
                    <label className="form-label">Country</label>
                    <input type="text" className="form-input" value={country} onChange={e => setCountry(e.target.value)} />
                  </div>
                </div>

                <h3 style={{ fontSize: '1.2rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '12px', marginTop: '16px' }}>
                  Social Handles & Professional Profiles
                </h3>
                
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="form-group">
                    <label className="form-label">LinkedIn Profile URL</label>
                    <input type="url" placeholder="https://linkedin.com/in/..." className="form-input" value={linkedinUrl} onChange={e => setLinkedinUrl(e.target.value)} />
                  </div>
                  <div className="form-group">
                    <label className="form-label">GitHub Profile URL</label>
                    <input type="url" placeholder="https://github.com/..." className="form-input" value={githubUrl} onChange={e => setGithubUrl(e.target.value)} />
                  </div>
                </div>

                <div className="form-group">
                  <label className="form-label">Portfolio or Website URL</label>
                  <input type="url" placeholder="https://..." className="form-input" value={portfolioUrl} onChange={e => setPortfolioUrl(e.target.value)} />
                </div>

                <h3 style={{ fontSize: '1.2rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '12px', marginTop: '16px' }}>
                  Summary / Bio
                </h3>
                
                <div className="form-group">
                  <label className="form-label">Professional Summary</label>
                  <textarea rows={5} placeholder="Describe your experience, technical expertise, and career accomplishments..." className="form-input" value={summary} onChange={e => setSummary(e.target.value)} style={{ resize: 'vertical' }} />
                </div>

                {/* Mock Work Experience and Education (Commented out in backend DTO) */}
                <h3 style={{ fontSize: '1.2rem', borderBottom: '1px solid var(--border-color)', paddingBottom: '12px', marginTop: '16px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                  Skills, Experience & Education
                  <span className="badge badge-indigo" style={{ fontSize: '0.65rem' }}>Local Mock Mode</span>
                </h3>
                <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>
                  These parameters are temporarily saved locally on the client interface to match the incomplete backend schema fields.
                </p>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '24px' }}>
                  <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
                    <span className="form-label">Mock Skillset Tags</span>
                    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '8px' }}>
                      {['React.js', 'Spring Boot', 'PostgreSQL', 'Java 21', 'Microservices', 'Git'].map((s, idx) => (
                        <span key={idx} className="badge badge-sky">{s}</span>
                      ))}
                    </div>
                  </div>
                  <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
                    <span className="form-label">Mock Career History</span>
                    <div style={{ padding: '12px', background: 'rgba(255,255,255,0.01)', border: '1px solid var(--border-color)', borderRadius: '8px', fontSize: '0.85rem' }}>
                      <strong>Senior Software Developer</strong> at Noir Tech (2024 - Present)<br/>
                      <span style={{ color: 'var(--text-muted)' }}>Created microservice configurations and React frontend panels.</span>
                    </div>
                  </div>
                </div>

                {/* Save Trigger */}
                <button onClick={handleSaveResume} className="btn btn-primary" disabled={saveLoading} style={{ padding: '12px 32px', width: 'fit-content', marginTop: '16px' }}>
                  {saveLoading ? 'Saving...' : 'Save Resume Updates'}
                </button>
              </div>

              {/* Dynamic Interactive Preview */}
              <div className="glass-panel" style={{ padding: '40px', border: '1px solid var(--border-color)' }}>
                <h3 style={{ fontSize: '1.2rem', marginBottom: '24px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                  <Eye size={18} className="text-gradient-secondary" />
                  Interactive Layout Preview: Template "{template}"
                </h3>
                
                {/* Simulated Sheet */}
                <div style={{
                  background: template === 'PROFESSIONAL' ? '#0f111a' : template === 'CREATIVE' ? 'linear-gradient(135deg, #180b30 0%, #0d061a 100%)' : template === 'MINIMAL' ? '#ffffff' : '#141b24',
                  color: template === 'MINIMAL' ? '#1e293b' : '#f8fafc',
                  border: template === 'MINIMAL' ? '1px solid #e2e8f0' : '1px solid rgba(255,255,255,0.05)',
                  padding: '40px',
                  borderRadius: '12px',
                  boxShadow: '0 10px 30px rgba(0,0,0,0.3)',
                  display: 'flex',
                  flexDirection: 'column',
                  gap: '24px',
                  transition: 'var(--transition-normal)',
                }}>
                  {/* Sheet Header */}
                  <div style={{ borderBottom: template === 'MINIMAL' ? '2px solid #1e293b' : '1px solid rgba(255,255,255,0.1)', paddingBottom: '16px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
                    <h1 style={{ fontSize: '2rem', color: template === 'MINIMAL' ? '#0f172a' : '#fff' }}>
                      {firstName || 'John'} {lastName || 'Doe'}
                    </h1>
                    <span style={{
                      fontSize: '1.1rem',
                      fontWeight: 600,
                      color: template === 'CREATIVE' ? '#a78bfa' : template === 'MINIMAL' ? '#475569' : 'var(--accent-secondary)',
                    }}>{headLine || 'Your Professional Headline'}</span>
                    
                    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '12px 24px', fontSize: '0.8rem', color: template === 'MINIMAL' ? '#64748b' : 'var(--text-secondary)', marginTop: '8px' }}>
                      {resumeEmail && <span>Email: {resumeEmail}</span>}
                      {resumePhone && <span>Phone: {resumePhone}</span>}
                      {(city || country) && <span>Location: {city}{city && country && ', '}{country}</span>}
                    </div>
                  </div>

                  {/* Sheet Summary */}
                  {summary && (
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
                      <h4 style={{ fontSize: '0.9rem', color: template === 'MINIMAL' ? '#0f172a' : 'var(--accent-primary)', textTransform: 'uppercase', letterSpacing: '0.05em' }}>
                        Executive Summary
                      </h4>
                      <p style={{ fontSize: '0.9rem', lineHeight: '1.6', color: template === 'MINIMAL' ? '#334155' : 'var(--text-secondary)' }}>
                        {summary}
                      </p>
                    </div>
                  )}

                  {/* Social links row */}
                  <div style={{ display: 'flex', gap: '16px', fontSize: '0.85rem' }}>
                    {linkedinUrl && <a href={linkedinUrl} target="_blank" rel="noreferrer" style={{ color: template === 'MINIMAL' ? '#2563eb' : 'var(--accent-secondary)', textDecoration: 'none' }}>LinkedIn</a>}
                    {githubUrl && <a href={githubUrl} target="_blank" rel="noreferrer" style={{ color: template === 'MINIMAL' ? '#2563eb' : 'var(--accent-secondary)', textDecoration: 'none' }}>GitHub</a>}
                    {portfolioUrl && <a href={portfolioUrl} target="_blank" rel="noreferrer" style={{ color: template === 'MINIMAL' ? '#2563eb' : 'var(--accent-secondary)', textDecoration: 'none' }}>Portfolio</a>}
                  </div>
                </div>

              </div>

            </main>
          ) : (
            <div className="glass-panel" style={{ padding: '80px', textAlign: 'center', display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '16px' }}>
              <FileText size={48} color="var(--text-muted)" />
              <h3 style={{ fontSize: '1.4rem' }}>No Resume Loaded</h3>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.95rem', maxWidth: '300px' }}>
                Please create a new resume or select an existing resume from the list panel to run the editor.
              </p>
            </div>
          )}

        </div>
      )}

    </div>
  );
}
