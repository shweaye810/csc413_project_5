package com.tz.shwe.final_project;


import android.content.Context;

public abstract class AbstractShapeFactory
{
    abstract Shape getShape (Context cnxt, ShapeType shape);

    public static ShapeFactory getShapeFactory (int style)
    {
        return new ShapeFactory(style/10, style%10);
    }
}
