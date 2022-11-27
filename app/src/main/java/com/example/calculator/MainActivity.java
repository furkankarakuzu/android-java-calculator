package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView text_result;
    TextView operator;
    EditText new_value_input;
    Double operand1 =null;
    Double operand2 =null;
    String pendingOperator;

    private String pendingOperator_text ="PENDINGOPERATOR";
    private String result_text ="VALUE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_result = findViewById(R.id.text_result);
        operator = findViewById(R.id.text_operator);
        new_value_input = findViewById(R.id.new_value_input);

        Button btn_0 = findViewById(R.id.btn_0);
        Button btn_1 = findViewById(R.id.btn_1);
        Button btn_2 = findViewById(R.id.btn_2);
        Button btn_3 = findViewById(R.id.btn_3);
        Button btn_4 = findViewById(R.id.btn_4);
        Button btn_5 = findViewById(R.id.btn_5);
        Button btn_6 = findViewById(R.id.btn_6);
        Button btn_7 = findViewById(R.id.btn_7);
        Button btn_8 = findViewById(R.id.btn_8);
        Button btn_9 = findViewById(R.id.btn_9);
        Button btn_dot = findViewById(R.id.btn_dot);
        Button btn_ac = findViewById(R.id.btn_ac);
        Button btn_del = findViewById(R.id.btn_del);
        Button btn_multi = findViewById(R.id.btn_multi);
        Button btn_div = findViewById(R.id.btn_div);
        Button btn_equals = findViewById(R.id.btn_equals);
        Button btn_plus = findViewById(R.id.btn_plus);
        Button btn_minus = findViewById(R.id.btn_minus);
        Button btn_percent = findViewById(R.id.btn_percent);

        View.OnClickListener viewOnclickEvent = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                new_value_input.append(button.getText().toString());
            }
        };
        btn_0.setOnClickListener(viewOnclickEvent);
        btn_1.setOnClickListener(viewOnclickEvent);
        btn_2.setOnClickListener(viewOnclickEvent);
        btn_3.setOnClickListener(viewOnclickEvent);
        btn_4.setOnClickListener(viewOnclickEvent);
        btn_5.setOnClickListener(viewOnclickEvent);
        btn_6.setOnClickListener(viewOnclickEvent);
        btn_7.setOnClickListener(viewOnclickEvent);
        btn_8.setOnClickListener(viewOnclickEvent);
        btn_9.setOnClickListener(viewOnclickEvent);
        btn_dot.setOnClickListener(viewOnclickEvent);

        View.OnClickListener operatorEvent = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                String operator2 = button.getText().toString();
                String value = new_value_input.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue,operator2);
                }
                catch (NumberFormatException e){
                    new_value_input.setText("");
                }
                pendingOperator = operator2;
                operator.setText(pendingOperator);
            }
        };
        btn_equals.setOnClickListener(operatorEvent);
        btn_plus.setOnClickListener(operatorEvent);
        btn_minus.setOnClickListener(operatorEvent);
        btn_multi.setOnClickListener(operatorEvent);
        btn_div.setOnClickListener(operatorEvent);
        btn_percent.setOnClickListener(operatorEvent);

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new_value_input.getText().toString().length() >0){
                    new_value_input.setText(new_value_input.getText().toString().substring(0,new_value_input.getText().toString().length()-1));
                }
            }
        });

        btn_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_result.setText("");
                operator.setText("");
                new_value_input.setText("");
                operand1 =null;
                operand2=null;
                pendingOperator = null;
            }
        });
    }
    private  void performOperation(Double value, String operator1){
        if (operand1 == null){
            operand1 = value;
            if (pendingOperator !=null){
                switch (pendingOperator){
                    case "sin":
                        operand1 = Math.sin(operand1);
                        break;
                    case "cos":
                        operand1 =Math.cos(operand1);
                        break;
                    case "√":
                        operand1 =Math.sqrt(operand1);
                        break;
                    case "!":
                        if (value == 0.0 || value == 1.0){
                            operand1 = 1.0;
                        }
                        else{
                            for (double i = value-1;i > 0;i--){
                                operand1 *=i;
                            }
                        }
                        break;
                }
            }
        }
        else {
            operand2=value;
            if (pendingOperator.equals("=")){
                pendingOperator = operator1;
            }
            switch (pendingOperator){
                case "=":
                    operand1 = operand2;
                    break;
                case "+":
                    operand1 +=operand2;
                    break;
                case "-":
                    operand1 -=operand2;
                    break;
                case "*":
                    operand1 *=operand2;
                    break;
                case "/":
                    if (operand2 ==0){
                        operand1 = null;
                    }
                    else {
                        operand1/=operand2;
                    }
                    break;
                case "%":
                    operand1 *= operand2/100;
                    break;
                case "^":
                    operand1 = Math.pow(operand1,operand2);
                    break;
            }
        }
        if (operand1 ==null){
            text_result.setText("Error");
            operator.setText("");
            new_value_input.setText("");
            operand1 =null;
            operand2=null;
            pendingOperator = null;
        }
        else {
            text_result.setText(operand1.toString());
            operator.setText(operator1);
            new_value_input.setText("");
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(pendingOperator_text,pendingOperator);
        if (operand1 != null){
            outState.putDouble(result_text,operand1);
            super.onSaveInstanceState(outState);

        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        operand1 = savedInstanceState.getDouble(result_text,Double.POSITIVE_INFINITY);
        if (operand1 == Double.POSITIVE_INFINITY){
            operand1 =null;
        }
        else {
            text_result.setText(String.valueOf(operand1));
        }
        pendingOperator = savedInstanceState.getString(pendingOperator_text);
        operator.setText(pendingOperator);
    }
}