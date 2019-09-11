package com.company.TCPeUDP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class AtendenteTCP extends Thread {

    Socket s;

    public AtendenteTCP(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        atender();
    }

    public void atender() {
        try {
            OutputStream outS = s.getOutputStream();
            InputStream inS = s.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inS));
            PrintWriter out = new PrintWriter(outS);

            String conteudo;
            while (true) {
                conteudo = "Resposta: " + in.readLine() + " Tudo certo!";
                for(int i = 0; i < 10; i++){
                    out.println(conteudo);
                    out.flush();
                }

            }

//            out.close();
//            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

