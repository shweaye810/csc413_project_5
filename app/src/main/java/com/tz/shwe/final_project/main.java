package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.ExtractEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
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

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class main extends AppCompatActivity {
    HashMap<String, Integer> list;
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
        list = new HashMap<String, Integer>();

        etxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    usr_in = etxt.getText().toString();
                    etxt.setText("");
                    //
                    cout.append(usr_in + "\n");
                    parse_expression(usr_in);

                    handled = true;
                    cout.append("> ");
                    scl_vw.fullScroll(ScrollView.FOCUS_DOWN);
                }
                return handled;
            }
        });
    }
    /*
    private void make_circle() {
        for (int i = 0; i < 10; ++i) {
            sh = sh_fact.getShape(cntx, ShapeType.Circle);
            sh_lst.add(sh);
            sh_lyt.addView(sh);
            updateShapeCount();
        }
    }

*/
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
    }

    void parse_expression(String s) {
        str_tok = new StringTokenizer(s);
        Error err = test_user_input();
        if (err == Error.Syntax_error) {
            cout.append("Syntax Error\n");
        }

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
        return list.containsKey(s);
    }

    boolean is_number(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    void set_circle() {

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
/*
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
    */

    boolean is_primary(String s)
    {
        return ((s.length() > 0) && Character.isLetter(s.charAt(0)));
    }

    int expr() throws Exception {
        //cout.append("in expr()");
        String s = get_token();
        int l = 0;
        if (is_primary(s)) {
            if (has_variable(s))
                l = list.get(s);
        } else if (is_number(s)) {
            l = Integer.parseInt(s);
        }
        String t = get_token();
        switch (t) {
            case ";":
                return l;
            case "+":
                l += expr();
                break;
            case "-":
                l -= expr();
                break;
            case "*":
                l *= expr();
                break;
            case "/":
                l /= expr();
                break;
            default:
                throw new Exception("Syntax Error\n");
        }
        return l;
    }


    Error expr_list() {
        Error err = Error.none;
        // cout.append("in expr_list()");
        String s = get_token();
        // cout.append("s : " + s + ".");
        if (is_primary(s)) {
            String t = get_token();
            if (t.equalsIgnoreCase("=")) {
                try {
                    int i = expr();
                    list.put(s, i);
                } catch (Exception e) {
                    cout.append(e.getMessage());
                }
            } else {
                err = Error.Syntax_error;
            }
        } else {
            err = Error.Syntax_error;
        }
        return err;
    }


    Error test_user_input() {
        // cout.append("in text_user_input()");
        String s = get_token();
        // cout.append("s : " + s + ".");
        Error err = Error.none;
        if (is_int(s)) {
            err = expr_list();
        } else if (is_circle(s)) {

        } else if (is_rectangle(s)) {

        } else {
            err = Error.Syntax_error;
        }
        return err;
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
