package fi.jessestenroth.koskaelokuvat.data.functions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is
 * from https://stackoverflow.com/questions/18953632/how-to-set-image-from-url-for-imageview
 */
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    //image url (comment by Jesse Stenroth)
    private String url;
    //imageview from view (comment by Jesse Stenroth)
    private ImageView imageView;

    public ImageLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            //open internet connection (comment by Jesse Stenroth)
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            //create bitmap of image (comment by Jesse Stenroth)
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        //when image is downloaded then set it to imageview (comment by Jesse Stenroth)
        imageView.setImageBitmap(result);
    }
}
