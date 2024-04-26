package br.com.webinffo.gerenciamento.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	@Column(nullable = false)
	private String logradouro;
	
	@NotBlank
	@Size(min = 3, max = 255)
	@Column(nullable = false)
	private String bairro;
	
	@NotBlank
	@Size(min = 3, max = 255)
	@Column(nullable = false)
	private String cidade;
	
	@NotNull(message = "Selecione uma UF")
	@Column(nullable = false, length = 2)
	@Enumerated(EnumType.STRING)
	private UF uf;
	
	@NotBlank
	@Size(min = 9, max = 9, message = "Deve conter {max} caracteres.")
	@Column(nullable = false, length = 9)
	private String cep;
	
	@NotNull(message = "Endereço não pode ser nulo")
	@Digits(integer = 5, fraction = 0)
	@Column(nullable = false, length = 5)
	private Integer numero;
	
	@Size(max = 255)
	private String complemento;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
