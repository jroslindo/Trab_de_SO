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

    //JOAO MEXENDO
    public void buttonIncrementar(ActionEvent event) {
        if (N == 0) {
            espacosNaMemoria = 0;
            filaEntrada = Processo.obterListaProcessos(arquivo.toString());
        }

        N++;
        int teste=0;

        Double numeroRepeticoes;

        for (int j = 0; j < listaObservavel1.size(); j++) {
            listaObservavel1.get(j).setTempoMorte(listaObservavel1.get(j).getTempoMorte() - 1);
            //System.out.println("ID: " + listaObservavel1.get(j).getId() + "Tempo de morte: " + listaObservavel1.get(j).getTempoMorte());
        }

        System.out.println("----------------------------------------------------");

        for (int j = 0; j < listaObservavel1.size(); j++) {
            if (listaObservavel1.get(j).getTempoMorte() <= 0) {
                logTextArea.setText(logTextArea.getText()+listaObservavel1.get(j).getId() + ", SAIU \n");
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
                    logTextArea.setText(logTextArea.getText() + filaEntrada.get(i).getId() + ", ENTROU \n");
                    filaEntrada.remove(i);
                    i--;
                    size--;

                } else {
                    System.out.println("MEMORIA CHEIA!!!");
                    logTextArea.setText(logTextArea.getText() + "MEMORIA CHEIA!!! \n");
                    N--;
                }
            }
        }

        memoriaIDColumn.setCellValueFactory(new PropertyValueFactory<Processo, Integer>("id"));
        memoriaID.setItems(listaObservavel1);

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
