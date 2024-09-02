package com.sample.auth.callback;

import com.opensymphony.xwork2.Action;

public class GoogleAction implements Action {
    public String execute() throws Exception {
        return "index";
    }
}
