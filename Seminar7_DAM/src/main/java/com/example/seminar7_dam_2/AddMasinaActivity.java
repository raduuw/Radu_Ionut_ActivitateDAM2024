package com.example.seminar7_dam_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddMasinaActivity extends AppCompatActivity {

    private MasinaDatabase database;

    private EditText modelEditText;
    private EditText anEditText;
    private EditText brandEditText;
    private CheckBox esteElectricaSwitch;
    private EditText caiPutereEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_masina);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        database = Room.databaseBuilder(getApplicationContext(), MasinaDatabase.class, "Masina.db").build();

        modelEditText = findViewById(R.id.editTextModel);
        anEditText = findViewById(R.id.editTextAn);
        brandEditText = findViewById(R.id.editTextBrand);
        esteElectricaSwitch = findViewById(R.id.checkBoxElectrica);
        caiPutereEditText = findViewById(R.id.editTextCaiPutere);


        Button btnSave = findViewById(R.id.buttonAdaugaMasina);
        btnSave.setOnClickListener(v -> {
            String model = modelEditText.getText().toString();
            int an = Integer.parseInt(anEditText.getText().toString());
            String brand = brandEditText.getText().toString();
            boolean esteElectrica = esteElectricaSwitch.isChecked();
            int caiPutere = Integer.parseInt(caiPutereEditText.getText().toString());

            CheckBox cbDisponibil = findViewById(R.id.cbDisponibilOnline);
            Boolean disponibilOnline = cbDisponibil.isChecked();

            Masina masina = new Masina(model, an, brand, esteElectrica, caiPutere);
            if(disponibilOnline){
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference("message");
//
//                    myRef.setValue("Hello, World!");
                try{
                    FileOutputStream file;
                    file = openFileOutput("elementeBifate.txt", MODE_APPEND);
                    OutputStreamWriter output = new OutputStreamWriter(file);
                    BufferedWriter writer = new BufferedWriter(output);
                    writer.write(masina.toString());
                    writer.close();
                    output.close();
                    file.close();
                    Toast.makeText(AddMasinaActivity.this, "element adaugat in fisier", Toast.LENGTH_LONG);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Toast.makeText(AddMasinaActivity.this, masina.toString(), Toast.LENGTH_LONG).show();


            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {

                    FileOutputStream file = openFileOutput("masiniNoi.txt", MODE_APPEND);
                    OutputStreamWriter output = new OutputStreamWriter(file);
                    BufferedWriter writer = new BufferedWriter(output);
                    writer.write(masina.toString() + "\n");
                    writer.close();
                    output.close();
                    file.close();

                    database.masinaDao().insert(masina);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            Intent resultIntent = new Intent();
            resultIntent.putExtra("masina", masina);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}