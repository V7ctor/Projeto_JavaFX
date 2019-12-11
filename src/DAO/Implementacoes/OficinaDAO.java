package DAO.Implementacoes;

import BD.Conexao;
import DAO.DepartamentoDAO;
import DAO.VendedorDAO;

public class OficinaDAO {

	public static VendedorDAO criarVendedorDAO() {
		return new VendedorDaoJDBC(Conexao.getConexao());
	}
	
	public static DepartamentoDAO criarDepartamentoDAO() {
		return new DepartamentoDaoJDBC(Conexao.getConexao());
	}
}