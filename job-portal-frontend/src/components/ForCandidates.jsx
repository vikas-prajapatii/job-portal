import { Link } from "react-router-dom"
import { Button } from "./ui/button"
import { CheckCircle2, Award, Zap } from "lucide-react"

export default function ForCandidates() {
  const benefits = [
    "ATS-friendly resumes that pass automated screening",
    "Faster shortlisting with AI-optimized applications",
    "Career insights and personalized recommendations"
  ]

  return (
    <section className="bg-[#071115] py-20 border-b border-slate-900/60 relative">
      <div className="container mx-auto px-4">
        <div className="grid gap-12 md:grid-cols-2 items-center max-w-6xl mx-auto">
          {/* Left: Content */}
          <div className="flex flex-col gap-6">
            <div className="inline-flex items-center gap-2 w-fit rounded-full border border-cyan-500/30 bg-cyan-950/30 px-3.5 py-1.5 text-sm text-cyan-400 font-medium">
              For Job Seekers
            </div>

            <h2 className="text-3xl md:text-4xl font-bold text-white">
              Land Your Dream Job Faster
            </h2>

            <p className="text-lg text-slate-400">
              Our AI-powered platform helps you create standout applications and discover opportunities that match your skills.
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
                  Start Building Resume
                </Button>
              </Link>
            </div>
          </div>

          {/* Right: Premium glassmorphic match checker mock */}
          <div className="flex items-center justify-center">
            <div className="w-full max-w-md bg-[#0C171C] border border-slate-900 rounded-2xl p-6 shadow-2xl relative overflow-hidden group">
              <div className="absolute -top-10 -right-10 w-32 h-32 bg-cyan-500/10 rounded-full blur-2xl" />
              
              <div className="flex items-center justify-between mb-6">
                <h4 className="text-sm font-bold text-white flex items-center gap-2">
                  <Award className="h-4 w-4 text-cyan-400" />
                  AI Resume Score Checker
                </h4>
                <span className="text-xs bg-cyan-950 text-cyan-400 px-2 py-0.5 rounded border border-cyan-500/20 font-mono">Verifying</span>
              </div>

              <div className="space-y-4">
                <div className="bg-slate-950/40 p-4 rounded-xl border border-slate-900 flex items-center justify-between">
                  <div>
                    <p className="text-xs text-slate-400">Format & Structure</p>
                    <p className="text-sm font-semibold text-white">ATS Compatibility</p>
                  </div>
                  <span className="text-sm font-bold text-green-400">Excellent</span>
                </div>

                <div className="bg-slate-950/40 p-4 rounded-xl border border-slate-900 flex items-center justify-between">
                  <div>
                    <p className="text-xs text-slate-400">Keyword Density</p>
                    <p className="text-sm font-semibold text-white">Full-Stack Skills</p>
                  </div>
                  <span className="text-sm font-bold text-cyan-400">Matched 95%</span>
                </div>

                <div className="pt-2 text-center">
                  <div className="inline-block bg-cyan-500/10 text-cyan-400 px-4 py-2 rounded-xl text-xs font-semibold border border-cyan-500/20">
                    ✨ Ready to apply — 95/100 Rating
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
