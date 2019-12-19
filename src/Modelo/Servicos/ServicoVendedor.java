package Modelo.Servicos;

import java.util.List;

import DAO.VendedorDAO;
import DAO.Implementacoes.OficinaDAO;
import Modelo.Entidades.Vendedor;

public class ServicoVendedor {
		private VendedorDAO dao = OficinaDAO.criarVendedorDAO();
		
		public List<Vendedor> pesquisarTodos(){
			return dao.encontrarTodos();
		}
		
		public void salvarOuAtualizar(Vendedor vendedor) {	
			if (vendedor.getId() == null) {
				dao.inserirDados(vendedor);
			}else {
				dao.atualizarDados(vendedor);
			}
		}
		
		public void excluirVendedor(Vendedor vendedor) {
			dao.excluirPerID(vendedor.getId());	
		}
		
}


