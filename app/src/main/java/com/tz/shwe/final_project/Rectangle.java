package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by blo on 3/24/16.
 */
public class Rectangle extends Shape {
    protected Rectangle(Context cnxt, int border, int fill) {
        super(cnxt, border, fill);
    }
    protected void onDraw(Canvas cnv) {
        pnt.setColor(fl);
        pnt.setStyle(Paint.Style.FILL);
        cnv.drawRect(x, y, x + r, y + r, pnt);
        int w = (int) (r) / 10;
        pnt.setStrokeWidth(w);
        pnt.setColor(bdr);
        pnt.setStyle(Paint.Style.STROKE);
        cnv.drawRect(x, y, x + r, y + r, pnt);
    }
    protected ShapeType getShapeType() {
        return ShapeType.Rectangle;
    }
}
