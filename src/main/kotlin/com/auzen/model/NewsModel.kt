package com.auzen.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("News")
open class NewsModel(
        /**
         * Id ini harus bisa diketahui sebelum data disimpan, harus mengandung informasi url,
         * sehingga apabila terdapat permintaan baru dengan url yang sama dapat langsung diproses
         * dengan data yang sudah pernah disimpan
         */
        @Id
        @Indexed(name = "NewsId")
        var id: String,
        /**
         * url sumber berita, biasanya dari website media masa
         */
        var url: String,
        /**
         * dalam format timestamp, dapatkan tanggal terbit, atau generate saat berita ini disimpan di DB
         */
        var postedTime: Long,
        /**
         * timestamp berita diakses untuk pertama kalinya
         */
        var accessFirstTime: Long = 0,
        /**
         * timestamp akses terakhir
         */
        var accessLastTime: Long = 0,
        /**
         * nilai positif untuk perhitungan total akses
         */
        var totalAccess: Int = 0,
        /**
         * kumpulan paragraf dalam berita ini
         */
        var paragraphs: List<ParagraphModel?> = listOf()
)