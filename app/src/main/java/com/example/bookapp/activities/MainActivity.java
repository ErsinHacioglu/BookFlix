package com.example.bookapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.R;
import com.example.bookapp.adapters.BookAdapter;
import com.example.bookapp.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> allBooks = new ArrayList<>();
    private boolean showingFavoritesOnly = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isUserLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.bookRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BookAdapter.BookNavigationHandler navigationHandler = new BookAdapter.BookNavigationHandlerImpl();
        bookAdapter = new BookAdapter(allBooks, navigationHandler);
        recyclerView.setAdapter(bookAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBooks(newText != null ? newText : "");
                return true;
            }
        });

        findViewById(R.id.subscriptionIcon).setOnClickListener(v ->
                startActivity(new Intent(this, SubscriptionActivity.class)));

        findViewById(R.id.profileIcon).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        ImageButton favoritesButton = findViewById(R.id.favoritesIcon);
        favoritesButton.setOnClickListener(v -> {
            showingFavoritesOnly = !showingFavoritesOnly;
            filterBooks(searchView.getQuery().toString());

            int icon = showingFavoritesOnly ? R.drawable.heart_filled : R.drawable.heart;
            favoritesButton.setImageResource(icon);

            String toastText = showingFavoritesOnly
                    ? "Sadece favoriler gösteriliyor"
                    : "Tüm kitaplar gösteriliyor";
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
        });

        loadBooks();
    }

    private void loadBooks() {
        allBooks = new ArrayList<>();

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 1,
                "title", "Simyacı",
                "author", "Paulo Coelho",
                "content", "Simyacı, hayallerin peşinden gitmenin önemini anlatan bir romandır.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=1Wh1KjPablJtnzq8AOdon_zglTo5NfcIS"
        )));

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 2,
                "title", "Beyaz Diş",
                "author", "Jack London",
                "content", "Doğanın acımasızlığı ve hayatta kalma mücadelesini anlatan klasik bir hikaye.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=1qM0adR1tua3Pb_CQng001Xi38c_ipqVH"
        )));

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 3,
                "title", "Don Kişot",
                "author", "Miguel de Cervantes",
                "content", "İdealist bir şövalyenin gerçek dünyadaki maceralarını konu alır.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=1SoWzPH8myUDHkEvB5l08JSmhiBOJCQB1"
        )));

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 4,
                "title", "1984",
                "author", "George Orwell",
                "content", "Totaliter bir rejimin baskısı altındaki bir toplumun distopik hikayesi.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=1EXb9AVZZvu_fyppjTy_w5qqcxIDoVQZ2"
        )));

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 5,
                "title", "Sefiller",
                "author", "Victor Hugo",
                "content", "Toplumsal adaletsizlik ve insanlık onuru üzerine dokunaklı bir klasik.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=1Ij7i7nQiMDIPhDr05V7fC6VGCpOjEsdQ"
        )));

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 6,
                "title", "Kürk Mantolu Madonna",
                "author", "Sabahattin Ali",
                "content", "Raif Efendi'nin iç dünyasını ve aşkını anlatan etkileyici bir roman.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=1wxx8ukGbxoFiOQPnOeveGwRdZ96lthjd"
        )));

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 7,
                "title", "Suç ve Ceza",
                "author", "Fyodor Dostoyevski",
                "content", "Vicdan, adalet ve insan ruhunun derinliklerini sorgulayan başyapıt.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=1g1rZ3LR4-MZp8YTgrUx0Q0eQfOqb0EDp"
        )));

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 8,
                "title", "Kayıp Zamanın İzinde",
                "author", "Marcel Proust",
                "content", "Zaman, hafıza ve bilinç üzerine kurulu bir anlatı deneyi.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=1UzMP85P5IsdDt9MujKpJM5tFJoyRJF9V"
        )));

        allBooks.add(Book.Companion.createFromMap(Map.of(
                "id", 9,
                "title", "Anna Karenina",
                "author", "Lev Tolstoy",
                "content", "Aşk, sadakat ve toplum baskıları üzerine klasik bir Rus romanı.",
                "pdfUrl", "https://drive.google.com/uc?export=download&id=132h1Cl9ooAyuuf9YrzsFuvX5ve90E0KP"
        )));

        bookAdapter.updateBooks(allBooks);
    }

    private void filterBooks(String query) {
        SharedPreferences sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        List<Book> filtered = new ArrayList<>();
        for (Book book : allBooks) {
            boolean matchesQuery = book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase());
            boolean isFavorite = sharedPrefs.getBoolean(book.getTitle(), false);
            if (matchesQuery && (!showingFavoritesOnly || isFavorite)) {
                filtered.add(book);
            }
        }
        bookAdapter.updateBooks(filtered);
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.contains("userId");
    }
}
