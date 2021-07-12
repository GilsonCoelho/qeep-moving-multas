package br.com.qm.multas;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import br.com.qm.multas.dao.CondutorDAO;
import br.com.qm.multas.dao.MultaDAO;
import br.com.qm.multas.dao.VeiculoDAO;
import br.com.qm.multas.entity.Condutor;
import br.com.qm.multas.entity.Multa;
import br.com.qm.multas.entity.Veiculo;
import br.com.qm.multas.exception.MultaException;

public class ProgramaPrincipal {

	public static void subMenu(int largura, String nome) {

		int larguraTotal = largura + nome.length() - 1;
		linhaMenu(larguraTotal);

		for (int i = 0; i <= largura; i++) {

			if (i == largura / 2) {
				System.out.printf(nome);
			} else if (i == 0) {
				System.out.printf("|");
			} else if (i == largura) {
				System.out.printf("|\n");
			} else {
				System.out.print(" ");
			}

		}

		linhaMenu(larguraTotal);

	}

	public static void linhaMenu(int larguraMenu) {

		for (int i = 0; i <= larguraMenu; i++) {

			if (i == 0) {
				System.out.printf("+");
			} else if (i == larguraMenu) {
				System.out.printf("+\n");
			} else {
				System.out.printf("-");
			}

		}

	}

	public static void addItensMenu(String itensMenu[], int larguraMenu, int numeroSaida) {

		for (int i = 0; i < itensMenu.length; i++) {

			for (int y = 0; y <= (larguraMenu - itensMenu[i].length()) - 3; y++) {

				if (i == itensMenu.length - 1 && y == 0) {

					System.out.printf("|%d- %s", numeroSaida, itensMenu[i]);

				} else if (y == 0 && i != itensMenu.length - 1) {

					System.out.printf("|%d- %s", i + 1, itensMenu[i]);

				} else if (y == (larguraMenu - itensMenu[i].length()) - 3) {

					System.out.printf("|\n");

				} else {
					System.out.printf(" ");
				}

			}

		}

	}

	public static void criaMenu(String nome, int largura, String itensMenu[], int numeroSaida) {

		int larguraMenu = largura + nome.length() - 1;

		linhaMenu(larguraMenu);

		for (int i = 0; i <= largura; i++) {

			if (i == largura / 2) {
				System.out.printf(nome);
			} else if (i == 0) {
				System.out.printf("|");
			} else if (i == largura) {
				System.out.printf("|\n");
			} else {
				System.out.print(" ");
			}

		}

		linhaMenu(larguraMenu);

		addItensMenu(itensMenu, larguraMenu, numeroSaida);

		linhaMenu(larguraMenu);

	}

