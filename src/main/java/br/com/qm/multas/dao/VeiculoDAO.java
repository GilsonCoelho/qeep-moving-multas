package br.com.qm.multas.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import br.com.qm.multas.entity.Condutor;
import br.com.qm.multas.entity.Multa;
import br.com.qm.multas.entity.Veiculo;
import br.com.qm.multas.exception.MultaException;

public class VeiculoDAO {

	private EntityManager manager;

	private CondutorDAO condutorDao;

	public VeiculoDAO(CondutorDAO condutorDao) {

		this.manager = Persistence.createEntityManagerFactory("multa").createEntityManager();
		this.condutorDao = condutorDao;

	}

	public void cadastraVeiculo(Veiculo veiculo, int cnhCondutor) throws MultaException {

		Condutor condutor = condutorDao.consultaCondutor(cnhCondutor);

		if (condutor == null) {

			throw new MultaException("Condutor não encontrado");

		}

		if (veiculo == null) {

			throw new MultaException("Veiculo não pode ser nulo");

		}

		if (manager.find(Veiculo.class, veiculo.getPlaca()) != null) {

			throw new MultaException("Veiculo ja cadastrado");

		}

		
		veiculo.setCondutor(condutor);

		manager.getTransaction().begin();
		manager.persist(veiculo);
		manager.getTransaction().commit();

	}
	
	public void removeVeiculo(String placa) throws MultaException {
		
		Veiculo veiculo = consultaVeiculo(placa);
		
		if(veiculo == null) {
			
			throw new MultaException("Veiculo não encontrado");
			
		}

		manager.getTransaction().begin();
		manager.remove(veiculo);
		manager.getTransaction().commit();
		
	}

	public Veiculo consultaVeiculo(String placa) throws MultaException {

		Veiculo veiculo = manager.find(Veiculo.class, placa);
				
		if(veiculo == null) {
			
			throw new MultaException("Veiculo não encontrado");
			
		}
		
		return veiculo;

	}
	
	public List<Veiculo> listaTodosVeiculos(){
		
		return manager.createQuery("SELECT c FROM Veiculo c",Veiculo.class).getResultList();
		
	}

	public void transfereVeiculo(String placaVeiculo, int cnhNovoDono, int cnhDonoAtual) throws MultaException {

		if (cnhNovoDono <= 0 || cnhDonoAtual <= 0) {

			throw new MultaException("Números das cnh invalido.");

		}

		Veiculo veiculo = manager.find(Veiculo.class, placaVeiculo);

		if (veiculo == null) {

			throw new MultaException("Veiculo não encontrado");

		}

		if (veiculo.getCondutor().getNumeroCnh() != cnhDonoAtual) {

			throw new MultaException("CNH do dono atual é diferente do registro do veiculo");

		}

		Condutor novoDono = manager.find(Condutor.class, cnhNovoDono);

		if (novoDono == null) {

			throw new MultaException("Novo dono não encontrado");

		}

		List<Multa> multasVeiculo = veiculo.getMultas();
		int contador = 0;

		for (Multa multas : multasVeiculo) {

			if (!multas.isPago()) {

				contador++;

			}

		}

		if (contador > 0) {

			throw new MultaException("Veiculo possui " + contador + " multas não pagas.");

		}

		veiculo.setCondutor(novoDono);
		manager.getTransaction().begin();
		manager.merge(veiculo);
		manager.getTransaction().commit();

	}

	public void imprimirRelatorio(String placa) throws MultaException {

		Veiculo veiculo = this.consultaVeiculo(placa);

		if (veiculo == null) {
			return;
		}

		try {

			FileWriter fw = new FileWriter("relatorio_" + placa);

			List<Multa> multas = veiculo.getMultas();

			if (multas.size() <= 0) {
				fw.write("O veiculo não possui multas");
			} else {
				for (Multa multa : multas) {
					fw.write(String.format(" %d R$%.2f\n", multa));
				}

				fw.close();
			}

		} catch (IOException e) {

			System.out.println(e.getMessage());

		}

	}

}