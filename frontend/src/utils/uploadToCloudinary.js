const CLOUD_NAME = "dcpesbd8q"
const UPLOAD_PRESET = "job-portal"

/**
 * Uploads a file to Cloudinary using an unsigned upload preset.
 * @param {File} file - The image file to upload
 * @returns {Promise<string>} The secure URL of the uploaded image
 */
export async function uploadToCloudinary(file) {
  if (!CLOUD_NAME || !UPLOAD_PRESET) {
    throw new Error("Cloudinary is not configured. Set VITE_CLOUDINARY_CLOUD_NAME and VITE_CLOUDINARY_UPLOAD_PRESET in your .env file.")
  }

  const formData = new FormData()
  formData.append("file", file)
  formData.append("upload_preset", UPLOAD_PRESET)
  formData.append("folder", "job-portal/avatars")

  const res = await fetch(`https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`, {
    method: "POST",
    body: formData,
  })

  if (!res.ok) {
    const err = await res.json().catch(() => ({}))
    throw new Error(err.error?.message || "Image upload failed")
  }

  const data = await res.json()
  return data.secure_url
}
