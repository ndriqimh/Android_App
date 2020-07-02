package com.fiek.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

public class veshjetMoti extends AppCompatActivity {

    private ImageView top;
    private ImageView bottom;
    private ImageView shoes;
    private ImageView Extras1;
    private ImageView Extras2;

    private TextView topTXT;
    private TextView bottomTXT;
    private TextView shoesTXT;
    private TextView Extras1TXT;
    private TextView Extras2TXT;
    public String a = "Veshjet";

    private TextView Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veshjet_moti);

        Double temperatura = Moti1.temperatura;    

        Location = findViewById(R.id.location_);
        top = findViewById(R.id.top_clothing_image);
        bottom = findViewById(R.id.bottom_clothing_image);
        shoes = findViewById(R.id.shoe_image);
        Extras1 = findViewById(R.id.extras1);
        Extras2 = findViewById(R.id.extras2);


        topTXT = findViewById(R.id.top_clothing_text);
        bottomTXT = findViewById(R.id.bottom_clothing_text);
        shoesTXT = findViewById(R.id.shoe_text);
        Extras1TXT = findViewById(R.id.extras1_text);
        Extras2TXT = findViewById(R.id.extras2_text);
        Location.setText(a);

        //hat
        if(temperatura <= 10){
            Extras1.setImageResource(R.drawable.hat);
            Extras1TXT.setText("Hat");

        } else{
            Extras1.setImageResource(R.color.Transparent);
            Extras1TXT.setText("");
        }

        //scarf
        if(temperatura < 5){
            Extras2.setImageResource(R.drawable.scarf);
            Extras2TXT.setText("Scarf");
        } else{
            Extras2.setImageResource(R.color.Transparent);
            Extras2TXT.setText("");
        }

        //top
        if(temperatura <= 5){
            top.setImageResource(R.drawable.winterjacket);
            topTXT.setText("Winter Jacket");
        } else if(temperatura > 5 && temperatura <= 12){
            top.setImageResource(R.drawable.jacket);
            topTXT.setText("Jacket");
        }else if(temperatura > 12 && temperatura < 20){
            top.setImageResource(R.drawable.sweater);
            topTXT.setText("Sweater");
        }else if(temperatura >= 20 && temperatura < 25){
            top.setImageResource(R.drawable.regularshirt);
            topTXT.setText("Shirt");
        }else if(temperatura >= 25){
            top.setImageResource(R.drawable.tshirt);
            topTXT.setText("T-Shirt");
        }

        //bottom
        if(temperatura >= 25){
            bottom.setImageResource(R.drawable.shorts);
            bottomTXT.setText("Shorts");
        }else{
            bottom.setImageResource(R.drawable.trousers);
            bottomTXT.setText("Trousers");
        }

        //boot
        if(temperatura < 5){
            shoes.setImageResource(R.drawable.boot);
            shoesTXT.setText("Boots");
        }else{
            shoes.setImageResource(R.drawable.shoe);
            shoesTXT.setText("Shoes");
        }
    }
}