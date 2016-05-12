package com.tz.shwe.final_project;

/**
 * Created by shwe on 5/11/16.
 */

public class BinaryEx extends Ex{
    String op;
    Ex lt;
    Ex rt;
    BinaryEx(String o, Ex l, Ex r) {
        lt = l;
        rt = r;
        op = o;
    }
}
