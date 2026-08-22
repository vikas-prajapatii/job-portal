import { Link } from "react-router-dom"
import { Button } from "./ui/button"
import { CheckCircle2, Users, ArrowUpRight } from "lucide-react"

export default function ForEmployers() {
  const benefits = [
    "Better candidate matching with AI-powered screening",
    "Reduced hiring noise and irrelevant applications",
    "Faster hiring process with qualified candidates"
  ]

  return (
    <section id="employers" className="py-20 bg-[#050D11] border-b border-slate-900/60 relative">
      <div className="container mx-auto px-4">
        <div className="grid gap-12 md:grid-cols-2 items-center max-w-6xl mx-auto">
          {/* Left: Premium visual mock */}
          <div className="flex items-center justify-center order-2 md:order-1">
            <div className="w-full max-w-md bg-[#0C171C] border border-slate-900 rounded-2xl p-6 shadow-2xl relative overflow-hidden group">
              <div className="absolute -bottom-10 -left-10 w-32 h-32 bg-teal-500/10 rounded-full blur-2xl" />
              
              <div className="flex items-center justify-between mb-5 border-b border-slate-900/60 pb-3">
                <h4 className="text-sm font-bold text-white flex items-center gap-2">
                  <Users className="h-4 w-4 text-cyan-400" />
                  AI Screening Dashboard
                </h4>
                <span className="text-[10px] text-slate-400">Total Applicants: 24</span>
              </div>

              <div className="space-y-3.5">
                {/* Applicant 1 */}
                <div className="bg-slate-950/50 p-3.5 rounded-xl border border-slate-900 flex items-center justify-between">
                  <div>
                    <p className="text-xs font-semibold text-white">Rohan Sharma</p>
                    <p className="text-[10px] text-slate-500">Java Developer · 4y Exp</p>
                  </div>
                  <div className="text-right">
                    <span className="text-xs bg-cyan-950 text-cyan-400 px-2 py-0.5 rounded border border-cyan-500/20 font-bold font-mono">
                      94% Match
                    </span>
                  </div>
                </div>

                {/* Applicant 2 */}
                <div className="bg-slate-950/50 p-3.5 rounded-xl border border-slate-900 flex items-center justify-between">
                  <div>
                    <p className="text-xs font-semibold text-white">Sneha Patel</p>
                    <p className="text-[10px] text-slate-500">React Architect · 6y Exp</p>
                  </div>
                  <div className="text-right">
                    <span className="text-xs bg-cyan-950 text-cyan-400 px-2 py-0.5 rounded border border-cyan-500/20 font-bold font-mono">
                      89% Match
                    </span>
                  </div>
                </div>

                {/* Footer status link */}
                <p className="text-[11px] text-slate-500 text-center flex items-center justify-center gap-1 cursor-pointer hover:text-cyan-400 transition-colors pt-1">
                  View all 24 applicants
                  <ArrowUpRight className="h-3 w-3" />
                </p>
              </div>
            </div>
          </div>

          {/* Right: Content */}
          <div className="flex flex-col gap-6 order-1 md:order-2">
            <div className="inline-flex items-center gap-2 w-fit rounded-full border border-cyan-500/30 bg-cyan-950/30 px-3.5 py-1.5 text-sm text-cyan-400 font-medium">
              For Employers
            </div>

            <h2 className="text-3xl md:text-4xl font-bold text-white">
              Find the Right Talent, Faster
            </h2>

            <p className="text-lg text-slate-400">
              Connect with pre-qualified candidates who match your requirements through our intelligent matching system.
            </p>

            {/* Benefits list */}
            <div className="flex flex-col gap-3">
              {benefits.map((benefit, index) => (
                <div key={index} className="flex items-start gap-3">
                  <CheckCircle2 className="w-5 h-5 text-cyan-400 mt-0.5 flex-shrink-0" />
                  <span className="text-slate-300 text-sm sm:text-base">{benefit}</span>
                </div>
              ))}
            </div>

            {/* CTA */}
            <div className="pt-2">
              <Link to="/register">
                <Button size="lg" className="bg-cyan-500 hover:bg-cyan-600 text-slate-950 font-semibold rounded-xl">
                  Post a Job
                </Button>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
