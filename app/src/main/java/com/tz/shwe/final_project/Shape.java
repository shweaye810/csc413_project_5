package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by blo on 3/24/16.
 */
public abstract class Shape extends View {
    protected float x, y, r, a, rad, width, height, min;
    final int sz;
    protected Paint pnt;
    protected int fl, bdr;

    protected Shape(Context context, int border, int fill) {
        super(context);
        pnt = new Paint();
        sz = 50;
        rad = 1;
        x = (float) Math.random();
        y = (float) Math.random();
        r = (float) Math.random() / rad;
        width = main.width - sz * 2;
        height = main.height - sz * 2;
        bdr = border;
        fl = fill;
        min = 30;
        set_value();
    }
    protected void setShapeAlpha(float alpha) {
        this.setAlpha(alpha);
    }
    protected float getShapeAlpha() {
        return getAlpha();
    }

    protected void removeShape() {
        this.setVisibility(View.GONE);
    }

    protected abstract ShapeType getShapeType();

    @Override
    protected abstract void onDraw(Canvas cnv);

    protected void set_value() {
        a = (float) Math.sqrt(width * height / 50);
        x = (x * width + sz);
        y = (y * height + sz);
        r = (r * a + min);
    }
}
