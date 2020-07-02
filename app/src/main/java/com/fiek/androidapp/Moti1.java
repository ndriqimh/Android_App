package com.fiek.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Moti1 extends Fragment {
    TextView view_city;
    TextView view_temp;
    TextView view_desc;
    TextView view_wind;
    TextView view_humidity;
    TextView view_sunrise;
    TextView view_sunset;

    ImageView view_weather;
    EditText search;
    FloatingActionButton search_floating;
    private Context mContext;
    private String responseData;
    private String icons;
    public static double temperatura;
    public String city;
    String json_String;

    private Button butoni_veshjet;
    private Button butoni_harta;
    private Button butoni_parashiko;

    public Moti1() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moti1, container, false);

        view_city = view.findViewById(R.id.town);
        view_temp = view.findViewById(R.id.temp);
        view_desc = view.findViewById(R.id.desc);

        view_wind = view.findViewById(R.id.wind);
        view_humidity = view.findViewById(R.id.humidity);

        view_sunrise = view.findViewById(R.id.sunrise);
        view_sunset = view.findViewById(R.id.sunset);

        view_weather = view.findViewById(R.id.wheather_image);
        search = view.findViewById(R.id.search_edit);
        search_floating = view.findViewById(R.id.floating_search);

        butoni_veshjet = view.findViewById(R.id.button);
        butoni_harta = view.findViewById(R.id.button2);
        butoni_parashiko = view.findViewById(R.id.butoni3);

        search_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getRootView().getWindowToken(), 0);
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    getWeather(String.valueOf(search.getText()));
                } else {
                    Toast.makeText(getActivity(), "Ju lutem lidhuni me internet!", Toast.LENGTH_LONG).show();

                }
            }
        });

        butoni_veshjet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    Intent i = new Intent(getActivity(), veshjetMoti.class);
                    startActivity(i);
                }
            }
        });

        butoni_harta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    Intent u = new Intent(getActivity(), MapsActivity.class);
                    startActivity(u);
                }
            }
        });

        butoni_parashiko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!city.equals(""))
                    getJson();
                else {
                    Toast.makeText(getActivity(), "Enter a location first", Toast.LENGTH_LONG).show();
                }
            }
        });

        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        String sh1 = sh.getString("view_city", "");
        String sh2 = sh.getString("view_desc", "");
        String sh3 = sh.getString("view_temp", "");
        String sh4 = sh.getString("view_wind", "");
        String sh5 = sh.getString("view_humiditys", "");
        String sh6 = sh.getString("view_sunrise", "");
        String sh7 = sh.getString("view_sunset", "");
        view_city.setText(sh1);
        view_desc.setText(sh2);
        view_temp.setText(sh3);
        view_wind.setText(sh4);
        view_humidity.setText(sh5);
        view_sunrise.setText(sh6);
        view_sunset.setText(sh7);

        return view;
    }

    private void getWeather(final String City) {
        OkHttpClient client = new OkHttpClient();

        city = City;
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=07548429c9a3c0607ce0887060372e63&units=metric")
                .get()
                .build();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONArray array = json.getJSONArray("weather");
                        JSONObject object = array.getJSONObject(0);

                        String description = object.getString("description");
                        icons = object.getString("icon");

                        JSONObject temp1 = json.getJSONObject("main");
                        Double Temperature = temp1.getDouble("temp");
                        temperatura = Temperature;

                        JSONObject wind1 = json.getJSONObject("wind");
                        Double Wind = wind1.getDouble("speed");


                        JSONObject humidity1 = json.getJSONObject("main");
                        Double Humidity = humidity1.getDouble("humidity");

                        JSONObject sunrise1 = json.getJSONObject("sys");
                        Double Sunrise = sunrise1.getDouble("sunrise");

                        JSONObject sunset1 = json.getJSONObject("sys");
                        Double Sunset = sunset1.getDouble("sunset");

                        setText(view_city, city);
                        String temps = Math.round(Temperature) + " °C";
                        setText(view_temp, temps);
                        setText(view_desc, description);
                        setImage(view_weather);

                        String winds = "Era: " + (Math.round(Wind) * 3.6) + " km/h";
                        setText(view_wind, winds);
                        String humiditys = "Lagështia: " + Math.round(Humidity) + "%";
                        setText(view_humidity, humiditys);

                        double ora1 = Math.round(Sunrise) * 1000;
                        Date data1 = new Date((long) ora1);
                        String sunrise = new SimpleDateFormat("hh:mm").format(data1);

                        double ora2 = Math.round(Sunset) * 1000;
                        Date data2 = new Date((long) ora2);
                        String sunset = new SimpleDateFormat("hh:mm").format(data2);

                        String sunrises = "Lindja: " + sunrise + " AM";
                        setText(view_sunrise, sunrises);
                        String sunsets = "Perëndimi: " + sunset + " PM";
                        setText(view_sunset, sunsets);

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPreferences.edit();

                        edit.putString("view_city", view_city.getText().toString());
                        edit.putString("view_desc", description);
                        edit.putString("view_temp", temps);
                        edit.putString("view_wind", winds);
                        edit.putString("view_humiditys", humiditys);
                        edit.putString("view_sunrise", sunrises);
                        edit.putString("view_sunset", sunsets);

                        edit.apply();


                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Ka ndodhur nje gabim,provoni përsëri", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Ka ndodhur nje gabim,provoni përsëri", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void setText(final TextView text, final String value) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText(value);
                }
            });
        }
    }

    private void setImage(final ImageView imageView) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String iconUrl = "http://openweathermap.org/img/wn/" + icons + "@2x.png";
                    Glide.with(mContext).load(iconUrl).into(imageView);
                }
            });
        }
    }

    public void getJson() {
        Toast.makeText(getActivity(), "Duke marrë të dhënat", Toast.LENGTH_SHORT).show();
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String JSONSTRING;
        String json_URL;

        @Override
        protected void onPreExecute() {
            json_URL = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=07548429c9a3c0607ce0887060372e63";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                java.net.URL URL = new URL(json_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) URL.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSONSTRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSONSTRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            json_String = result;
            parseJson();
        }
    }

    public void parseJson() {
        if (json_String == null) {
            Toast.makeText(getActivity(), "Provoni përsëri", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getActivity(), ParashikimiActivity.class);
            intent.putExtra("json", json_String);
            startActivity(intent);
        }
    }

}