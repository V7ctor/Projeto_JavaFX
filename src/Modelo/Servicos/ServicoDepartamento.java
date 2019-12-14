package Modelo.Servicos;

import java.util.List;

import DAO.DepartamentoDAO;
import DAO.Implementacoes.OficinaDAO;
import Modelo.Entidades.Departamento;

public class ServicoDepartamento {

	private DepartamentoDAO dao = OficinaDAO.criarDepartamentoDAO();
	
	public List<Departamento> pesquisarTodos(){
		return dao.encontrarTodos();
	}
	
	public void salvarOuAtualizar(Departamento depto) {	
		if (depto.getId() == null) {
			dao.inserirDados(depto);
		}else {
			dao.atualizarDados(depto);
		}
	}
}

