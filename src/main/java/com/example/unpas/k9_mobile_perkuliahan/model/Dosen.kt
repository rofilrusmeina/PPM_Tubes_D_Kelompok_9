package com.example.unpas.k9_mobile_perkuliahan.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dosen (
    @PrimaryKey val id: String,
    val nidn: String,
    val nama: String,
    val gelar_depan: String,
    val gelar_belakang: String,
//    val pendidikan: Pendidikan
    val pendidikan : String
)

//enum class Pendidikan (val pendidikan: String) {
//    s2("S2"),
//    s3("S3")
//}