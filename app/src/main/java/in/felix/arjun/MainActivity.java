package in.felix.arjun;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnsave,btnCautre;
    ImageView image;
    Bitmap img,editedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCautre = findViewById(R.id.btnCaputure);
        image = findViewById(R.id.img);

        btnCautre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 2000);

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && resultCode == Activity.RESULT_OK) {
             img = (Bitmap) data.getExtras().get("data");
            //image.setImageBitmap(img);
            SetImage(img);
        }


    }
    public void SetImage(Bitmap imgg){

        Bitmap editedImage = Bitmap.createBitmap(imgg.getWidth(),imgg.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.frame);
        overlay = Bitmap.createScaledBitmap(overlay,imgg.getWidth(),imgg.getHeight(),false);

        Canvas canvas = new Canvas(editedImage);
        canvas.drawBitmap(imgg, 0, 0, new Paint());
        canvas.drawBitmap(overlay, 0, 0, new Paint());
        image.setImageBitmap(editedImage);
        SaveImage(editedImage);
    }



    public void SaveImage(Bitmap editedImage){

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/RRjun");
        myDir.mkdirs();
        String name = "arjunmore"+".jpeg";
        File file = new File (myDir, name);
        if (file.exists ()){
            file.delete ();
        }else{
            try {
                FileOutputStream out = new FileOutputStream(file);
                editedImage.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
