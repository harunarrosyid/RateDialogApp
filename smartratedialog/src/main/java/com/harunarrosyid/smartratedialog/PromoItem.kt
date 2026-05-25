package com.harunarrosyid.smartratedialog

data class PromoItem(
    val title: String,
    val description: String,
    val iconResId: Int, // ID Gambar lokal (R.drawable...)
    val actionUrl: String // Link tujuan (Play Store, Web, dll)
)