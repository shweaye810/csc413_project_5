package com.tz.shwe.final_project;

import android.content.Context;

/**
 * Created by blo on 3/24/16.
 */

public class ShapeFactory {
    protected int bdr, fl;

    public ShapeFactory(int border, int fill) {
        bdr = border;
        fl = fill;
    }

    public Shape getShape(Context cnxt, ShapeType shape) {
        if (shape == null)
            return null;
        if (shape == ShapeType.Circle)
            return new Circle(cnxt, bdr, fl);
        else if (shape == ShapeType.Rectangle)
            return new Rectangle(cnxt, bdr, fl);
        return null;
    }
}
