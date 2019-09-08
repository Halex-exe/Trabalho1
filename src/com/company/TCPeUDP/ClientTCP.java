package com.company.TCPeUDP;

import java.io.*;
import java.net.Socket;

public class ClientTCP {
    public static void main(String[] args) throws IOException {

        try {
            Socket s = new Socket("localhost", 8422);

            OutputStream outS = s.getOutputStream();
            InputStream inS = s.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inS));
            PrintWriter out = new PrintWriter(outS);

            BufferedReader inTeclado = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Digite uma mensagem:");
            String mensagem = inTeclado.readLine();
            out.println(mensagem);
            out.flush();

            mensagem = in.readLine();
            System.out.println(mensagem);

            in.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
