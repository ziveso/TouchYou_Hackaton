package com.example.chawakorn.enjoystick;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    Button leftBtn;
    Button rightBtn;
    Button upBtn;
    Button downBtn;
    Button customBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        leftBtn = (Button) findViewById(R.id.leftBtn);
        rightBtn = (Button) findViewById(R.id.rightBtn);
        upBtn = (Button) findViewById(R.id.upBtn);
        downBtn = (Button) findViewById(R.id.downBtn);
        customBtn = (Button) findViewById(R.id.customBtn);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        customBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == upBtn.getId()) {
            Intent intent = new Intent();
            intent.putExtra("MESSAGE", "LEFT");
            setResult(10, intent);
            finish();
        }
        if (view.getId() == downBtn.getId()) {
            Intent intent = new Intent();
            intent.putExtra("MESSAGE", "RIGHT");
            setResult(20, intent);
            finish();
        }
        if (view.getId() == rightBtn.getId()) {
            Intent intent = new Intent();
            intent.putExtra("MESSAGE", "UP");
            setResult(30, intent);
            finish();
        }
        if (view.getId() == leftBtn.getId()) {
            Intent intent = new Intent();
            intent.putExtra("MESSAGE", "DOWN");
            setResult(40, intent);
            finish();
        }
        if (view.getId() == customBtn.getId()) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Setting");
            alert.setMessage("Enter a character");
            final EditText input = new EditText(this);
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable != null && editable.length() > 1) {
                        input.setText(editable.subSequence(1, editable.length()));
                        input.setSelection(1);
                    }
                }
            });
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent intent = new Intent();
                    intent.putExtra("MESSAGE", input.getText().toString());
                    setResult(2, intent);
                    finish();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();
        }


    }
}
