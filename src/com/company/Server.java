package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        /* cria um socket "servidor" associado a porta 8000
          já aguardando conexões
        */
        ServerSocket servidor = new ServerSocket(8000);
        //aceita a primeita conexao que vier
        Socket socket = servidor.accept();
        //verifica se esta conectado  
        if (socket.isConnected()) {
            //imprime na tela o IP do cliente
            System.out.println("O computador "+ socket.getInetAddress() + " se conectou ao servidor.");
        }


        //CRIA O (InputStream) PARA LER O DADOS ENVIADOS PELO Client:
        //cria um BufferedReader a partir do InputStream do cliente
        BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Requisição: ");
        //Lê a primeira linha
        String linha = buffer.readLine();
        //Enquanto a linha não for vazia
        while (!linha.isEmpty()) {
            //imprime a linha
            System.out.println(linha);
            //lê a proxima linha
            linha = buffer.readLine();
        }

        /*
        Veja que agora utilizamos um BufferedReader ao invés do Scanner,
        isto por que o Scanner mesmo após ter terminado de ler a requisição ele espera
        que a a conexão seja encerrada, a fim de aguardar novas entradas,
        mas como não é interessante para gente esperar,  vamos usar o Buffer pois podemos
        verificar se a linha for vazia, se for, simplesmente encerra o programa sem ter que
        aguardar que a conexão seja encerrada. (Caso seja necessário continuar lendo a
        entrada antes da conexão encerras é so pegar o InputReader novamente e continuar lendo.
         */
    }
}