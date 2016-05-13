package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by blo on 3/24/16.
 */
public class Circle extends Shape {
    private int mPivotX, mPivotY, a;
    private float radius;

    protected Circle(Context cnxt, int border, int fill) {
        super(cnxt, border, fill);
    }

    /*protected void onDraw(Canvas cnv) {
        pnt.setColor(fl);
        pnt.setStyle(Paint.Style.FILL);
        cnv.drawCircle(x, y, r, pnt);
        int w = (int) (r) / 10;
        pnt.setStrokeWidth(w);
        pnt.setColor(bdr);
        pnt.setStyle(Paint.Style.STROKE);
        cnv.drawCircle(x, y, r, pnt);
    }*/

    public void setRadius()
    {
        //Generate random numbers for center point and radius of the circle
        radius = (1 + (float) Math.random()) * 50;
        a = 100;
        int minX = a * 2;
        int maxX = getWidth() - (a*2);

        int minY = a * 2;
        int maxY = getHeight() - (a*2);

        Random random = new Random();
        mPivotX = random.nextInt(maxX - minX + 1) + minX;
        mPivotY = random.nextInt(maxY - minY + 1) + minY;
    }

    protected ShapeType getShapeType() {
        return ShapeType.Circle;
    }

    @Override
    public void onDraw(Canvas canvas) {
        setRadius();
        pnt.setStyle(Paint.Style.FILL);
        pnt.setColor(getColor(fl));
        canvas.drawCircle(mPivotX, mPivotY, radius, pnt);
        pnt.setStyle(Paint.Style.STROKE);
        pnt.setColor((getColor(bdr)));
        pnt.setStrokeWidth(10);
        canvas.drawCircle(mPivotX, mPivotY, radius, pnt);
    }
}

