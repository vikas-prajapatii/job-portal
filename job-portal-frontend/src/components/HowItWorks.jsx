import { Card, CardContent } from "./ui/card"
import { UserCircle, Search, Send } from "lucide-react"

export default function HowItWorks() {
  const steps = [
    {
      icon: UserCircle,
      title: "Create your profile",
      description: "Build an AI-optimized resume in minutes with our smart builder."
    },
    {
      icon: Search,
      title: "Discover relevant jobs",
      description: "Get matched with opportunities that fit your skills and experience."
    },
    {
      icon: Send,
      title: "Apply & track applications",
      description: "Apply with one click and track all your applications in one place."
    }
  ]

  return (
    <section className="bg-[#071115] py-20 border-y border-slate-900/60 relative">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-white mb-3">
            How It Works
          </h2>
          <p className="text-lg text-slate-400 max-w-2xl mx-auto">
            Get started on your career journey in three simple steps
          </p>
        </div>

        {/* Steps Grid */}
        <div className="grid gap-8 md:grid-cols-3 max-w-5xl mx-auto">
          {steps.map((step, index) => (
            <Card key={index} className="relative overflow-hidden border-slate-900 bg-[#0C171C] hover:border-cyan-500/30 transition-all duration-300 shadow-xl group">
              {/* Step number badge */}
              <div className="absolute top-4 right-4 w-8 h-8 rounded-full bg-cyan-950 text-cyan-400 flex items-center justify-center text-sm font-semibold border border-cyan-500/20">
                {index + 1}
              </div>

              <CardContent className="pt-8 pb-6 px-6">
                {/* Icon */}
                <div className="mb-4 w-12 h-12 rounded-lg bg-cyan-500/10 flex items-center justify-center group-hover:bg-cyan-500/20 transition-colors">
                  <step.icon className="w-6 h-6 text-cyan-400" />
                </div>

                {/* Title */}
                <h3 className="text-xl font-semibold text-white mb-2 group-hover:text-cyan-400 transition-colors">
                  {step.title}
                </h3>

                {/* Description */}
                <p className="text-slate-400 text-sm leading-relaxed">
                  {step.description}
                </p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  )
}
