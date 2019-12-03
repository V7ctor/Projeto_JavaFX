package GUI.util;

import javafx.scene.control.TextField;

public class Restricoes {

	public static void setCampoInteger(TextField campo) {
		campo.textProperty().addListener((obs, valorNulo, novoValor) -> {
			if (novoValor != null && !novoValor.matches("\\d*")) {
				campo.setText(valorNulo);
			}
		});
	}
	
	public static void setCampoDouble(TextField campo) {
		campo.textProperty().addListener((obs, valorNulo, novoValor)->{
			if (novoValor != null && !novoValor.matches("\\d*([\\.]\\d*)?")) {
				campo.setText(valorNulo);
			}
		});
	}
	
	public static void setCampoValorMax(Integer max, TextField campo) {
		campo.textProperty().addListener((obs, valorNulo, novoValor) -> {
			if (novoValor != null && novoValor.length() > max) {
				campo.setText(valorNulo);
			}
		});
	}
}
