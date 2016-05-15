package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by blo on 3/24/16.
 */
public class Rectangle extends Shape {
    private int mRectX, mRectY, b;
    private float mRectX1, mRectY1,mRectX2, mRectY2;

    protected Rectangle(Context cnxt, int border, int fill) {
        super(cnxt, border, fill);
    }

    /*protected void onDraw(Canvas cnv) {
        pnt.setColor(fl);
        pnt.setStyle(Paint.Style.FILL);
        cnv.drawRect(x, y, x + r, y + r, pnt);
        int w = (int) (r) / 10;
        pnt.setStrokeWidth(w);
        pnt.setColor(bdr);
        pnt.setStyle(Paint.Style.STROKE);
        cnv.drawRect(x, y, x + r, y + r, pnt);
    }*/

    protected ShapeType getShapeType() {
        return ShapeType.Rectangle;
    }

    public void setRectXY()
    {
        //Generate random no. for left, top, right, bottom locations of rect
        b = 50;
        mRectX2 = (1 + (float) Math.random()) * 100;
        mRectY2 = (1 + (float) Math.random()) * 100;
        int minX = b * 2;
        int maxX = getWidth() - (b*2);

        int minY = b * 2;
        int maxY = getHeight() - (b *2);

        Random random = new Random();
        mRectX = random.nextInt(maxX - minX) + minX;
        mRectY = random.nextInt(maxY - minY) + minY;
        mRectX1 = mRectX + mRectX2;
        mRectY1 = mRectY + mRectY2;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        setRectXY();
        pnt.setStyle(Paint.Style.FILL);
        pnt.setColor(getColor(fl));
        canvas.drawRect(mRectX, mRectY, mRectX1, mRectY1, pnt);
        pnt.setStyle(Paint.Style.STROKE);
        pnt.setColor(getColor(bdr));
        pnt.setStrokeWidth(10);
        canvas.drawRect(mRectX-5, mRectY-5, mRectX1+5, mRectY1+5, pnt);
    }
}
