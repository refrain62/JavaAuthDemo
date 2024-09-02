package com.sample.auth.callback;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * google認証サーブレット
 */
@WebServlet(name="googleServlet", urlPatterns = {"/auth/callback/google"})
public class googleServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.get
    }


}
