package br.com.qm.multas.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Condutor {
	
	@Id
	@Column (name = "numero_cnh")
	private int numeroCnh;
	
	@Column (name = "data_emissao_cnh", nullable = false)
	private LocalDate dataEmissaoCnh;
	
	@Column (name = "orgao_emissor_cnh",nullable = false )
	private String orgaoEmissorCnh;
	
	@Column (name = "pontuacao_cnh")
	private int pontuacaoCnh;
	
	private boolean suspensa = false;

	@OneToMany(mappedBy = "condutor")
	private List<Veiculo> veiculo;
	
	public Condutor() {
		
	}

	public Condutor(int numeroCnh, LocalDate dataEmissaoCnh, String orgaoEmissorCnh, int pontuacaoCnh) {
		
		this.numeroCnh = numeroCnh;
		this.dataEmissaoCnh = dataEmissaoCnh;
		this.orgaoEmissorCnh = orgaoEmissorCnh;
		this.pontuacaoCnh = pontuacaoCnh;
	}
	

	
	
	@Override
	public String toString() {
		return "Condutor [numeroCnh=" + numeroCnh + ", dataEmissaoCnh=" + dataEmissaoCnh + ", orgaoEmissorCnh="
				+ orgaoEmissorCnh + ", pontuacaoCnh=" + pontuacaoCnh + ", suspensa=" + suspensa + "]";
	}

	public int getNumeroCnh() {
		return numeroCnh;
	}


	public void setNumeroCnh(int numeroCnh) {
		this.numeroCnh = numeroCnh;
	}


	public LocalDate getDataEmissaoCnh() {
		return dataEmissaoCnh;
	}


	public void setDataEmissaoCnh(LocalDate dataEmissaoCnh) {
		this.dataEmissaoCnh = dataEmissaoCnh;
	}


	public String getOrgaoEmissorCnh() {
		return orgaoEmissorCnh;
	}


	public void setOrgaoEmissorCnh(String orgaoEmissorCnh) {
		this.orgaoEmissorCnh = orgaoEmissorCnh;
	}


	public int getPontuacaoCnh() {
		return pontuacaoCnh;
	}


	public void setPontuacaoCnh(int pontuacaoCnh) {
		this.pontuacaoCnh = pontuacaoCnh;
	}


	public List<Veiculo> getVeiculo() {
		return veiculo;
	}


	public void setVeiculo(List<Veiculo> veiculo) {
		this.veiculo = veiculo;
	}

	public boolean isSuspensa() {
		return suspensa;
	}

	public void setSuspensa(boolean suspensa) {
		this.suspensa = suspensa;
	}

}