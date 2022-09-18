package com.example.foodstore;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

public class Languages {

    private final String language;
    private final Drawable icon;

    public Languages(String language, Drawable icon)
    {
        this.language = language;
        this.icon = icon;
    }

    public String getLanguage()
    {
        return language;
    }

    public Drawable getIcon() {
        return icon;
    }
}