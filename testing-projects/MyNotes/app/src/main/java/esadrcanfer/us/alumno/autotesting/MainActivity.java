package esadrcanfer.us.alumno.autotesting;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView) findViewById(R.id.listView1);
        final NotesAdapter adapter = new NotesAdapter(this, NoteStore.getNotes(), NoteStore.getSelectedItems(), NoteStore.getSelectedRads(), NoteStore.getSelectedOption1(), NoteStore.getSelectedOption2(), NoteStore.getSelectedOption3(), NoteStore.getSelectedSwitches());
        listview.setAdapter(adapter);
    }

    public void createNote(View view){
        Intent intent = new Intent(view.getContext(), CreateNoteActivity.class);
        startActivityForResult(intent, 0);
    }

}

