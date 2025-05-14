package com.example.bookapp.models

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val content: String,
    val pdfUrl: String
) {
    companion object {
        fun createFromMap(data: Map<String, Any?>): Book {
            return Book(
                id = data["id"] as? Int ?: 0,
                title = data["title"] as? String ?: "Unknown Title",
                author = data["author"] as? String ?: "Unknown Author",
                content = data["content"] as? String ?: "",
                pdfUrl = data["pdfUrl"] as? String ?: ""
            )
        }
    }
}
