package com.example.seminar5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;



public class AddMasinaActivity extends AppCompatActivity {


    private EditText modelEditText;
    private EditText anEditText;
    private EditText brandEditText;
    private CheckBox esteElectricaSwitch;
    private EditText caiPutereEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_masina);

        modelEditText = findViewById(R.id.editTextModel);
        anEditText = findViewById(R.id.editTextAn);
        brandEditText = findViewById(R.id.editTextBrand);
        esteElectricaSwitch = findViewById(R.id.checkBoxElectrica);
        caiPutereEditText = findViewById(R.id.editTextCaiPutere);

        Intent intent = getIntent();
        Masina masinaPrimita = intent.getParcelableExtra("masina");

        if (masinaPrimita != null) {
            modelEditText.setText(masinaPrimita.getModel());
            anEditText.setText(String.valueOf(masinaPrimita.getAn()));
            brandEditText.setText(masinaPrimita.getBrand());
            esteElectricaSwitch.setChecked(masinaPrimita.isEsteElectrica());
            caiPutereEditText.setText(String.valueOf(masinaPrimita.getCaiPutere()));
        }


        Button btnSave = findViewById(R.id.buttonAdaugaMasina);
        btnSave.setOnClickListener(v -> {
            String model = modelEditText.getText().toString();
            int an = Integer.parseInt(anEditText.getText().toString());
            String brand = brandEditText.getText().toString();
            boolean esteElectrica = esteElectricaSwitch.isChecked();
            int caiPutere = Integer.parseInt(caiPutereEditText.getText().toString());

            Masina masinaModificata = new Masina(model, an, brand, esteElectrica, caiPutere);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("masina", masinaModificata);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}