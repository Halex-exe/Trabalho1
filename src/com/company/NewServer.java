package com.company;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alexandre MK
 */
public class NewServer {

    private static final String DOCROOT = "/Users/alexandre/Desktop/Java Workspace/Trabalho1/src/com/company/www";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HashMap<String, String> contentType = new HashMap<String, String>();
        String fileName;

        contentType.put("gif", "image/gif");
        contentType.put("html", "text/html");
        contentType.put("tml", "text/html");
        contentType.put("jpg", "image/jpeg");
        contentType.put("pdf", "application/pdf");
        contentType.put("png", "image/png");

        try {
            ServerSocket ss = new ServerSocket(8084);
            while (true) {
                Socket s = ss.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());

                String linha = "";
                linha = in.readLine();

                String partesGet[] = linha.split(" ");
                fileName = partesGet[1];
                while ((linha = in.readLine()).length() > 0);
                System.out.println(fileName);
                File arquivo = new File(DOCROOT + fileName);
                if (arquivo.exists()) {
                    StringBuilder header200Ok = new StringBuilder();

                    header200Ok.append("HTTP/1.1 200 OK");
                    header200Ok.append("\n");
                    header200Ok.append("Content-Type: ");
                    header200Ok.append(contentType.get(fileName.substring(fileName.length() - 3)));
                    header200Ok.append("\n");
                    header200Ok.append("Connection: close");
                    header200Ok.append("\n");
                    header200Ok.append("Server: Beta Server 0.1");
                    header200Ok.append("\n");
                    header200Ok.append("Content-Length: ");
                    header200Ok.append(arquivo.length());
                    header200Ok.append("\n\n");

                    out.write(header200Ok.toString().getBytes());

                    byte buffer[] = new byte[1024];
                    FileInputStream leitorBytesArquivo = new FileInputStream(arquivo);
                    int bytesLidos = 0;
                    while ((bytesLidos = leitorBytesArquivo.read(buffer)) > 0) {
                        out.write(buffer, 0, bytesLidos);
                    }
                    out.flush();
                } else {
                    StringBuilder htmlErro404 = new StringBuilder();
                    htmlErro404.append("<html>");
                    htmlErro404.append("<body bgcolor=blue>");
                    htmlErro404.append("<h1>");
                    htmlErro404.append("Recurso nao encontrado!");
                    htmlErro404.append("</h1>");
                    htmlErro404.append("<br>");
                    htmlErro404.append(partesGet[1]);
                    htmlErro404.append(" nao foi encontrado neste caminho!");
                    htmlErro404.append("</body>");
                    htmlErro404.append("</html>");

                    StringBuilder headerErro404 = new StringBuilder();

                    headerErro404.append("HTTP/1.1 404 Not Found");
                    headerErro404.append("\n");
                    headerErro404.append("Content-Type: text/html");
                    headerErro404.append("\n");
                    headerErro404.append("Content-Length: ");
                    headerErro404.append(htmlErro404.length());
                    headerErro404.append("\n\n");

                    out.write(headerErro404.toString().getBytes());
                    out.write(htmlErro404.toString().getBytes());
                    out.flush();
                }
                out.close();
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}