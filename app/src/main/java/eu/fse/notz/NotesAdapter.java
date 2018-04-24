package eu.fse.notz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{
    private ArrayList<Note> mDataset;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextV;
        public TextView descriptionTextV;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView)itemView.findViewById(R.id.cv);
            titleTextV = (TextView)itemView.findViewById(R.id.title_tv);
            descriptionTextV = (TextView)itemView.findViewById((R.id.text_tv));

            Random random = new Random();
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            cardView.setBackgroundColor(color);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String title = mDataset.get(getAdapterPosition()).getTitle();
                    String description = mDataset.get(getAdapterPosition()).getDescription();

                    Intent openNote = new Intent(context,NoteActivity.class);
                    openNote.putExtra("sendTitle", title);
                    openNote.putExtra("sendDescription", description);
                    openNote.putExtra("position", getAdapterPosition());
                    ((MainActivity)context).startActivityForResult(openNote, MainActivity.EDIT_REQUEST);
                }
            });

        }
    }

    public NotesAdapter(ArrayList<Note> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    public Note getNote(int index) {
        return mDataset.get(index);
    }

    public void updateNote(int index,Note note){
        mDataset.set(index,note);
        notifyItemChanged(index);
    }

    public void addNote(Note note, int position){
        this.mDataset.add(position, note);
        notifyItemInserted(position);

    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.titleTextV.setText(mDataset.get(position).getTitle());
        holder.descriptionTextV.setText(mDataset.get(position).getDescription());

    }

    public int getItemCount() {
        return mDataset.size();
    }
}
