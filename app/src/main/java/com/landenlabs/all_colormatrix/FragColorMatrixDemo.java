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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MenuCompat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import utils.SeekBarExt1;
import com.landenlabs.all_colormatrix.SpinnerImageTextAdapter.Data;


/**
 * Sample fragment demonstrate ColorMatrix.
 */
@SuppressWarnings("UnnecessaryLocalVariable")
public class FragColorMatrixDemo extends FragBottomNavBase
        implements AdapterView.OnItemSelectedListener {

    private GridView gridview;
    private SeekBarExt1 slider;
    private ImageView imageView;
    private Spinner imageMenu;
    private Spinner bgMenu;
    private Set<Integer> gridCellSelected = new HashSet<>();
    private ColorStateList colorGreen = new ColorStateList(
            new int[][]{ new int[]{}},
            new int[]{  0xc0008000 }    // GREEN
    );



    // Matrix single array, as follows: [ a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t ]
    // When applied to a color [r, g, b, a], the resulting color is computed as (after clamping) ;
    //   R' = a*R + b*G + c*B + d*A + e;
    //   G' = f*R + g*G + h*B + i*A + j;
    //   B' = k*R + l*G + m*B + n*A + o;
    //   A' = p*R + q*G + r*B + s*A + t;
    private float[] matrix = {
            1, 0, 0, 0, 0,          // red
            0, 1, 0, 0, 0,          // green
            0, 0, 1, 0, 0,          // blue
            .5f, .5f, .5f, 0, 0,    // alpha

    };

    private float[] matrixNormal = {
            1, 0, 0, 0, 0,          // red
            0, 1, 0, 0, 0,          // green
            0, 0, 1, 0, 0,          // blue
            0, 0, 0, 1, 0           // alpha
    };

    private float[] matrixInvert = {
            -1, 0, 0, 0, 255,          // red
            0, -1, 0, 0, 255,          // green
            0, 0, -1, 0, 255,          // blue
            1, 1, 1, 1, 0            // alpha
    };

    private float[] matrixRed = {
            1, 1, 1, 0, 0,          // red
            0, 0, 0, 0, 0,          // green
            0, 0, 0, 0, 0,          // blue
            0, 0, 0, 1, 0           // alpha
    };

    private float[] matrixGreen = {
            0, 0, 0, 0, 0,          // red
            1, 1, 1, 0, 0,          // green
            0, 0, 0, 0, 0,          // blue
            0, 0, 0, 1, 0           // alpha
    };


    private float[] matrixBlue = {
            0, 0, 0, 0, 0,          // red
            0, 0, 0, 0, 0,          // green
            1, 1, 1, 0, 0,          // blue
            0, 0, 0, 1, 0           // alpha
    };

    private float[] matrixGray = {
            0.33f, 0.33f, 0.33f, 0, 0,          // red
            0.33f, 0.33f, 0.33f, 0, 0,          // green
            0.33f, 0.33f, 0.33f, 0, 0,          // blue
            0, 0, 0, 1, 0               // alpha
    };

    /*  https://docs.rainmeter.net/tips/colormatrix-guide/
        ;Black & White
        ColorMatrix1=1.5;1.5;1.5;0;0
        ColorMatrix2=1.5;1.5;1.5;0;0
        ColorMatrix3=1.5;1.5;1.5;0;0
        ColorMatrix5=-1;-1;-1;0;1
     */

    private Data[] imageList = new Data[] {
            new Data("sphere 3 ", R.drawable.bg_sphere3),
            new Data("cloud", R.drawable.wx_sun_30d),
            new Data("sun", R.drawable.wx_sun_31d),
            new Data("sphere red", R.drawable.bg_sphere_red),
            new Data("sphere green", R.drawable.bg_sphere_green),
            new Data("raindrop", R.drawable.wx_thunder),
            new Data("black & white", R.drawable.squares100),
    };

    private Data[] bgList = new Data[] {
            new Data("black & white", R.drawable.squares100),
            new Data("black", R.drawable.bg_all_black),
            new Data("white", R.drawable.bg_all_white),
            new Data("gray", R.drawable.bg_all_gray),
            new Data("sphere red", R.drawable.bg_sphere_red),
            new Data("sphere green", R.drawable.bg_sphere_green),
            new Data("sphere 3 ", R.drawable.bg_sphere3),
            new Data("colors", R.drawable.bg_colors)
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, R.layout.frag_colormatrix_demo);
        this.setHasOptionsMenu(true);
        setBarTitle("ColorMatrix Demo");

        gridview = root.findViewById(R.id.matrix_gridview);
        gridview.setAdapter(new ColorMatrixAdapter(getActivitySafe()));
        gridview.requestLayout();

        imageView = root.findViewById(R.id.matrix_image);
        imageView.setColorFilter(new ColorMatrixColorFilter(matrix));

        slider = root.findViewById(R.id.matrix_slider);
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setValue(progress / 100.0f - 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        imageMenu = root.findViewById(R.id.matric_image_menu);
        SpinnerImageTextAdapter imageTextAdapter = new SpinnerImageTextAdapter(getContext(), imageList );
        imageMenu.setAdapter(imageTextAdapter);
        imageMenu.setOnItemSelectedListener(this);

        bgMenu = root.findViewById(R.id.matric_bg_menu);
        SpinnerImageTextAdapter bgAdapter = new SpinnerImageTextAdapter(getContext(), bgList );
        bgMenu.setAdapter(bgAdapter);
        bgMenu.setOnItemSelectedListener(this);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        menu.clear();
        MenuCompat.setGroupDividerEnabled(menu, true);
        inflater.inflate(R.menu.menu_settings, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.matrix_menu_r:
                gridCellSelected.clear();
                System.arraycopy(matrixRed, 0, matrix, 0, matrix.length);
                break;
            case R.id.matrix_menu_g:
                gridCellSelected.clear();
                System.arraycopy(matrixGreen, 0, matrix, 0, matrix.length);
                break;
            case R.id.matrix_menu_b:
                gridCellSelected.clear();
                System.arraycopy(matrixBlue, 0, matrix, 0, matrix.length);
                break;
            case R.id.matrix_menu_gray:
                gridCellSelected.clear();
                System.arraycopy(matrixGray, 0, matrix, 0, matrix.length);
                break;
            case R.id.matrix_menu_invert:
                gridCellSelected.clear();
                System.arraycopy(matrixInvert, 0, matrix, 0, matrix.length);
                break;
            case R.id.matrix_menu_0:
                if (!gridCellSelected.isEmpty()) {
                     setValue(0f);
                } else {
                    Arrays.fill(matrix, 0f);
                }
                break;
            case R.id.matrix_menu_1:
                if (!gridCellSelected.isEmpty()) {
                    setValue(1f);
                } else {
                    Arrays.fill(matrix, 1f);
                }
                break;
            case R.id.matrix_menu_normal:
                gridCellSelected.clear();
                System.arraycopy(matrixNormal, 0, matrix, 0, matrix.length);
                break;
        }

        imageView.setColorFilter(new ColorMatrixColorFilter(matrix));
        gridview.setAdapter(new ColorMatrixAdapter(getActivitySafe()));
        gridview.requestLayout();

        return false;
        // return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        // super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void setValue(float fValue) {
        for (Integer pos : gridCellSelected) {
            TextView view = (TextView)gridview.getChildAt(pos);
            boolean lastCol = (pos%5)==4;
            float value = lastCol ? Math.max(0, fValue)*255 : fValue;
            view.setText(lastCol ? String.valueOf(Math.round(value)) : floatStr(value));
            matrix[pos] = value;
        }
        imageView.setColorFilter(new ColorMatrixColorFilter(matrix));
    }

    private static String floatStr(float fvalue) {
        String str = String.format(Locale.US, "%1.2f", fvalue);
        return str;
    }


    // =============================================================================================
    //  Adapter required to fill GridView cell values.

    private final static int leftCellPadPx = 10;
    private final static int rightCellPadPx = 10;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Data data;
        if (parent == imageMenu) {
            data = imageList[pos];
            imageView.setImageResource(data.imageRes);
        }
        if (parent == bgMenu) {
            data = bgList[pos];
            imageView.setBackground( imageView.getResources().getDrawable(data.imageRes, imageView.getContext().getTheme()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @SuppressWarnings("Convert2Lambda")
    class ColorMatrixAdapter extends BaseAdapter {
        private final Context mContext;
        private final int rowHeightPx;

        ColorMatrixAdapter(Context context) {
            mContext = context;
            rowHeightPx = context.getResources().getDimensionPixelSize(R.dimen.page_row_height);
        }

        public int getCount() {
            return matrix.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            View view;
            GridView.LayoutParams lp = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rowHeightPx);
            int topCellPadPx = 0;

            view = makeText(matrix[pos], pos);
            view.setLayoutParams(lp);
            view.setPadding(leftCellPadPx, topCellPadPx, rightCellPadPx, 0);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getBackground() == null) {
                        view.setBackgroundResource(R.drawable.bg_anim_gradient);
                        view.setBackgroundTintList(colorGreen);
                        ((AnimatedVectorDrawable) view.getBackground()).start();
                        float fvalue = matrix[pos];
                        boolean lastCol = (pos % 5) == 4;
                        fvalue = lastCol ? fvalue/255 : fvalue;
                        slider.setProgress((Math.round((fvalue+1)*100)));
                        gridCellSelected.add(pos);
                    } else {
                        view.setBackground(null);
                        gridCellSelected.remove(pos);
                    }
                }
            });
            return view;
        }


        @SuppressWarnings("unused")
        private TextView makeText(float fvalue, final int pos) {
            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            // textView.setText(floatStr(fvalue));
            boolean lastCol = (pos % 5) == 4;
            textView.setText(lastCol ? String.valueOf(Math.round(fvalue)) : floatStr(fvalue));
            textView.setTextColor(Color.WHITE);
            return textView;
        }

    }
}
