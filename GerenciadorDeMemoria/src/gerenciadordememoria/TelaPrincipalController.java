/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciadordememoria;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gustavo
 */
public class TelaPrincipalController extends InterfaceUsuario {

    public TelaPrincipalController() {
        super("TelaPrincipal.fxml");
    }

    private String tamanhoMemoria;
    private String tamanhoPagina;
    private String arquivo;
    //JOAO MEXENDO
    private int N;
    private int size;
    private int espacosNaMemoria;

    @FXML
    private TableView<Processo> memoriaID;
    @FXML
    private TableColumn<Processo, Integer> memoriaIDColumn;

    private int numeroPaginasTotal;
    private ArrayList<Processo> filaExecucao = new ArrayList<>();
    
    @FXML
    private Label labelN;
    
    @FXML
    private TextArea logTextArea;

    //JOAO PAROU AKI
    @FXML
    private TableView<Processo> filaTableView;

    @FXML
    private TableColumn<Processo, Integer> idTableColumn;

    @FXML
    private TableColumn<Processo, Integer> criadoTableColumn;

    @FXML
    private TableColumn<Processo, Integer> mortoTableColumn;

    @FXML
    private TableColumn<Processo, Integer> tamanhoTableColumn;
    
    @FXML
    private Label labelProcessos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //JOAO MEXENDO
        N = 0;   
        
        
        numeroPaginasTotal = Integer.parseInt(tamanhoMemoria) / Integer.parseInt(tamanhoPagina);
        //Parte do código responsavel por preencher a tabela de fila de processos
        ArrayList<Processo> listaObservadora = Processo.obterListaProcessos(arquivo.toString());
        size = listaObservadora.size();
        ObservableList<Processo> listaObservavel = FXCollections.observableArrayList();
        int i = 0;
        while (i < listaObservadora.size()) {
            listaObservavel.add(listaObservadora.get(i));
            i++;
        }

