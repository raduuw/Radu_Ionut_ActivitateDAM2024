package com.example.seminar_4_dam;

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

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


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

            Masina masina = new Masina(model, an, brand, esteElectrica, caiPutere);


            Toast.makeText(AddMasinaActivity.this, masina.toString(), Toast.LENGTH_LONG).show();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("masina", masina);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}