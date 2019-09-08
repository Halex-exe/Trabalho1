package com.company;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Date;

public class Server {

    public static void main(String[] args) throws IOException {
        /* cria um socket "servidor" associado a porta 8000
         já aguardando conexões
         */
        String teste = "Alexandre";
        ServerSocket servidor = new ServerSocket(8000);
        //aceita a primeita conexao que vier
        Socket socket = servidor.accept();
        //verifica se esta conectado
        if (socket.isConnected()) {
            //imprime na tela o IP do cliente
            System.out.println(socket.getInetAddress());

            //cria um BufferedReader a partir do InputStream do cliente
            RequisicaoHTTP requisicao = RequisicaoHTTP.lerRequisicao(socket.getInputStream());

            //se o caminho foi igual a / entao deve pegar o /index.html
            if (requisicao.getRecurso().equals("/")) {
                requisicao.setRecurso("/Users/alexandre/Desktop/Java Workspace/Trabalho1/src/com/company/www/home.html");
            }else{
                if ((requisicao.getRecurso().equals("/runner.html"))){
                    requisicao.setRecurso("/Users/alexandre/Desktop/Java Workspace/Trabalho1/src/com/company/www/runner.html");
                }
            }
            //abre o arquivo pelo caminho
            File arquivo = new File(requisicao.getRecurso());
            //File arquivo = new File(requisicao.getRecurso().replaceFirst("/", ""));

            RespostaHTTP resposta;

            //se o arquivo existir então criamos a reposta de sucesso, com status 200
            if (arquivo.exists()) {
                resposta = new RespostaHTTP(requisicao.getProtocolo(), 200, "OK");
            } else {
                //se o arquivo não existe então criamos a reposta de erro, com status 404
                resposta = new RespostaHTTP(requisicao.getProtocolo(), 404, "Not Found");
            }
            //lê todo o conteúdo do arquivo para bytes e gera o conteudo de resposta
            resposta.setConteudoResposta(Files.readAllBytes(arquivo.toPath()));
            //converte o formato para o GMT espeficicado pelo protocolo HTTP
            String dataFormatada = Util.formatarDataGMT(new Date());
            //cabeçalho padrão da resposta HTTP/1.1
            resposta.setCabecalho("Location", "https://localhost:8000/");
            resposta.setCabecalho("Date", dataFormatada);
            resposta.setCabecalho("Server", "MeuServidor/1.0");
            resposta.setCabecalho("Content-Type", "text/html");
            resposta.setCabecalho("Content-Length",resposta.getTamanhoResposta());
            //cria o canal de resposta utilizando o outputStream
            resposta.setSaida(socket.getOutputStream());
            resposta.enviar();

        }
    }
}