package com.example.braceletblazeboss;

import android.graphics.drawable.Drawable;

public class ApiRequestParams
{
    Drawable image;
    String   imageName;
    String   imageFileName;
    String   title;
    int      id;

    public ApiRequestParams()
    {
        this.image = null;
        this.title = "";
        this.id    = 0;
    }

    public ApiRequestParams(Drawable image, String imageName, String imageFileName, String title)
    {
        this.image = image;
        this.imageName = imageName;
        this.imageFileName = imageFileName;
        this.title = title;
        this.id    = 0;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
