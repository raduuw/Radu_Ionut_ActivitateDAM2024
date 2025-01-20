package com.example.seminar7_dam_2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImaginiActivity extends AppCompatActivity {

    private List<ImaginiDomeniu> lista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imagini);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<String> linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/BMW_M3_Competition_%28G80%29_IMG_4041.jpg/1920px-BMW_M3_Competition_%28G80%29_IMG_4041.jpg");
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/a/ac/Audi_RS7_C8_IMG_4323.jpg");
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/2018_Tesla_Model_S_75D.jpg/1920px-2018_Tesla_Model_S_75D.jpg");
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Festival_automobile_international_2015_-_Mercedes_AMG_GT_-_003.jpg/1920px-Festival_automobile_international_2015_-_Mercedes_AMG_GT_-_003.jpg");
        linkuriImagini.add("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Porsche_911_No_1000000%2C_70_Years_Porsche_Sports_Car%2C_Berlin_%281X7A3888%29.jpg/1920px-Porsche_911_No_1000000%2C_70_Years_Porsche_Sports_Car%2C_Berlin_%281X7A3888%29.jpg");


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        List<Bitmap> imagini = new ArrayList<>();


        executor.execute(new Runnable() {
            @Override
            public void run() {
                for(String link : linkuriImagini) {
                    HttpURLConnection con = null;
                    try {
                        URL url = new URL(link);
                        con = (HttpURLConnection) url.openConnection();
                        InputStream is = con.getInputStream();
                        imagini.add(BitmapFactory.decodeStream(is));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lista = new ArrayList<>();
                        lista.add(new ImaginiDomeniu("BMW M3", imagini.get(0), "https://www.bmw.com"));
                        lista.add(new ImaginiDomeniu("Audi RS7", imagini.get(1), "https://www.audi.com"));
                        lista.add(new ImaginiDomeniu("Tesla Model S", imagini.get(2), "https://www.tesla.com"));
                        lista.add(new ImaginiDomeniu("Mercedes-Benz AMG GT", imagini.get(3), "https://www.mercedes-benz.com"));
                        lista.add(new ImaginiDomeniu("Porsche 911", imagini.get(4), "https://www.porsche.com"));

                        ListView lv = findViewById(R.id.imagini);
                        ImagineAdapter adapter = new ImagineAdapter(lista, getApplicationContext(), R.layout.imagine_layout);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it = new Intent(getApplicationContext(), WebViewActivity.class);
                                it.putExtra("link", lista.get(i).getLink());
                                startActivity(it);
                            }
                        });
                    }
                });
            }
        });
    }
}