package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by blo on 3/24/16.
 */
public class Rectangle extends Shape
{
    private static int mRectX, mRectY, mRectX1, mRectY1;

    protected Rectangle(Context cnxt, int border, int fill)
    {
        super(cnxt, border, fill);
    }

    protected ShapeType getShapeType()
    {
        return ShapeType.Rectangle;
    }

    public static void setRectXY(int x, int y, int x1, int y1)
    {
        mRectX = x;
        mRectY = y;
        mRectX1 = x1;
        mRectY1 = y1;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        pnt.setStyle(Paint.Style.FILL);
        pnt.setColor(getColor(fl));
        canvas.drawRect(mRectX, mRectY, mRectX1, mRectY1, pnt);
        pnt.setStyle(Paint.Style.STROKE);
        pnt.setColor(getColor(bdr));
        int r = (Math.abs(mRectX - mRectX1) + Math.abs(mRectY - mRectY1)) / 2;
        r = r * 20 / 100;
        pnt.setStrokeWidth(r);
        canvas.drawRect(mRectX, mRectY, mRectX1, mRectY1, pnt);
    }
}
