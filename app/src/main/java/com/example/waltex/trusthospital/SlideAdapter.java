package com.example.waltex.trusthospital;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    //list of images
    public int[] list_images = {
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
    };

    //list of titles
    public String[] list_titles = {
           "Health Care",
            "Hygiene",
            "Vaccination",
            "Heart"
    };

    //list of descriptions
    public String[] list_description = {
            "Health care, health-care, or healthcare is the maintenance or improvement of health via the prevention, diagnosis, and treatment of disease, illness, injury, and other physical and mental impairments in people. Health care is delivered by health professionals in allied health fields. Physicians and physician associates are a part of these health professionals.",
            "Hygiene is a set of practices performed to preserve health. According to the World Health Organization (WHO), \"Hygiene refers to conditions and practices that help to maintain health and prevent the spread of diseases.\" Personal hygiene refers to maintaining the body's cleanliness.",
            "Vaccination is the administration of a vaccine to help the immune system develop protection from a disease. Vaccines contain a microorganism or virus in a weakened or killed state, or proteins or toxins from the organism.",
            "The heart is a muscular organ in most animals, which pumps blood through the blood vessels of the circulatory system. Blood provides the body with oxygen and nutrients, as well as assisting in the removal of metabolic wastes."
    };

    //list of backgroundColors
    public int[] list_backgroundcolor = {
            Color.rgb(124,77,255),
            Color.rgb(235,85,85),
            Color.rgb(110,49,49),
            Color.rgb(1,180,212),

    };

public SlideAdapter (Context context) {
    this.context = context;
}




    @Override
    public int getCount() {
        return list_titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slide, container, false);
        LinearLayout layoutslide = v.findViewById(R.id.slideLinearLayout);
        ImageView imgslide = v.findViewById(R.id.slideImg);
        TextView txttitle = v.findViewById(R.id.txttile);
        TextView description = v.findViewById(R.id.txtDescription);
        layoutslide.setBackgroundColor(list_backgroundcolor[position]);
        imgslide.setImageResource(list_images[position]);
        txttitle.setText(list_titles[position]);
        description.setText(list_description[position]);
        container.addView(v);

        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);

    }
}
