import { useState } from "react"
import { format } from "date-fns"
import { CalendarIcon, Link2, HelpCircle } from "lucide-react"
import { Card, CardContent } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import { Textarea } from "@/components/ui/textarea"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { cn } from "@/lib/utils"

export default function AdditionalDetails({
  expectedSalary,
  setExpectedSalary,
  availableFrom,
  setAvailableFrom,
  portfolioUrl,
  setPortfolioUrl,
  screeningAnswer,
  setScreeningAnswer,
}) {
  const [open, setOpen] = useState(false)

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-2xl font-bold text-slate-900 mb-2">Additional Details</h2>
        <p className="text-slate-600">Provide salary expectations, availability, and extra links or notes</p>
      </div>

      <Card>
        <CardContent className="p-6 space-y-6">
          {/* Expected Salary */}
          <div className="space-y-2">
            <Label className="text-sm font-semibold text-slate-700">Expected Monthly Salary (INR)</Label>
            <div className="relative">
              <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500 text-sm font-medium">₹</span>
              <Input
                type="number"
                min="0"
                placeholder="e.g. 80000"
                value={expectedSalary}
                onChange={(e) => setExpectedSalary(e.target.value)}
                className="pl-7 rounded-xl focus:ring-brand"
              />
            </div>
          </div>

          {/* Available From */}
          <div className="space-y-2">
            <Label className="text-sm font-semibold text-slate-700">Available From</Label>
            <Popover open={open} onOpenChange={setOpen}>
              <PopoverTrigger asChild>
                <Button
                  variant="outline"
                  className={cn(
                    "w-full justify-start text-left font-normal rounded-xl border-slate-200 hover:bg-slate-50",
                    !availableFrom && "text-muted-foreground"
                  )}
                >
                  <CalendarIcon className="mr-2 size-4 text-slate-400" />
                  {availableFrom ? format(availableFrom, "PPP") : "Pick a date"}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0" align="start">
                <Calendar
                  mode="single"
                  selected={availableFrom}
                  onSelect={(date) => {
                    setAvailableFrom(date)
                    setOpen(false)
                  }}
                  disabled={(date) => date < new Date(new Date().setHours(0, 0, 0, 0))}
                  initialFocus
                />
              </PopoverContent>
            </Popover>
          </div>

          {/* Portfolio or Video Resume URL */}
          <div className="space-y-2">
            <Label className="text-sm font-semibold text-slate-700 flex items-center gap-1.5">
              <Link2 className="h-4 w-4 text-slate-400" />
              Portfolio / Video Resume URL
            </Label>
            <Input
              type="url"
              placeholder="e.g. https://myportfolio.com or https://loom.com/my-video"
              value={portfolioUrl}
              onChange={(e) => setPortfolioUrl(e.target.value)}
              className="rounded-xl focus:ring-brand"
            />
            <p className="text-xs text-slate-400">Share your website link or a Loom/YouTube video presentation of yourself</p>
          </div>

          {/* Custom Screening Question */}
          <div className="space-y-2">
            <Label className="text-sm font-semibold text-slate-700 flex items-center gap-1.5">
              <HelpCircle className="h-4 w-4 text-slate-400" />
              Why should we hire you for this role?
            </Label>
            <Textarea
              placeholder="Briefly describe what makes you a great fit for this position..."
              value={screeningAnswer}
              onChange={(e) => setScreeningAnswer(e.target.value)}
              className="min-h-[120px] rounded-xl focus:ring-brand"
              maxLength={1000}
            />
            <div className="flex justify-end text-xs text-slate-400">
              <span>{screeningAnswer.length}/1000 characters</span>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  )
}
