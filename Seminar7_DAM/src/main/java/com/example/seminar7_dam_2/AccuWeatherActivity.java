package com.example.seminar7_dam_2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccuWeatherActivity extends AppCompatActivity {
    String apiKey = "a9stzXGfLXcf19KveYZ5gSL823ENGVq7";
    String cheieOras;
    String min;
    String max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accu_weather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button btnAPI = findViewById(R.id.buttonAPI);

        Spinner spinnerDays = findViewById(R.id.spinnerDays);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.days,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerDays.setAdapter(adapter);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        btnAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etoras = findViewById(R.id.editOras);
                String oras = etoras.getText().toString();

                String url = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey="+apiKey+"&q="+oras;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder forecastText = new StringBuilder();
                        HttpURLConnection con = null;
                        try{
                            URL apiUrl = new URL(url);
                            con = (HttpURLConnection) apiUrl.openConnection();
                            con.setRequestMethod("GET");
                            con.connect();

                            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            StringBuilder response = new StringBuilder();
                            String line;

                            while((line = reader.readLine()) != null){
                                response.append(line);
                            }

                            reader.close();
                            con.disconnect();

                            JSONArray jsonArray = new JSONArray(response.toString());
                            JSONObject object = jsonArray.getJSONObject(0);
                            cheieOras = object.getString("Key");

                            Spinner spDays = findViewById(R.id.spinnerDays);
                            String days = spDays.getSelectedItem().toString();
                            int day = Integer.parseInt(days);

                            forecastText.append("Cod oras: ").append(cheieOras).append("\n");
                            forecastText.append("Temperatura: ").append("\n");

                            if(day == 1) {

                                //one day forecast
                                String weatherUrl = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + cheieOras + "?apikey=" + apiKey + "&metric=true";

                                apiUrl = new URL(weatherUrl);
                                con = (HttpURLConnection) apiUrl.openConnection();
                                con.setRequestMethod("GET");
                                con.connect();

                                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                response = new StringBuilder();
                                while ((line = reader.readLine()) != null) {
                                    response.append(line);
                                }
                                reader.close();
                                con.disconnect();

                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONObject dailyForecasts = jsonObject.getJSONArray("DailyForecasts").getJSONObject(0);
                                JSONObject temperature = dailyForecasts.getJSONObject("Temperature");
                                min = temperature.getJSONObject("Minimum").getString("Value");
                                max = temperature.getJSONObject("Maximum").getString("Value");

                                forecastText.append("Minima: ").append(min).append("°C\n");
                                forecastText.append("Maxima: ").append(max).append("°C\n");
                            }
                            else
                            if(day == 5) {
                                //five days forecast

                                String weatherUrl5Days = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/" + cheieOras + "?apikey=" + apiKey + "&metric=true";
                                apiUrl = new URL(weatherUrl5Days);
                                con = (HttpURLConnection) apiUrl.openConnection();
                                con.setRequestMethod("GET");
                                con.connect();

                                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                response = new StringBuilder();
                                while ((line = reader.readLine()) != null) {
                                    response.append(line);
                                }
                                reader.close();
                                con.disconnect();

                                JSONObject jsonObject1 = new JSONObject(response.toString());
                                JSONArray fiveDaysForecast = jsonObject1.getJSONArray("DailyForecasts");

                                StringBuilder fiveDaysText = new StringBuilder();
                                for (int i = 0; i < fiveDaysForecast.length(); i++) {
                                    JSONObject fiveDays = fiveDaysForecast.getJSONObject(i);
                                    JSONObject temperature1 = fiveDays.getJSONObject("Temperature");

                                    String minim = temperature1.getJSONObject("Minimum").getString("Value");
                                    String maxim = temperature1.getJSONObject("Maximum").getString("Value");

                                    String date = fiveDays.getString("Date").split("T")[0];

                                    forecastText.append("Ziua: ").append(date).append("\n");
                                    forecastText.append("Minima: ").append(minim).append("°C\n");
                                    forecastText.append("Maxima: ").append(maxim).append("°C\n\n");
                                }
                            }

                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                TextView orasTV = findViewById(R.id.tvResult);
                                //orasTV.setText("Cod oras: "+cheieOras+"\nTemperatura:\nMinima: "+min+"°C\nMaximă: " + max + "°C");
                                orasTV.setText(forecastText.toString());
                            }
                        });
                    }
                });
            }
        });
    }
}