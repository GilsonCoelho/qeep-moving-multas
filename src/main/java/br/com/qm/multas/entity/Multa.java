package br.com.qm.multas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class Multa {
	
	@Id
	@Column(name = "codigo_multa")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigoMulta;
	
	@Column(nullable = false)
	private float valor;
	
	@Column(nullable = false)
	private int pontuacao;
	
	@Column(columnDefinition = "boolean default false" )
	private boolean pago;
	
	@ManyToOne
	@JoinColumn(name = "codigo_veiculo_fk", referencedColumnName = "placa")
	private Veiculo veiculo;
	
	public Multa() {
		
	}

	public Multa(float valor, int pontuacao) {
		
		this.valor = valor;
		this.pontuacao = pontuacao;
		
	}

	
	
	@Override
	public String toString() {
		return "Multa [codigoMulta=" + codigoMulta + ", valor=" + valor + ", pontuacao=" + pontuacao + ", pago=" + pago
				+ ", veiculo=" + veiculo + "]";
	}

	public int getCodigoMulta() {
		return codigoMulta;
	}

	public void setCodigoMulta(int codigoMulsta) {
		this.codigoMulta = codigoMulsta;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}
}