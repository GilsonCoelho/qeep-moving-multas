package br.com.qm.multas.dao;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import br.com.qm.multas.entity.Condutor;
import br.com.qm.multas.entity.Multa;
import br.com.qm.multas.entity.Veiculo;
import br.com.qm.multas.exception.MultaException;

public class CondutorDAO {

	EntityManager manager;

	public CondutorDAO() {

		this.manager = Persistence.createEntityManagerFactory("multa").createEntityManager();

	}

	public void cadatraCondutor(Condutor condutor) throws MultaException  {

		
		if ( condutor.getDataEmissaoCnh() == null || condutor.getOrgaoEmissorCnh() == null  || condutor.getDataEmissaoCnh() == null) {

			throw new MultaException("Dados obrigatório não podem ser nulos ( data, orgão emissor e veiculo )");
           
		}
		
		Condutor verificaCadastro = manager.find(Condutor.class, condutor.getNumeroCnh());
		
		if(verificaCadastro != null) {
			
			throw new MultaException("O condutor ja cadastrado");
			
		}

		manager.getTransaction().begin();
		manager.persist(condutor);
		manager.getTransaction().commit();
		


	}

	public void removeCondutor(int numeroCnh) throws MultaException {

		Condutor condutor = consultaCondutor(numeroCnh);

		manager.getTransaction().begin();
		manager.remove(condutor);
		manager.getTransaction().commit();

	}

	public Condutor consultaCondutor(int numeroCnh) throws MultaException {

		if (numeroCnh <= 0) {

			throw new MultaException("Valor digitado não pode ser nagativo ou zero");

		}
		
		return manager.find(Condutor.class,numeroCnh);		

	}

	public List<Condutor> listaTodosCondutores() {
		
		return manager.createQuery("SELECT c FROM Condutor c",Condutor.class).getResultList();


	}
	
	public List<Veiculo> listaVeiculoPorCondutor( int nroCnh) throws MultaException{
		
		
			Condutor condutor = this.consultaCondutor(nroCnh);

			if(condutor == null) {
				
				throw new MultaException("Contudor não encontrado");
				
			}
			
			return condutor.getVeiculo();
			
		
	}
	
	public List<Multa> listaMultasPorCondutor(int cnhCondutor) throws MultaException{
		
		
		Condutor condutor = this.consultaCondutor(cnhCondutor);
		
		if(condutor == null) {
			
			throw new MultaException("Condutor não encontrado");
			
		}
		
		
		List<Multa> multasCondutor = new ArrayList<Multa>();
		
		for(Veiculo veiculo : condutor.getVeiculo()) {
		
			for(Multa multa: veiculo.getMultas()) {
				
				multasCondutor.add(multa);
				
			}
			
			
		}
		
		
		return multasCondutor;
		
	}
	
	
	

}