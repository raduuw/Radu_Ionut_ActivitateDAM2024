package com.example.seminar_04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MasinaListActivity extends AppCompatActivity {

    private List<Masina> masini = null;
    private int idModificat = 0;
    private MasiniAdapter adapter = null;
    MasinaDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_masina_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = Room.databaseBuilder(this, MasinaDatabase.class, "Masina.db").build();

        ListView lv = findViewById(R.id.masiniLv);
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                masini = database.masinaDao().getAll();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new MasiniAdapter(masini, getApplicationContext(), R.layout.row_item);
                        lv.setAdapter(adapter);
                    }
                });
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentModifica = new Intent(getApplicationContext(), AddMasinaActivity.class);
                intentModifica.putExtra("anime", masini.get(i));
                idModificat = i;
                startActivityForResult(intentModifica, 209);
                Toast.makeText(getApplicationContext(), masini.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences sp = getSharedPreferences("obiecteFavorite", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(masini.get(i).getKey(), masini.get(i).toString());
                editor.apply();

                Toast.makeText(getApplicationContext(), "Masina a fost salvata în Favorite", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 209) {
                Masina masinaModificat = data.getParcelableExtra("masina");

                Executor executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.myLooper());

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.masinaDao().update(masinaModificat);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                masini.set(idModificat, masinaModificat);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }
    }
}