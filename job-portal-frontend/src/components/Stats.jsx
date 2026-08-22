export default function Stats() {
  const stats = [
    {
      value: "10,000+",
      label: "Active Jobs"
    },
    {
      value: "5,000+",
      label: "Registered Candidates"
    },
    {
      value: "2x",
      label: "Faster Hiring Rate"
    }
  ]

  return (
    <section className="bg-[#071115] py-16 border-b border-slate-900/60 relative">
      <div className="container mx-auto px-4">
        <div className="grid gap-8 md:grid-cols-3 max-w-4xl mx-auto">
          {stats.map((stat, index) => (
            <div key={index} className="text-center group">
              {/* Value */}
              <div className="text-4xl md:text-5xl font-extrabold text-cyan-400 mb-2 group-hover:scale-105 transition-transform duration-300">
                {stat.value}
              </div>

              {/* Label */}
              <div className="text-sm text-slate-450 font-medium tracking-wide">
                {stat.label}
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
