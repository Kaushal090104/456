package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewHeading, textViewQuestion, textViewResult, textViewAnswers;
    private RadioGroup radioGroupOptions;
    private RadioButton radioButtonOption1, radioButtonOption2, radioButtonOption3, radioButtonOption4;
    private Button buttonSubmit;

    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        textViewHeading = findViewById(R.id.textViewHeading);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewResult = findViewById(R.id.textViewResult);
        textViewAnswers = findViewById(R.id.textViewAnswers);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        radioButtonOption1 = findViewById(R.id.radioButtonOption1);
        radioButtonOption2 = findViewById(R.id.radioButtonOption2);
        radioButtonOption3 = findViewById(R.id.radioButtonOption3);
        radioButtonOption4 = findViewById(R.id.radioButtonOption4);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set heading
        textViewHeading.setText("Quiz Application");

        // Load questions
        loadQuestions();

        // Initialize quiz
        currentQuestionIndex = 0;
        score = 0;
        displayQuestion();

        // Set button click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("Which is the smallest planet in our solar system?", new String[]{"Mercury", "Venus", "Earth", "Mars"}, "Mercury"));
        questions.add(new Question("Who is known as the 'father of computers'?", new String[]{"Charles Babbage", "Alan Turing", "John von Neumann", "Bill Gates"}, "Charles Babbage"));
        questions.add(new Question("What is the largest mammal in the world?", new String[]{"Elephant", "Blue Whale", "Great White Shark", "Giraffe"}, "Blue Whale"));
        questions.add(new Question("Which element has the chemical symbol 'O'?", new String[]{"Gold", "Oxygen", "Osmium", "Hydrogen"}, "Oxygen"));
        questions.add(new Question("What is the capital city of Australia?", new String[]{"Sydney", "Melbourne", "Brisbane", "Canberra"}, "Canberra"));
        questions.add(new Question("Which country invented tea?", new String[]{"India", "China", "Japan", "Sri Lanka"}, "China"));
        questions.add(new Question("In which year did World War II end?", new String[]{"1940", "1942", "1945", "1947"}, "1945"));
        questions.add(new Question("Who wrote the novel '1984'?", new String[]{"George Orwell", "Aldous Huxley", "Ray Bradbury", "Jules Verne"}, "George Orwell"));
        questions.add(new Question("What is the main ingredient in traditional Japanese miso soup?", new String[]{"Soybean Paste", "Fish Broth", "Rice", "Seaweed"}, "Soybean Paste"));
        questions.add(new Question("Which planet is known for its rings?", new String[]{"Mars", "Jupiter", "Saturn", "Uranus"}, "Saturn"));
        // Add more questions as needed
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            textViewQuestion.setText(currentQuestion.getQuestion());
            String[] options = currentQuestion.getOptions();
            radioButtonOption1.setText(options[0]);
            radioButtonOption2.setText(options[1]);
            radioButtonOption3.setText(options[2]);
            radioButtonOption4.setText(options[3]);
            radioGroupOptions.clearCheck(); // Clear previous selection
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        int selectedId = radioGroupOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedAnswer = selectedRadioButton.getText().toString();
        Question currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer();

        if (selectedAnswer.equals(correctAnswer)) {
            score++;
        }

        currentQuestionIndex++;
        displayQuestion();
    }

    private void finishQuiz() {
        // Hide question related views
        textViewQuestion.setVisibility(View.GONE);
        radioGroupOptions.setVisibility(View.GONE);
        buttonSubmit.setVisibility(View.GONE);

        // Show score
        textViewResult.setVisibility(View.VISIBLE);
        textViewResult.setText("Quiz completed!\nYour Score: " + score + " out of " + questions.size());

        // Show correct answers
        StringBuilder answers = new StringBuilder("\n\nCorrect Answers:\n");
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            answers.append("Question ").append(i + 1).append(": ").append(question.getQuestion()).append("\n");
            answers.append("Correct Answer: ").append(question.getCorrectAnswer()).append("\n\n");
        }
        textViewAnswers.setVisibility(View.VISIBLE);
        textViewAnswers.setText(answers.toString());

        // Scroll to top of answers
        ScrollView scrollViewQuiz = findViewById(R.id.scrollViewQuiz);
        scrollViewQuiz.smoothScrollTo(0, 0);
    }
}
