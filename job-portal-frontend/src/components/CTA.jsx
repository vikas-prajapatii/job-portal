import { Link } from "react-router-dom"
import { Button } from "./ui/button"
import { ArrowRight } from "lucide-react"

export default function CTA() {
  return (
    <section className="py-20 bg-[#050D11] relative overflow-hidden">
      {/* Background glowing blob */}
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[350px] h-[350px] bg-cyan-950/20 rounded-full blur-[90px] pointer-events-none" />

      <div className="container mx-auto px-4 relative z-10">
        <div className="max-w-3xl mx-auto text-center">
          {/* Headline */}
          <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold text-white mb-4">
            Your Career, Powered by AI
          </h2>

          {/* Subtext */}
          <p className="text-lg text-slate-400 mb-8 max-w-2xl mx-auto">
            Join thousands of job seekers and employers who are already using Noir Hire to automate resume building and find matching roles.
          </p>

          {/* CTA Buttons */}
          <div className="flex flex-wrap gap-4 justify-center">
            <Link to="/register">
              <Button size="lg" className="text-base bg-cyan-500 hover:bg-cyan-600 text-slate-950 font-semibold rounded-xl px-8 shadow-[0_0_15px_rgba(6,182,212,0.3)]">
                Create Account
                <ArrowRight className="ml-2 h-4 w-4" />
              </Button>
            </Link>
            <Link to="/register">
              <Button size="lg" variant="outline" className="text-base rounded-xl border-slate-800 text-slate-300 hover:bg-slate-900 hover:text-white px-8">
                Explore Jobs
              </Button>
            </Link>
          </div>
        </div>
      </div>
    </section>
  )
}
