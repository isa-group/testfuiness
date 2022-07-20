package esadrcanfer.us.textinputapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View view) {
        EditText edit = (EditText)findViewById(R.id.editText1);
        TextView text = (TextView) findViewById(R.id.text2);
        if(checkEmail(edit.getText().toString())){
            Intent intent = new Intent (view.getContext(), Main2Activity.class);
            startActivityForResult(intent, 0);
        } else if(edit.getText().length() != 0){
            text.setText("Se ha introducido '" +edit.getText() +"' y se ha pulsado el botón" );
            edit.setText("");
        } else {

            text.setText("Se ha pulsado el botón");
        }
    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
}
