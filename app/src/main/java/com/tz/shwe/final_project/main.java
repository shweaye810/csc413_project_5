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
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.RunnableFuture;

public class main extends AppCompatActivity {
    HashMap<Var, Int> map;
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
    String usr_in, crt_tok, s_tok;
    StringTokenizer str_tok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh_lyt = (RelativeLayout) findViewById(R.id.rltv_lyt);
        etxt = (EditText) findViewById(R.id.cin);
        sh_lst = new Vector();
        adapter = ArrayAdapter.createFromResource(this,
                                                  R.array.color_arrays, android.R.layout.simple_list_item_1);

        cntx = this.getApplicationContext();
        txt_vw = (TextView) findViewById(R.id.textView);
        cout = (TextView) findViewById(R.id.cout);
        sh_fact = Factory.get_shape_factory(bdr, fl);
        scl_vw = (ScrollView) findViewById(R.id.scroller);

        mode = "normal";
        map = new HashMap<Var, Int>();

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

                        scl_vw.post(new Runnable() {
                                @Override
                                public void run() {
                                    scl_vw.fullScroll(scl_vw.FOCUS_DOWN);
                                }
                            });
                    }
                    return handled;
                }
            });
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
    }

    void parse_expression(String s) {
        str_tok = new StringTokenizer(s);
        try {
            test_user_input();
        } catch (Exception e) {
            cout.append(e.getMessage());
        }
    }

    boolean is_circle(String s ) {
        return (s.equalsIgnoreCase("circle") || s.equalsIgnoreCase("circ"));
    }

    boolean is_rectangle(String s) {
        return (s.equalsIgnoreCase("rectangle") || s.equalsIgnoreCase("rect"));
    }

    boolean is_clear(String s)
    {
        return (s.equalsIgnoreCase("clear") || s.equalsIgnoreCase("cls"));
    }

    boolean is_int(String s ) {
        return s.equalsIgnoreCase("int");
    }
    boolean is_keyword(String s) {
        return is_int(s) || is_circle(s) || is_rectangle(s) || is_clear(s);
    }

    Int get_variable(Var s) {
        for (Var key: map.keySet()) {
            if (key.equalsIgnoreCase(s))
                return map.get(key);
        }
        return null;
    }

    boolean is_number(String s) {
        int i;
        for (i = 0; i < s.length(); ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return i > 0 ? true : false;
    }

    void get_token() {
        crt_tok = "";
        if (str_tok.hasMoreTokens()) {
            crt_tok = str_tok.nextToken();
        }
    }


    boolean is_primary(String s)
    {
        return ((s.length() > 0) && Character.isLetter(s.charAt(0)));
    }

    int get_int() throws Exception {
        int l = prim();
        return l;
    }

    int prim() throws Exception {
        get_token();
        int l = 0;
        if (is_primary(crt_tok)) {
            Var tmp = new Var(crt_tok);
            Int x = get_variable(tmp);
            if (x != null)
                l = x.get();
            else
                throw new Exception("Variable " + crt_tok + " is not found!\n");
        } else if (is_number(crt_tok)) {
            l = Integer.parseInt(crt_tok);
        } else if (crt_tok.equalsIgnoreCase("(")) {
            l = paren_expr();
        } else {
            throw new Exception("Syntax Error.\n");
        }
        return l;
    }
    int paren_expr() throws Exception {
        int v = expr();
        if (!crt_tok.equalsIgnoreCase(")")) {
            throw new Exception("Syntax Error. Expected ')'\n");
        }
        return v;
    }
    int term() throws Exception {
        int l = prim();
        while (true) {
            get_token();
            switch (crt_tok) {
            case "*":
                l *= prim();
                break;
            case "/":
                int d = prim();
                if (d != 0) {
                    l /= d;
                    break;
                };
                throw new Exception("Can't divide by 0\n");
            default:
                return l;
            }
        }
    }

    int expr() throws Exception {
        int l = term();
        while (true) {
            switch (crt_tok) {
            case "+":
                l += term();
                break;
            case "-":
                l -= term();
                break;
            default:
                return l;
            }
        }
    }

    void expr_list(boolean to_dcl) throws Exception {
        if (to_dcl)
            get_token();
        String s = crt_tok;
        get_token();
        if (is_primary(s) && crt_tok.equalsIgnoreCase("=")) {
            Int t = get_variable(new Var(s));
            if (t == null && !to_dcl) {
                throw new Exception("Variable's not declared!\n");
            } else if (t != null && to_dcl) {
                throw new Exception("Variable exist!\n");
            }
            int i = expr();
            if (!crt_tok.equalsIgnoreCase(";")) {
                throw new Exception("Syntax Error. Expected ';'\n");
            }

            if (!to_dcl) {
                t.set(i);
            } else {
                map.put(new Var(s), new Int(i));
            }
        } else {
            throw new Exception("Syntax Error.\n");
        }
    }

    void test_user_input() throws Exception{

        get_token();
        if (crt_tok == null) {
            return;
        } else if (is_int(crt_tok)) {
            try {
                expr_list(true);
            } catch (Exception e) {
                cout.append(e.getMessage() + "Usage: int x = 0 ;\n");
            }
        }
        else if (is_clear(crt_tok)){
            sh_lyt.removeAllViews();
            sh_lst.clear();
            updateShapeCount();
            map.clear();
            cout.setText("");
        }
        else if (is_circle(crt_tok)) {
            int x, y, r, s;
            try {
                x = get_int();
                y = get_int();
                r = get_int();
                s = get_int();
                adjustShapeAlpha();
                sh_fact = AbstractShapeFactory.getShapeFactory(s);
                sh = sh_fact.getShape(cntx, ShapeType.Circle);
                Circle.setRadius(r);
                Circle.setLocation(x, y);
                sh_lst.add(sh);
                updateShapeCount();
                sh_lyt.addView(sh);
            } catch (Exception e) {
                cout.append(e.getMessage() + "Usage: circle x y r s\n" );
            }
        }
        else if (is_rectangle(crt_tok)) {
            int x, y, x1, y1, s;
            try {
                x = get_int();
                y = get_int();
                x1 = get_int();
                y1 = get_int();
                s = get_int();
                adjustShapeAlpha();
                sh_fact = AbstractShapeFactory.getShapeFactory(s);
                Rectangle.setRectXY(x, y, x1, y1);
                sh = sh_fact.getShape(cntx, ShapeType.Rectangle);
                sh_lst.add(sh);
                updateShapeCount();
                sh_lyt.addView(sh);
            } catch (Exception e) {
                cout.append(e.getMessage() + "Usage: Rectangle x1 y1 x2 y2 s\n");
            }
        }
        else if (crt_tok.equalsIgnoreCase("print")) {
            for (Var key: map.keySet()) {
                cout.append(key.to_string() + " = " + map.get(key).get() + "\n");
            }
        }
        else {
            try {
                expr_list(false);
            } catch (Exception e) {
                cout.append(e.getMessage());
            }
        }
    }

    //fading animation: setting alpha to a translucent value (0 < alpha < 1)
    void  adjustShapeAlpha(){
        int i = 0;
        while (i < sh_lst.size()){
            Shape tmpShape = (Shape) sh_lst.get(i);
            if (tmpShape.getShapeAlpha() > 0.0f){
                tmpShape.setShapeAlpha(tmpShape.getShapeAlpha()- 0.1f);
            }
            else {
                tmpShape.removeShape();
                sh_lst.remove(i);
            }
            i++;
        }
    }

    //shape counter
    void updateShapeCount(){
        int noOfRectangle = 0, noOfCircle = 0, i = 0;
        while (i < sh_lst.size()){
            Shape tmpShape = (Shape) sh_lst.get(i);
            ShapeType shType = tmpShape.getShapeType();
            if (shType == ShapeType.Circle) {
                noOfCircle++;
            }
            else if (shType == ShapeType.Rectangle){
                noOfRectangle++;
            }
            i++;
        }
        txt_vw.setText("Circle: " + noOfCircle + "\tRectangle: " + noOfRectangle);
    }
}
