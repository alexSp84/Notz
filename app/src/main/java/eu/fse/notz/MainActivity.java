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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST = 1001;
    public static final int RESULT_DELETE = 2002;
    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton actionButton;
    private TextView noItem;


    private ArrayList<Note> myDataset;

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
        noItem = findViewById(R.id.no_item);


        myDataset = new ArrayList<>();

        if(getIntent() != null){

            Intent intent = getIntent();
            if(intent.getAction().equals(Intent.ACTION_SEND)){
                String title = intent.getStringExtra(Intent.EXTRA_TEXT);
                showDialog(title);

            }
        }

        getNoteFromURL();

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

            }

            if(resultCode == RESULT_DELETE){
                final int editedNotePosition = data.getIntExtra("position",-1);
                mAdapter.deleteNote(editedNotePosition);

                Snackbar.make(mRecyclerView,getString(R.string.note_removed), Snackbar.LENGTH_LONG)
                        .setAction(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Note note = new Note(data.getStringExtra("title"),
                                        data.getStringExtra("description"));

                                mAdapter.addNote(note, editedNotePosition);
                            }
                        })
                        .show();
            }
        }
    }

    private void showDialog(){
        showDialog(null);
    }

    private void showDialog(String title) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        View dialogView = (View) LayoutInflater.from(this).inflate(R.layout.dialog_note, null);
        alertBuilder.setView(dialogView);
        alertBuilder.setTitle(R.string.dialog_add_note_title);

        final EditText titleEdTxt = (EditText) dialogView.findViewById(R.id.title_et);
        final EditText descriptionEdTxt = (EditText) dialogView.findViewById(R.id.description_et);

        if(title!= null) titleEdTxt.setText(title);

        alertBuilder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String insertedTitle = titleEdTxt.getText().toString();
                String insertedDescription = descriptionEdTxt.getText().toString();

                Note note = new Note(insertedTitle, insertedDescription);
                mAdapter.addNote(note, 0);
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

    private void setNoItemText(){

        if(myDataset.isEmpty())
            noItem.setText(R.string.NoItem);
        else
            noItem.setText(R.string.empty);
    }


    private void getNoteFromURL(){
        // Make HTTP call

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://5af1bf8530f9490014ead894.mockapi.io/api/v1/notes";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Log.d("jsonRequest", response.toString());

                        try {
                            JSONArray result = response.getJSONArray("data");
                            ArrayList<Note> noteListFromResponse = Note.getNotesList(result);
                            mAdapter.addNotesList(noteListFromResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, "Si è verificato un errore: "+error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

}