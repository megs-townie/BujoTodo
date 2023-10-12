package com.example.m10_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    // Needs to reference this App path
    public final static String EXTRA_MESSAGE = "com.example.m10_intent_01.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity-INTENT", "onCreate: ");
    }


    /**
     * Called when the user clicks the Send button
     * Send custom intent to another internal activity
     * http://developer.android.com/training/basics/firstapp/starting-activity.html
     */
    public void sendMessage1(View view) {
        // Send custom intent to another internal activity

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        Log.w("MainActivity-INTENT", "sendMessage1: " + message);

        startActivity(intent);
    }


    /**
     * Intent Action=View Data=text
     * http://developer.android.com/guide/components/intents-common.html
     */
    public void sendMessage2(View view) {
        // ACTION_SEND intent with EXTRA_TEXT ... goes to messaging, email, ...

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();

        Intent intent = new Intent();
        intent.setAction(intent.ACTION_SEND);
        intent.putExtra(intent.EXTRA_TEXT, "This is my text to send: " + message);
        intent.setType("text/plain");

        Log.w("MainActivity-INTENT", "sendMessage2: " + message);

        startActivity(intent);
    }

    /**
     * See Android Web Site
     * http://developer.android.com/training/sharing/send.html
     */
    public void sendMessage3(View view) {
        // ACTION_SEND intent with EXTRA_TEXT ... goes to messaging, email, ...

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        Log.w("MainActivity-INTENT", "sendMessage3-1: " + message);

        Uri webpage = Uri.parse(message);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        Log.w("MainActivity-INTENT", "sendMessage3-2: " + message);
        startActivity(intent);
    }


    private int PICK_IMAGE_REQUEST = 17;

    /**
     * Intent Action=ACTION_GET_CONTENT Data=text
     * Ask for a photo from the gallery
     * http://codetheory.in/android-pick-select-image-from-gallery-with-intents/
     */
    public void sendMessage4(View view) {
        // ACTION_SEND intent with EXTRA_TEXT ... goes to messaging, email, ...

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        Log.w("MainActivity-INTENT", "sendMessage4-1: " + message);

        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        Log.w("MainActivity-INTENT", "sendMessage4-2: " + message);
    }


    /**
     * Returns after photo picked from gallery
     * http://codetheory.in/android-pick-select-image-from-gallery-with-intents/
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.w("MainActivity-INTENT", "onActivityResult-1 requestCode:" + requestCode + " resultCode:" + resultCode);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Log.w("MainActivity-INTENT", "onActivityResult-2: " + String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}