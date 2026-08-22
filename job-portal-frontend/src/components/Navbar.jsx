import { Link } from "react-router-dom"
import { Button } from "./ui/button"

export default function Navbar() {
  return (
    <nav className="sticky top-0 z-50 w-full border-b border-slate-900 bg-[#050D11]/90 backdrop-blur-md">
      <div className="container mx-auto flex h-16 items-center justify-between px-4 sm:px-6">
        {/* Logo */}
        <div className="flex items-center">
          <Link to="/" className="text-xl font-bold tracking-wider text-white">
            Noir<span className="text-cyan-400">Hire</span>
          </Link>
        </div>

        {/* Navigation Links */}
        <div className="hidden md:flex items-center gap-8">
          <a
            href="#jobs"
            className="text-sm font-medium text-slate-400 hover:text-cyan-400 transition-colors"
          >
            Jobs
          </a>
          <a
            href="#employers"
            className="text-sm font-medium text-slate-400 hover:text-cyan-400 transition-colors"
          >
            For Employers
          </a>
          <a
            href="#features"
            className="text-sm font-medium text-slate-400 hover:text-cyan-400 transition-colors"
          >
            Features
          </a>
        </div>

        {/* Action Buttons */}
        <div className="flex items-center gap-3">
          <Link to="/login">
            <Button variant="ghost" className="text-sm text-slate-300 hover:text-white hover:bg-slate-900">
              Login
            </Button>
          </Link>
          <Link to="/register">
            <Button className="text-sm bg-cyan-500 hover:bg-cyan-600 text-slate-950 font-semibold rounded-xl">
              Get Started
            </Button>
          </Link>
        </div>
      </div>
    </nav>
  )
}
