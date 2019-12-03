package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Aplicacao.Main;
import GUI.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TelaPrincipalControlador implements Initializable{

	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	public void onMenuItemVendedorAction() {
		System.out.println("Menu do vendedor!!!");
	}
	
	@FXML
	public void onMenuItemDepartamentoAction() {
		carregarView("/GUI/TelaDepartamentoLista.fxml");
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		carregarView("/GUI/TelaSobre.fxml");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	private synchronized void carregarView(String caminho) {
		
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(caminho));
			VBox novoVBox = carregar.load();
			
			Scene telaPrincipal = Main.getTelaPrincipal();
						
			VBox telaVBox = (VBox) ((ScrollPane) telaPrincipal.getRoot()).getContent();
			
			Node menuTela = telaVBox.getChildren().get(0);
			
			telaVBox.getChildren().clear();
			telaVBox.getChildren().add(menuTela);
			telaVBox.getChildren().addAll(novoVBox.getChildren());
			

		} catch (IOException e) {
			Alertas.mostrarAlerta("IOException!!", "Erro ao tentar Localizar Tela!", e.getMessage(), AlertType.ERROR);
		}
	}

}


