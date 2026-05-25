package com.harunarrosyid.smartratedialog

data class PromoItem(
    val title: String,
    val description: String,
    val iconUrl: String,   // <--- GANTI JADI STRING (URL)
    val actionUrl: String
)