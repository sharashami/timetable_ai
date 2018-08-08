package configuracao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import utils.Definicoes;

public class ConfiguracaoRepository {
	private EntityManager entityManager;

	public ConfiguracaoRepository(EntityManager entityManager){
		this.entityManager = entityManager;
	}

	public Configuracao getConfiguracao(configuracao.ConfiguracaoBean.CONFIG_CHAVES nome){
		Configuracao conf = null;
		try {
			conf =  (Configuracao) this.entityManager.createNamedQuery(Definicoes.CONFIGURACAO_GET).setParameter("nome", nome.name()).getSingleResult();
		}catch(NoResultException e){conf = null;}
		
		return conf;
	}
}
