package com.example.unpas.k9_mobile_perkuliahan.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.unpas.k9_mobile_perkuliahan.R

enum class Menu (
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
){
    HOME(R.string.home, Icons.Default.Home, "home"),
    DOSEN(R.string.dosen, Icons.Default.Person, "dosen"),
    MAHASISWA(R.string.mahasiswa, Icons.Default.AccountBox, "mahasiswa"),
    MATKUL(R.string.matkul, Icons.Default.Email, "matkul");

    companion object {
        fun getTabFromResource(@StringRes resource: Int) : Menu
        {
            return when (resource) {
                R.string.home -> HOME
                R.string.dosen -> DOSEN
                R.string.mahasiswa -> MAHASISWA
                else -> MATKUL
            }
        }
    }
}