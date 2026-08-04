import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Search, MapPin, Sparkles, Building2, UserCheck, ChevronRight, Zap } from 'lucide-react';
import { api } from '../api/client';
import JobCard from '../components/JobCard';

export default function Home() {
  const navigate = useNavigate();
  const [keyword, setKeyword] = useState('');
  const [location, setLocation] = useState('');
  const [featuredJobs, setFeaturedJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        const jobs = await api.job.search();
        // Take the top 3 jobs as featured
        setFeaturedJobs(jobs.slice(0, 3));
      } catch (err) {
        console.error('Failed to load featured jobs', err);
      } finally {
        setLoading(false);
      }
    };
    fetchJobs();
  }, []);

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    const query = new URLSearchParams();
    if (keyword) query.set('keyword', keyword);
    if (location) query.set('location', location);
    navigate(`/jobs?${query.toString()}`);
  };

  return (
    <div className="container" style={{ minHeight: '80vh', display: 'flex', flexDirection: 'column', gap: '80px', marginTop: '40px' }}>
      
      {/* Hero Section */}
      <section style={{
        textAlign: 'center',
        padding: '60px 24px',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        gap: '24px',
        position: 'relative',
      }}>
        {/* Glow Background Circles */}
        <div style={{
          position: 'absolute',
          top: '-10%',
          left: '50%',
          transform: 'translateX(-50%)',
          width: '500px',
          height: '500px',
          background: 'radial-gradient(circle, var(--accent-primary-glow) 0%, transparent 70%)',
          zIndex: -1,
          pointerEvents: 'none',
        }} />

        <div className="glass-panel" style={{
          padding: '8px 16px',
          borderRadius: '50px',
          fontSize: '0.85rem',
          fontWeight: 600,
          color: 'var(--accent-secondary)',
          display: 'inline-flex',
          alignItems: 'center',
          gap: '8px',
          border: '1px solid rgba(14, 165, 233, 0.2)',
          background: 'rgba(14, 165, 233, 0.05)',
        }}>
          <Sparkles size={14} />
          <span>Intelligent Job Board Matching v1.0</span>
        </div>

        <h1 style={{
          fontSize: '3.6rem',
          maxWidth: '850px',
          lineHeight: '1.15',
          background: 'linear-gradient(to right, #ffffff 40%, #c7d2fe 80%, #6366f1 100%)',
          WebkitBackgroundClip: 'text',
          WebkitTextFillColor: 'transparent',
          marginTop: '12px',
        }}>
          Discover Your Career Potential With <span className="text-gradient-primary">Noir Job Portal</span>
        </h1>

        <p style={{
          fontSize: '1.2rem',
          color: 'var(--text-secondary)',
          maxWidth: '650px',
          lineHeight: '1.6',
        }}>
          Connect with top-tier product and software companies using our automated, context-aware developer-matching features.
        </p>

        {/* Search Panel */}
        <form onSubmit={handleSearchSubmit} className="glass-panel" style={{
          display: 'flex',
          alignItems: 'center',
          gap: '16px',
          padding: '16px 24px',
          width: '100%',
          maxWidth: '800px',
          borderRadius: '20px',
          boxShadow: '0 20px 50px rgba(0,0,0,0.4)',
          border: '1px solid rgba(255, 255, 255, 0.08)',
          marginTop: '20px',
          flexWrap: 'wrap',
        }}>
          {/* Keyword Input */}
          <div style={{
            display: 'flex',
            alignItems: 'center',
            gap: '10px',
            flex: '1',
            minWidth: '200px',
            borderRight: '1px solid var(--border-color)',
            paddingRight: '16px',
          }} className="search-field-wrapper">
            <Search size={18} color="var(--text-muted)" />
            <input
              type="text"
              placeholder="Job title, keywords, skill..."
              value={keyword}
              onChange={e => setKeyword(e.target.value)}
              style={{
                background: 'transparent',
                border: 'none',
                color: 'var(--text-primary)',
                outline: 'none',
                width: '100%',
                fontSize: '1rem',
              }}
            />
          </div>

          {/* Location Input */}
          <div style={{
            display: 'flex',
            alignItems: 'center',
            gap: '10px',
            flex: '1',
            minWidth: '200px',
          }} className="search-field-wrapper">
            <MapPin size={18} color="var(--text-muted)" />
            <input
              type="text"
              placeholder="City, country, or remote..."
              value={location}
              onChange={e => setLocation(e.target.value)}
              style={{
                background: 'transparent',
                border: 'none',
                color: 'var(--text-primary)',
                outline: 'none',
                width: '100%',
                fontSize: '1rem',
              }}
            />
          </div>

          {/* Submit Button */}
          <button type="submit" className="btn btn-primary" style={{
            padding: '14px 28px',
            borderRadius: '12px',
          }}>
            Search Jobs
          </button>
        </form>
      </section>

      {/* Stats Counter Section */}
      <section style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
        gap: '24px',
      }}>
        {[
          { label: 'Verified Openings', count: '1,200+', icon: <Building2 size={24} className="text-gradient-secondary" /> },
          { label: 'Corporate Partners', count: '150+', icon: <Sparkles size={24} className="text-gradient-primary" /> },
          { label: 'Active Candidates', count: '10,000+', icon: <UserCheck size={24} className="text-gradient-secondary" /> },
        ].map((stat, i) => (
          <div key={i} className="glass-panel" style={{
            padding: '24px',
            display: 'flex',
            alignItems: 'center',
            gap: '20px',
          }}>
            <div style={{
              background: 'rgba(255, 255, 255, 0.03)',
              padding: '12px',
              borderRadius: '12px',
              border: '1px solid var(--border-color)',
            }}>{stat.icon}</div>
            <div style={{ display: 'flex', flexDirection: 'column' }}>
              <span style={{ fontSize: '1.8rem', fontWeight: 800, fontFamily: 'Outfit' }}>{stat.count}</span>
              <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', fontWeight: 500 }}>{stat.label}</span>
            </div>
          </div>
        ))}
      </section>

      {/* Featured Jobs Section */}
      <section style={{ display: 'flex', flexDirection: 'column', gap: '32px' }}>
        <div style={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'flex-end',
        }}>
          <div>
            <h2 style={{ fontSize: '2.2rem' }}>Featured Job Opportunities</h2>
            <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem', marginTop: '6px' }}>
              Hand-picked listings from premium software companies.
            </p>
          </div>
          <Link to="/jobs" style={{
            color: 'var(--accent-primary)',
            textDecoration: 'none',
            fontWeight: 600,
            fontSize: '0.95rem',
            display: 'flex',
            alignItems: 'center',
            gap: '4px',
          }}>
            Explore All Jobs <ChevronRight size={16} />
          </Link>
        </div>

        {loading ? (
          <div style={{ textAlign: 'center', padding: '40px' }}>Loading job postings...</div>
        ) : (
          <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fit, minmax(340px, 1fr))',
            gap: '24px',
          }}>
            {featuredJobs.map(job => (
              <JobCard key={job.id} job={job} />
            ))}
          </div>
        )}
      </section>

      {/* Features Showcase Section */}
      <section style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))',
        gap: '32px',
        padding: '40px 0',
      }}>
        <div style={{ display: 'flex', flexDirection: 'column', justifySelf: 'center', gap: '16px', maxWidth: '350px' }}>
          <h2 style={{ fontSize: '2.2rem', lineHeight: '1.2' }}>Tailored For Developers & Modern Employers</h2>
          <p style={{ color: 'var(--text-muted)' }}>
            Say goodbye to long, automated screening forms and endless filtering. Noir Job Portal establishes direct, authenticated pipelines.
          </p>
          <Link to="/auth?mode=signup" className="btn btn-primary" style={{ width: 'fit-content', marginTop: '12px' }}>
            Get Started Now
          </Link>
        </div>

        <div className="glass-panel" style={{ padding: '32px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div style={{
            width: '40px',
            height: '40px',
            borderRadius: '8px',
            background: 'rgba(99, 102, 241, 0.1)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            border: '1px solid rgba(99, 102, 241, 0.2)',
          }}>
            <Zap className="text-gradient-primary" size={20} />
          </div>
          <h3 style={{ fontSize: '1.3rem' }}>Direct Auth Headers</h3>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem' }}>
            Microservices context propagation automatically communicates details about candidates, employers, and companies using secure gateway injection.
          </p>
        </div>

        <div className="glass-panel" style={{ padding: '32px', display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div style={{
            width: '40px',
            height: '40px',
            borderRadius: '8px',
            background: 'rgba(14, 165, 233, 0.1)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            border: '1px solid rgba(14, 165, 233, 0.2)',
          }}>
            <Sparkles className="text-gradient-secondary" size={20} />
          </div>
          <h3 style={{ fontSize: '1.3rem' }}>Structured Resume Previews</h3>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem' }}>
            Store customizable resume templates, update metadata, and set defaults that automatically link to job application submissions.
          </p>
        </div>
      </section>
      
    </div>
  );
}
