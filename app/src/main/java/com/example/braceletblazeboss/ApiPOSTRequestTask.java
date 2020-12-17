package com.example.braceletblazeboss;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class ApiPOSTRequestTask extends AsyncTask<ApiRequestParams, Integer, ApiResult>
{
    public TaskObserver taskObserver = null;
    private static String CK = "ck_936f4d6d4849180d245b94d5c09b9b3b13ed7707";
    private static String CS = "cs_bf4897c554689e1dd14c0c16404db672390bbcc2";
    private static String clrf = "\r\n";
    private static String hyphen = "--";
    private static String boundary = "111DIVISION111";

    public ApiPOSTRequestTask(TaskObserver taskObserver)
    {
        this.taskObserver = taskObserver;
    }

    @Override
    protected ApiResult doInBackground(ApiRequestParams... apiParams)
    {
        ApiResult result = new ApiResult();
        URL url = null;
        String postfix = "consumer_key=" + CK;
        postfix += "&consumer_secret=" + CS;

        ApiRequestParams params = apiParams[0];

        // Compress drawable
        Bitmap bmp = ((BitmapDrawable) params.image).getBitmap();
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 0, byteOutputStream);
        final byte[] bmpData = byteOutputStream.toByteArray();

        try
        {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("https://braceletblaze.com/wp-json/wp/v2/media")
                    .append("?").append(postfix);

            url = new URL(urlBuilder.toString());

            String response = "";
            BufferedReader reader = null;


            try
            {
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setChunkedStreamingMode(0);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Disposition", "attachment; filename=" + params.imageFileName);
                connection.setRequestProperty("Content-Type", URLConnection.guessContentTypeFromName(params.imageFileName));
                DataOutputStream data = new DataOutputStream(connection.getOutputStream());
                data.write(bmpData);
                data.flush();

                int responseCode = connection.getResponseCode();
                //writer.write(bmpData);
                //writer.flush();
                //OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                //writeStream(out);

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }

                response = builder.toString();

                result.set(response);
                connection.disconnect();
            }
            catch (IOException e)
            {
                result.set(e.getMessage());
                e.printStackTrace();
            }
            finally
            {
                try {
                    reader.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (MalformedURLException e)
        {
            result.set(e.getMessage());
            e.printStackTrace();
        }


        return result;
    }

    /**
     * Creates the request payload
     *
     * @param  params
     * @return String
     */
    protected String createMultipartPayload(ApiRequestParams params)
    {
        StringBuilder builder = new StringBuilder();
        // Add text content
        // Content type text/plain
        // Content disposition form-data; name="";
        // Add the image
        // filename
        builder.append(hyphen).append(boundary).append(clrf)
                .append("Content-Disposition: form-data; name=\"").append(params.imageName).append("\"; filename=\"")
                .append(params.imageFileName).append("\"").append(clrf)
                .append("Content-Type: ").append(URLConnection.guessContentTypeFromName(params.imageFileName)).append(clrf);

        return builder.toString();
    }

}
