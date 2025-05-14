package com.example.bookapp.activities

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookapp.R

class BookDetailActivity : AppCompatActivity() {

    private lateinit var bookTitle: TextView
    private lateinit var bookAuthor: TextView
    private lateinit var bookContent: TextView
    private lateinit var favoriteButton: ImageButton
    private lateinit var openPdfButton: ImageButton
    private lateinit var downloadBookButton: ImageButton
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        bookTitle = findViewById(R.id.bookTitle)
        bookAuthor = findViewById(R.id.bookAuthor)
        bookContent = findViewById(R.id.bookContent)
        favoriteButton = findViewById(R.id.favoriteButton)
        openPdfButton = findViewById(R.id.openPdfButton)
        downloadBookButton = findViewById(R.id.downloadBookButton)

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        val title = intent.getStringExtra("title") ?: "Bilinmeyen Kitap"
        val author = intent.getStringExtra("author") ?: "Bilinmeyen Yazar"
        val content = intent.getStringExtra("content") ?: "İçerik bulunamadı."
        val pdfUrl = intent.getStringExtra("pdfUrl") ?: ""

        bookTitle.text = title
        bookAuthor.text = "Yazar: $author"
        bookContent.text = content

        val isFavorite = sharedPreferences.getBoolean(title, false)
        updateFavoriteButton(isFavorite)

        favoriteButton.setOnClickListener {
            val newFavoriteStatus = !sharedPreferences.getBoolean(title, false)
            sharedPreferences.edit().putBoolean(title, newFavoriteStatus).apply()
            updateFavoriteButton(newFavoriteStatus)
            val message = if (newFavoriteStatus) "Favorilere eklendi!" else "Favorilerden kaldırıldı!"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        openPdfButton.setOnClickListener {
            if (!isUserSubscribed()) {
                showSubscriptionDialog()
                return@setOnClickListener
            }

            if (pdfUrl.isNotEmpty()) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(Uri.parse(pdfUrl), "application/pdf")
                        flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "PDF görüntüleyici uygulama bulunamadı.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "PDF bağlantısı bulunamadı.", Toast.LENGTH_SHORT).show()
            }
        }

        downloadBookButton.setOnClickListener {
            if (!isUserSubscribed()) {
                showSubscriptionDialog()
                return@setOnClickListener
            }

            if (pdfUrl.isNotEmpty()) {
                downloadPdfFromUrl(title, pdfUrl)
            } else {
                Toast.makeText(this, "PDF bağlantısı bulunamadı.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val icon = if (isFavorite) R.drawable.heart_filled else R.drawable.heart
        favoriteButton.setImageResource(icon)
    }

    private fun downloadPdfFromUrl(title: String, pdfUrl: String) {
        val safeTitle = title.replace(Regex("[^a-zA-Z0-9\\s]"), "_")
        val request = DownloadManager.Request(Uri.parse(pdfUrl))
            .setTitle(title)
            .setDescription("PDF indiriliyor...")
            .setMimeType("application/pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$safeTitle.pdf")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(this, "İndirme başladı. İndirilenler klasörünü kontrol edin.", Toast.LENGTH_LONG).show()
    }

    private fun isUserSubscribed(): Boolean {
        val sessionPrefs = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        return sessionPrefs.getBoolean("isSubscribed", false)
    }

    private fun showSubscriptionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Abonelik Gerekli")
            .setMessage("Bu işlemi gerçekleştirebilmek için aktif bir aboneliğiniz olmalı.")
            .setPositiveButton("Abone Ol") { _, _ ->
                startActivity(Intent(this, SubscriptionActivity::class.java))
            }
            .setNegativeButton("İptal", null)
            .show()
    }
}
