package com.fiek.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ParashikimiAdapter extends ArrayAdapter {

    List mList = new ArrayList();

    public ParashikimiAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(ParashikimiModel object) {
        super.add(object);
        mList.add(object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;
        ForecastHolder forecastHolder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.parashikimi_rreshti, parent, false);
            forecastHolder = new ForecastHolder();
            forecastHolder.date_txt = row.findViewById(R.id.date_time);
            forecastHolder.temperature_txt = row.findViewById(R.id.temperature);
            forecastHolder.feelslike_txt = row.findViewById(R.id.feelsLike);
            forecastHolder.condit_txt = row.findViewById(R.id.Condition);
            forecastHolder.conditDescription_txt = row.findViewById(R.id.ConditionDescription);
            forecastHolder.wind_txt = row.findViewById(R.id.Wind);
            forecastHolder.humid_txt = row.findViewById(R.id.Humidity);
            forecastHolder.pressure_txt = row.findViewById(R.id.Pressure);
            forecastHolder.icon_img = row.findViewById(R.id.weatherIcon);
            row.setTag(forecastHolder);
        } else {
            forecastHolder = (ForecastHolder) row.getTag();
        }

        ParashikimiModel parashikimiModel = (ParashikimiModel) this.getItem(position);
        forecastHolder.date_txt.setText(parashikimiModel.getDate());
        forecastHolder.temperature_txt.setText((int) Math.round(parashikimiModel.getTemperature()) + "");
        forecastHolder.feelslike_txt.setText((int) Math.round(parashikimiModel.getFeels_like()) + "");
        forecastHolder.condit_txt.setText(parashikimiModel.getCondition());
        forecastHolder.conditDescription_txt.setText(parashikimiModel.getConditionDescription());
        forecastHolder.wind_txt.setText("Era: " + Math.round((parashikimiModel.getWind()) * 10.0) / 10.0 + " km/h");
        forecastHolder.humid_txt.setText("Lagështia: " + Math.round((parashikimiModel.getHumidity()) * 10.0) / 10.0 + "%");
        forecastHolder.pressure_txt.setText("Shtypja: " + Math.round((parashikimiModel.getPressure()) * 10.0) / 10.0 + " kPa");
        forecastHolder.icon_img.setImageResource(VendosFotot.weatherIcon(parashikimiModel.getICON()));
        return row;
    }

    static class ForecastHolder {
        TextView date_txt;
        TextView temperature_txt;
        TextView feelslike_txt;
        TextView condit_txt;
        TextView conditDescription_txt;
        TextView wind_txt;
        TextView humid_txt;
        TextView pressure_txt;
        ImageView icon_img;
    }
}
