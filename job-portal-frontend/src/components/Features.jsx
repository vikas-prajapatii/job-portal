import { Card, CardContent } from "./ui/card"
import { FileText, Target, Zap, BarChart3, Users } from "lucide-react"

export default function Features() {
  const features = [
    {
      icon: FileText,
      title: "AI Resume Builder",
      description: "Create ATS-friendly resumes optimized for applicant tracking systems with AI assistance."
    },
    {
      icon: Target,
      title: "Smart Job Matching",
      description: "Get personalized job recommendations based on your skills, experience, and preferences."
    },
    {
      icon: Zap,
      title: "One-Click Apply",
      description: "Apply to multiple jobs instantly with pre-filled information and saved preferences."
    },
    {
      icon: BarChart3,
      title: "Application Tracking",
      description: "Monitor all your applications in one dashboard with real-time status updates."
    },
    {
      icon: Users,
      title: "Recruiter Insights",
      description: "Understand what recruiters are looking for with AI-powered industry insights."
    }
  ]

  return (
    <section id="features" className="py-20 bg-[#050D11] relative">
      <div className="container mx-auto px-4">
        {/* Section Header */}
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-white mb-3">
            Key Features
          </h2>
          <p className="text-lg text-slate-400 max-w-2xl mx-auto">
            Everything you need to streamline your job search and matching process
          </p>
        </div>

        {/* Features Grid */}
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3 max-w-6xl mx-auto">
          {features.map((feature, index) => (
            <Card key={index} className="border-slate-900 bg-[#0C171C] hover:border-cyan-500/30 transition-all duration-300 shadow-xl group">
              <CardContent className="pt-6 pb-6 px-6">
                {/* Icon */}
                <div className="mb-4 w-12 h-12 rounded-lg bg-cyan-500/10 flex items-center justify-center group-hover:bg-cyan-500/20 transition-colors">
                  <feature.icon className="w-6 h-6 text-cyan-400" />
                </div>

                {/* Title */}
                <h3 className="text-lg font-semibold text-white mb-2 group-hover:text-cyan-400 transition-colors">
                  {feature.title}
                </h3>

                {/* Description */}
                <p className="text-sm text-slate-400 leading-relaxed">
                  {feature.description}
                </p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  )
}
