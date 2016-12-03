package com.example.chawakorn.enjoystick;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    EditText editText;
    Button addBtn;
    TextView myText;
    RelativeLayout r1;
    Button btnLock;
    ClientHandler clientHandler;
    String currentText = "";
    boolean touchable = true;
    int resultCode;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r1 = (RelativeLayout) findViewById(R.id.activity_main);
        btnLock = (Button) findViewById(R.id.keyLock);
        addBtn = (Button) findViewById(R.id.addBtn);
        clientHandler = new ClientHandler("172.20.10.3", 8888);
        clientHandler.start();
        addBtn.setOnClickListener(this);
        btnLock.setOnClickListener(this);
        r1.setOnTouchListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == addBtn.getId()) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityForResult(intent, 2);
        }
        if (view.getId() == btnLock.getId()) {
            touchable = !touchable;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            currentText = data.getStringExtra("MESSAGE");
        }
        this.resultCode = resultCode;
        this.touchable = true;

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (touchable) {
            touchable = !touchable;
            if (resultCode == 2) {
                Button custom = customButton(motionEvent, currentText);
                r1.addView(custom);
            } else {
                Button arrow = arrowButton(motionEvent);
                r1.addView(arrow);
            }
        }

        return false;
    }

    private Button arrowButton(MotionEvent motionEvent) {
        float x = motionEvent.getX() * motionEvent.getXPrecision();
        float y = motionEvent.getY() * motionEvent.getYPrecision();
        final Button btn = new Button(getApplicationContext());
        if (resultCode == 10) { // up
            btn.setText("^");
        }
        if (resultCode == 20) { // down
            btn.setText("V");
        }
        if (resultCode == 30) { // right
            btn.setText(">");
        }
        if (resultCode == 40) { // left
            btn.setText("<");
        }
        RelativeLayout.LayoutParams bp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        bp.leftMargin = (int) x - 200;
        bp.topMargin = (int) y - 200;
        btn.setLayoutParams(bp);
        final int myresultCode = this.resultCode;
        if (resultCode == 10) { // up
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        clientHandler.send("UP");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (resultCode == 20) { // down
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        clientHandler.send("DOWN");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (resultCode == 30) { // RIGHT
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        clientHandler.send("RIGHT");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        if (resultCode == 40) { // left
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        clientHandler.send("LEFT");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        return btn;
    }

    private Button customButton(MotionEvent motionEvent, String text) {
        float x = motionEvent.getX() * motionEvent.getXPrecision();
        float y = motionEvent.getY() * motionEvent.getYPrecision();
        final Button btn = new Button(getApplicationContext());
        btn.setText(text);
//        RelativeLayout.LayoutParams bp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams bp = new RelativeLayout.LayoutParams(350, 350);
        bp.leftMargin = (int) x - 250;
        bp.topMargin = (int) y - 250;
        btn.setLayoutParams(bp);
        btn.setOnTouchListener(new View.OnTouchListener() {
            float yDirection;
            float xDirection;
            float currentX;
            float currentY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!touchable) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            xDirection = motionEvent.getRawX();
                            yDirection = motionEvent.getRawY();
                            currentX = btn.getX();
                            currentY = btn.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float deltaX = motionEvent.getRawX() - xDirection;
                            float deltaY = motionEvent.getRawY() - yDirection;
                            btn.setX(currentX + deltaX);
                            btn.setY(currentY + deltaY);
                            break;
                    }
                }
                return false;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    clientHandler.send(btn.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return btn;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
