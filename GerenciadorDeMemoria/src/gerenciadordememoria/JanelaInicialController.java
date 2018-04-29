/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciadordememoria;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author João
 */
public class JanelaInicialController extends InterfaceUsuario { 
    @FXML
    private ComboBox memoriaBox;
    
    @FXML
    private ComboBox paginaBox;  
    
    @FXML
    private TextField fileTextField;
    
    ObservableList<String> memoriaList = FXCollections.observableArrayList("2048","1024","512");
    ObservableList<String> paginaList = FXCollections.observableArrayList("128","64","32","16");
    
    public JanelaInicialController() {
        super("JanelaInicial.fxml");      
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        memoriaBox.setItems(memoriaList);        
        paginaBox.setItems(paginaList);
    }   
    
   
    @FXML
    private void ButtonSelecionador(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");

        File arq =  fileChooser.showOpenDialog(stage);
        
        fileTextField.setText(arq.toString());
    }
    
    @FXML
    private void ButtonAvancar(ActionEvent event) {
        TelaPrincipalController proximaTela = new TelaPrincipalController();
        proximaTela.setArquivo(fileTextField.getText());
        proximaTela.setTamanhoMemoria(memoriaBox.getValue().toString());
        proximaTela.setTamanhoPagina(paginaBox.getValue().toString());
        GerenciadorJanela.obterInstancia().abreJanela(proximaTela);
    }  
    
    @FXML
    private void ButtonInstrucoes(ActionEvent event) {
        InstrucoesController proximaTela = new InstrucoesController();
        GerenciadorJanela.obterInstancia().abreJanela(proximaTela);
    }  
    
}
