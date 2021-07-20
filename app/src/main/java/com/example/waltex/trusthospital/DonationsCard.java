package com.example.waltex.trusthospital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class DonationsCard extends AppCompatActivity {

    private LinearLayout bottomsheetLayout;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView imgShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_card);

        bottomsheetLayout = findViewById(R.id.bottomsheet);
        imgShare = findViewById(R.id.sharingImg);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheetLayout);

        //state hidden
       bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        //state Collapsed
      //  bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //state expand
       // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show BottomSheet
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

    }
}
