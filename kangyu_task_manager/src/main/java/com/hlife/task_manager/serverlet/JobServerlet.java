package com.hlife.task_manager.serverlet;


import com.hlife.task_manager.server.Server;
import hprose.common.HproseMethods;
import hprose.server.HproseServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/hprose/hello"})
public class JobServerlet extends HproseServlet {

    //private IJobAndTriggerService iJobAndTriggerService;

    @Override
    protected void setGlobalMethods(HproseMethods methods) {
        super.setGlobalMethods(methods);

        methods.addMethod("sayHello", new Server());
    }
}
