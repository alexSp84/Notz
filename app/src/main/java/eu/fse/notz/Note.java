package eu.fse.notz;

public class Note {

    private String titleTxt;
    private String contentTxt;

    public Note(String titleTxt, String contentTxt) {
        this.titleTxt = titleTxt;
        this.contentTxt = contentTxt;
    }

    public String getTitleTxt() {
        return titleTxt;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setTitleTxt(String titleTxt) {
        this.titleTxt = titleTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }
}
