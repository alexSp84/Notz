package eu.fse.notz;

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

    public static class ViewHolder extends RecyclerView.ViewHolder{
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
        }
    }

    public NotesAdapter(ArrayList<Note> myDataset) {
        mDataset = myDataset;
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
