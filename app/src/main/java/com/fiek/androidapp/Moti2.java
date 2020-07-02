package com.fiek.androidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class Moti2 extends Fragment {

    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    TextView txt5;

    public Moti2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moti2, container, false);
        txt1 = view.findViewById(R.id.text1);
        txt2 = view.findViewById(R.id.text2);
        txt3 = view.findViewById(R.id.text3);
        txt4 = view.findViewById(R.id.text4);
        txt5 = view.findViewById(R.id.text5);
        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        if (sh.contains(Moti1.emri)) {
            txt1.setText("Qyteti: " + (sh.getString(Moti1.emri, "")));
        }
        if (sh.contains(Moti1.temp)) {
            txt2.setText("Temperatura: " + (sh.getString(Moti1.temp, "")));
        }
        if (sh.contains(Moti1.desc)) {
            txt3.setText("Koha: " + (sh.getString(Moti1.desc, "")));
        }
        if (sh.contains(Moti1.lindja)) {
            txt4.setText(sh.getString(Moti1.lindja, ""));
        }if (sh.contains(Moti1.emri)) {
            txt5.setText(sh.getString(Moti1.perendimi, ""));
        }

        return view;
    }
}