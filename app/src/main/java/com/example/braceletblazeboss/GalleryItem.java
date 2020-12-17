package com.example.braceletblazeboss;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class GalleryItem
{
    public Drawable image;
    public String   title;

    public GalleryItem(Drawable image, String title)
    {
        this.image = image;
        this.title = title;
    }
}