	public static void menuCondutor(Scanner in, CondutorDAO condutorDao) {

		int opcaoCondutor = 0;
		String[] itensMenu = { "Cadastro", "Removação", "Consulta", "Listar todos",
				"Lista todos os veiculos do condutor", "Lista multa por condutor", "Voltar ao menu anterior" };

		do {

			criaMenu("GERENCIAMENTO CONDUTOR", 44, itensMenu, 0);
			System.out.print("Digite a opção desejada : ");
			opcaoCondutor = in.nextInt();

			switch (opcaoCondutor) {
			case 1:

				subMenu(44, "CADASTRO CONDUTOR");

				System.out.printf("\nDigite o número da cnh : ");
				int numeroCnhCadastro = in.nextInt();

				in.nextLine();

				System.out.print("Digite a data de emissão no formato (ano - mes - dia ) : ");
				LocalDate dataEmissao = LocalDate.parse(in.nextLine());

				System.out.print("Digite o nome do orgão emissor : ");
				String orgaoEmissor = in.nextLine();

				System.out.print("Digite o número de pontos : ");
				int pontos = in.nextInt();

				Condutor condutor = new Condutor(numeroCnhCadastro, dataEmissao, orgaoEmissor, pontos);

				try {

					condutorDao.cadatraCondutor(condutor);
					System.out.printf("\n Cadastrado com sucesso. \n ");
					linhaMenu(58);

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 2:

				subMenu(44, "CONSULTA CONDUTOR");
				System.out.print("Digite o númedo da cnh:");
				int cnhDeleta = in.nextInt();

				try {

					condutorDao.removeCondutor(cnhDeleta);
					System.out.printf("\n Condutor removido com sucesso. \n ");

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 3:

				subMenu(44, "CONSULTA CONDUTOR");

				System.out.print("Digite o número da cnh:");
				int cnh = in.nextInt();

				try {

					Condutor condutorConsulta = condutorDao.consultaCondutor(cnh);

					System.out.println(condutorConsulta);

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 4:

				subMenu(44, "LISTA TODOS CONDUTORES");
				int contadorCondutor = 1;

				List<Condutor> condutores = condutorDao.listaTodosCondutores();

				for (Condutor condutorLista : condutores) {

					System.out.println(contadorCondutor + "- " + condutorLista);
					contadorCondutor++;
				}

				break;

			case 5:

				subMenu(44, "LISTA TODOS VEICULOS DO CONDUTOR");
				System.out.print("Digite o númedo da cnh:");
				int cnhListaVeiculos = in.nextInt();
				int contadorVeiculos = 1;

				try {

					List<Veiculo> veiculosDoCondutor = condutorDao.listaVeiculoPorCondutor(cnhListaVeiculos);

					for (Veiculo veiculos : veiculosDoCondutor) {

						System.out.println(contadorVeiculos + "- " + veiculos);
						contadorVeiculos++;

					}

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}
				break;

			case 6:

				subMenu(44, "LISTA TODOS AS MULSTAS DO CONDUTOR");

				System.out.print("Digite o númedo da cnh:");
				int cnhListaMultas = in.nextInt();
				int contadorMulta = 1;

				try {

					List<Multa> multas = condutorDao.listaMultasPorCondutor(cnhListaMultas);

					for (Multa multa : multas) {

						System.out.println(contadorMulta + "- " + multa);
						contadorMulta++;
					}

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 0:

				System.out.printf("\n Voltando ao menu anteior <----\n");
				break;

			default:

				System.out.printf("\n Valor digitado invalido.\n");

				break;
			}

		} while (opcaoCondutor != 0);

	}

	public static void menuVeiculo(Scanner in, VeiculoDAO veiculoDao) {

		int opcaoVeiculo = 0;
		String[] itensMenu = { "Cadastro", "Removação", "Consulta", "Listar todos", "Tranfere veiculo",
				"Voltar ao menu anterior" };

		do {

			criaMenu("GERENCIAMENTO VEICULO", 44, itensMenu, 0);

			System.out.print("Digite a opção desejada : ");
			opcaoVeiculo = in.nextInt();

			switch (opcaoVeiculo) {

			case 1:

				in.nextLine();
				subMenu(44, "CADASTRA VEICULO");

				System.out.print("Digite a placa:");
				String placa = in.nextLine();

				System.out.print("Digite o ano:");
				String ano = in.nextLine();

				System.out.print("Digite o modelo:");
				String modelo = in.nextLine();

				System.out.print("Digite a cnh do condutor:");
				int cnhCondutor = in.nextInt();

				try {

					Veiculo veiculo = new Veiculo(placa, ano, modelo);

					veiculoDao.cadastraVeiculo(veiculo, cnhCondutor);

					System.out.printf("\n Veiculo cadastrado com sucesso. \n ");

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 2:

				in.nextLine();
				subMenu(44, "REMOVE UM VEICULO");

				System.out.print("Digite a placa do veiculo:");
				String placaRemover = in.nextLine();

				try {

					veiculoDao.removeVeiculo(placaRemover);
					System.out.printf("\n Veiculo removido com sucesso. \n ");

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 3:

				in.nextLine();
				subMenu(44, "CONSULTA VEICULO");

				System.out.print("Digite a placa do veiculo:");
				String placaConsulta = in.nextLine();

				try {

					System.out.println(veiculoDao.consultaVeiculo(placaConsulta));

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 4:

				List<Veiculo> veiculos = veiculoDao.listaTodosVeiculos();
				int contadorVeiculo = 1;

				for (Veiculo veiculo : veiculos) {

					System.out.println(contadorVeiculo + "- " + veiculo);

				}

				break;

			case 5:

				in.nextLine();
				subMenu(44, "TRANSFERE VEICULO");

				System.out.print("Digite o número da placa:");
				String placaTransfere = in.nextLine();

				System.out.print("Digite o número da cnh do dono atual:");
				int cnhDonoAtual = in.nextInt();

				System.out.print("Digite o número da cnh do novo dono:");
				int cnhNovoDono = in.nextInt();

				try {
					veiculoDao.transfereVeiculo(placaTransfere, cnhNovoDono, cnhDonoAtual);
					System.out.println("Trasferencia realizada com sucesso");

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 0:

				System.out.printf("\n Voltando ao menu anteior <----\n");
				break;

			default:

				System.out.printf("\n Valor digitado invalido.\n");
				break;
			}

		} while (opcaoVeiculo != 0);

	}

	public static void menuMulta(Scanner in, MultaDAO multaDao) {

		int opcaoMulta = 0;
		String[] itensMenu = { "Cadastro", "Removação", "Consulta", "Listar todos Multas", "Voltar ao menu anterior" };

		do {

			criaMenu("GERENCIAMENTO MULTAS", 44, itensMenu, 0);

			System.out.print("Digite a opção desejada : ");
			opcaoMulta = in.nextInt();

			switch (opcaoMulta) {

			case 1:

				subMenu(44, "APLICA MULTA");

				System.out.print("Digite o valor:");
				float valorMulta = in.nextFloat();

				System.out.print("Digite o número de pontos:");
				int pontos = in.nextInt();

				in.nextLine();
				System.out.print("Digite a placa do veiculo:");
				String placa = in.nextLine();

				Multa multa = new Multa(valorMulta, pontos);

				try {

					multaDao.cadastraMulta(multa, placa);
					System.out.println("Multa aplicada com sucesso.");

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 2:

				subMenu(44, "REMOVE MULTA");

				System.out.print("Digite o código da multa:");
				int codigoMulta = in.nextInt();

				try {

					multaDao.removeMulta(codigoMulta);
					System.out.printf("\n Multa removida com sucesso \n");

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 3:

				subMenu(44, "PESQUISA MULTA");

				System.out.print("Digite o código da multa:");
				int codigoMultaPesquisa = in.nextInt();

				try {

					System.out.println(multaDao.consultaMultaCodigo(codigoMultaPesquisa));

				} catch (MultaException e) {

					System.out.println("Não foi possivel realizar a operação : " + e.getMenssagem() + " ! ");

				}

				break;

			case 4:

				subMenu(44, "LISTA MULTA");
				int contadorLista = 1;

				List<Multa> multas = multaDao.listaMultas();

				for (Multa multaLista : multas) {

					System.out.println(contadorLista + "- " + multaLista);
					contadorLista++;
				}

				break;

			case 0:

				System.out.printf("\n Voltando ao menu anteior <----\n");
				break;

			default:

				System.out.printf("\n Valor digitado invalido.\n");
				break;
			}

		} while (opcaoMulta != 0);

	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		CondutorDAO condutorDao = new CondutorDAO();
		VeiculoDAO veiculoDao = new VeiculoDAO(condutorDao);
		MultaDAO multaDao = new MultaDAO(veiculoDao);

		int opcaoPrincipal = 0;
		String[] itensMenu = { "Condutor", "Veiculo", "Multa", "Sair"};
		do {
			
			criaMenu("MENU PRINCIPAL", 44, itensMenu, 0);

			System.out.print("Digite a opção desejada : ");
			opcaoPrincipal = in.nextInt();

			switch (opcaoPrincipal) {
			
			case 1:
				
				menuCondutor(in, condutorDao);
				break;
				
			case 2:
				
				menuVeiculo(in, veiculoDao);
				break;
				
			case 3:
				
                menuMulta(in, multaDao);
				break;
				
				
			case 0:
				
				System.out.printf("\n Sistema finalizado. \n");
				break;

			default:
				
				System.out.printf("\n Valor digitado invalido.\n");
				break;
				
			}

		} while (opcaoPrincipal != 0);

		// menuCondutor(in, condutorDao);

	}

}