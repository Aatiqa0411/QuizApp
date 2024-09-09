package com.example.offlinequiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int totalscore=0;
    private  int currentQuestionIndex=0;
    private TextView scoreText;
    private TextView finalMessage, questiontext;
    private RadioGroup answerGroup;
    private Button nextButton, prevButton,showAnswerButton, endExamButton;
    private int timeRemaining=180000;

    private CountDownTimer timer;

    private String[] questions={
            "Q1:What is the capital of Australia?",
            "Q2:Which planet is known as the Red Planet?",
            "Q3:Who wrote Pride and Prejudice?",
            "Q4:What is the largest ocean on Earth?",
            "Q5:Which element has the chemical symbol 0",
            "Q6:Who was the first person to walk on the moon?",
            "Q7:Which country hosted the 2016 Summer Olympics?",
            "Q8:What is the hardest natural substance on Earth?",
            "Q9:Which is the smallest country in the world by land area?",
            "Q10:What is the square root of 144?",
            "Q11:In which year did World War II end?",
            "Q12:Who is the author of the Harry Potter series?",
            "Q13:What is the capital of Canada?",
            "Q14:What is the chemical formula for water?",
            "Q15:What is the currency of Japan?",
            "Q16:Who painted the Mona Lisa?",
            "Q17:What is the speed of light in a vacuum?",
            "Q18:Which gas do plants absorb from the atmosphere during photosynthesis?",
            "Q19:Which is the largest planet in our solar system?",
            "Q20:Who invented the telephone?",
            "Q21:Which continent is the Sahara Desert located in?",
            "Q22:What is the tallest mountain in the world?",
            "Q23:What is the chemical symbol for gold?",
            "Q24:Which country is famous for the Great Wall?",
            "Q25:Who was the first President of the United States?",
            "Q26:Which animal is known as the King of the Jungle?",
            "Q27:What is the longest river in the world?",
            "Q28:What is the main ingredient in guacamole?",
            "Q29:In which year did the Titanic sink?",
            "Q30:What is the largest mammal on Earth?"

    };
    private String[][] answers={
            {"Sydney","Melbourne" ,"Canberra" ,"Perth"},
            {"Earth","Mars","Venus" ,"Jupiter"},
            {"Charles Dickens"," Jane Austen","Mark Twain","Emily Bronte"},
            {"Atlantic","Indian","Southern","Pacific"},
            {"Oxygen","Gold","Osmium","Iron"},
            {"Neil Armstrong","Yuri Gagarin","Buzz Aldrin","Michael Collins"},
            {"China","Brazil","Japan","United Kingdom"},
            {"Diamond", "Iron", "Gold","Quartz"},
            {"Monaco" ,"Liechtenstein","Vatican City","San Marino"},
            {"10","12","14","16"},
            {"1939","1941","1945","1947"},
            {"J.R.R. Tolkien", "George R.R. Martin","J.K. Rowling","C.S. Lewis"},
            {"Toronto", "Vancouver","Ottawa","Montreal"},
            {"CO2" ,"H2O" ,"O2" ,"N2"},
            {"Yen", "Won", "Yuan","Ringgit"},
            {"Michelangelo", "Pablo Picasso", "Leonardo da Vinci", "Vincent van Gogh"},
            {"299,792 km/s", "150,000 km/s","100,000 km/s","1,000,000 km/s"},
            {"Oxygen", "Nitrogen","Carbon dioxide","Methane"},
            {"Earth", "Saturn", "Mars","Jupiter"},
            {"Thomas Edison", "Alexander Graham Bell", "Nikola Tesla","Guglielmo Marconi"},
            {"South America", "Africa", "Asia", "Australia"},
            {"K2", "Mount Kilimanjaro", "Mount Everest", "Mount Fuji"},
            {"Ag", "Au", "Gd","Go"},
            {"Japan", "China","India","South Korea"},
            {"Abraham Lincoln", "Thomas Jefferson","George Washington","John Adams"},
            {"Elephant", "Lion","Tiger","Cheetah"},
            {"Amazon River","Nile River","Yangtze River","Mississippi River"},
            {"Tomato", "Avocado","Potato","Lettuce"},
            {"1912", "1914","1905","1920"},
            {"Elephant","Blue Whale","Hippopotamus","Giraffe"}

    };

    private int[] correctanswers ={2,1,1,3,0,0,1,0,2,1,2,2,2,1,0,2,0,2,3,1,1,2,1,1,2,1,1,1,0,1};
    private TextView   timeDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        scoreText = findViewById(R.id.score_text);
        questiontext = findViewById(R.id.question_test);
        answerGroup = findViewById(R.id.answer_group);
        nextButton = findViewById(R.id.next_button);
        finalMessage = findViewById(R.id.final_message);
        timeDisplay=findViewById(R.id.timer_count);
        prevButton=findViewById(R.id.previous_button);
        showAnswerButton=findViewById(R.id.show_ans_button);
        endExamButton=findViewById(R.id.end_exam_button);

        loadQuestion();
        startTimer();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    loadQuestion();
                } else {
                    endExam();
                }
                answerGroup.clearCheck();
            }
        });
        prevButton.setOnClickListener(v -> {
            if(currentQuestionIndex>0)
            {
                currentQuestionIndex--;
                loadQuestion();
            }
        });
        showAnswerButton.setOnClickListener(v -> {
            totalscore-=1;
            ((RadioButton) answerGroup.getChildAt(correctanswers[currentQuestionIndex])).setChecked(true);
            scoreText.setText("Total Score:"+totalscore);
        });
        endExamButton.setOnClickListener(v -> endExam());

    }

    private void loadQuestion(){
        questiontext.setText(questions[currentQuestionIndex]);
        ((RadioButton) answerGroup.getChildAt(0)).setText(answers[currentQuestionIndex][0]);
        ((RadioButton) answerGroup.getChildAt(1)).setText(answers[currentQuestionIndex][1]);
        ((RadioButton) answerGroup.getChildAt(2)).setText(answers[currentQuestionIndex][2]);
        ((RadioButton) answerGroup.getChildAt(3)).setText(answers[currentQuestionIndex][3]);
    }

    private void checkAnswer(){
        int selectedId=answerGroup.getCheckedRadioButtonId();
        int selectedIndex=answerGroup.indexOfChild(findViewById(selectedId));
        if (selectedIndex==correctanswers[currentQuestionIndex])
        {
            totalscore+=5;
        }
        else
        {
            totalscore-=1;
        }
        scoreText.setText("Total Score: "+totalscore);
    }
    private void startTimer()
    {
        timer=new CountDownTimer(timeRemaining,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining=(int)millisUntilFinished;
                int minutes=timeRemaining/60000;
                int seconds=(timeRemaining%60000)/1000;
                timeDisplay.setText(String.format("Time: %02d:%02d",minutes,seconds));
            }

            @Override
            public void onFinish() {
                endExam();

            }
        }.start();
    }
    private void endExam(){
        if(timer!=null)
        {
            timer.cancel();
        }
        int totalQuestions=questions.length;
        double percentage=((double) totalscore/(totalQuestions*5))*100;

        finalMessage.setText(String.format("Congratulation ! Your Total Score : %d\nPercentage: %.2f%%",totalscore,percentage));
        finalMessage.setVisibility(View.VISIBLE);

        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
        showAnswerButton.setEnabled(false);
        endExamButton.setEnabled(false);

    }
}