package com.example.baithuchanh1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvAuthorCategory, tvStatus, tvNote;
    private RatingBar rbRating;
    private Button btnMarkRead, btnBack, btnDelete;

    private Book currentBook;
    private boolean isUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAuthorCategory = findViewById(R.id.tvDetailAuthorCategory);
        tvStatus = findViewById(R.id.tvDetailStatus);
        tvNote = findViewById(R.id.tvDetailNote);
        rbRating = findViewById(R.id.rbDetailRating);
        btnMarkRead = findViewById(R.id.btnMarkRead);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        currentBook = (Book) getIntent().getSerializableExtra("book");

        if (currentBook != null) {
            displayBookDetails();
        }

        rbRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                currentBook.setRating(rating);
                isUpdated = true;
            }
        });

        btnMarkRead.setOnClickListener(v -> {
            currentBook.setStatus("Đã đọc");
            displayBookDetails();
            isUpdated = true;
        });

        btnBack.setOnClickListener(v -> finishWithResult());

        btnDelete.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("action", "DELETE");
            intent.putExtra("bookId", currentBook.getId());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void displayBookDetails() {
        tvTitle.setText(currentBook.getTitle());
        tvAuthorCategory.setText(currentBook.getAuthor() + " · " + currentBook.getCategory());
        tvStatus.setText(currentBook.getStatus());
        rbRating.setRating(currentBook.getRating());
        tvNote.setText(currentBook.getNote().isEmpty() ? "(Không có ghi chú)" : currentBook.getNote());

        if ("Đã đọc".equals(currentBook.getStatus())) {
            btnMarkRead.setEnabled(false);
            btnMarkRead.setAlpha(0.5f);
        } else {
            btnMarkRead.setEnabled(true);
            btnMarkRead.setAlpha(1.0f);
        }
    }

    private void finishWithResult() {
        if (isUpdated) {
            Intent intent = new Intent();
            intent.putExtra("action", "UPDATE");
            intent.putExtra("book", currentBook);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        finishWithResult();
        super.onBackPressed();
    }
}