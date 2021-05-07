package br.com.ftt.ec6.seniorLiving.entities.support;

public class State {
	
	private Long id;
	private String nome;
	private Region regiao;
	private String sigla;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Region getRegiao() {
		return regiao;
	}
	public void setRegiao(Region regiao) {
		this.regiao = regiao;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
}
