package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GerenciadorController {
    @FXML
    public TextArea txaLista;

    public static String recebeValor;
    
    public static String recebeVal;
    
    public static String recebeValo;
    

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
    
    txaLista.setText(recebeValor);
    txaLista.setText(recebeVal);
    txaLista.setText(recebeValo);
    }	


}