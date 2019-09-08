package com.company.TCPeUDP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args){

        try{
            ServerSocket ss = new ServerSocket(8422);
            Socket s = ss.accept();

            OutputStream outS = s.getOutputStream();
            InputStream inS = s.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inS));
            PrintWriter out = new PrintWriter(outS);

            String conteudo = in.readLine();
            conteudo = "Resposta: " + conteudo + " tudo certo!";
            out.println(conteudo);
            out.flush();

            out.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
