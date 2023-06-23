package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ConexaoBD {
    private static EntityManagerFactory factory;
    private static EntityManager entidadeConexao;
    
    private static void criarConexao(){
        factory = Persistence.createEntityManagerFactory("AulaValidacaoPU");
        entidadeConexao = factory.createEntityManager();        
    }
    
    public static EntityManager getConection(){
        if(entidadeConexao == null || !entidadeConexao.isOpen()){
            criarConexao();
        }
        return entidadeConexao;
    }
}
