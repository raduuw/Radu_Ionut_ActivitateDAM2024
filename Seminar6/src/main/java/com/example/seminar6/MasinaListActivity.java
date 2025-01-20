package com.example.seminar6;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;


public class MasinaListActivity extends AppCompatActivity {

    private List<Masina> masini = null;
    private int idModificat = 0;
    private MasiniAdapter adapter = null;

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
        Intent it = getIntent();
        masini = it.getParcelableArrayListExtra("masini");
        if (masini == null) {
            masini = new ArrayList<>();
        }

        List<Masina> masina = it.getParcelableArrayListExtra("masini");
        ListView lv = findViewById(R.id.masiniLv);

        adapter = new MasiniAdapter(masini, getApplicationContext(), R.layout.adapter);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener((adapterView, view, position, id) -> {
            Masina masinaSelectata = masini.get(position);
            Intent intentModifica = new Intent(getApplicationContext(), AddMasinaActivity.class);
            intentModifica.putExtra("masini", masinaSelectata);
            idModificat = position;
            startActivityForResult(intentModifica, 209);
        });

        lv.setOnItemLongClickListener((adapterView, view, position, id) -> {
            Masina masinaStearsa = masini.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Șters: " + masinaStearsa.toString(), Toast.LENGTH_SHORT).show();
            return true;
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 209 && data != null) {
            Masina masinaModificata = data.getParcelableExtra("masina");
            masini.set(idModificat, masinaModificata);
            adapter.notifyDataSetChanged();
        }
    }
}