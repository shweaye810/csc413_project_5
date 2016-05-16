package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by blo on 3/24/16.
 */
public class Circle extends Shape
{
    private static int mPivotX, mPivotY;
    private static int radius;

    protected Circle(Context cnxt, int border, int fill)
    {
        super(cnxt, border, fill);
    }

    public static void setRadius(int r)
    {
        radius = r;
    }

    public static void setLocation(int x, int y)
    {
        mPivotX = x;
        mPivotY = y;
    }

    protected ShapeType getShapeType()
    {
        return ShapeType.Circle;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        pnt.setStyle(Paint.Style.FILL);
        pnt.setColor(getColor(fl));
        canvas.drawCircle(mPivotX, mPivotY, radius, pnt);
        pnt.setStyle(Paint.Style.STROKE);
        pnt.setColor((getColor(bdr)));
        pnt.setStrokeWidth(radius * 20 / 100);
        canvas.drawCircle(mPivotX, mPivotY, radius, pnt);
    }
}

