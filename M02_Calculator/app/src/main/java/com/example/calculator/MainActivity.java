package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action for each button
        ImageButton btnAdd = findViewById(R.id.b_Add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performCalculation('+');
            }
        });

        ImageButton btnSubtract = findViewById(R.id.b_Subtract);
        btnSubtract.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performCalculation('-');
            }
        });

        ImageButton btnMultiply = findViewById(R.id.b_Multiply);
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performCalculation('*');
            }
        });

        ImageButton btnDivide = findViewById(R.id.b_Divide);
        btnDivide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performCalculation('/');
            }
        });
    }

    private void performCalculation(char operation) {
        Double d1 = 0.0;
        Double d2 = 0.0;
        Double answer = 0.0;

        EditText textN1 = findViewById(R.id.editTextN1);
        EditText textN2 = findViewById(R.id.editTextN2);
        EditText textANS = findViewById(R.id.editTextNumAns);

        try {
            d1 = Double.parseDouble(textN1.getText().toString());
            d2 = Double.parseDouble(textN2.getText().toString());
            switch (operation) {
                case '+': answer = d1 + d2; break;
                case '-': answer = d1 - d2; break;
                case '*': answer = d1 * d2; break;
                case '/':
                    if (d2 != 0) {
                        answer = d1 / d2;
                    } else {
                        Log.w("M01_Calculator", "Attempted division by zero");
                        return;
                    }
                    break;
            }
        } catch (Exception e) {
            Log.w("M01_Calculator", "Operation selected with no valid inputs ... " + answer);
            return;
        }

        // Set the answer into the answer field
        textANS.setText(answer.toString());

        // Log the operation
        Log.w("M01_Calculator", operation + " Selected with => " + d1 + " " + operation + " " + d2 + "=" + answer);
    }
}
