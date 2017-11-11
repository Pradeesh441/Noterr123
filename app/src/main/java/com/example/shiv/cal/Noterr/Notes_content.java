package com.example.shiv.cal.Noterr;

/**
 * Created by apple on 05/11/17.
 */

public class Notes_content {
    private Integer Seq_no;

    public Integer getSeq_no() {
        return Seq_no;
    }

    public void setSeq_no(Integer seq_no) {
        Seq_no = seq_no;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCont_type() {
        return Cont_type;
    }

    public void setCont_type(String cont_type) {
        Cont_type = cont_type;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    private Integer ID;
    private String Cont_type;
    private String Content;
}
