package persistencia;

import javax.persistence.EntityManager;
import java.util.List;
import modelo.Entidade;

/**
 *
 * @author andre.luchesi
 */
public abstract class DAO {
    
    private final EntityManager entityManager;
    protected Class classModel;
    
    public DAO(Class classModel) {
        this.classModel = classModel;
        entityManager = ConexaoBD.getConection();
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public Entidade findById(int id){
        return (Entidade) entityManager.find(classModel, id);
    }
    
    public List<Entidade> findAll(){
        return entityManager.createQuery(entityManager.getCriteriaBuilder().createQuery(classModel)).getResultList();
    }
    
    public boolean saveOrUpdate(Entidade entidade) {
        try {
            if (entidade == null) {
                throw new Exception("A entidade não pode está nula!");
            }
            entityManager.getTransaction().begin();
            if (entidade.getId() == null) {
                entityManager.persist(entidade);
            } else {
                entityManager.merge(entidade);
            }
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.err.println("Falha ao Salvar a Entidade!\n" + ex.getMessage());
            entityManager.getTransaction().rollback();
            return false;
        }
    }
    
     public boolean delete(Entidade entidade) {
         try {
            if (entidade == null) {
                throw new Exception("A entidade não pode está nula!");
            }
            entityManager.getTransaction().begin();
            entityManager.remove(entidade);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.err.println("Falha ao Remover Entidade!\n" + ex.getMessage());
            entityManager.getTransaction().rollback();
            return false;
        }
    }
    public void deleteById( int id) {
        delete(findById(id));
    }
}
