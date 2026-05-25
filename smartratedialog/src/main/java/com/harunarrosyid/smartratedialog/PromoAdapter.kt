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
import com.bumptech.glide.Glide // <--- IMPORT GLIDE
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

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

        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.description

        // ==========================================
        // KODE BARU: MUAT GAMBAR DARI URL PAKE GLIDE
        // ==========================================
        Glide.with(holder.itemView.context)
            .load(item.iconUrl) // Mengambil dari Direct Link
            .transform(CenterCrop(), RoundedCorners(16)) // Opsional: Bikin sudut ikon jadi agak bulat
            .into(holder.ivIcon)

        // Fungsi klik: Buka URL saat tombol ditekan
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
        holder.itemView.setOnClickListener(clickListener)
    }

    override fun getItemCount(): Int = items.size
}