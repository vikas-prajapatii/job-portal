import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { api } from '../api/client';
import JobCard from '../components/JobCard';
import { Search, MapPin, SlidersHorizontal, Briefcase } from 'lucide-react';

export default function Jobs() {
  const [searchParams, setSearchParams] = useSearchParams();
  
  const [keyword, setKeyword] = useState(searchParams.get('keyword') || '');
  const [location, setLocation] = useState(searchParams.get('location') || '');
  const [jobType, setJobType] = useState(searchParams.get('jobType') || '');
  const [workMode, setWorkMode] = useState(searchParams.get('workMode') || '');
  
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchJobs = async () => {
    setLoading(true);
    try {
      const filters = {};
      if (keyword) filters.keyword = keyword;
      if (location) filters.location = location;
      if (jobType) filters.jobType = jobType;
      if (workMode) filters.workMode = workMode;

      const data = await api.job.search(filters);
      setJobs(data);
    } catch (err) {
      console.error('Failed to search jobs', err);
    } finally {
      setLoading(false);
    }
  };

  // Trigger search on mount and whenever searchParams update
  useEffect(() => {
    fetchJobs();
  }, [searchParams]);

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    const params = {};
    if (keyword) params.keyword = keyword;
    if (location) params.location = location;
    if (jobType) params.jobType = jobType;
    if (workMode) params.workMode = workMode;
    setSearchParams(params);
  };

  const handleClearFilters = () => {
    setKeyword('');
    setLocation('');
    setJobType('');
    setWorkMode('');
    setSearchParams({});
  };

  return (
    <div className="container" style={{ minHeight: '80vh', display: 'flex', flexDirection: 'column', gap: '32px', marginTop: '24px' }}>
      
      {/* Search Header Bar */}
      <form onSubmit={handleSearchSubmit} className="glass-panel" style={{
        display: 'flex',
        alignItems: 'center',
        gap: '16px',
        padding: '12px 20px',
        borderRadius: '16px',
        flexWrap: 'wrap',
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', flex: 1, minWidth: '200px' }}>
          <Search size={18} color="var(--text-muted)" />
          <input
            type="text"
            placeholder="Search keywords, title, technology..."
            value={keyword}
            onChange={e => setKeyword(e.target.value)}
            style={{ background: 'transparent', border: 'none', color: 'var(--text-primary)', outline: 'none', width: '100%' }}
          />
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', flex: 1, minWidth: '200px', borderLeft: '1px solid var(--border-color)', paddingLeft: '16px' }} className="search-field-wrapper">
          <MapPin size={18} color="var(--text-muted)" />
          <input
            type="text"
            placeholder="City, country, or Remote"
            value={location}
            onChange={e => setLocation(e.target.value)}
            style={{ background: 'transparent', border: 'none', color: 'var(--text-primary)', outline: 'none', width: '100%' }}
          />
        </div>
        <button type="submit" className="btn btn-primary" style={{ padding: '10px 24px' }}>
          Find Jobs
        </button>
      </form>

      {/* Main Grid: Sidebar + Listings */}
      <div className="dashboard-grid">
        
        {/* Sidebar Filters */}
        <aside className="glass-panel" style={{ padding: '24px', display: 'flex', flexDirection: 'column', gap: '24px', height: 'fit-content' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <h3 style={{ fontSize: '1.2rem', display: 'flex', alignItems: 'center', gap: '8px' }}>
              <SlidersHorizontal size={18} />
              Filters
            </h3>
            <button onClick={handleClearFilters} style={{ background: 'none', border: 'none', color: 'var(--text-muted)', fontSize: '0.85rem', cursor: 'pointer', fontWeight: 500 }} onMouseOver={e => e.target.style.color = 'var(--text-primary)'} onMouseOut={e => e.target.style.color = 'var(--text-muted)'}>
              Clear All
            </button>
          </div>

          {/* Job Type Filter */}
          <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <span className="form-label">Job Type</span>
            <select className="form-input form-select" value={jobType} onChange={e => setJobType(e.target.value)}>
              <option value="">All Types</option>
              <option value="FULL_TIME">Full Time</option>
              <option value="PART_TIME">Part Time</option>
              <option value="CONTRACT">Contract</option>
              <option value="INTERNSHIP">Internship</option>
            </select>
          </div>

          {/* Work Mode Filter */}
          <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <span className="form-label">Work Mode</span>
            <select className="form-input form-select" value={workMode} onChange={e => setWorkMode(e.target.value)}>
              <option value="">All Modes</option>
              <option value="REMOTE">Remote</option>
              <option value="HYBRID">Hybrid</option>
              <option value="ON_SITE">On Site</option>
            </select>
          </div>
        </aside>

        {/* Listings Content */}
        <main style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <span style={{ fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
              Showing {jobs.length} jobs
            </span>
          </div>

          {loading ? (
            <div className="glass-panel" style={{ padding: '80px', textAlign: 'center', color: 'var(--text-secondary)' }}>
              Loading latest job opportunities...
            </div>
          ) : jobs.length === 0 ? (
            <div className="glass-panel" style={{ padding: '80px', textAlign: 'center', display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '16px' }}>
              <Briefcase size={48} color="var(--text-muted)" />
              <h3 style={{ fontSize: '1.4rem' }}>No Jobs Found</h3>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.95rem', maxWidth: '300px' }}>
                We couldn't find any job postings matching your filter parameters. Try clearing filters.
              </p>
              <button onClick={handleClearFilters} className="btn btn-secondary">Clear Filters</button>
            </div>
          ) : (
            <div style={{
              display: 'grid',
              gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))',
              gap: '24px',
            }}>
              {jobs.map(job => (
                <JobCard key={job.id} job={job} />
              ))}
            </div>
          )}
        </main>

      </div>

    </div>
  );
}
