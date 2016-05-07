package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;

import java.util.Vector;

public class main extends AppCompatActivity {

    Vector sh_lst;
    Button bt_rct, bt_crc, bt_cls;
    Spinner spnr_bdr, spnr_fl;
    int bdr, fl;
    Shape sh;
    ShapeFactory sh_fact;
    Context cntx;
    static TextView txt_vw;
    public static float width, height;
    RelativeLayout sh_lyt;
    String mode;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh_lyt = (RelativeLayout) findViewById(R.id.rltv_lyt);
        bdr = fl = Color.BLACK;

        sh_lst = new Vector();
        spnr_bdr = (Spinner) findViewById(R.id.spinner_bdr);
        spnr_fl = (Spinner) findViewById(R.id.spinner_fl);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.color_arrays, android.R.layout.simple_list_item_1);

        spnr_bdr.setAdapter(adapter);
        spnr_fl.setAdapter(adapter);

        bt_rct = (Button) findViewById(R.id.btn_rct);
        bt_crc = (Button) findViewById(R.id.btn_crc);
        bt_cls = (Button) findViewById(R.id.btn_cls);

        cntx = this.getApplicationContext();
        txt_vw = (TextView) findViewById(R.id.textView);

        mode = "normal";

        bt_rct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adjustShapeAlpha();
                sh = sh_fact.getShape(cntx, ShapeType.Rectangle);
                sh_lst.add(sh);
                sh_lyt.addView(sh);
                updateShapeCount();
            }
        });

        bt_crc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adjustShapeAlpha();
                sh = sh_fact.getShape(cntx, ShapeType.Circle);
                sh_lst.add(sh);
                sh_lyt.addView(sh);
                updateShapeCount();
            }
        });

        bt_cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sh_lst.clear();
                sh_lyt.removeAllViews();
                updateShapeCount();
            }
        });

        spnr_bdr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int pos, long id) {
                bdr = get_color(parent.getItemAtPosition(pos).toString());
                sh_fact = Factory.get_shape_factory(bdr, fl);
            }

            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });

        spnr_fl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int pos, long id) {
                fl = get_color(parent.getItemAtPosition(pos).toString());
                sh_fact = Factory.get_shape_factory(bdr, fl);
            }

            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });

    }
    
    int get_color(String s)
    {
        if (s.equalsIgnoreCase("blue"))
            return Color.BLUE;
        else if (s.equalsIgnoreCase("cyan"))
            return Color.CYAN;
        else if (s.equalsIgnoreCase("Dark Gray"))
            return Color.DKGRAY;
        else if (s.equalsIgnoreCase("Gray"))
            return Color.GRAY;
        else if (s.equalsIgnoreCase("Green"))
            return Color.GREEN;
        else if (s.equalsIgnoreCase("Light Gray"))
            return Color.LTGRAY;
        else if (s.equalsIgnoreCase("Magenta"))
            return Color.MAGENTA;
        else if (s.equalsIgnoreCase("Red"))
            return Color.RED;
        else if (s.equalsIgnoreCase("white"))
            return Color.WHITE;
        else if (s.equalsIgnoreCase("Yellow"))
            return Color.YELLOW;
        return Color.BLACK;
    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        updateSizeInfo();
    }
    private void updateSizeInfo() {
        width = sh_lyt.getWidth();
        height = sh_lyt.getHeight();
    }
    void adjustShapeAlpha() {
        for (int i = 0; i < sh_lst.size(); i++) {
            Shape tmp = (Shape) sh_lst.get(i);
            if (tmp.getShapeAlpha() > 0.1f) {
                tmp.setShapeAlpha(tmp.getShapeAlpha() - 0.1f);
            } else {
                tmp.removeShape();
                sh_lst.remove(i);
            }
        }

    }
    void updateShapeCount() {
        int rct_cnt = 0, crc_cnt = 0;
        for (int i = 0; i < sh_lst.size(); i++) {
            Shape tmp = (Shape) sh_lst.get(i);
            ShapeType sh_t = tmp.getShapeType();
            if (sh_t == ShapeType.Rectangle)
                rct_cnt++;
            else if (sh_t == ShapeType.Circle)
                crc_cnt++;
        }
        txt_vw.setText(rct_cnt + " Rectangles, " + crc_cnt + " Circles.");
    }
}
