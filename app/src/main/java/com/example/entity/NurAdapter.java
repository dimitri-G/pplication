package com.example.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pplication.R;

import java.util.List;

public class NurAdapter extends ArrayAdapter<Nur> {
    private int resourceId;

    public NurAdapter(Context context, int textViewResourceId, List<Nur> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Nur shop = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView title = view.findViewById(R.id.title);
        TextView real = view.findViewById(R.id.userReal);
        TextView std = view.findViewById(R.id.standard);

        //shopAddr.setVisibility(View.GONE);
        title.setText(shop.getTitle());
        real.setText(shop.getReal());
        std.setText(shop.getStd());

        return view;
    }
}
