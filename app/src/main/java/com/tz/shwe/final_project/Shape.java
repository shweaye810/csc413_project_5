package com.tz.shwe.final_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by blo on 3/24/16.
 */
public abstract class Shape extends View
{
    protected Paint pnt;
    protected int fl, bdr;

    protected Shape(Context context, int border, int fill)
    {
        super(context);
        pnt = new Paint();
        bdr = border;
        fl = fill;
    }

    protected void setShapeAlpha(float alpha)
    {
        this.setAlpha(alpha);
    }

    protected float getShapeAlpha()
    {
        return getAlpha();
    }

    protected void removeShape()
    {
        this.setVisibility(View.GONE);
    }

    protected abstract ShapeType getShapeType();

    @Override
    protected abstract void onDraw(Canvas cnv);

    public static int getColor(int color)
    {
        switch (color)
        {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.GRAY;
            case 5:
                return Color.LTGRAY;
            case 6:
                return Color.MAGENTA;
            case 7:
                return Color.RED;
            case 8:
                return Color.YELLOW;
            case 9:
                return Color.WHITE;
        }
        return Color.BLACK;
    }
}

