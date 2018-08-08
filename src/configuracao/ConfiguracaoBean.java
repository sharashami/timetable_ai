package configuracao;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class ConfiguracaoBean implements Serializable{
	
	private transient ConfiguracaoRepository repository = new ConfiguracaoRepository(getManager());
	public enum CONFIG_CHAVES{
		NUMERO_DIAS,
		AG_TAMANHO_POPULACAO,
		AG_SLOTS_POR_SEMESTRE,
		AG_CREDITOS_POR_SLOT,
		AG_TAXA_MUTACAO,
		AG_TAXA_CROSSOVER
	}
	
	private static final long serialVersionUID = 1L;

	private EntityManager getManager() {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
	    return (EntityManager) request.getAttribute("entityManager");
	}	

	public int numeroDias(){
		
		Configuracao conf = repository.getConfiguracao(CONFIG_CHAVES.NUMERO_DIAS);
		return (conf != null)?Integer.valueOf(conf.getValor()):0;
	}

	public int agTamanhoPopulacao(){
		
		Configuracao conf = repository.getConfiguracao(CONFIG_CHAVES.AG_TAMANHO_POPULACAO);
		return (conf != null)?Integer.valueOf(conf.getValor()):0;
	}

	public int agCreditosPorSlot(){
		
		Configuracao conf = repository.getConfiguracao(CONFIG_CHAVES.AG_CREDITOS_POR_SLOT);
		return (conf != null)?Integer.valueOf(conf.getValor()):0;
	}

	public int agSlotsPorSemestre(){
		
		Configuracao conf = repository.getConfiguracao(CONFIG_CHAVES.AG_SLOTS_POR_SEMESTRE);
		return (conf != null)?Integer.valueOf(conf.getValor()):0;
	}

	public double agTaxaCrossover(){
		
		Configuracao conf = repository.getConfiguracao(CONFIG_CHAVES.AG_TAXA_CROSSOVER);
		return (conf != null)?Double.valueOf(conf.getValor()):0;
	}
	public int agTaxaMutacao(){
		
		Configuracao conf = repository.getConfiguracao(CONFIG_CHAVES.AG_TAXA_MUTACAO);
		return (conf != null)?Integer.valueOf(conf.getValor()):0;
	}
}
