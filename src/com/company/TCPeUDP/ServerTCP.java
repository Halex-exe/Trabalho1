package com.company.TCPeUDP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            ServerSocket ss = new ServerSocket(8422);
            Socket s;
            OutputStream outS;
            InputStream inS;
            BufferedReader in;
            PrintWriter out;
            String conteudo;
            AtendenteTCP a;

            while (true) {
                s = ss.accept();
                a = new AtendenteTCP(s);
                a.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

