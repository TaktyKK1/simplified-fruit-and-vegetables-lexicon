package com.example.proj10_jurek_lukasz_leksykon_owocow_i_warzyw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WpisAdapter extends ArrayAdapter<Roslina> {

    public WpisAdapter(Context context, List<Roslina> rosliny) {
        super(context, 0, rosliny);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Roslina roslina = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.wpis_komorka, parent, false);

        TextView title = convertView.findViewById(R.id.komorkaTytul);
        TextView desc = convertView.findViewById(R.id.komorkaOpis);
        ImageView imageView = convertView.findViewById(R.id.imageView2);
        title.setText(roslina.getNazwa());
        desc.setText(roslina.getTyp());
        if(desc.getText().toString().equals("Warzywo")){
            imageView.setImageResource(R.drawable.warzywo);
        }
        else{
            imageView.setImageResource(R.drawable.owoc);
        }
        return convertView;
        }
}

