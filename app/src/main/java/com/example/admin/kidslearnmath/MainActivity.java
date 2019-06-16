package com.example.admin.kidslearnmath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonAddition, buttonSubtraction, buttonMultiplication, buttonDivision, buttonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddition = findViewById(R.id.buttonLearnAddition);
        buttonSubtraction = findViewById(R.id.buttonLearnSubtraction);
        buttonMultiplication = findViewById(R.id.buttonLearnMultiplication);
        buttonDivision = findViewById(R.id.buttonLearnDivision);
        buttonAbout = findViewById(R.id.buttonAbout);

        buttonAddition.setOnClickListener(new OperatorButtonListener(this, "+"));
        buttonSubtraction.setOnClickListener(new OperatorButtonListener(this, "-"));
        buttonMultiplication.setOnClickListener(new OperatorButtonListener(this, "x"));
        buttonDivision.setOnClickListener(new OperatorButtonListener(this, "/"));
    }
}