        idTableColumn.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("id"));
        criadoTableColumn.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("tempoCriacao"));
        mortoTableColumn.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("tempoMorte"));
        tamanhoTableColumn.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("tamanho"));
        filaTableView.setItems(listaObservavel);

    }

    private ArrayList<Processo> filaEntrada = new ArrayList<>();
    private ObservableList<Processo> listaObservavel1 = FXCollections.observableArrayList();
    private int QTDProcesos = 0;
    @FXML
    private Label labelQTD;
    
    private int QTDNaoAguardou=0;
    @FXML
    private Label labelSemAguardo;
    
    private Processo temporario;
    private int tempoDeExecucao=0;
    private int QTDAguardou = 0;
    
    @FXML
    private Label labelAguardo;
    

    //JOAO MEXENDO
    public void buttonIncrementar(ActionEvent event) {
        if (N == 0) {
            espacosNaMemoria = 0;
            filaEntrada = Processo.obterListaProcessos(arquivo.toString());
        }
        
        tempoDeExecucao++;

        N++;
        labelN.setText("Tempo de execução: " + tempoDeExecucao);        
        labelProcessos.setText("Linhas de pagina: " + numeroPaginasTotal);
        
        int teste=0;

        Double numeroRepeticoes;

        for (int j = 0; j < listaObservavel1.size(); j++) {
            listaObservavel1.get(j).setTempoMorte(listaObservavel1.get(j).getTempoMorte() - 1);
            //System.out.println("ID: " + listaObservavel1.get(j).getId() + "Tempo de morte: " + listaObservavel1.get(j).getTempoMorte());
        }

        //System.out.println("----------------------------------------------------");

        for (int j = 0; j < listaObservavel1.size(); j++) {
            if (listaObservavel1.get(j).getTempoMorte() <= 0) {
                logTextArea.setText(listaObservavel1.get(j).getId() + ", SAIU \n" + logTextArea.getText());
                listaObservavel1.remove(j);
                espacosNaMemoria--;                
                j--;
                
            }

        }

        for (int i = 0; i < size; i++) {

            if (filaEntrada.get(i).getTempoCriacao() == N) {                

                numeroRepeticoes = Math.ceil((filaEntrada.get(i).getTamanho()) / Double.parseDouble(tamanhoPagina));

                if ((numeroPaginasTotal - espacosNaMemoria - numeroRepeticoes) >= 0) {   
                    
                    for (int j = 0; j < numeroRepeticoes; j++) {
                        Processo temp = new Processo();
                        temp.setId(filaEntrada.get(i).getId());
                        temp.setTamanho(filaEntrada.get(i).getTamanho());
                        temp.setTempoCriacao(filaEntrada.get(i).getTempoCriacao());
                        temp.setTempoMorte(filaEntrada.get(i).getTempoMorte());
                        listaObservavel1.add(temp);
                        espacosNaMemoria++;   
                        teste=j;                        
                        
                    }
                    if (filaEntrada.get(i).isAguardou() == false){
                        QTDNaoAguardou++;
                    }
                    QTDProcesos++;
                    labelQTD.setText("Quantidade de processos: " + QTDProcesos);
                    logTextArea.setText(filaEntrada.get(i).getId() + ", ENTROU, TEMPO: "+ N+" \n" + logTextArea.getText());
                    filaEntrada.remove(i);
                    i--;                    
                    size--;

                } else {
                    filaEntrada.get(i).setAguardou(true);                   
                    if (filaEntrada.get(i).isAguardou2() == false){
                        QTDAguardou++;
                        filaEntrada.get(i).setAguardou2(true);
                    }
                    
                    System.out.println("MEMORIA CHEIA!!!");
                    logTextArea.setText("MEMORIA CHEIA!!! \n" + logTextArea.getText());
                    N--;
                }
            }
        }
        
        labelAguardo.setText("Processos que aguardaram: " + QTDAguardou);
        labelSemAguardo.setText("Processos sem aguardo: " + QTDNaoAguardou);
        memoriaIDColumn.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("id"));
        memoriaID.setItems(listaObservavel1);

    }
    
    public void buttonAutoIncrementar (ActionEvent event){
        while(true){
            if (N == 0) {
            espacosNaMemoria = 0;
            filaEntrada = Processo.obterListaProcessos(arquivo.toString());
        }
            
        if (listaObservavel1.size()==0 && N!=0)
            break;
        
        tempoDeExecucao++;

        N++;
        labelN.setText("Tempo de execução: " + tempoDeExecucao);
        labelProcessos.setText("Linhas de pagina: " + numeroPaginasTotal);
        int teste=0;

        Double numeroRepeticoes;

        for (int j = 0; j < listaObservavel1.size(); j++) {
            listaObservavel1.get(j).setTempoMorte(listaObservavel1.get(j).getTempoMorte() - 1);
            //System.out.println("ID: " + listaObservavel1.get(j).getId() + "Tempo de morte: " + listaObservavel1.get(j).getTempoMorte());
        }

        //System.out.println("----------------------------------------------------");

        for (int j = 0; j < listaObservavel1.size(); j++) {
            if (listaObservavel1.get(j).getTempoMorte() <= 0) {
                logTextArea.setText(listaObservavel1.get(j).getId() + ", SAIU \n" + logTextArea.getText());
                listaObservavel1.remove(j);
                espacosNaMemoria--;                
                j--;
                
            }

        }

        for (int i = 0; i < size; i++) {

            if (filaEntrada.get(i).getTempoCriacao() == N) {                

                numeroRepeticoes = Math.ceil((filaEntrada.get(i).getTamanho()) / Double.parseDouble(tamanhoPagina));

                if ((numeroPaginasTotal - espacosNaMemoria - numeroRepeticoes) >= 0) {   
                    
                    for (int j = 0; j < numeroRepeticoes; j++) {
                        Processo temp = new Processo();
                        temp.setId(filaEntrada.get(i).getId());
                        temp.setTamanho(filaEntrada.get(i).getTamanho());
                        temp.setTempoCriacao(filaEntrada.get(i).getTempoCriacao());
                        temp.setTempoMorte(filaEntrada.get(i).getTempoMorte());
                        listaObservavel1.add(temp);
                        espacosNaMemoria++;   
                        teste=j;
                        
                    }
                    if (filaEntrada.get(i).isAguardou() == false){
                        QTDNaoAguardou++;
                    }
                    QTDProcesos++;
                    labelQTD.setText("Quantidade de processos: " + QTDProcesos);
                    logTextArea.setText(filaEntrada.get(i).getId() + ", ENTROU, TEMPO: "+ N+" \n" + logTextArea.getText());
                    filaEntrada.remove(i);
                    i--;
                    size--;

                } else {
                    filaEntrada.get(i).setAguardou(true);
                    if (filaEntrada.get(i).isAguardou2() == false){
                        QTDAguardou++;
                        filaEntrada.get(i).setAguardou2(true);
                    }
                    System.out.println("MEMORIA CHEIA!!!");
                    logTextArea.setText("MEMORIA CHEIA!!! \n" + logTextArea.getText());
                    N--;
                }                
            }
        }
        
        labelAguardo.setText("Processos que aguardaram: " + QTDAguardou);
        labelSemAguardo.setText("Processos sem aguardo: " + QTDNaoAguardou);
        memoriaIDColumn.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("id"));
        memoriaID.setItems(listaObservavel1);
        }
    }

    public String getTamanhoMemoria() {
        return tamanhoMemoria;
    }

    public void setTamanhoMemoria(String tamanhoMemoria) {
        this.tamanhoMemoria = tamanhoMemoria;
    }

    public String getTamanhoPagina() {
        return tamanhoPagina;
    }

    public void setTamanhoPagina(String tamanhoPagina) {
        this.tamanhoPagina = tamanhoPagina;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

}   //criando a segunda tela
