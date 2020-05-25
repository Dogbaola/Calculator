package ng.com.techsoft.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private String display="";
    private EditText inputtext;
    private TextView displaytext;
    private String currentOperator="";
    private String result="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton deletevar = (ImageButton) findViewById(R.id.butdelet);
        deletevar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletenumber();
            }
        });


        screen = (TextView)findViewById(R.id.input_box);
        screen.setText(display);
        inputtext = findViewById(R.id.input_box);
        displaytext = findViewById(R.id.result_box);
    }

    private void appendToLast(String str) {
        this.inputtext.getText().append(str);
    }

    public void onClickNumber(View v) {
        Button b = (Button) v;
        display += b.getText();
        appendToLast(display);
        display="";
    }

    public void onClickOperator(View v) {
        Button b = (Button) v;
        display += b.getText();
        if(endsWithOperatore())
        {
            replace(display);
            currentOperator = b.getText().toString();
            display = "";
        }
        else {
            appendToLast(display);
            currentOperator = b.getText().toString();

            display = "";
        }

    }

    public void onClearButton(View v) {
        inputtext.getText().clear();
        displaytext.setText("");
    }

    public void deletenumber() {
        this.inputtext.getText().delete(getinput().length() - 1, getinput().length());
    }

    private String getinput() {
        return this.inputtext.getText().toString();
    }

    private boolean endsWithOperatore() {
        return getinput().endsWith("+") || getinput().endsWith("-") || getinput().endsWith("/") || getinput().endsWith("x");
    }

    private void replace(String str) {
        inputtext.getText().replace(getinput().length() - 1, getinput().length(), str);
    }

    private double operate(String a,String b,String cp)
    {
        switch(cp) {
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "x": return Double.valueOf(a) * Double.valueOf(b);
            case "\u00F7": return Double.valueOf(a) / Double.valueOf(b);
            default: return -1;
        }
    }

    public void equalresult(View v) {
        String input = getinput();

        if(!endsWithOperatore()) {

            if (input.contains("x")) {
                input = input.replaceAll("x", "*");
            }

            if (input.contains("\u00F7")) {
                input = input.replaceAll("\u00F7", "/");
            }

            Expression expression = new ExpressionBuilder(input).build();
            double result = expression.evaluate();

            displaytext.setText(String.valueOf(result));
        }
        else displaytext.setText("");

        System.out.println(result);
    }

}