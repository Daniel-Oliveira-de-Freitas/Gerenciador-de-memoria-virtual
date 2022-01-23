package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GerenciadorController {
    @FXML
    public ListView<String> lista;

    public static String recebeValor;
    
    public static String recebeVal;
    
    public static String recebeValo;
    
    public static String recebeV;
    
    @FXML
    private TextField txfMemPric;

    @FXML
    private TextField txfNumPagProc;

    @FXML
    private TextField txfNumProc;

    @FXML
    private TextField txfWorking;

    @FXML
    void btnSubmit(ActionEvent event) {
		GerenciadorDeMemoria gerenciadorDeMemoria = new GerenciadorDeMemoria(
				Integer.parseInt(txfMemPric.getText()), Integer.parseInt(txfWorking.getText()));
		
		Processo[] processos = Processo.criarProcessos(Integer.parseInt(txfNumPagProc.getText()), gerenciadorDeMemoria,
				Integer.parseInt(txfNumProc.getText()));
		
		Processo.terminarProcessos(processos);
    
    lista.getItems().addAll(recebeValor);
    lista.getItems().add(recebeVal);
    lista.getItems().add(recebeValo);
    lista.getItems().add(recebeV);
    }	


}