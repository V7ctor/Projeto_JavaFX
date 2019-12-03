package Aplicacao;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage palco) {
		try {
			
			FXMLLoader carregamentoTela = new FXMLLoader(getClass().getResource("/GUI/TelaPrincipal.fxml"));
			ScrollPane carregamentoScroll = carregamentoTela.load();
			
			carregamentoScroll.setFitToHeight(true);
			carregamentoScroll.setFitToWidth(true);
			
			Scene telaPrincipal = new Scene(carregamentoScroll );
			palco.setScene(telaPrincipal);
			palco.setTitle("Aplicação Crud JavaFX");
			palco.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

