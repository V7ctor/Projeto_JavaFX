package GUI.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alertas {

	public static void mostrarAlerta(String titulo, String Cabecalho, String conteudo, AlertType tipoAlerta) {		
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(titulo);
		alerta.setHeaderText(Cabecalho);
		alerta.setContentText(conteudo);
		alerta.show();
	}
}
