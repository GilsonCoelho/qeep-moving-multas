package br.com.qm.multas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import br.com.qm.multas.entity.Condutor;
import br.com.qm.multas.entity.Multa;
import br.com.qm.multas.entity.Veiculo;
import br.com.qm.multas.exception.MultaException;

public class MultaDAO {

	private EntityManager manager;
	private VeiculoDAO veiculoDao;

	public MultaDAO(VeiculoDAO veiculoDao) {

		this.manager = Persistence.createEntityManagerFactory("multa").createEntityManager();
		this.veiculoDao = veiculoDao;
	}

	public void cadastraMulta(Multa multa, String placaVeiculo) throws MultaException {

		Veiculo veiculo = veiculoDao.consultaVeiculo(placaVeiculo);

		if (veiculo == null) {

			throw new MultaException("Veiculo inexistente na base de dados");

		}

		multa.setVeiculo(veiculo);

		if (multa.getValor() <= 0) {

			throw new MultaException("Valor da multa não pode ser negativo ou zero ");

		}

		if (multa.getPontuacao() <= 0) {

			throw new MultaException("A pontuação não pode ser negativa ou zero");

		}

		if (veiculo.getCondutor().getPontuacaoCnh() >= 30) {

			veiculo.getCondutor().setPontuacaoCnh( veiculo.getCondutor().getPontuacaoCnh() + multa.getPontuacao() );
			veiculo.getCondutor().setSuspensa(true);
		
			multa.setVeiculo(veiculo);

			manager.getTransaction().begin();
			manager.persist(multa);
			manager.getTransaction().commit();

			System.out.println("Carteira suspensa");

		} else {

			multa.getVeiculo().getCondutor().setPontuacaoCnh(multa.getPontuacao());

			manager.getTransaction().begin();
			manager.persist(multa);
			manager.getTransaction().commit();
			

		}

	}

	public Multa consultaMultaCodigo(int codigoMulta) throws MultaException {

		if (codigoMulta <= 0) {

			throw new MultaException("O código da multa não pode ser negativa");

		}

		Multa multa = manager.find(Multa.class, codigoMulta);

		if (multa == null) {

			throw new MultaException("Não foi encontrado nenhuma multa com o codigo informado");

		}

		return multa;

	}

	public void removeMulta(int codigoMulta) throws MultaException {

		if (codigoMulta <= 0) {

			throw new MultaException("O código da multa não pode ser negativa");

		}

		Multa multa = manager.find(Multa.class, codigoMulta);

		if (multa == null) {

			throw new MultaException("Não foi encontrado nenhuma multa com o codigo informado");

		}

		manager.getTransaction().begin();
		manager.remove(multa);
		manager.getTransaction().commit();

	}

	public List<Multa> listaMultas() {

		return manager.createQuery("SELECT c FROM Multa c", Multa.class).getResultList();

	}

	public List<Multa> listaMultaPorCondutor(int cnhCondutor) throws MultaException {

		if (cnhCondutor <= 0) {

			throw new MultaException("O número da cnh não pode ser negativo");

		}

		return null;

	}

	public void aplicaMulta(String placa, Multa multa) throws MultaException {

		Veiculo veiculo = veiculoDao.consultaVeiculo(placa);

		if (veiculo == null) {

			throw new MultaException("Veiculo não encontrado");

		}

		Condutor condutorVeiculo = veiculo.getCondutor();

		if (condutorVeiculo == null) {
			
			throw new MultaException("Veiculo sem condutor");
			
		}

		int pontuacaoTotal = condutorVeiculo.getPontuacaoCnh() + multa.getPontuacao();
		condutorVeiculo.setPontuacaoCnh(pontuacaoTotal);

		if (pontuacaoTotal >= 30) {

			condutorVeiculo.setSuspensa(true);

		}

		manager.getTransaction().begin();
		manager.persist(multa);
		manager.getTransaction().commit();

	}

}