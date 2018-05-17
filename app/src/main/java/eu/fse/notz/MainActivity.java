package eu.fse.notz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST = 1001;
    public static final int RESULT_DELETE = 2002;
    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton actionButton;


    private ArrayList<Note> myDataset;
    private DatabaseHandler notzDatabase = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // mLayoutManager = new LinearLayoutManager(this);
        // mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        actionButton = findViewById(R.id.action_btn);

        myDataset = new ArrayList<>();

        myDataset = notzDatabase.getAllNotes();

        mAdapter = new NotesAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_REQUEST){

            if(resultCode == Activity.RESULT_OK){

                int editedNotePosition = data.getIntExtra("position",-1);


                mAdapter.updateNote(editedNotePosition, data.getStringExtra("title"),
                        data.getStringExtra("description"));

                notzDatabase.updateNote(mAdapter.getNote(editedNotePosition));

            }

            if(resultCode == RESULT_DELETE){
                final int editedNotePosition = data.getIntExtra("position",-1);
                notzDatabase.deleteNote(mAdapter.getNote(editedNotePosition));

                mAdapter.deleteNote(editedNotePosition);

                Snackbar.make(mRecyclerView,getString(R.string.note_removed), Snackbar.LENGTH_LONG)
                        .setAction(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Note note = new Note(data.getStringExtra("title"),
                                        data.getStringExtra("description"));

                                mAdapter.addNote(note, editedNotePosition);
                                notzDatabase.addNote(note);
                            }
                        })
                        .show();
            }
        }
    }

    private void showDialog() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        View dialogView = (View) LayoutInflater.from(this).inflate(R.layout.dialog_note, null);
        alertBuilder.setView(dialogView);
        alertBuilder.setTitle(R.string.dialog_add_note_title);

        final EditText titleEdTxt = (EditText) dialogView.findViewById(R.id.title_et);
        final EditText descriptionEdTxt = (EditText) dialogView.findViewById(R.id.description_et);

        alertBuilder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String insertedTitle = titleEdTxt.getText().toString();
                String insertedDescription = descriptionEdTxt.getText().toString();

                Note note = new Note(insertedTitle, insertedDescription);
                mAdapter.addNote(note, 0);

                notzDatabase.addNote(note);
            }
        });

        alertBuilder.setNegativeButton(R.string.dialog_negative_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                });

        alertBuilder.show();
    }

}