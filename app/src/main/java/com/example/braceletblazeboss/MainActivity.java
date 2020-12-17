package com.example.braceletblazeboss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.EnvironmentCompat;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public  static String       LOGTAG = "MainActivity";
    private ApiPOSTRequestTask  apiPOSTRequestTask  = null;
    private Drawable            productDrawable     = null;
    private ImageView           productImageView    = null;
    private Button              uploadImageButton   = null;
    private Button              sendProductButton   = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productImageView  = findViewById(R.id.product_image);

        uploadImageButton = findViewById(R.id.button_upload_image);
        uploadImageButton.setOnClickListener(v -> displayGalleryPopup());
        sendProductButton = findViewById(R.id.button_send_product);
        sendProductButton.setOnClickListener(v -> sendProductToShop());


        apiPOSTRequestTask = new ApiPOSTRequestTask(new TaskObserver()
        {
            @Override public void onTaskCompleted(ApiResult result)
            {
                Log.d(LOGTAG, result.get());
            }
        });
    }

    public ArrayList<GalleryItem> readExternalStorageDir(String dirName)
    {
        ArrayList<GalleryItem> galleryList = new ArrayList<GalleryItem>();
        AssetManager manager = getAssets();

        //String path = Environment.getStorageDirectory().toString() + "/" + dirName;
                //Environment.getExternalStorageDirectory().toString() + "/" + dirName;

        String pictureDir = Environment.getExternalStoragePublicDirectory(

                Environment.DIRECTORY_PICTURES

        ).getAbsolutePath();

        Log.d(LOGTAG, "reading files from: " + pictureDir);

        try {

            File directory  = new File(pictureDir);
            File fileList[] = directory.listFiles();

            if (fileList == null)
            {

                Log.d(LOGTAG, "Directory does not exist");

            } else {

                Log.d(LOGTAG, "Files in directory: " + fileList.length);

                for (int i = 0; i < fileList.length; i++)
                {
                    String fileName = fileList[i].getName();
                    File file = new File(directory, fileName);
                    Drawable drawable = Drawable.createFromPath(file.toString());
                    galleryList.add(new GalleryItem(drawable, fileName));
                    Log.d(LOGTAG, "file: " + fileName);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return galleryList;

    }

    public void displayGalleryPopup() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View galleryView = inflater.inflate(R.layout.popup_gallery_layout, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow popupWindow = new PopupWindow(galleryView, width, height, true);
        popupWindow.showAtLocation(findViewById(R.id.main_container), Gravity.CENTER, 0, 0);

        // Load images here
        ArrayList<GalleryItem> galleryList = readExternalStorageDir("Pictures");
        ListView galleryListView = galleryView.findViewById(R.id.listview_gallery);
        GalleryListAdapter adapter = new GalleryListAdapter(this, galleryList);

        if (galleryList.size() > 0) {

            galleryListView.setAdapter(adapter);
            galleryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    productDrawable = galleryList.get(position).image;
                    Toast.makeText(getApplicationContext(), galleryList.get(position).title, Toast.LENGTH_LONG);
                    productImageView.setImageDrawable(productDrawable);
                    popupWindow.dismiss();
                    // set image to ui and dismiss the popup
                }
            });

        }

        Button cancelButton = galleryView.findViewById(R.id.button_cancel_popup);

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupWindow.dismiss();
            }
        });

    }

    public void sendProductToShop()
    {
        if (productDrawable != null && apiPOSTRequestTask != null)
        {
            apiPOSTRequestTask.execute(
                    new ApiRequestParams(productDrawable, "imagename", "imagename.jpg", "Dreamy")
            );
        }
    }
}