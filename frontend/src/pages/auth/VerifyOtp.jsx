import { useEffect, useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { useDispatch } from "react-redux"
import { useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod"
import * as z from "zod"

import AuthLayout from "../../components/auth/AuthLayout"
import api from "../../store/api"
import { Button } from "../../components/ui/button"
import { Input } from "../../components/ui/input"
import { Label } from "../../components/ui/label"
import { ArrowLeft, KeyRound, AlertCircle, Loader2, CheckCircle2 } from "lucide-react"
import { fetchCurrentUser } from "../../store/user/userThunk"

const verifyOtpSchema = z.object({
  otp: z.string().min(6, "OTP must be 6 digits").max(6, "OTP must be 6 digits"),
})

export default function VerifyOtp() {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  
  const [email, setEmail] = useState("")
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)
  const [success, setSuccess] = useState(false)

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(verifyOtpSchema),
    defaultValues: {
      otp: "",
    },
  })

  // Parse email from URL query param on mount
  useEffect(() => {
    const params = new URLSearchParams(window.location.search)
    const emailParam = params.get("email")
    if (emailParam) {
      setEmail(emailParam)
    } else {
      setError("Invalid access: email address is missing.")
    }
  }, [])

  const onSubmit = async (data) => {
    if (!email) {
      setError("Email address is missing.")
      return
    }

    setIsLoading(true)
    setError(null)

    try {
      const response = await api.post("/auth/verify-otp", {
        email,
        otp: data.otp,
      })

      // Store accessToken on success
      if (response.data && response.data.jwt) {
        localStorage.setItem("accessToken", response.data.jwt)
        dispatch(fetchCurrentUser())
        setSuccess(true)
        setTimeout(() => {
          navigate("/jobs")
        }, 1500)
      } else {
        setError("Failed to authenticate after verification. Please try logging in.")
      }
    } catch (err) {
      setError(
        err.response?.data?.message || "Invalid or expired OTP. Please try again."
      )
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <AuthLayout
      title="Verify your account"
      description="Enter the 6-digit OTP sent to your email"
      footerText="Remember your password?"
      footerLink="/login"
      footerLinkText="Back to login"
    >
      {!success ? (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
          {/* Helper Text */}
          <div className="flex items-start gap-3 p-4 bg-brand/5 border border-brand/20 rounded-lg">
            <div className="flex-shrink-0">
              <div className="w-10 h-10 rounded-full bg-brand/10 flex items-center justify-center">
                <KeyRound className="h-5 w-5 text-brand" />
              </div>
            </div>
            <div className="flex-1">
              <p className="text-sm font-medium text-brand">One-Time Password</p>
              <p className="text-sm text-brand/70 mt-0.5">
                We sent a 6-digit verification code to <span className="font-semibold">{email}</span>.
              </p>
            </div>
          </div>

          {/* Error Alert */}
          {error && (
            <div className="flex items-center gap-3 p-4 bg-red-50 border border-red-200 rounded-lg animate-in slide-in-from-top-2">
              <div className="flex-shrink-0">
                <div className="w-10 h-10 rounded-full bg-red-100 flex items-center justify-center">
                  <AlertCircle className="h-5 w-5 text-red-600" />
                </div>
              </div>
              <div className="flex-1">
                <p className="text-sm font-medium text-red-900">Verification Failed</p>
                <p className="text-sm text-red-700 mt-0.5">{error}</p>
              </div>
            </div>
          )}

          {/* OTP Input */}
          <div className="space-y-2">
            <Label htmlFor="otp" className="text-sm font-semibold text-slate-700">
              6-Digit Code
            </Label>
            <div className="relative group">
              <div className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 group-focus-within:text-brand transition-colors">
                <KeyRound className="h-4 w-4" />
              </div>
              <Input
                id="otp"
                type="text"
                placeholder="123456"
                maxLength={6}
                {...register("otp")}
                className={cn(
                  "pl-10 h-11 transition-all tracking-widest text-center text-lg font-bold",
                  errors.otp
                    ? "border-red-300 focus-visible:ring-red-500"
                    : "focus-visible:ring-brand focus-visible:border-brand"
                )}
                disabled={isLoading || !email}
              />
            </div>
            {errors.otp && (
              <p className="text-xs text-red-600 flex items-center gap-1.5 mt-1.5 animate-in slide-in-from-top-1">
                <AlertCircle className="h-3 w-3" />
                {errors.otp.message}
              </p>
            )}
          </div>

          {/* Verify Button */}
          <Button
            type="submit"
            className="w-full h-11 bg-brand hover:bg-brand/90 shadow-md hover:shadow-lg transition-all duration-200"
            disabled={isLoading || !email}
          >
            {isLoading ? (
              <>
                <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                Verifying OTP...
              </>
            ) : (
              "Verify and Log In"
            )}
          </Button>

          {/* Back to Login Link */}
          <Link
            to="/login"
            className="flex items-center justify-center gap-2 text-sm font-medium text-slate-600 hover:text-slate-900 transition-colors group"
          >
            <ArrowLeft className="h-4 w-4 group-hover:-translate-x-0.5 transition-transform" />
            Back to login
          </Link>
        </form>
      ) : (
        <div className="space-y-6 animate-in fade-in slide-in-from-bottom-4 duration-500 text-center py-6">
          <div className="relative">
            <div className="absolute inset-0 bg-green-400/20 rounded-full blur-2xl"></div>
            <div className="relative w-20 h-20 mx-auto rounded-full bg-gradient-to-br from-green-100 to-green-50 border-2 border-green-200 flex items-center justify-center">
              <CheckCircle2 className="h-10 w-10 text-green-600" />
            </div>
          </div>
          <div className="space-y-2">
            <h3 className="text-xl font-bold text-slate-900">Email Verified!</h3>
            <p className="text-sm text-slate-600">Your account is active. Redirecting you to the portal...</p>
          </div>
        </div>
      )}
    </AuthLayout>
  )
}

function cn(...inputs) {
  return inputs.filter(Boolean).join(" ")
}
