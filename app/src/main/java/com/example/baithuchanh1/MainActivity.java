package com.example.baithuchanh1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalBooks, tvReadBooks, tvUnreadBooks;
    private ListView lvBooks;
    private Button btnAddBook;

    private ArrayList<Book> bookList;
    private BookAdapter adapter;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String action = data.getStringExtra("action");

                    if ("ADD".equals(action)) {
                        Book newBook = (Book) data.getSerializableExtra("book");
                        if (newBook != null) {
                            bookList.add(newBook);
                        }
                    } else if ("UPDATE".equals(action)) {
                        Book updatedBook = (Book) data.getSerializableExtra("book");
                        if (updatedBook != null) {
                            for (int i = 0; i < bookList.size(); i++) {
                                if (bookList.get(i).getId().equals(updatedBook.getId())) {
                                    bookList.set(i, updatedBook);
                                    break;
                                }
                            }
                        }
                    } else if ("DELETE".equals(action)) {
                        String bookId = data.getStringExtra("bookId");
                        if (bookId != null) {
                            for (int i = 0; i < bookList.size(); i++) {
                                if (bookList.get(i).getId().equals(bookId)) {
                                    bookList.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    updateStatistics();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalBooks = findViewById(R.id.tvTotalBooks);
        tvReadBooks = findViewById(R.id.tvReadBooks);
        tvUnreadBooks = findViewById(R.id.tvUnreadBooks);
        lvBooks = findViewById(R.id.lvBooks);
        btnAddBook = findViewById(R.id.btnAddBook);

        initData();

        adapter = new BookAdapter(this, bookList);
        lvBooks.setAdapter(adapter);
        updateStatistics();

        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            launcher.launch(intent);
        });

        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            Book selectedBook = bookList.get(position);
            Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
            intent.putExtra("book", selectedBook);
            launcher.launch(intent);
        });
    }

    private void initData() {
        bookList = new ArrayList<>();
        bookList.add(new Book("1", "Lập trình Android cơ bản", "Nguyễn Văn A", "Công nghệ", "Đang đọc", 4.0f, "Sách dành cho người mới bắt đầu"));
        bookList.add(new Book("2", "Đắc nhân tâm", "Dale Carnegie", "Kỹ năng sống", "Đã đọc", 5.0f, "Sách học làm người rất hay"));
        bookList.add(new Book("3", "Dế Mèn phiêu lưu ký", "Tô Hoài", "Văn học", "Chưa đọc", 0.0f, "Truyện thiếu nhi kinh điển"));
    }

    private void updateStatistics() {
        int total = bookList.size();
        int read = 0;
        int unread = 0;

        for (Book b : bookList) {
            if ("Đã đọc".equals(b.getStatus())) {
                read++;
            } else {
                unread++;
            }
        }

        tvTotalBooks.setText(String.valueOf(total));
        tvReadBooks.setText(String.valueOf(read));
        tvUnreadBooks.setText(String.valueOf(unread));
    }
}