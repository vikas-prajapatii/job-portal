import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { api } from '../api/client';
import { Mail, Lock, User, Phone, Sparkles } from 'lucide-react';

export default function Auth() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const modeParam = searchParams.get('mode');
  
  const [isSignUp, setIsSignUp] = useState(modeParam === 'signup');
  const [role, setRole] = useState('ROLE_CANDIDATE'); // ROLE_CANDIDATE or ROLE_EMPLOYER
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [phone, setPhone] = useState('');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setIsSignUp(modeParam === 'signup');
  }, [modeParam]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      if (isSignUp) {
        const response = await api.auth.signup(fullName, email, password, role, phone);
        console.log('Signup success:', response);
      } else {
        const response = await api.auth.login(email, password);
        console.log('Login success:', response);
      }
      
      // Dispatch auth change event to Navbar
      window.dispatchEvent(new Event('auth-change'));
      
      // Route based on role
      const session = api.auth.getCurrentSession();
      if (session?.user?.role === 'ROLE_EMPLOYER') {
        navigate('/employer');
      } else {
        navigate('/profile');
      }
    } catch (err) {
      setError(err.message || 'An error occurred during authentication.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container" style={{
      minHeight: '80vh',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: '40px 24px',
    }}>
      <div className="glass-panel" style={{
        width: '100%',
        maxWidth: '480px',
        padding: '40px',
        display: 'flex',
        flexDirection: 'column',
        gap: '24px',
        border: '1px solid var(--border-color)',
        boxShadow: '0 20px 60px rgba(0,0,0,0.5)',
      }}>
        
        {/* Logo and Titles */}
        <div style={{ textAlign: 'center', display: 'flex', flexDirection: 'column', gap: '8px' }}>
          <h2 style={{ fontSize: '2rem' }}>
            {isSignUp ? 'Create your Account' : 'Welcome Back'}
          </h2>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem' }}>
            {isSignUp ? 'Join the AI-driven tech job portal' : 'Access your professional dashboard'}
          </p>
        </div>

        {/* Error Alert */}
        {error && (
          <div style={{
            background: 'rgba(239, 68, 68, 0.1)',
            border: '1px solid rgba(239, 68, 68, 0.3)',
            borderRadius: '8px',
            color: '#fca5a5',
            padding: '12px 16px',
            fontSize: '0.9rem',
            textAlign: 'center',
          }}>
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          {isSignUp && (
            <>
              {/* Role Toggle Selector */}
              <div className="form-group">
                <span className="form-label">Register As</span>
                <div style={{ display: 'flex', gap: '12px' }}>
                  <button
                    type="button"
                    onClick={() => setRole('ROLE_CANDIDATE')}
                    className={`btn ${role === 'ROLE_CANDIDATE' ? 'btn-primary' : 'btn-secondary'}`}
                    style={{ flex: 1, padding: '10px' }}
                  >
                    Candidate
                  </button>
                  <button
                    type="button"
                    onClick={() => setRole('ROLE_EMPLOYER')}
                    className={`btn ${role === 'ROLE_EMPLOYER' ? 'btn-primary' : 'btn-secondary'}`}
                    style={{ flex: 1, padding: '10px' }}
                  >
                    Employer
                  </button>
                </div>
              </div>

              {/* Full Name */}
              <div className="form-group">
                <label className="form-label">Full Name</label>
                <div style={{ position: 'relative' }}>
                  <User size={16} style={{ position: 'absolute', left: '16px', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
                  <input
                    type="text"
                    required
                    placeholder="Enter your name"
                    className="form-input"
                    value={fullName}
                    onChange={e => setFullName(e.target.value)}
                    style={{ paddingLeft: '44px', width: '100%' }}
                  />
                </div>
              </div>

              {/* Phone */}
              <div className="form-group">
                <label className="form-label">Phone Number</label>
                <div style={{ position: 'relative' }}>
                  <Phone size={16} style={{ position: 'absolute', left: '16px', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
                  <input
                    type="tel"
                    required
                    placeholder="Enter phone number"
                    className="form-input"
                    value={phone}
                    onChange={e => setPhone(e.target.value)}
                    style={{ paddingLeft: '44px', width: '100%' }}
                  />
                </div>
              </div>
            </>
          )}

          {/* Email */}
          <div className="form-group">
            <label className="form-label">Email Address</label>
            <div style={{ position: 'relative' }}>
              <Mail size={16} style={{ position: 'absolute', left: '16px', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
              <input
                type="email"
                required
                placeholder="you@example.com"
                className="form-input"
                value={email}
                onChange={e => setEmail(e.target.value)}
                style={{ paddingLeft: '44px', width: '100%' }}
              />
            </div>
          </div>

          {/* Password */}
          <div className="form-group">
            <label className="form-label">Password</label>
            <div style={{ position: 'relative' }}>
              <Lock size={16} style={{ position: 'absolute', left: '16px', top: '50%', transform: 'translateY(-50%)', color: 'var(--text-muted)' }} />
              <input
                type="password"
                required
                placeholder="••••••••"
                className="form-input"
                value={password}
                onChange={e => setPassword(e.target.value)}
                style={{ paddingLeft: '44px', width: '100%' }}
              />
            </div>
          </div>

          {/* Submit Button */}
          <button type="submit" className="btn btn-primary" disabled={loading} style={{
            width: '100%',
            padding: '12px',
            marginTop: '8px',
          }}>
            {loading ? 'Processing...' : (isSignUp ? 'Create Account' : 'Sign In')}
          </button>
        </form>

        {/* Toggle Mode */}
        <div style={{ textAlign: 'center', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
          {isSignUp ? (
            <span>
              Already have an account?{' '}
              <button onClick={() => setIsSignUp(false)} style={{
                background: 'none',
                border: 'none',
                color: 'var(--accent-primary)',
                fontWeight: 600,
                cursor: 'pointer',
              }}>
                Sign In
              </button>
            </span>
          ) : (
            <span>
              Don't have an account yet?{' '}
              <button onClick={() => setIsSignUp(true)} style={{
                background: 'none',
                border: 'none',
                color: 'var(--accent-primary)',
                fontWeight: 600,
                cursor: 'pointer',
              }}>
                Register
              </button>
            </span>
          )}
        </div>

      </div>
    </div>
  );
}
