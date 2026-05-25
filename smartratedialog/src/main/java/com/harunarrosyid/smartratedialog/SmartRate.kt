package com.harunarrosyid.smartratedialog

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

object SmartRate {

    fun show(
        context: Context,
        appName: String,
        supportEmail: String,
        packageName: String,
        // Parameter Dialog Bawaan
        titleText: String = "Enjoying $appName?",
        messageText: String = "If you like using our app, please take a moment to rate it!",
        btnRateText: String = "Rate Now",
        btnLaterText: String = "Later",
        btnFeedbackText: String = "Send Feedback",
        // Parameter Promosi Silang (Default = kosong)
        promoApps: List<PromoItem> = emptyList()
    ) {
        val bottomSheet = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_smart_rate, null)
        bottomSheet.setContentView(view)

        // Binding View
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvMessage = view.findViewById<TextView>(R.id.tvMessage)
        val btnRate = view.findViewById<Button>(R.id.btnRate)
        val btnLater = view.findViewById<Button>(R.id.btnLater)
        val btnFeedback = view.findViewById<Button>(R.id.btnFeedback)

        val layoutPromo = view.findViewById<LinearLayout>(R.id.layoutPromo)
        val rvPromo = view.findViewById<RecyclerView>(R.id.rvPromo)

        // Set Teks
        tvTitle.text = titleText
        tvMessage.text = messageText
        btnRate.text = btnRateText
        btnLater.text = btnLaterText
        btnFeedback.text = btnFeedbackText

        // Logika Tombol
        btnRate.setOnClickListener {
            openPlayStore(context, packageName)
            bottomSheet.dismiss()
        }

        btnLater.setOnClickListener {
            bottomSheet.dismiss()
        }

        if (supportEmail.isNotEmpty()) {
            btnFeedback.visibility = View.VISIBLE
            btnFeedback.setOnClickListener {
                sendFeedbackEmail(context, supportEmail, appName)
                bottomSheet.dismiss()
            }
        } else {
            btnFeedback.visibility = View.GONE
        }

        // ==========================================
        // LOGIKA PROMOSI SILANG (CROSS-PROMOTION)
        // ==========================================
        if (promoApps.isNotEmpty()) {
            layoutPromo.visibility = View.VISIBLE
            rvPromo.layoutManager = LinearLayoutManager(context)
            rvPromo.adapter = PromoAdapter(promoApps)
        } else {
            layoutPromo.visibility = View.GONE
        }

        bottomSheet.show()
    }

    private fun openPlayStore(context: Context, packageName: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    private fun sendFeedbackEmail(context: Context, email: String, appName: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
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