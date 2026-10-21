package com.example.baithuchanh1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;

public class AddBookActivity extends AppCompatActivity {
    private EditText etTitle, etAuthor, etNote;
    private Spinner spCategory;
    private RadioGroup rgStatus;
    private RatingBar rbRating;
    private TextView tvRatingValue;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etNote = findViewById(R.id.etNote);
        spCategory = findViewById(R.id.spCategory);
        rgStatus = findViewById(R.id.rgStatus);
        rbRating = findViewById(R.id.rbRatingAdd);
        tvRatingValue = findViewById(R.id.tvRatingValue);
        btnSave = findViewById(R.id.btnSave);

        String[] categories = {"Văn học", "Khoa học", "Công nghệ", "Kỹ năng sống", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        rbRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) ->
                tvRatingValue.setText((int) rating + " / 5 sao")
        );

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();

            if (title.isEmpty()) {
                etTitle.setError("Tên sách không được để trống");
                return;
            }
            if (author.isEmpty()) {
                etAuthor.setError("Tác giả không được để trống");
                return;
            }

            String category = spCategory.getSelectedItem().toString();
            int selectedStatusId = rgStatus.getCheckedRadioButtonId();
            RadioButton selectedRb = findViewById(selectedStatusId);
            String status = selectedRb.getText().toString();
            float rating = rbRating.getRating();
            String note = etNote.getText().toString().trim();

            Book newBook = new Book(UUID.randomUUID().toString(), title, author, category, status, rating, note);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("action", "ADD");
            resultIntent.putExtra("book", newBook);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}