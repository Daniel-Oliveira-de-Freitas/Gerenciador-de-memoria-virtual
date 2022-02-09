package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GerenciadorController {
	private final int VALOR_LIMITE = 12;

	public static String recebeValor;

	public static String recebeVal;

	public static String recebeValo;

	public static String recebeV;

	@FXML
	public ListView<String> lista;

	@FXML
	public TextField txfMemPric;

	@FXML
	public TextField txfNumPagProc;

	@FXML
	public TextField txfNumProc;

	@FXML
	public TextField txfWorking;

	public boolean limite() {
		if (Integer.parseInt(txfMemPric.getText()) > VALOR_LIMITE
				|| Integer.parseInt(txfNumPagProc.getText()) > VALOR_LIMITE
				|| Integer.parseInt(txfNumProc.getText()) > VALOR_LIMITE
				|| Integer.parseInt(txfWorking.getText()) > VALOR_LIMITE) {
			alerta("Valor máximo nos campos é " + VALOR_LIMITE + " !");
			return false;
		} else {
			return true;
		}
	}

	@FXML
	void btnSubmit(ActionEvent event) {
		if (txfMemPric.getText().isBlank() || txfNumPagProc.getText().isBlank() || txfNumProc.getText().isBlank()
				|| txfWorking.getText().isBlank()) {
			alerta("Você deve preencher todos os campos!");
		} else {
			if (limite() == true) {
				GerenciadorDeMemoria gerenciadorDeMemoria = new GerenciadorDeMemoria(
						Integer.parseInt(txfMemPric.getText()), Integer.parseInt(txfWorking.getText()));

				Processo[] processos = Processo.criarProcessos(Integer.parseInt(txfNumPagProc.getText()),
						gerenciadorDeMemoria, Integer.parseInt(txfNumProc.getText()));

				Processo.terminarProcessos(processos);
				lista.getItems().addAll(recebeValor);
				lista.getItems().add(recebeVal);
				lista.getItems().add(recebeValo);
				lista.getItems().add(recebeV);
			}
		}
	}

	public void alerta(String msg) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Atenção");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
