package professor;

//import javax.persistence.PersistenceContextType;;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import utils.Definicoes;

import com.sun.istack.internal.NotNull;


@Entity //inidicar que sera criado uma tabela no banco
@Table(name="professor")
@NamedQueries({//nome da classe
    @NamedQuery(name=Definicoes.PROFESSOR_LISTAR,query="SELECT p FROM Professor p ORDER BY p.id"),
  //  @NamedQuery(name=Definicoes.PROFESSOR_LISTAR_POR_NOME,query="SELECT p FROM Professor p WHERE p.nome LIKE UPPER(:nome) ORDER BY p.id"),
    @NamedQuery(name=Definicoes.PROFESSOR_LISTAR_POR_NOME,query="SELECT p FROM Professor p WHERE p.nome LIKE :nome ORDER BY p.id"),
    @NamedQuery(name=Definicoes.PROFESSOR_RESETAR_CRED_LIVRE,query="UPDATE Professor p SET p.creditosLivres = p.creditosTotais WHERE p.ativo = true"),
    @NamedQuery(name = Definicoes.PROFESSOR_COUNT, query="SELECT COUNT(p) FROM Professor p"),
    @NamedQuery(name = Definicoes.PROFESSOR_GET_LOGIN, query="select p from Professor p where p.login=:login")
    //UPDATE SemestreLetivo sl SET sl.ativo = false WHERE sl.id <> :id
}) 


//columnDefinition="bigint(20) default 0" PARA COLOCAR nas colunas se precisar algo extra, utilzando isto tem que inserir o tipo do campo tb
public class Professor implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator( name = "professor_id", sequenceName = "professor_seq", allocationSize = 1 )  
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "professor_id" )
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable=false, unique=true,length=Definicoes.STRING_NOME)
	@NotNull
	private String nome;
	
	@Column(nullable=false, unique=true,length=70)
	@NotNull
	private String nomeBreve;
	
	@Column(nullable=false, columnDefinition="boolean default true")
	private boolean ativo = true;

	@Column(nullable=false, columnDefinition="integer default 0")
	private int creditosTotais;

	@Column(nullable=false, columnDefinition="integer default 0")
	private int creditosLivres;

	@Column(columnDefinition="boolean default false")
	private boolean administrador = false;

	@Column(nullable=false, unique=true,length=100)
	@NotNull
	private String login;

	@Column(nullable=false,length=64)
	private String senha;
	
	@Column(nullable=false, unique=true)
	private Long cpf;

	@Column(nullable=true, length=256)
	private String chaveEmail;
	
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
		this.nome = (nome != null)?nome.toUpperCase():nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getCreditosTotais() {
		return creditosTotais;
	}

	public void setCreditosTotais(int creditosTotais) {
		this.creditosTotais = creditosTotais;
	}

	public int getCreditosLivres() {
		return creditosLivres;
	}

	public void setCreditosLivres(int creditosLivres) {
		this.creditosLivres = creditosLivres;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long  getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}


	public String getChaveEmail() {
		return chaveEmail;
	}

	public void setChaveEmail(String chaveEmail) {
		this.chaveEmail = chaveEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (administrador ? 1231 : 1237);
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result
				+ ((chaveEmail == null) ? 0 : chaveEmail.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + creditosLivres;
		result = prime * result + creditosTotais;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		if (administrador != other.administrador)
			return false;
		if (ativo != other.ativo)
			return false;
		if (chaveEmail == null) {
			if (other.chaveEmail != null)
				return false;
		} else if (!chaveEmail.equals(other.chaveEmail))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (creditosLivres != other.creditosLivres)
			return false;
		if (creditosTotais != other.creditosTotais)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}

	public String getNomeBreve() {
		return nomeBreve;
	}

	public void setNomeBreve(String nomeBreve) {
		this.nomeBreve = (nomeBreve != null)?nomeBreve.toUpperCase():nomeBreve;
	}

	
}
