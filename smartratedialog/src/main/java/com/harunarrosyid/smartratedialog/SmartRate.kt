package com.harunarrosyid.smartratedialog // Sesuaikan dengan nama package library Anda

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object SmartRate {

    /**
     * Menampilkan Dialog Rating dengan opsi kustomisasi teks (Multi-bahasa)
     */
    fun show(
        context: Context,
        appName: String,
        supportEmail: String,
        packageName: String,
        // ==========================================
        // PARAMETER TEKS DENGAN NILAI DEFAULT
        // ==========================================
        titleText: String = "Enjoying $appName?",
        messageText: String = "If you like using our app, please take a moment to rate it!",
        btnRateText: String = "Rate Now",
        btnLaterText: String = "Later",
        btnFeedbackText: String = "Send Feedback" // Tombol tambahan untuk email keluhan
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(titleText)
        builder.setMessage(messageText)
        builder.setCancelable(false) // Mencegah dialog tertutup jika user klik area luar

        // 1. TOMBOL RATE (Buka Play Store)
        builder.setPositiveButton(btnRateText) { dialog, _ ->
            openPlayStore(context, packageName)
            dialog.dismiss()
        }

        // 2. TOMBOL LATER (Tutup Dialog)
        builder.setNegativeButton(btnLaterText) { dialog, _ ->
            dialog.dismiss()
        }

        // 3. TOMBOL FEEDBACK (Buka Aplikasi Email)
        // Jika Anda tidak ingin tombol ini muncul, Anda bisa menghapus blok setNeutralButton ini.
        if (supportEmail.isNotEmpty()) {
            builder.setNeutralButton(btnFeedbackText) { dialog, _ ->
                sendFeedbackEmail(context, supportEmail, appName)
                dialog.dismiss()
            }
        }

        // Tampilkan Dialog
        val dialog = builder.create()
        dialog.show()
    }

    // ====================================================
    // FUNGSI PENDUKUNG UNTUK MEMBUKA PLAY STORE
    // ====================================================
    private fun openPlayStore(context: Context, packageName: String) {
        try {
            // Coba buka menggunakan aplikasi Google Play Store langsung
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Jika aplikasi Play Store tidak ada di HP, buka lewat Browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    // ====================================================
    // FUNGSI PENDUKUNG UNTUK MENGIRIM EMAIL KELUHAN
    // ====================================================
    private fun sendFeedbackEmail(context: Context, email: String, appName: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Hanya aplikasi email yang akan merespon ini
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback for $appName")
            putExtra(Intent.EXTRA_TEXT, "Hello Support Team,\n\nI have some feedback regarding the app:\n")
        }
        try {
            context.startActivity(Intent.createChooser(intent, "Send Email..."))
        } catch (e: Exception) {
            Toast.makeText(context, "No email app installed on this device.", Toast.LENGTH_SHORT).show()
        }
    }
}