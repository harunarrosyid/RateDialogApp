package com.harunarrosyid.ratedialogapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri

object SmartRate {

    /**
     * Menampilkan dialog feedback pintar untuk menyaring ulasan.
     *
     * @param context Konteks dari Activity yang memanggil.
     * @param appName Nama aplikasi untuk ditampilkan di judul dialog.
     * @param supportEmail Alamat email untuk menerima keluhan/masukan.
     * @param packageName Package name aplikasi untuk routing ke Play Store.
     */
    fun show(context: Context, appName: String, supportEmail: String, packageName: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Suka $appName?")
        builder.setMessage("Hai! Saya butuh masukan kamu untuk bikin aplikasi ini lebih baik. Boleh minta waktunya 10 detik?")

        // Jika Suka -> Buka halaman aplikasi di Google Play Store
        builder.setPositiveButton("Suka Banget! \uD83D\uDE0A") { dialog, _ ->
            openPlayStore(context, packageName)
            dialog.dismiss()
        }

        // Jika Kurang Suka -> Buka aplikasi Email
        builder.setNegativeButton("Kurang Suka \uD83D\uDE1E") { dialog, _ ->
            sendFeedbackEmail(context, supportEmail, appName)
            dialog.dismiss()
        }

        // Mencegah dialog ditutup secara tidak sengaja
        builder.setCancelable(false)
        builder.show()
    }

    private fun openPlayStore(context: Context, packageName: String) {
        try {
            // Mencoba membuka via aplikasi Play Store (Deep Link)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback: Buka via browser jika aplikasi Play Store tidak ada
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
            context.startActivity(intent)
        }
    }

    private fun sendFeedbackEmail(context: Context, email: String, appName: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Memastikan hanya aplikasi email yang merespons
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "Masukan untuk aplikasi $appName")
        }
        context.startActivity(Intent.createChooser(intent, "Kirim Masukan"))
    }
}