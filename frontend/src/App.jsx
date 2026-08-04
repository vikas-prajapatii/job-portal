import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

// Layouts
import UserLayout from './components/user/layout/UserLayout';
import DashboardLayout from './components/employer/layout/DashboardLayout';
import AdminLayout from './components/admin/layout/AdminLayout';

// Auth Pages
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';
import ForgotPassword from './pages/auth/ForgotPassword';
import LandingPage from './pages/LandingPage';

// User (Job Seeker) Pages
import UserJobs from './pages/user/Jobs';
import UserJobDetails from './pages/user/JobDetails';
import ApplyJob from './pages/user/ApplyJob';
import UserApplications from './pages/user/Applications';
import UserResumes from './pages/user/Resumes';
import ResumeEdit from './pages/user/ResumeEdit';
import ResumeView from './pages/user/ResumeView';
import SavedJobs from './pages/user/SavedJobs';
import UserProfile from './pages/user/Profile';
import AIMatch from './pages/user/AIMatch';
import AITools from './pages/user/AITools';
import UserSettings from './pages/user/Settings';

// Employer Pages
import EmployerDashboard from './pages/employer/Dashboard';
import EmployerJobs from './pages/employer/Jobs';
import CreateJob from './pages/employer/CreateJob';
import EditJob from './pages/employer/EditJob';
import EmployerApplications from './pages/employer/Applications';
import ApplicationScreeningPage from './pages/employer/ApplicationScreeningPage';
import AIScreening from './pages/employer/AiScreening/AIScreening';
import Candidates from './pages/employer/Candidates';
import CompanyProfile from './pages/employer/CompanyProfile';
import EmployerBilling from './pages/employer/Billing';
import EmployerSettings from './pages/employer/Settings';
import EmployerMessages from './pages/employer/Messages';

// Admin Pages
import AdminDashboard from './pages/admin/Dashboard';
import AdminUsers from './pages/admin/Users';
import AdminCompanies from './pages/admin/Companies';
import AdminJobs from './pages/admin/Jobs';
import AdminJobMeta from './pages/admin/JobMeta';
import AdminSubscriptions from './pages/admin/Subscriptions';
import AdminSettings from './pages/admin/Settings';

// Guards
import ProtectedRoute from './components/auth/ProtectedRoute';
import RoleBasedRoute from './components/auth/RoleBasedRoute';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />

        {/* Protected Candidate Routes (under UserLayout) */}
        <Route element={<RoleBasedRoute allowedRoles={["ROLE_JOB_SEEKER", "ROLE_USER"]} />}>
          <Route element={<UserLayout />}>
            <Route path="/jobs" element={<UserJobs />} />
            <Route path="/jobs/:id" element={<UserJobDetails />} />
            <Route path="/apply/:jobId" element={<ApplyJob />} />
            <Route path="/applications" element={<UserApplications />} />
            <Route path="/resumes" element={<UserResumes />} />
            <Route path="/resumes/create" element={<ResumeEdit />} />
            <Route path="/resumes/:id" element={<ResumeEdit />} />
            <Route path="/resumes/:id/view" element={<ResumeView />} />
            <Route path="/saved-jobs" element={<SavedJobs />} />
            <Route path="/profile" element={<UserProfile />} />
            <Route path="/ai-match" element={<AIMatch />} />
            <Route path="/ai-tools" element={<AITools />} />
            <Route path="/settings" element={<UserSettings />} />
          </Route>
        </Route>

        {/* Protected Employer Routes (under DashboardLayout) */}
        <Route element={<RoleBasedRoute allowedRoles={["ROLE_EMPLOYER"]} />}>
          <Route element={<DashboardLayout />}>
            <Route path="/employer" element={<Navigate to="/employer/dashboard" replace />} />
            <Route path="/employer/dashboard" element={<EmployerDashboard />} />
            <Route path="/employer/jobs" element={<EmployerJobs />} />
            <Route path="/employer/jobs/create" element={<CreateJob />} />
            <Route path="/employer/jobs/edit/:id" element={<EditJob />} />
            <Route path="/employer/applications" element={<EmployerApplications />} />
            <Route path="/employer/applications/:id" element={<ApplicationScreeningPage />} />
            <Route path="/employer/ai-screening" element={<AIScreening />} />
            <Route path="/employer/candidates" element={<Candidates />} />
            <Route path="/employer/company" element={<CompanyProfile />} />
            <Route path="/employer/billing" element={<EmployerBilling />} />
            <Route path="/employer/settings" element={<EmployerSettings />} />
            <Route path="/employer/messages" element={<EmployerMessages />} />
          </Route>
        </Route>

        {/* Protected Admin Routes (under AdminLayout) */}
        <Route element={<RoleBasedRoute allowedRoles={["ROLE_ADMIN"]} />}>
          <Route element={<AdminLayout />}>
            <Route path="/admin" element={<Navigate to="/admin/dashboard" replace />} />
            <Route path="/admin/dashboard" element={<AdminDashboard />} />
            <Route path="/admin/users" element={<AdminUsers />} />
            <Route path="/admin/companies" element={<AdminCompanies />} />
            <Route path="/admin/jobs" element={<AdminJobs />} />
            <Route path="/admin/job-meta" element={<AdminJobMeta />} />
            <Route path="/admin/subscriptions" element={<AdminSubscriptions />} />
            <Route path="/admin/settings" element={<AdminSettings />} />
          </Route>
        </Route>

        {/* Catch-all Redirect */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
