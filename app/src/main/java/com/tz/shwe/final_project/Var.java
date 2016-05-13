package com.tz.shwe.final_project;

/**
 * Created by shwe on 5/11/16.
 */
public class Var extends Ex {
    String name;

    Var(String s) { name = s; }

    String to_string() {
        return name;
    }
    boolean equalsIgnoreCase(Var t) {
        return this.name.equalsIgnoreCase(t.name);
    }

    boolean equals(Var t) {
        return this.name.equals(t.name);
    }

}
