package com.landenlabs.all_colormatrix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpinnerImageTextAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private Data[] data;

    static class Data {
        String text;
        int imageRes;
        Data(String text, int imageRes) {
            this.text = text;
            this.imageRes = imageRes;
        }
    }

    public SpinnerImageTextAdapter(Context context, Data[] data) {
        super(context,  R.layout.spinner_image_text);
        this.ctx = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_image_text, parent, false);

        TextView textView = row.findViewById(R.id.spinnerText);
        textView.setText(data[position].text);

        ImageView imageView = row.findViewById(R.id.spinnerImage);
        imageView.setImageResource(data[position].imageRes);

        return row;
    }
}