import { Link } from "react-router-dom"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../ui/card"
import { Sparkles } from "lucide-react"
import authSideImage from "../../assets/auth_side_image.jpg"

const AUTH_IMAGE = authSideImage

export default function AuthLayout({ title, description, children, footerText, footerLink, footerLinkText }) {
  return (
    <div className="min-h-screen flex items-center justify-center bg-linear-to-br from-slate-50 via-brand/5 to-slate-50 relative overflow-hidden">
        {/* Decorative blobs */}
        <div className="absolute inset-0 overflow-hidden pointer-events-none">
          <div className="absolute -top-40 -right-40 w-80 h-80 bg-brand/10 rounded-full blur-3xl" />
          <div className="absolute -bottom-40 -left-40 w-80 h-80 bg-purple-400/10 rounded-full blur-3xl" />
        </div>

        <div className="relative flex flex-col items-center justify-center min-h-screen p-6 sm:p-10">
          <div className="w-full max-w-md">

            {/* Logo */}
            <div className="text-center mb-8 animate-in fade-in slide-in-from-top-4 duration-500">
              <Link to="/" className="inline-flex items-center gap-2 group">
                <div className="relative">
                  <div className="absolute inset-0 bg-brand/20 rounded-lg blur-xl group-hover:bg-brand/30 transition-all" />
                  <div className="relative bg-brand p-2 rounded-lg">
                    <Sparkles className="w-6 h-6 text-white" />
                  </div>
                </div>
                <h1 className="text-3xl font-bold text-slate-900">
                  Noir<span className="text-brand">Hire</span>
                </h1>
              </Link>
              <p className="text-sm text-slate-600 mt-2">AI-Powered Job Portal</p>
            </div>

            {/* Auth Card */}
            <Card className="border-slate-200/60 shadow-xl backdrop-blur-sm bg-white/80 animate-in fade-in slide-in-from-bottom-4 duration-500">
              <CardHeader className="space-y-2 pb-4">
                <CardTitle className="text-2xl font-bold text-center bg-linear-to-r from-slate-900 to-slate-700 bg-clip-text text-transparent">
                  {title}
                </CardTitle>
                {description && (
                  <CardDescription className="text-center text-base">
                    {description}
                  </CardDescription>
                )}
              </CardHeader>
              <CardContent className="pt-2">{children}</CardContent>
            </Card>

            {/* Footer link */}
            {footerText && (
              <p className="text-center text-sm text-slate-600 mt-6 animate-in fade-in slide-in-from-bottom-4 duration-700">
                {footerText}{" "}
                <Link
                  to={footerLink}
                  className="font-semibold text-brand hover:text-brand/80 transition-colors underline-offset-4 hover:underline"
                >
                  {footerLinkText}
                </Link>
              </p>
            )}

            {/* Trust indicators (shown on mobile where image is hidden) */}
            <div className="flex lg:hidden items-center justify-center gap-6 mt-8 text-xs text-slate-500 animate-in fade-in duration-1000">
              {[
                { dot: "bg-green-500", text: "Secure" },
                { dot: "bg-blue-500", text: "Fast" },
                { dot: "bg-purple-500", text: "Private" },
              ].map(({ dot, text }) => (
                <div key={text} className="flex items-center gap-1.5">
                  <div className={`w-1.5 h-1.5 rounded-full ${dot}`} />
                  <span>{text}</span>
                </div>
              ))}
            </div>

          </div>
        </div>
      </div>
  )
}
