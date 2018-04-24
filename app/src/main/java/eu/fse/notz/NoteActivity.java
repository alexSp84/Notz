package eu.fse.notz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {

    EditText titleEdTxt;
    EditText descriptionEdTxt;
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleEdTxt = (EditText) findViewById(R.id.title_note_et);
        descriptionEdTxt = (EditText) findViewById(R.id.description__note_et);
        confirmBtn = (Button) findViewById(R.id.edit_confirm);

        final Intent receivedNote = getIntent();
        String title = receivedNote.getStringExtra("sendTitle");
        String description = receivedNote.getStringExtra("sendDescription");

        titleEdTxt.setText(title);
        descriptionEdTxt.setText(description);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editedTitle = titleEdTxt.getText().toString();
                String editedDescription = descriptionEdTxt.getText().toString();
                int position = receivedNote.getIntExtra("position",-1);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("title", editedTitle);
                returnIntent.putExtra("description", editedDescription);
                returnIntent.putExtra("position",position);
                setResult(Activity.RESULT_OK,returnIntent);

                finish();
            }
        });

    }
}
