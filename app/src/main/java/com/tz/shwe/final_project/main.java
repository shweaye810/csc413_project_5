package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.ExtractEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;

import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.RunnableFuture;

public class main extends AppCompatActivity {
    HashSet<String> set;
    Vector sh_lst;
    int bdr, fl;
    Shape sh;
    ShapeFactory sh_fact;
    Context cntx;
    static TextView txt_vw, cout;
    public static float width, height;
    ScrollView scl_vw;
    RelativeLayout sh_lyt;
    String mode;
    ArrayAdapter<CharSequence> adapter;
    EditText etxt;
    String usr_in;
    StringTokenizer str_tok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh_lyt = (RelativeLayout) findViewById(R.id.rltv_lyt);
        bdr = fl = Color.BLACK;
        etxt = (EditText) findViewById(R.id.cin);
        bdr = fl = Color.BLACK;
        sh_lst = new Vector();
        adapter = ArrayAdapter.createFromResource(this,
                R.array.color_arrays, android.R.layout.simple_list_item_1);

        cntx = this.getApplicationContext();
        txt_vw = (TextView) findViewById(R.id.textView);
        cout = (TextView) findViewById(R.id.cout);
        sh_fact = Factory.get_shape_factory(bdr, fl);
        scl_vw = (ScrollView) findViewById(R.id.scroller);

        mode = "normal";

        etxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    usr_in = etxt.getText().toString();
                    etxt.setText("");
                    //
                    cout.append(usr_in + "\n> ");
                    parse_expression(usr_in);

                    handled = true;
                    scl_vw.fullScroll(ScrollView.FOCUS_DOWN);
                }
                return handled;
            }
        });
    }
    private void make_circle() {
        for (int i = 0; i < 10; ++i) {
            sh = sh_fact.getShape(cntx, ShapeType.Circle);
            sh_lst.add(sh);
            sh_lyt.addView(sh);
            updateShapeCount();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        updateSizeInfo();
    }
    private void updateSizeInfo() {
        if (sh_lyt == null)
            sh_lyt = (RelativeLayout) findViewById(R.id.rltv_lyt);
        width = sh_lyt.getMeasuredWidth();
        height = sh_lyt.getMeasuredHeight();
        if (width == 0)
            width = 500;
        if (height == 0)
            height = 500;
    }

    void parse_expression(String s) {
        str_tok = new StringTokenizer(s);
        boolean pass = test_user_input();

    }

    boolean is_circle(String s ) {
        return (s.equalsIgnoreCase("circle") || s.equalsIgnoreCase("circ"));
    }

    boolean is_rectangle(String s) {
        return (s.equalsIgnoreCase("rectangle") || s.equalsIgnoreCase("rect"));
    }

    boolean is_int(String s ) {
        return s.equalsIgnoreCase("int");
    }
    boolean is_keyword(String s) {
        return is_int(s) || is_circle(s) || is_rectangle(s);
    }

    boolean has_variable(String s) {
        return set.contains(s);
    }

    boolean is_number(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isDigit(s[i])) {
                return false;
            }
        }
        return true;
    }

    String get_token() {
        if (str_tok.hasMoreTokens()) {
            return str_tok.nextToken();
        }
        return "";
    }

    boolean is_operator(String s) {
        return s.equalsIgnoreCase("+") || s.equalsIgnoreCase("-") ||
                s.equalsIgnoreCase("*") || s.equalsIgnoreCase("/") || s.equalsIgnoreCase("\\");
    }

    void set_int () {
        String s = get_token();
        if (s.equalsIgnoreCase("=")) {
            int t = get_int();
        }
    }
    int get_int() {
        String s = get_token();
        if (has_variable(s)) {
            String t = get_token();
            if (t.length() == 0) {
                return Integer.parseInt(s);
            } else {

            }

        }
    }
    void set_circle() {

    }

    boolean test_expression() {
        String s = get_token();
        if (has_variable(s) || is_number(s)) {
            s = get_token();
            if (is_operator(s)) {
                
            }
        }
    }


    boolean test_user_input() {
        String s = get_token();

        if (is_int(s)) {
            s = get_token();
            if (s.equalsIgnoreCase("=")) {
                return test_expression();
            }
            return false;
        } else if (is_circle(s)) {

        } else if (is_rectangle(s)) {

        } else if (set.contains(s)) {

        }
        /*
        } else if (is_number(s)) {

        } else if (is_operator(s)) {

        }
*/
        return true;
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
