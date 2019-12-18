package GUI.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alertas {

	public static Optional<ButtonType> mostrarConfirmacao(String titulo, String conteudo) {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(conteudo);
		return alerta.showAndWait();
	}
	
	public static void mostrarAlerta(String titulo, String Cabecalho, String conteudo, AlertType tipoAlerta) {		
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(titulo);
		alerta.setHeaderText(Cabecalho);
		alerta.setContentText(conteudo);
		alerta.show();
	}
}
