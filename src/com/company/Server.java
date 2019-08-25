package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
        /*
        Sabemos que a primeira linha da requisição contem o método,
        o arquivo solicitado e o protocolo separados por um espaço em branco,
        para o nosso servidor o método não importa, então assumiremos sempre o GET,
        e o protocolo será sempre o HTTP/1.1, então o que nos importa é o arquivo solicitado.
        Vamos alterar o nosso código que deve ficar assim:
         */

        /* Lê a primeira linha contem as informaçoes da requisição
        */
        String linha = buffer.readLine();
        //quebra a string pelo espaço em branco
        String[] dadosReq = linha.split(" ");
        //pega o metodo
        String metodo = dadosReq[0];
        //paga o caminho do arquivo
        String caminhoArquivo = dadosReq[1];
        //pega o protocolo
        String protocolo = dadosReq[2];
        //Enquanto a linha não for vazia
        while (!linha.isEmpty()) {
            //imprime a linha
            System.out.println(linha);
            //lê a proxima linha
            linha = buffer.readLine();
        }
        //se o caminho foi igual a / entao deve pegar o /index.html
        if (caminhoArquivo.equals("/")) {
            caminhoArquivo = "/Users/alexandre/Desktop/Java Workspace/Trabalho1/src/com/company/www/home.html";
        }
        //abre o arquivo pelo caminho
        File arquivo = new File(caminhoArquivo);
        byte[] conteudo;
        //status de sucesso - HTTP/1.1 200 OK
        String status = protocolo + " 200 OK\r\n";
        //se o arquivo não existe então abrimos o arquivo de erro, e mudamos o status para 404
        if (!arquivo.exists()) {
            status = protocolo + " 404 Not Found\r\n";
            arquivo = new File("/Users/alexandre/Desktop/Java Workspace/Trabalho1/src/com/company/www/404.html");
        }
        conteudo = Files.readAllBytes(arquivo.toPath());

        /*
        Veja que ainda não respondemos ao navegados com os dados, apenas montamos uma parte da resposta,
        para enviar a resposta precisaremos do OutputStream e montar uma string com a estrutura básica da resposta,
        dai vamos escrever esses dados no stream, semelhante ao que fizemos na parte II do nosso tutorial:
         */

        //cria um formato para o GMT espeficicado pelo HTTP
        SimpleDateFormat formatador = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss", Locale.ENGLISH);
        formatador.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date data = new Date();
        //Formata a data para o padrao
        String dataFormatada = formatador.format(data) + " GMT";
        //cabeçalho padrão da resposta HTTP
        String header = status
                + "Location: https://localhost:8000/\r\n"
                + "Date: " + dataFormatada + "\r\n"
                + "Server: MeuServidor/1.0\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: " + conteudo.length + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";
        //cria o canal de resposta utilizando o outputStream
        OutputStream resposta = socket.getOutputStream();
        //escreve o headers em bytes
        resposta.write(header.getBytes());
        //escreve o conteudo em bytes
        resposta.write(conteudo);
        //encerra a resposta
        resposta.flush();



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