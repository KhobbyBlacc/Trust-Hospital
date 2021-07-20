package com.example.waltex.trusthospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView donationsCard, heartCard, hearingCard, editCard, healthCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donationsCard = findViewById(R.id.donations_card);
        hearingCard = findViewById(R.id.hearing_card);
        healthCard = findViewById(R.id.health_card);
        editCard = findViewById(R.id.edit_card);
        heartCard = findViewById(R.id.heart_card);

        //add click listener
        donationsCard.setOnClickListener(this);
        heartCard.setOnClickListener(this);
        healthCard.setOnClickListener(this);
        hearingCard.setOnClickListener(this);
        editCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()) {
            case R.id.donations_card : i = new Intent(this, DonationsCard.class);
                    startActivity(i);
            break;

            case R.id.health_card : i = new Intent(this, HealthCard.class);
                startActivity(i);
                break;

            case R.id.hearing_card : i = new Intent(this, HearingCard.class);
                startActivity(i);
                break;

            case R.id.edit_card : i = new Intent(this, EditCard.class);
                startActivity(i);
                break;

            case R.id.heart_card : i = new Intent(this, HeartCard.class);
                startActivity(i);
                break;

                default: break;
        }
    }
}
