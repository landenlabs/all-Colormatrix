package com.landenlabs.all_colormatrix;

/*
 * Copyright (C) 2019 Dennis Lang (landenlabs@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

/**
 * Adapter for pull-down menu (spinner) with image and text.
 */
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

    SpinnerImageTextAdapter(Context context, Data[] data) {
        super(context,  R.layout.spinner_image_text);
        this.ctx = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_image_text, parent, false);

        TextView textView = row.findViewById(R.id.spinnerText);
        textView.setText(data[position].text);

        ImageView imageView = row.findViewById(R.id.spinnerImage);
        imageView.setImageResource(data[position].imageRes);

        return row;
    }
}