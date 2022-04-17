package com.examples.datamanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "data.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.form_edit_text_data);

        Button readButton = findViewById(R.id.form_button_read);
        readButton.setOnClickListener(v -> {
            readDataText(editText, FILE_NAME);
        });

        Button saveButton = findViewById(R.id.form_button_save);
        saveButton.setOnClickListener(v -> {
            writeDataText(editText, FILE_NAME);
        });

        readDataText(editText, FILE_NAME);
    }

    /**
     * Устанавливает значение элемента editText,
     * данными прочитанными в файле расположенному по пути filePath.
     */
    private void readDataText(EditText editText, String filePath) {
        try {
            String text = readText(filePath);
            editText.setText(text);
        } catch (Exception error) {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Запмсывает данные полученные из элемента editText,
     * в файл расположенный по адресу filePath
     */
    private void writeDataText(EditText editText, String filePath) {
        try {
            String text = editText.getText().toString();
            writeText(filePath, text);
            Toast.makeText(this, R.string.toast_save_file_ok, Toast.LENGTH_LONG).show();
        } catch (Exception error) {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Чтение файла расположенного по адресу filePath,
     * возвращает прочитанный текст.
     */
    private String readText(String filePath) throws IOException {
        String text = null;

        FileInputStream stream = null;

        try {
            stream = openFileInput(filePath);
            byte[] data = new byte[stream.available()];
            stream.read(data);
            text = new String(data);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return text;
    }

    /**
     * Записать текста в файл расположенного по адресу filePath
     */
    private void writeText(String filePath, String text) throws IOException {
        FileOutputStream stream = null;

        try {
            stream = openFileOutput(filePath, MODE_PRIVATE);
            byte[] data = text.getBytes(StandardCharsets.UTF_8);
            stream.write(data);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}