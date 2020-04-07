package com.auzen.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

class ParagraphModel(
        /**
         * id ini harus unik dan mengandung informasi total karakter, total kata, total titik (kalimat),
         * sehingga apabila terdapat informasi paragraf yang mirip dapat langsung mengambil data yang pernah disimpan
         */
        @Id
        @Indexed(name = "ParagraphId", unique = true)
        var id: String,
        /**
         * ID berita, {@see NewsModel#id}
         */
        var newsId: String,
        /**
         * ID paragraf sebelumnya, null jika ini adalah paragraf pertama
         */
        var paragraphPreviousId: Int? = null,
        /**
         * ID paragraf selanjutnya, null jika ini adalah pparagraf terakhir
         */
        var paragraphNextId: Int? = null,
        /**
         * berisi karakter dalam paragraf, bisa huruf, tanda baca, atau simbol baca
         */
        var content: String,
        /**
         * timestamp pengaksesan pertama kali
         */
        var accessFirstTime: Long,
        /**
         * timestamp pengaksesan terakhir kali
         */
        var accessLastTime: Long = 0
)
