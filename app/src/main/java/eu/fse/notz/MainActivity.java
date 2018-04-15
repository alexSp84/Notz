package eu.fse.notz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Note> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();

        Note pinPalazzo = new Note("PIN", "1234");
        myDataset.add(pinPalazzo);

        Note pinCasa = new Note("PIN2", "5678999999999999999999999999990000000009");
        myDataset.add(pinCasa);

        Note terzaNota = new Note("Lista","sfjhjsdhgjdghdjghdjghjdghjd");
        myDataset.add(terzaNota);

        mAdapter = new NotesAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }
}
