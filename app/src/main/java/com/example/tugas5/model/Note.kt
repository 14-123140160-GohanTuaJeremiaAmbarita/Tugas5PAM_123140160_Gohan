package com.example.tugas5.model

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val createdAt: String = ""
)

// Sample data untuk testing
val sampleNotes = listOf(
    Note(1, "Belajar Kotlin", "Kotlin adalah bahasa pemrograman modern...", false, "2023-10-01"),
    Note(2, "Compose Multiplatform", "CMP memungkinkan kita membangun UI...", true, "2023-10-02"),
    Note(3, "Navigation Component", "NavHost, NavController, Routes...", false, "2023-10-03")
)
