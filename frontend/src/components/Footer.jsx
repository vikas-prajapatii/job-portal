import React from 'react';
import { Briefcase } from 'lucide-react';

export default function Footer() {
  return (
    <footer style={{
      borderTop: '1px solid var(--border-color)',
      padding: '48px 24px',
      marginTop: '64px',
      background: 'var(--bg-secondary)',
    }}>
      <div className="container" style={{
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-between',
        alignItems: 'center',
        gap: '24px',
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <div style={{
            background: 'linear-gradient(135deg, var(--accent-primary) 0%, var(--accent-secondary) 100%)',
            width: '28px',
            height: '28px',
            borderRadius: '6px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
          }}>
            <Briefcase size={16} color="#fff" />
          </div>
          <span style={{
            fontSize: '1.1rem',
            fontWeight: 800,
            fontFamily: 'Outfit, sans-serif',
          }}>
            Noir Job Portal
          </span>
        </div>
        <p style={{
          fontSize: '0.85rem',
          color: 'var(--text-muted)',
          textAlign: 'right',
        }}>
          &copy; {new Date().getFullYear()} Noir Technologies. All rights reserved. Powered by Advanced Agentic Intelligence.
        </p>
      </div>
    </footer>
  );
}
