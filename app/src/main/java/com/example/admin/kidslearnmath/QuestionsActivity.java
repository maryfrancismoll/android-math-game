package com.example.admin.kidslearnmath;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionsActivity extends AppCompatActivity {

    // String operator = "";

    TextView textViewOperation, textViewFirstNumber, textViewSecondNumber,
            textViewChances, textViewQuestionNumber, textViewLevel;
    ImageView imageViewOperation, imageViewOperator;
    LinearLayout layoutQuestions;
    Button buttonChoiceOne, buttonChoiceTwo, buttonChoiceThree, buttonChoiceFour;

    Integer level;
    Integer question;
    Integer chances;
    Integer correctPosition;

    final static Integer clusterSize = 10;
    final static Integer optionsRange = 7;
    final static Integer questionsPerLevel = 10;
    String operator = "";
    List<Button> buttonList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        level = 0;
        question = 0;
        chances = 5;

        textViewOperation = findViewById(R.id.textViewOperation);
        textViewFirstNumber = findViewById(R.id.textViewFirstNumber);
        textViewSecondNumber = findViewById(R.id.textViewSecondNumber);
        textViewChances = findViewById(R.id.textViewChances);
        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber);
        textViewLevel = findViewById(R.id.textViewLevel);

        imageViewOperator = findViewById(R.id.imageViewOperator);
        imageViewOperation = findViewById(R.id.imageViewOperator2);
        layoutQuestions = findViewById(R.id.layoutQuestions);

        buttonChoiceOne = findViewById(R.id.buttonChoiceOne);
        buttonChoiceTwo = findViewById(R.id.buttonChoiceTwo);
        buttonChoiceThree = findViewById(R.id.buttonChoiceThree);
        buttonChoiceFour = findViewById(R.id.buttonChoiceFour);

        buttonChoiceOne.setOnClickListener(new AnswerButtonListener(1, buttonChoiceOne));
        buttonChoiceTwo.setOnClickListener(new AnswerButtonListener(2, buttonChoiceTwo));
        buttonChoiceThree.setOnClickListener(new AnswerButtonListener(3, buttonChoiceThree));
        buttonChoiceFour.setOnClickListener(new AnswerButtonListener(4, buttonChoiceFour));

        buttonList = new ArrayList<>();
        buttonList.add(buttonChoiceOne);
        buttonList.add(buttonChoiceTwo);
        buttonList.add(buttonChoiceThree);
        buttonList.add(buttonChoiceFour);

        //GetBundles, GetExtras
        Bundle bundle = this.getIntent().getExtras();
        operator = bundle.get("operator").toString();
        setOperation(operator);

        loadData();
    }

    private void loadData(){
        //reset button text colors
        resetButtonTextColors();

        if (++question <= questionsPerLevel) {
            level = 1;
        }else{
            level = (question / questionsPerLevel) + 1;
        }
        textViewLevel.setText("Level : " + level);
        textViewQuestionNumber.setText("Question : " + question);
        textViewChances.setText("Chances : " + chances);

        //generate 2 random numbers
        Integer firstNumber = this.getRandomNumber();
        Integer secondNumber = this.getRandomNumber();

        if (operator.equalsIgnoreCase("/")){
            if (secondNumber == 0){
                secondNumber = this.getRandomNonZeroNumber();
            }
            if(firstNumber < secondNumber || firstNumber % secondNumber > 0) {
                firstNumber = firstNumber * secondNumber;
            }
        }

        if (operator.equalsIgnoreCase("-")){
            if (secondNumber > firstNumber){ //interchange to avoid negatives
                int temp = firstNumber;
                firstNumber = secondNumber;
                secondNumber = temp;
            }
        }

        Integer correctAnswer = this.calculateCorrectAnswer(firstNumber, secondNumber, operator);

        textViewFirstNumber.setText(firstNumber.toString());
        textViewSecondNumber.setText(secondNumber.toString());

        populateButtons(correctAnswer, operator);
    }

    private void resetButtonTextColors(){
        for (int x = 0; x < buttonList.size(); x++){
            buttonList.get(x).setTextColor(getResources().getColor(R.color.original));
            buttonList.get(x).setEnabled(true);
        }
    }

    private Integer getRandomNumber(){
        Random random = new Random();
        return random.nextInt((clusterSize * level) + 1 ); //will give 0 - 10 for level 1, 0 - 20 for level 2, and so on.
    }

    private Integer getRandomNonZeroNumber(){
        Random random = new Random();
        return random.nextInt(clusterSize * level) + 1; //will give 1 - 10 for level 1, 1 - 20 for level 2, and so on.
    }

    private Integer getRandomNumber(int size, int increment){
        Random random = new Random();
        return random.nextInt(size) + increment;
    }

    private Integer calculateCorrectAnswer(Integer firstNumber, Integer secondNumber, String operator){
        if (operator.equalsIgnoreCase("+")){
            return firstNumber + secondNumber;
        }else if (operator.equalsIgnoreCase("-")){
            return firstNumber - secondNumber;
        }else if (operator.equalsIgnoreCase("x")){
            return firstNumber * secondNumber;
        }else if (operator.equalsIgnoreCase("/")){
            if (secondNumber > 0) return firstNumber / secondNumber;
        }
        return 0;
    }

    private void populateButtons(Integer correctAnswer, String operator){
        Random random = new Random();
        correctPosition = random.nextInt(4); //return any number from 0 to 3
        List<Integer> options = new ArrayList<>();
        options.add(correctAnswer);
        int increment = correctAnswer - (buttonList.size() - 1);

        for (int x = 0; x < buttonList.size(); x++){
            if (correctPosition == x){ //place the correct answer
                buttonList.get(x).setText(correctAnswer.toString());
            }else{
                Integer randomIncorrect;
                do {
                    randomIncorrect = this.getRandomNumber(optionsRange, increment);
                }
                while (randomIncorrect < 0 || options.contains(randomIncorrect));
                options.add(randomIncorrect);
                buttonList.get(x).setText(randomIncorrect.toString());
            }
        }
    }

    private void setOperation(String operator){
        if (operator.equalsIgnoreCase("+")){
            textViewOperation.setText("Learn Addition");
            imageViewOperation.setImageResource(R.mipmap.addition_fg);
            imageViewOperator.setImageResource(R.mipmap.addition_fg);
            layoutQuestions.setBackgroundColor(getResources().getColor(R.color.addition_blue));
        }else if (operator.equalsIgnoreCase("-")){
            textViewOperation.setText("Learn Subtraction");
            imageViewOperation.setImageResource(R.mipmap.subtraction_fg);
            imageViewOperator.setImageResource(R.mipmap.subtraction_fg);
            layoutQuestions.setBackgroundColor(getResources().getColor(R.color.subtraction_pink));
        }else if (operator.equalsIgnoreCase("x")){
            textViewOperation.setText("Learn Multiplication");
            imageViewOperation.setImageResource(R.mipmap.multiplication_fg);
            imageViewOperator.setImageResource(R.mipmap.multiplication_fg);
            layoutQuestions.setBackgroundColor(getResources().getColor(R.color.multiplication_orange));
        }else if (operator.equalsIgnoreCase("/")){
            textViewOperation.setText("Learn Division");
            imageViewOperation.setImageResource(R.mipmap.division_fg);
            imageViewOperator.setImageResource(R.mipmap.division_fg);
            layoutQuestions.setBackgroundColor(getResources().getColor(R.color.division_green));
        }
    }

    public void useChance(){
        if (--chances < 0){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            textViewChances.setText(("Chances : " + chances));
        }
    }

    class AnswerButtonListener implements View.OnClickListener {

        Button button;
        Integer position;

        public AnswerButtonListener(Integer position, Button button) {
            this.button = button;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (correctPosition == (position -1)) {
                button.setTextColor(QuestionsActivity.this.getResources().getColor(R.color.correct));
                loadData();
            } else {
                button.setTextColor(QuestionsActivity.this.getResources().getColor(R.color.wrong));
                button.setEnabled(false);
                useChance();
            }
        }
    }
}
