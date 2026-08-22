export default function Footer() {
  const footerLinks = [
    { label: "About", href: "#about" },
    { label: "Contact", href: "#contact" },
    { label: "Privacy Policy", href: "#privacy" },
    { label: "Terms", href: "#terms" }
  ]

  return (
    <footer className="border-t border-slate-900/60 bg-[#050D11]">
      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col md:flex-row items-center justify-between gap-4">
          {/* Logo */}
          <div className="text-sm text-slate-500">
            © 2026 <span className="font-semibold text-white tracking-wide">Noir<span className="text-cyan-450">Hire</span></span>. All rights reserved.
          </div>

          {/* Links */}
          <div className="flex items-center gap-6">
            {footerLinks.map((link, index) => (
              <a
                key={index}
                href={link.href}
                className="text-sm text-slate-400 hover:text-cyan-400 transition-colors"
              >
                {link.label}
              </a>
            ))}
          </div>
        </div>
      </div>
    </footer>
  )
}
