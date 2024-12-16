package com.example.seminar_02;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf("activitate", "s-a apelat onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("activitate", "s-a apelat onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("activitate", "s-a apelat onResume");
        Toast.makeText(this, R.string.mesaj, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activitate", "s-a apelat onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("activitate", "s-a apelat onStop");
    }

    @Override
    protected void onDestroy() {
        Log.i("activitate", "s-a apelat onDestroy");
        super.onDestroy();
    }
}