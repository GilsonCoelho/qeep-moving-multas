package br.com.qm.multas.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Veiculo {
	
	@Id
	private String placa;
	
	@Column(nullable = false)
	private String ano;
	
	@Column(nullable = false)
	private String modelo;
	
	@ManyToOne
	@JoinColumn(name = "numero_cnh_dono_fk", referencedColumnName = "numero_cnh")
	private Condutor condutor;
	
	@OneToMany(mappedBy = "veiculo")
	private List<Multa> multas;
	
	
	public Veiculo() {
		
	}

	

	
	
	public Veiculo(String placa, String ano, String modelo) {
		super();
		this.placa = placa;
		this.ano = ano;
		this.modelo = modelo;
	}

	@Override
	public String toString() {
		return "Veiculo [placa=" + placa + ", ano=" + ano + ", modelo=" + modelo + ", condutor=" + condutor
				+ ", multas=" + multas + "]";
	}

	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Condutor getCondutor() {
		return condutor;
	}

	public void setCondutor(Condutor condutor) {
		this.condutor = condutor;
	}

	public List<Multa> getMultas() {
		return multas;
	}

	public void setMultas(List<Multa> multas) {
		this.multas = multas;
	}
		
}
