package com.myproject.handler;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CustomTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter output = getJspContext().getOut();
        output.print("Custom tag created here!");
    }
}
