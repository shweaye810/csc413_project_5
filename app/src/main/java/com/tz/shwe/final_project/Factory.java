package com.tz.shwe.final_project;

public abstract class Factory {
    static public ShapeFactory get_shape_factory(int border, int fill) {
        return new ShapeFactory(border, fill);
    }
}
