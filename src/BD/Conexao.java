package BD;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;


public class Conexao {

	private static Connection conector;
	
	public static Connection getConexao() {
		if (conector == null) {
			try {

				Properties pros = carregarPropriedades();
				String url = pros.getProperty("dburl");
				conector = (Connection) DriverManager.getConnection(url, pros);
				
			}catch (SQLException e) {
				throw new DBExcecao(e.getMessage());
			}
		}
		return conector;
	}
	

	public static void fecharConexao() {
		if (conector != null) {
			try {
				conector.close();
			} catch (SQLException e) {
				throw new DBExcecao(e.getMessage());
			}
		}
	}
	
	
	public static void fecharStatement(PreparedStatement pst) {
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				throw new DBExcecao(e.getMessage());
			}
		}
	}

	public static void fecharResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBExcecao(e.getMessage());
			}
		}
	}
	
	private static Properties carregarPropriedades() {
		
		try (FileInputStream fs = new FileInputStream("db.properties")){
			
			Properties pros = new Properties();
			pros.load(fs);
			return pros;
			
		}catch (IOException e) {
			throw new DBExcecao(e.getMessage());
		}
	}
}
