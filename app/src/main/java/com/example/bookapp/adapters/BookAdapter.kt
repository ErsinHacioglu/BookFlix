package com.example.bookapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.R
import com.example.bookapp.activities.BookDetailActivity
import com.example.bookapp.models.Book

class BookAdapter(
    private var filteredBooks: List<Book>,
    private val navigationHandler: BookNavigationHandler
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.bookTitle)
        val authorText: TextView = itemView.findViewById(R.id.bookAuthor)

        fun bind(book: Book) {
            titleText.text = book.title
            authorText.text = "Yazar: ${book.author}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = filteredBooks[position]
        holder.bind(book)

        holder.itemView.setOnClickListener {
            navigationHandler.navigateToBookDetail(holder.itemView.context, book)
        }
    }

    override fun getItemCount() = filteredBooks.size

    fun updateBooks(newBooks: List<Book>) {
        filteredBooks = newBooks
        notifyDataSetChanged()
    }



    interface BookNavigationHandler {
        fun navigateToBookDetail(context: Context, book: Book)
    }

    class BookNavigationHandlerImpl : BookNavigationHandler {
        override fun navigateToBookDetail(context: Context, book: Book) {
            val intent = Intent(context, BookDetailActivity::class.java).apply {
                putExtra("title", book.title)
                putExtra("author", book.author)
                putExtra("content", book.content)
                putExtra("pdfUrl", book.pdfUrl)
                putExtra("id", book.id)
            }
            context.startActivity(intent)
        }
    }
}
