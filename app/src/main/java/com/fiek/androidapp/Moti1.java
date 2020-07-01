package com.fiek.androidapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Moti1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Moti1 extends Fragment {
    TextView view_city;
    TextView view_temp;
    TextView view_desc;

    ImageView view_weather;
    EditText search;
    FloatingActionButton search_floating;
    private Context mContext;
    private String responseData;
    private String icons;

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

        view_weather = view.findViewById(R.id.wheather_image);
        search = view.findViewById(R.id.search_edit);
        search_floating = view.findViewById(R.id.floating_search);

        search_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getRootView().getWindowToken(), 0);
                getWeather(String.valueOf(search.getText()));
            }
        });
        return view;
    }

    private void getWeather(final String City)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=" + City + "&appid=07548429c9a3c0607ce0887060372e63&units=metric")
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

                        setText(view_city, City);
                        String temps = Math.round(Temperature) + " °C";
                        setText(view_temp, temps);
                        setText(view_desc, description);
                        setImage(view_weather);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
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
}