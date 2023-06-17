package com.example.unpas.k9_mobile_perkuliahan.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Mahasiswa (
    @PrimaryKey val id: String,
    val npm: String,
    val nama: String,
    val tanggal_lahir: String,
    val jenis_kelamin: String

)