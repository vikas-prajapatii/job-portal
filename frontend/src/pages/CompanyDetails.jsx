import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { api } from '../api/client';
import { ArrowLeft, Building2 } from 'lucide-react';

export default function CompanyDetails() {
  const navigate = useNavigate();
  
  const [isEditing, setIsEditing] = useState(false);
  const [companyId, setCompanyId] = useState(null);
  
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [website, setWebsite] = useState('');
  const [logoUrl, setLogoUrl] = useState('');
  const [type, setType] = useState('PRODUCT'); // PRODUCT, SERVICE
  const [industry, setIndustry] = useState('IT'); // IT, HEALTHCARE, FINANCE, etc.
  
  const [loading, setLoading] = useState(true);
  const [saveLoading, setSaveLoading] = useState(false);

  useEffect(() => {
    // Validate session
    const session = api.auth.getCurrentSession();
    if (!session || session.user.role !== 'ROLE_EMPLOYER') {
      navigate('/auth');
      return;
    }
    
    loadCompanyDetails();
  }, [navigate]);

  const loadCompanyDetails = async () => {
    setLoading(true);
    try {
      const data = await api.company.getMyCompany();
      setCompanyId(data.id);
      setName(data.name || '');
      setDescription(data.description || '');
      setWebsite(data.website || '');
      setLogoUrl(data.logoUrl || '');
      setType(data.type || 'PRODUCT');
      setIndustry(data.industry || 'IT');
      setIsEditing(true);
    } catch (err) {
      console.log('No company profile found, starting new setup.');
      setIsEditing(false);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaveLoading(true);

    const payload = { name, description, website, logoUrl, type, industry };

    try {
      if (isEditing) {
        await api.company.update(companyId, payload);
        alert('Company profile updated successfully!');
      } else {
        await api.company.create(payload);
        alert('Company profile created successfully!');
      }
      navigate('/employer');
    } catch (err) {
      alert('Failed to save company profile: ' + err.message);
    } finally {
      setSaveLoading(false);
    }
  };

  return (
    <div className="container" style={{ minHeight: '80vh', marginTop: '24px', maxWidth: '650px' }}>
      
      {/* Back link */}
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
        <div style={{ display: 'flex', gap: '16px', alignItems: 'center' }}>
          <div style={{
            width: '48px',
            height: '48px',
            borderRadius: '10px',
            background: 'rgba(99,102,241,0.1)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            border: '1px solid rgba(99,102,241,0.2)'
          }}>
            <Building2 className="text-gradient-primary" size={24} />
          </div>
          <div>
            <h1 style={{ fontSize: '1.8rem' }}>
              {isEditing ? 'Edit Company Profile' : 'Setup Company Profile'}
            </h1>
            <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', marginTop: '2px' }}>
              Provide company details for candidates viewing your job ads.
            </p>
          </div>
        </div>

        {loading ? (
          <div style={{ textAlign: 'center', padding: '20px' }}>Loading profile configurations...</div>
        ) : (
          <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
            
            <div className="form-group">
              <label className="form-label">Company Name</label>
              <input type="text" required placeholder="e.g. Noir Technologies" className="form-input" value={name} onChange={e => setName(e.target.value)} />
            </div>

            <div className="form-group">
              <label className="form-label">Logo Image URL</label>
              <input type="url" placeholder="https://..." className="form-input" value={logoUrl} onChange={e => setLogoUrl(e.target.value)} />
            </div>

            <div className="form-group">
              <label className="form-label">Company Website URL</label>
              <input type="url" required placeholder="https://yourcompany.com" className="form-input" value={website} onChange={e => setWebsite(e.target.value)} />
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
              <div className="form-group">
                <span className="form-label">Company Type</span>
                <select className="form-input form-select" value={type} onChange={e => setType(e.target.value)}>
                  <option value="PRODUCT">Product Based</option>
                  <option value="SERVICE">Service Based</option>
                </select>
              </div>
              <div className="form-group">
                <span className="form-label">Industry Classification</span>
                <select className="form-input form-select" value={industry} onChange={e => setIndustry(e.target.value)}>
                  <option value="IT">Information Technology</option>
                  <option value="FINANCE">Financial Services</option>
                  <option value="HEALTHCARE">Healthcare & Biotech</option>
                  <option value="EDUCATION">Education Tech</option>
                </select>
              </div>
            </div>

            <div className="form-group">
              <label className="form-label">Company Bio / Description</label>
              <textarea rows={5} required placeholder="Tell candidates about your company mission, tech stack, and workplace culture..." className="form-input" value={description} onChange={e => setDescription(e.target.value)} style={{ resize: 'vertical' }} />
            </div>

            <button type="submit" className="btn btn-primary" disabled={saveLoading} style={{ padding: '12px 32px', width: 'fit-content', marginTop: '8px' }}>
              {saveLoading ? 'Saving...' : 'Save Company Profile'}
            </button>

          </form>
        )}
      </div>

    </div>
  );
}
