package cn.musicspider.dao.sequence;

/**
 * Created by lei2j on 2018/5/19.
 */
public enum Sequence {

    Song(1,"song"),
    COMMENT(2,"comment");

    private int code;
    private String text;

    Sequence(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
