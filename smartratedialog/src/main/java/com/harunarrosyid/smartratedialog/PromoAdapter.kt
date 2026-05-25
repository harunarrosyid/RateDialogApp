package com.harunarrosyid.smartratedialog

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PromoAdapter(private val items: List<PromoItem>) : RecyclerView.Adapter<PromoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.ivPromoIcon)
        val tvTitle: TextView = view.findViewById(R.id.tvPromoTitle)
        val tvDesc: TextView = view.findViewById(R.id.tvPromoDesc)
        val btnAction: Button = view.findViewById(R.id.btnPromoAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_promo_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.ivIcon.setImageResource(item.iconResId)
        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.description

        // Fungsi klik: Buka URL (Play Store / Web) saat tombol ditekan
        val clickListener = View.OnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.actionUrl))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                holder.itemView.context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        holder.btnAction.setOnClickListener(clickListener)
        holder.itemView.setOnClickListener(clickListener) // Bisa diklik di mana saja di area tersebut
    }

    override fun getItemCount(): Int = items.size
}