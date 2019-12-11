package DAO.Implementacoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import BD.Conexao;
import BD.DBExcecao;
import DAO.DepartamentoDAO;
import Modelo.Entidades.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDAO {

	private Connection conn;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void inserirDados(Departamento departamento) {
		
		try {
			
			pst = conn.prepareStatement("INSERT INTO tbdepartamento "
					+ "(Nome) "
					+ "VALUES "
					+ "(?)", Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, departamento.getNome());
			
			int linhasAfetadas = pst.executeUpdate();
			
			if (linhasAfetadas > 0) {
				rs = pst.getGeneratedKeys();
				if (rs.next())  {
					int id = rs.getInt(1);
					departamento.setId(id);
				}
			} else {
				throw new DBExcecao("Nenhuma Linha foi Alterada!"); 
			}
			
		}catch (SQLException e) {
			throw new DBExcecao(e.getMessage());
		}finally {
			Conexao.fecharStatement(pst);
		}
	}

	@Override
	public void atualizarDados(Departamento departamento) {
		
		try {
			
			pst = conn.prepareStatement("UPDATE tbdepartamento " 
					+"SET Nome = ? WHERE Id = ?");
			
			pst.setString(1, departamento.getNome());
			pst.setInt(2, departamento.getId());
			
			pst.executeUpdate();
			
		}catch (SQLException e) {
			throw new DBExcecao(e.getMessage());
		}
		
	}

	@Override
	public void excluirPerID(Integer Id) {
		
		try {
			pst = conn.prepareStatement("DELETE FROM tbdepartamento WHERE Id = ?");
			
			pst.setInt(1, Id);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Id"));
		dep.setNome(rs.getString("DepNome"));
		return dep;
	}

	@Override
	public Departamento pesquisarPerID(Integer Id) {
		try {
			
			pst = conn.prepareStatement(
					"SELECT tbdepartamento.*,tbdepartamento.Nome as DepNome " + 
					"FROM tbdepartamento " + 
					"WHERE tbdepartamento.Id = ?"
					);
			
			pst.setInt(1, Id);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				Departamento dep = instanciaDepartamento(rs);
				return dep;
			}
			
			return null;
			
		} catch (SQLException e) {
			throw new DBExcecao(e.getMessage());
		}finally {
			Conexao.fecharResultSet(rs);
			Conexao.fecharStatement(pst);
		}
	}

	@Override
	public List<Departamento> encontrarTodos() {
		
		try {
			pst = conn.prepareStatement(
						"SELECT tbdepartamento.*, tbdepartamento.Nome as DepNome " + 
				        "FROM tbdepartamento " + 
				        "ORDER BY Nome");
			
			rs = pst.executeQuery();
			
			List<Departamento> lista = new ArrayList<Departamento>();
			
			while (rs.next()) {
				Departamento dep = instanciaDepartamento(rs);
				lista.add(dep);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.fecharResultSet(rs);
			Conexao.fecharStatement(pst);
		}
		return null;
	}

}
