package com.iassets.common.servlet.systemsettings;

import com.iassets.common.servlet.CommonBasicServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/bio/ChangePasswordDisplay", "/gen/ChangePasswordDisplay"})
public class ChangePasswordDisplayServlet extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.forward(request, response, "/systemsettings/ChangePassword.jsp");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.forward(request, response, "/systemsettings/ChangePassword.jsp");
    }
}
