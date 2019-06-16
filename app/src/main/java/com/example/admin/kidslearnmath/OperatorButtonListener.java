package com.example.admin.kidslearnmath;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class OperatorButtonListener implements View.OnClickListener {

    String operator;
    Context context;

    public OperatorButtonListener(Context context, String operator) {
        this.context = context;
        this.operator = operator;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, QuestionsActivity.class);
        intent.putExtra("operator", operator);
        context.startActivity(intent);
    }
}
