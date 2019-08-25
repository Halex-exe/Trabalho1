package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RespostaHTTP {

    private String protocolo;
    private int codigoResposta;
    private String mensagem;
    private byte[] conteudoResposta;
    private Map<String, List> cabecalhos;
    private OutputStream saida;

    public RespostaHTTP() {

    }

    public RespostaHTTP(String protocolo, int codigoResposta, String mensagem) {
        this.protocolo = protocolo;
        this.codigoResposta = codigoResposta;
        this.mensagem = mensagem;
    }

    /**
     * Envia os dados da resposta ao cliente.
     *
     * @throws IOException
     */
    public void enviar() throws IOException {
        //escreve o headers em bytes
        saida.write(montaCabecalho());
        //escreve o conteudo em bytes
        saida.write(conteudoResposta);
        //encerra a resposta
        saida.flush();
    }

    /**
     * Insere um item de cabeçalho no mapa
     *
     * @param chave
     * @param valores lista com um ou mais valores para esta chave
     */
    public void setCabecalho(String chave, String... valores) {
        if (cabecalhos == null) {
            cabecalhos = new TreeMap<>();
        }
        cabecalhos.put(chave, Arrays.asList(valores));
    }

    /**
     * pega o tamanho da resposta em bytes
     *
     * @return retorna o valor em bytes do tamanho do conteudo da resposta
     * convertido em string
     */
    public String getTamanhoResposta() {
        return getConteudoResposta().length + "";
    }

    /**
     * converte o cabecalho em string.
     *
     * @return retorna o cabecalho em bytes
     */
    private byte[] montaCabecalho() {
        return this.toString().getBytes();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(protocolo).append(" ").append(codigoResposta).append(" ").append(mensagem).append("\r\n");
        for (Map.Entry<String, List> entry : cabecalhos.entrySet()) {
            str.append(entry.getKey());
            String stringCorrigida = Arrays.toString(entry.getValue().toArray()).replace("[", "").replace("]", "");
            str.append(": ").append(stringCorrigida).append("\r\n");
        }
        str.append("\r\n");
        return str.toString();
    }

    public byte[] getConteudoResposta() {
        return conteudoResposta;
    }

    public void setConteudoResposta(byte[] conteudoResposta) {
        this.conteudoResposta = conteudoResposta;
    }

    public OutputStream getSaida() {
        return saida;
    }

    public void setSaida(OutputStream saida) {
        this.saida = saida;
    }
}