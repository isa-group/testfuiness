package esadrcanfer.us.alumno.autotesting;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Integer id;
    RadioButton r1, r2, r3;
    CheckBox c1, c2, c3;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = new Integer(extras.get("noteid").toString());
            EditText edit = (EditText)findViewById(R.id.editText1);
            edit.setText(NoteStore.getNotes().get(id));
            String dataString = extras.getString("data");
            Spinner spinner = (Spinner) findViewById(R.id.spinner1);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.notes_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            spinner.setSelection(getIndex(spinner, dataString));

            spinner.setOnItemSelectedListener(this);

            String radString = extras.getString("radioChosen");

            r1 = (RadioButton) findViewById(R.id.idRadio1);
            r2 = (RadioButton) findViewById(R.id.idRadio2);
            r3 = (RadioButton) findViewById(R.id.idRadio3);

            if(r1.getText().toString().equals(radString)){
                r1.setChecked(true);
            }

            if(r2.getText().toString().equals(radString)){
                r2.setChecked(true);
            }

            if(r3.getText().toString().equals(radString)){
                r3.setChecked(true);
            }

            Boolean check1 = extras.getBoolean("boxChosen1");
            Boolean check2 = extras.getBoolean("boxChosen2");
            Boolean check3 = extras.getBoolean("boxChosen3");

            c1 = (CheckBox) findViewById(R.id.checkBox);
            c2 = (CheckBox) findViewById(R.id.checkBox2);
            c3 = (CheckBox) findViewById(R.id.checkBox3);

            if(check1.equals(true)){
                c1.setChecked(true);
            }

            if(check2.equals(true)){
                c2.setChecked(true);
            }

            if(check3.equals(true)){
                c3.setChecked(true);
            }

            Boolean isSwitched = extras.getBoolean("switchChosen");

            aSwitch = (Switch) findViewById(R.id.switch3);

            if(isSwitched.equals(true)){
                aSwitch.setChecked(true);
            }
        }
    }

    public void save(View view) {
        EditText edit = (EditText)findViewById(R.id.editText1);
        if(edit.getText().length() != 0) {
            // find spinner object here
            Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
            //get selected value here
            String itemStr = spinner1.getSelectedItem().toString();

            String radStr = null; // store the text corresponding to  the RadioButton which is clicked

            if(r1.isChecked() == true){
                radStr = r1.getText().toString();
            }

            if(r2.isChecked() == true){
                radStr = r2.getText().toString();
            }

            if(r3.isChecked() == true){
                radStr = r3.getText().toString();
            }

            NoteStore.updateNote(edit.getText().toString(), id, itemStr, radStr, c1.isChecked(), c2.isChecked(), c3.isChecked(), aSwitch.isChecked());

            //put selected vale and start new activity
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("data", itemStr);
            intent.putExtra("radioChosen", radStr);
            intent.putExtra("boxChosen1", c1.isChecked());
            intent.putExtra("boxChosen2", c2.isChecked());
            intent.putExtra("boxChosen3", c3.isChecked());
            intent.putExtra("switchChosen", aSwitch.isChecked());

            startActivityForResult(intent, 0);
        }
    }

    public void delete(View view) {
        NoteStore.deleteNote(NoteStore.getNotes().get(id), id);
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle extras = getIntent().getExtras();
        String dataString = extras.getString("data");

        String selected = adapterView.getItemAtPosition(i).toString();
        if(!selected.equals(dataString)){
            Toast.makeText(this, selected, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, dataString, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    public void onRadioButtonClicked(View view) {
        String radStr = null;

        if(r1.isChecked() == true){
            radStr = r1.getText().toString();
        }

        if(r2.isChecked() == true){
            radStr = r2.getText().toString();
        }

        if(r3.isChecked() == true){
            radStr = r3.getText().toString();
        }

        Toast.makeText(this, radStr, Toast.LENGTH_SHORT).show();
    }

    public void onCheckBoxClicked(View view) {
        String checkStr = null;

        if(c1.isChecked() == true){
            checkStr = c1.getText().toString();
        }

        if(c2.isChecked() == true){
            checkStr = c2.getText().toString();
        }

        if(c3.isChecked() == true){
            checkStr = c3.getText().toString();
        }

        Toast.makeText(this, checkStr, Toast.LENGTH_SHORT).show();
    }

    public void onSwitchClicked(View view) {
        String switchText = null;

        if(aSwitch.isChecked()){
            switchText = "Alarma activada";
        }else{
            switchText = "Alarma desactivada";
        }

        Toast.makeText(this, switchText, Toast.LENGTH_SHORT).show();
    }
}
