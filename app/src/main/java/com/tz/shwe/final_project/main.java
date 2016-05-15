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
    Shape circle, rectangle;
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

    void set_circle() {

    }

    void get_token() {
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
                throw new Exception("variable " + crt_tok + " not found!\n");
        } else if (is_number(crt_tok)) {
            l = Integer.parseInt(crt_tok);
        } else if (crt_tok.equalsIgnoreCase("(")) {
            l = paren_expr();
        } else {
            throw new Exception("Syntax Error\n");
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

    void expr_list() throws Exception {
        get_token();
        String s = crt_tok;
        get_token();
        if (is_primary(s) && crt_tok.equalsIgnoreCase("=")) {
            try {
                int i = expr();
                if (!crt_tok.equalsIgnoreCase(";")) {
                    throw new Exception("Syntax Error\n");
                }
                map.put(new Var(s), new Int(i));
            } catch (Exception e) {
                cout.append(e.getMessage());
            }
        } else {
            throw new Exception("Syntax Error\n");
        }
    }

    void test_user_input() throws Exception{
        get_token();
        if (is_int(crt_tok)) {
            try {
                expr_list();
            } catch (Exception e) {
                cout.append(e.getMessage() + "Usage: int x = 0 ;\n");
            }
        } else if (is_circle(crt_tok)) {
            int x, y, r, s;
            try {
                x = get_int();
                y = get_int();
                r = get_int();
                s = get_int();
                cout.append(x + " " + y + " " + r + " " + s + "\n");
                circle = sh_fact.getShape(cntx,ShapeType.Circle);
                Circle.setRadius(r);
                Circle.setLocation(x, y);
                sh_lyt.addView(circle);
                sh_fact = AbstractShapeFactory.getShapeFactory(s);
            } catch (Exception e) {
                cout.append(e.getMessage() + "Usage: circle x y r s\n" );
            }
        } else if (is_rectangle(crt_tok)) {
            int x, y, x2, y2, s;
            try {
                x = get_int();
                y = get_int();
                x2 = get_int();
                y2 = get_int();
                s = get_int();
                cout.append(x + " "  + y + " " +  x2 + " " + y2 + " " + s + "\n");
                rectangle = sh_fact.getShape(cntx, ShapeType.Rectangle);
                sh_lyt.addView(rectangle);
                sh_fact = AbstractShapeFactory.getShapeFactory(s);
            } catch (Exception e) {
                cout.append(e.getMessage() + "Usage: Rectangle x1 y1 x2 y2 s\n");
            }
        } else if (crt_tok.equalsIgnoreCase("print")) {
            for (Var key: map.keySet()) {
                cout.append(key.to_string() + " = " + map.get(key).get() + "\n");
            }
        } else {
            throw new Exception("Syntax Error\n");
        }
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
