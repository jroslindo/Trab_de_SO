/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciadordememoria;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo
 */
public class Processo {
    
    private int id;
    private int tempoCriacao;
    private int tempoMorte;
    private int tamanho;
    private boolean aguardou = false;
    private boolean aguardou2 = false;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the tempoCriacao
     */
    public int getTempoCriacao() {
        return tempoCriacao;
    }

    /**
     * @param tempoCriacao the tempoCriacao to set
     */
    public void setTempoCriacao(int tempoCriacao) {
        this.tempoCriacao = tempoCriacao;
    }

    /**
     * @return the tempoMorte
     */
    public int getTempoMorte() {
        return tempoMorte;
    }

    /**
     * @param tempoMorte the tempoMorte to set
     */
    public void setTempoMorte(int tempoMorte) {
        this.tempoMorte = tempoMorte;
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * @param tamanho the tamanho to set
     */
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    
    //Funcao que le o arquivo escolhido pelo usuario
    static public ArrayList<Processo> obterListaProcessos(String diretorio) {

        ArrayList<Processo> processos = new ArrayList();
        try {
            FileReader arq = new FileReader(diretorio);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha;
            int i = 0;

            //Laço para popular o arraylist com os processos
            while ((linha = lerArq.readLine()) != null) {
                if (linha.trim().equals("")) {
                    continue;
                }
                Processo proc = new Processo();
                String array[] = linha.split(",");
                proc.setId(Integer.parseInt(array[0]));
                proc.setTempoCriacao(Integer.parseInt(array[1]));
                proc.setTempoMorte(Integer.parseInt(array[2]));
                proc.setTamanho(Integer.parseInt(array[3]));
                processos.add(proc);
                i++;

            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Processo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Processo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return processos;
    }

    /**
     * @return the aguardou
     */
    public boolean isAguardou() {
        return aguardou;
    }

    /**
     * @param aguardou the aguardou to set
     */
    public void setAguardou(boolean aguardou) {
        this.aguardou = aguardou;
    }

    /**
     * @return the aguardou2
     */
    public boolean isAguardou2() {
        return aguardou2;
    }

    /**
     * @param aguardou2 the aguardou2 to set
     */
    public void setAguardou2(boolean aguardou2) {
        this.aguardou2 = aguardou2;
    }
    

}
