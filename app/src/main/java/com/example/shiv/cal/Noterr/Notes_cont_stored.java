package com.example.shiv.cal.Noterr;

import java.io.Serializable;

/**
 * Created by apple on 17/11/17.
 */

public class Notes_cont_stored implements Serializable {
    public Notes_cont_stored(String content) {
        Content = content;
    }

    private String Content;
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


}
