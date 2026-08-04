import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Briefcase, User, LogOut, LayoutDashboard, PlusCircle, Menu, X } from 'lucide-react';
import { api } from '../api/client';

export default function Navbar() {
  const navigate = useNavigate();
  const [session, setSession] = useState(null);
  const [menuOpen, setMenuOpen] = useState(false);

  // Sync auth state
  useEffect(() => {
    const handleAuthChange = () => {
      setSession(api.auth.getCurrentSession());
    };
    
    // Initial check
    handleAuthChange();
    
    // Listen to custom local storage event (if triggered in same window)
    window.addEventListener('storage', handleAuthChange);
    // Custom trigger for auth changes
    window.addEventListener('auth-change', handleAuthChange);
    
    return () => {
      window.removeEventListener('storage', handleAuthChange);
      window.removeEventListener('auth-change', handleAuthChange);
    };
  }, []);

  const handleLogout = () => {
    api.auth.logout();
    window.dispatchEvent(new Event('auth-change'));
    navigate('/');
  };

  const user = session?.user;
  const isCandidate = user?.role === 'ROLE_CANDIDATE';
  const isEmployer = user?.role === 'ROLE_EMPLOYER';

  return (
    <nav className="glass-panel" style={{
      position: 'sticky',
      top: '16px',
      margin: '0 24px 24px 24px',
      zIndex: 100,
      padding: '12px 24px',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'space-between',
      border: '1px solid var(--border-color)',
      borderRadius: '16px',
    }}>
      {/* Brand Logo */}
      <Link to="/" style={{
        display: 'flex',
        alignItems: 'center',
        gap: '8px',
        textDecoration: 'none',
      }}>
        <div style={{
          background: 'linear-gradient(135deg, var(--accent-primary) 0%, var(--accent-secondary) 100%)',
          width: '36px',
          height: '36px',
          borderRadius: '8px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          boxShadow: '0 4px 12px rgba(99, 102, 241, 0.25)',
        }}>
          <Briefcase size={20} color="#fff" />
        </div>
        <span style={{
          fontSize: '1.4rem',
          fontWeight: 800,
          fontFamily: 'Outfit, sans-serif',
          background: 'linear-gradient(135deg, #fff 30%, #a5b4fc 100%)',
          WebkitBackgroundClip: 'text',
          WebkitTextFillColor: 'transparent',
        }}>
          Noir Job Portal
        </span>
      </Link>

      {/* Desktop Navigation Links */}
      <div style={{
        display: 'flex',
        alignItems: 'center',
        gap: '24px',
      }} className="nav-desktop-links">
        <Link to="/jobs" style={{
          color: 'var(--text-secondary)',
          textDecoration: 'none',
          fontWeight: 600,
          fontSize: '0.95rem',
          transition: 'var(--transition-fast)',
        }} onMouseOver={e => e.target.style.color = 'var(--text-primary)'}
           onMouseOut={e => e.target.style.color = 'var(--text-secondary)'}>
          Find Jobs
        </Link>
        
        {isCandidate && (
          <Link to="/profile" style={{
            color: 'var(--text-secondary)',
            textDecoration: 'none',
            fontWeight: 600,
            fontSize: '0.95rem',
          }} onMouseOver={e => e.target.style.color = 'var(--text-primary)'}
             onMouseOut={e => e.target.style.color = 'var(--text-secondary)'}>
            Resume Builder
          </Link>
        )}

        {isEmployer && (
          <>
            <Link to="/employer" style={{
              color: 'var(--text-secondary)',
              textDecoration: 'none',
              fontWeight: 600,
              fontSize: '0.95rem',
              display: 'flex',
              alignItems: 'center',
              gap: '6px',
            }} onMouseOver={e => e.target.style.color = 'var(--text-primary)'}
               onMouseOut={e => e.target.style.color = 'var(--text-secondary)'}>
              <LayoutDashboard size={16} />
              Dashboard
            </Link>
            <Link to="/post-job" style={{
              color: 'var(--text-secondary)',
              textDecoration: 'none',
              fontWeight: 600,
              fontSize: '0.95rem',
              display: 'flex',
              alignItems: 'center',
              gap: '6px',
            }} onMouseOver={e => e.target.style.color = 'var(--text-primary)'}
               onMouseOut={e => e.target.style.color = 'var(--text-secondary)'}>
              <PlusCircle size={16} />
              Post a Job
            </Link>
          </>
        )}
      </div>

      {/* Auth Actions */}
      <div style={{
        display: 'flex',
        alignItems: 'center',
        gap: '12px',
      }} className="nav-desktop-actions">
        {user ? (
          <div style={{
            display: 'flex',
            alignItems: 'center',
            gap: '16px',
          }}>
            <Link to="/profile" style={{
              display: 'flex',
              alignItems: 'center',
              gap: '8px',
              color: 'var(--text-primary)',
              textDecoration: 'none',
              background: 'rgba(255, 255, 255, 0.04)',
              padding: '6px 12px',
              borderRadius: '8px',
              border: '1px solid var(--border-color)',
              fontSize: '0.9rem',
              fontWeight: 500,
            }}>
              <User size={16} className="text-gradient-primary" />
              {user.fullName}
            </Link>
            <button onClick={handleLogout} className="btn btn-secondary" style={{
              padding: '8px 16px',
              fontSize: '0.85rem',
            }}>
              <LogOut size={14} />
              Log Out
            </button>
          </div>
        ) : (
          <div style={{
            display: 'flex',
            alignItems: 'center',
            gap: '12px',
          }}>
            <Link to="/auth" className="btn btn-secondary" style={{
              padding: '8px 20px',
              fontSize: '0.9rem',
            }}>
              Sign In
            </Link>
            <Link to="/auth?mode=signup" className="btn btn-primary" style={{
              padding: '8px 20px',
              fontSize: '0.9rem',
            }}>
              Register
            </Link>
          </div>
        )}
      </div>
    </nav>
  );
}
