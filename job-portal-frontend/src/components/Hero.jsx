import { Link } from "react-router-dom"
import { Button } from "./ui/button"
import { Sparkles, Briefcase, CheckCircle2, UserCheck, ShieldAlert } from "lucide-react"

export default function Hero() {
  return (
    <section className="relative overflow-hidden bg-[#050D11] pt-24 pb-20 md:pt-32 md:pb-28">
      {/* Background radial gradients for glowing aesthetic */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[500px] h-[500px] bg-cyan-950/10 rounded-full blur-[120px] pointer-events-none" />
      <div className="absolute top-1/3 left-10 w-[300px] h-[300px] bg-teal-950/10 rounded-full blur-[100px] pointer-events-none" />

      <div className="container mx-auto px-4 md:px-6 relative z-10">
        <div className="grid gap-12 lg:grid-cols-2 items-center">
          {/* Left: Text + CTAs */}
          <div className="flex flex-col gap-6">
            {/* AI Badge */}
            <div className="inline-flex items-center gap-2 w-fit rounded-full border border-cyan-500/30 bg-cyan-950/30 px-3.5 py-1.5 text-sm text-cyan-400 font-medium tracking-wide shadow-[0_0_15px_-3px_rgba(6,182,212,0.2)]">
              <Sparkles className="h-4 w-4 text-cyan-400 animate-pulse" />
              <span>Next-Gen AI Job Matching</span>
            </div>

            {/* Headline */}
            <h1 className="text-4xl sm:text-5xl lg:text-6xl font-bold tracking-tight text-white leading-[1.1]">
              Elevate Your Career with <span className="bg-linear-to-r from-cyan-400 to-teal-400 bg-clip-text text-transparent">Noir Hire</span>
            </h1>

            {/* Subheading */}
            <p className="text-base sm:text-lg text-slate-400 max-w-xl leading-relaxed">
              Ditch the generic job boards. Noir Hire leverages deep neural matching to connect engineers with verified tech roles instantly and analyzes skills gaps in real-time.
            </p>

            {/* CTA Buttons */}
            <div className="flex flex-wrap gap-4 pt-2">
              <Link to="/register">
                <Button size="lg" className="text-base rounded-xl bg-cyan-500 hover:bg-cyan-600 text-slate-950 font-semibold px-8 shadow-[0_0_20px_rgba(6,182,212,0.3)]">
                  Get Started Free
                </Button>
              </Link>
              <Link to="/register">
                <Button size="lg" variant="outline" className="text-base rounded-xl border-slate-800 text-slate-300 hover:bg-slate-900 hover:text-white px-8">
                  Post a Job
                </Button>
              </Link>
            </div>

            {/* Trust indicators */}
            <div className="flex items-center gap-6 text-sm text-slate-500 pt-4 border-t border-slate-900/60 mt-2">
              <div className="flex items-center gap-2">
                <div className="h-2 w-2 rounded-full bg-cyan-500 animate-ping"></div>
                <span className="text-slate-400">Free forever for seekers</span>
              </div>
              <div className="flex items-center gap-2">
                <div className="h-2 w-2 rounded-full bg-teal-500"></div>
                <span className="text-slate-400">Verified Companies only</span>
              </div>
            </div>
          </div>

          {/* Right: Premium Interactive-looking Dashboard Illustration */}
          <div className="flex items-center justify-center lg:justify-end">
            <div className="relative w-full max-w-lg">
              {/* Decorative side lights */}
              <div className="absolute -inset-1 rounded-2xl bg-linear-to-r from-cyan-500 to-teal-500 opacity-20 blur-xl pointer-events-none" />
              
              {/* Main Card Box */}
              <div className="relative rounded-2xl border border-slate-800 bg-[#0C171C]/90 p-6 backdrop-blur-xl shadow-2xl">
                {/* Simulated Window controls */}
                <div className="flex items-center gap-1.5 mb-6 border-b border-slate-900/60 pb-4">
                  <div className="h-3 w-3 rounded-full bg-red-500/80" />
                  <div className="h-3 w-3 rounded-full bg-yellow-500/80" />
                  <div className="h-3 w-3 rounded-full bg-green-500/80" />
                  <span className="text-xs text-slate-500 font-mono ml-2">noir-matching-engine.sh</span>
                </div>

                <div className="space-y-6">
                  {/* Candidate row */}
                  <div className="flex items-center justify-between bg-slate-950/40 p-4 rounded-xl border border-slate-900">
                    <div className="flex items-center gap-3">
                      <div className="h-10 w-10 rounded-full bg-cyan-500/10 flex items-center justify-center border border-cyan-500/20">
                        <UserCheck className="h-5 w-5 text-cyan-400" />
                      </div>
                      <div>
                        <p className="text-sm font-semibold text-white">Alex Rivera</p>
                        <p className="text-xs text-slate-500">Full Stack Engineer</p>
                      </div>
                    </div>
                    <span className="text-xs bg-cyan-950/50 text-cyan-400 px-2.5 py-1 rounded-full font-mono border border-cyan-500/20">
                      Active
                    </span>
                  </div>

                  {/* Connected line with glowing match circle */}
                  <div className="relative flex justify-center py-1">
                    <div className="absolute top-1/2 left-0 right-0 h-px bg-slate-850" />
                    <div className="relative z-10 h-10 w-10 rounded-full bg-[#050D11] border border-cyan-500/30 flex items-center justify-center shadow-[0_0_15px_rgba(6,182,212,0.2)]">
                      <Sparkles className="h-5 w-5 text-cyan-400 animate-spin-slow" />
                    </div>
                  </div>

                  {/* Job target row */}
                  <div className="flex items-center justify-between bg-slate-950/40 p-4 rounded-xl border border-slate-900">
                    <div className="flex items-center gap-3">
                      <div className="h-10 w-10 rounded-full bg-teal-500/10 flex items-center justify-center border border-teal-500/20">
                        <Briefcase className="h-5 w-5 text-teal-400" />
                      </div>
                      <div>
                        <p className="text-sm font-semibold text-white">AI Systems Engineer</p>
                        <p className="text-xs text-slate-500">Noir Hire Tech Corp</p>
                      </div>
                    </div>
                    <span className="text-xs bg-teal-950/50 text-teal-400 px-2.5 py-1 rounded-full font-mono border border-teal-500/20">
                      ₹ 18 - 24 LPA
                    </span>
                  </div>

                  {/* AI Match Meter */}
                  <div className="pt-2">
                    <div className="flex justify-between items-center mb-2">
                      <span className="text-xs font-semibold text-slate-400">Match score compatibility</span>
                      <span className="text-xs font-bold text-cyan-400">97% Match</span>
                    </div>
                    <div className="w-full bg-slate-950 h-2.5 rounded-full overflow-hidden border border-slate-900">
                      <div className="bg-linear-to-r from-cyan-500 to-teal-400 h-full rounded-full w-[97%] shadow-[0_0_10px_rgba(6,182,212,0.5)]" />
                    </div>
                    <p className="text-[10px] text-slate-500 mt-2 italic text-center">AI analysis: Selected resume overlaps 9/10 core required backend skill sets.</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
