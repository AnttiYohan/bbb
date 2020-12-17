package com.example.braceletblazeboss;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GalleryListAdapter implements ListAdapter
{
    private ArrayList<GalleryItem> galleryItemList;
    private final Activity context;

    /**
     * FlightListAdapter constructor
     *
     * @param context
     * @param galleryItemList
     */
    public GalleryListAdapter(Activity context, ArrayList<GalleryItem> galleryItemList)
    {
        this.context         = context;
        this.galleryItemList = galleryItemList;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public int getCount()
    {
        return galleryItemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return galleryItemList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
        //return this.flightList.get(position).id;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    /**
     * Creates an custom ListView item from 'flight_list_row'
     *
     * @param  position
     * @param  rowView
     * @param  parent
     * @return View
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View rowView, @NonNull ViewGroup parent)
    {
        GalleryItem galleryItem = galleryItemList.get(position);

        if (rowView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.gallery_item_layout, null);

            ImageView image = rowView.findViewById(R.id.gallery_item_image);
            TextView  title = rowView.findViewById(R.id.gallery_item_title);

            image.setImageDrawable(galleryItem.image);
            title.setText(galleryItem.title);
        }

        return rowView;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        return galleryItemList.size();
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return false;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
}
