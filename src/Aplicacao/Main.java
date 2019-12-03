package Aplicacao;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage palco) {
		try {
			
			FXMLLoader carregamentoTela = new FXMLLoader(getClass().getResource("/GUI/TelaPrincipal.fxml"));
			Parent carregar = carregamentoTela.load();
			Scene telaPrincipal = new Scene(carregar);
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

