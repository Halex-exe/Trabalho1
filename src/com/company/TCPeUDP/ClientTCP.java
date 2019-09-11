package com.company.TCPeUDP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP extends Thread{
    Socket s;

    OutputStream outS;
    InputStream inS;

    BufferedReader in;
    PrintWriter out;
    BufferedReader inTeclado;

    public void conectar() {
        try {
            s = new Socket("localhost", 8422);

            outS = s.getOutputStream();
            inS = s.getInputStream();

            in = new BufferedReader(new InputStreamReader(inS));
            out = new PrintWriter(outS);
            inTeclado = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void enviar(String msg) {
        out.println(msg);
        out.flush();
    }

    public void aguardar() {
        try {
            while (true) {
                System.out.println(in.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        aguardar();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ClientTCP c = new ClientTCP();
        c.conectar();
        c.start();
        String txt;

        try {
            while (true) {
                txt = c.inTeclado.readLine();
                c.enviar(txt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
