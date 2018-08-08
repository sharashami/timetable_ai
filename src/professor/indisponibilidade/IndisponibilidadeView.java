package professor.indisponibilidade;

import semana.DiaDaSemana;
import semana.Turno;


public class IndisponibilidadeView {
	
	private String periodo;

	private int periodoValor;
	private boolean indisponivel;
	private Turno turno;
	private DiaDaSemana dia;
	
	public boolean isIndisponivel() {
		return indisponivel;
	}
	public void setIndisponivel(boolean indisponivel) {
		this.indisponivel = indisponivel;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public DiaDaSemana getDia() {
		return dia;
	}
	public void setDia(DiaDaSemana dia) {
		this.dia = dia;
	}
	public int getPeriodoValor() {
		return periodoValor;
	}
	public void setPeriodoValor(int periodoValor) {
		this.periodoValor = periodoValor;
	}
	
}
