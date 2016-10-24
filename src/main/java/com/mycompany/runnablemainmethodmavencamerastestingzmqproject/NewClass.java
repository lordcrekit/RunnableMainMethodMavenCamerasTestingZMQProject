/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.runnablemainmethodmavencamerastestingzmqproject;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 *
 * @author CAAPP_Admin
 */
public class NewClass {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            try (ZMQ.Socket requester = context.createSocket(ZMQ.REQ)) {
                requester.connect("tcp://localhost:43210");
                if (!requester.send("command: status".getBytes(), 0)) {
                    throw new Exception("Failed to send");
                }

                byte[] reply = requester.recv(0);
                if (reply.length == 0) {
                    throw new Exception("No reply");
                }

                Object obj = new Yaml().load(new String(reply));
                Logger.getLogger(NewClass.class.getName()).log(Level.INFO, obj.toString());
                // Read version information
            } catch (Exception ex) {
                Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
