package com.company.TCPeUDP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args){

        try{
            ServerSocket ss = new ServerSocket(8422);

            while (true) {
                Socket s = ss.accept();                         //Servidor porque ele aguarda uma conexão.

                OutputStream outS = s.getOutputStream(); //Fluxos atrelados ao canal do socket.
                InputStream inS = s.getInputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(inS));
                PrintWriter out = new PrintWriter(outS);

                String conteudo = in.readLine(); //fica bloqueado esperando uma linha de texto.
                conteudo = "Resposta: " + conteudo + " tudo certo!";
                out.println(conteudo);
                out.flush();

                out.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
