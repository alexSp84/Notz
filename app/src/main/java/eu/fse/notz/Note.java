package eu.fse.notz;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Note {

    private String title;
    private String description;
    private int id;
    private boolean isShowOnTop;

   public Note(String title, String description){
       this.title = title;
       this.description = description;
   }

    public static ArrayList<Note> getNotesList(JSONArray notes){

        ArrayList<Note> list = new ArrayList<>();

        for (int i = 0; i < notes.length(); i++){

            try {
                JSONObject jsonNote = notes.getJSONObject(i);
                list.add(new Note(jsonNote));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

   public Note(JSONObject jsonNote){
       try {
           title = jsonNote.getString("title");
           description = jsonNote.getString("description");
           id = jsonNote.getInt("id");

       } catch (JSONException e) {
           Log.e("Note", e.getMessage());
       }
   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isShowOnTop() {
        return isShowOnTop;
    }

    public void setShowOnTop(boolean showOnTop) {
        isShowOnTop = showOnTop;
    }
}
