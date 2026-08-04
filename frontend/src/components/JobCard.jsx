import React from 'react';
import { Link } from 'react-router-dom';
import { MapPin, DollarSign, Briefcase, Calendar, Star } from 'lucide-react';

export default function JobCard({ job }) {
  const getWorkModeBadge = (mode) => {
    switch (mode) {
      case 'REMOTE': return 'badge-emerald';
      case 'HYBRID': return 'badge-sky';
      default: return 'badge-indigo';
    }
  };

  const formattedSalary = (range) => {
    if (!range || (!range.minSalary && !range.maxSalary)) return 'Not Disclosed';
    const min = range.minSalary ? `$${(range.minSalary / 1000).toFixed(0)}k` : '';
    const max = range.maxSalary ? `$${(range.maxSalary / 1000).toFixed(0)}k` : '';
    return min && max ? `${min} - ${max}` : min || max;
  };

  const companyLogo = job.company?.logoUrl || 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=100&h=100&fit=crop';
  const companyName = job.company?.name || 'Noir Technologies';

  return (
    <div className="glass-panel-interactive animate-fade-in" style={{
      padding: '24px',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'space-between',
      gap: '16px',
      position: 'relative',
      height: '100%',
    }}>
      {/* Header Info */}
      <div style={{ display: 'flex', gap: '16px' }}>
        <img src={companyLogo} alt={companyName} style={{
          width: '48px',
          height: '48px',
          borderRadius: '10px',
          objectFit: 'cover',
          border: '1px solid var(--border-color)',
        }} />
        <div style={{ display: 'flex', flexDirection: 'column', gap: '4px' }}>
          <Link to={`/jobs/${job.id}`} style={{ textDecoration: 'none' }}>
            <h3 style={{
              fontSize: '1.15rem',
              fontWeight: 700,
              color: 'var(--text-primary)',
              transition: 'var(--transition-fast)',
            }} onMouseOver={e => e.target.style.color = 'var(--accent-primary)'}
               onMouseOut={e => e.target.style.color = 'var(--text-primary)'}>
              {job.title}
            </h3>
          </Link>
          <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', fontWeight: 500 }}>
            {companyName}
          </span>
        </div>
      </div>

      {/* Description Preview */}
      <p style={{
        fontSize: '0.9rem',
        color: 'var(--text-muted)',
        display: '-webkit-box',
        WebkitLineClamp: 3,
        WebkitBoxOrient: 'vertical',
        overflow: 'hidden',
        lineHeight: 1.5,
      }}>
        {job.description}
      </p>

      {/* Job Details Meta */}
      <div style={{
        display: 'grid',
        gridTemplateColumns: '1fr 1fr',
        gap: '10px 16px',
        fontSize: '0.85rem',
        color: 'var(--text-secondary)',
        borderTop: '1px solid var(--border-color)',
        paddingTop: '16px',
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <MapPin size={14} className="text-gradient-secondary" />
          <span>{job.location?.city || 'Remote'}, {job.location?.country || 'India'}</span>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <DollarSign size={14} className="text-gradient-primary" />
          <span>{formattedSalary(job.salaryRange)} / Year</span>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <Briefcase size={14} className="text-gradient-secondary" />
          <span>{job.jobType ? job.jobType.replace('_', ' ') : 'Full Time'}</span>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <Calendar size={14} className="text-gradient-primary" />
          <span>{job.createdAt ? new Date(job.createdAt).toLocaleDateString(undefined, {month: 'short', day: 'numeric'}) : 'Just now'}</span>
        </div>
      </div>

      {/* Badges / Bottom Section */}
      <div style={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        flexWrap: 'wrap',
        gap: '12px',
        marginTop: '8px',
      }}>
        <div style={{ display: 'flex', gap: '8px' }}>
          <span className={`badge ${getWorkModeBadge(job.workMode)}`}>
            {job.workMode}
          </span>
          <span className="badge badge-indigo">
            {job.experienceLevel}
          </span>
        </div>
        <Link to={`/jobs/${job.id}`} className="btn btn-glass" style={{
          padding: '6px 14px',
          fontSize: '0.8rem',
          borderRadius: '8px',
        }}>
          Details
        </Link>
      </div>
    </div>
  );
}
