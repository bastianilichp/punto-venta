package cl.puntoventa.app.dao;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public abstract class AbstractDaoImpl<T> implements AppDao<T> {

    private static final Logger LOG = Logger.getLogger(AbstractDaoImpl.class.getName());

    private final Class<T> entityClass;

    @PersistenceContext(unitName = "PUNTO")
    public EntityManager entityManager;

    public AbstractDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public abstract List<T> findAll();

    @Override
    public abstract List<T> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);

    @Override
    public abstract Long count(Map<String, FilterMeta> filterBy);

    public boolean create(T entity) {

        boolean flag = false;

        try {

            this.entityManager.persist(entity);

            flag = true;

        } catch (Exception ex) {

            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);

        }

        return flag;

    }

    public boolean update(T entity) {

        boolean flag = false;

        try {

            this.entityManager.merge(entity);

            flag = true;

        } catch (Exception ex) {

            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);

        }

        return flag;

    }

    public boolean delete(T entity) {

        boolean flag = false;

        try {

            this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));

            flag = true;

        } catch (Exception ex) {

            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);

        }

        return flag;
    }

    public T findOneById(Object id) {

        Object obj = null;

        try {

            if (id != null){
                
                obj = this.entityManager.find(entityClass, id);
            }
            

        } catch (Exception ex) {

            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);

        }

        return (obj == null) ? null : this.entityClass.cast(obj);

    }

    @Override
    public boolean executeSQL(String hql) {

        boolean flag = false;

        try {

            this.entityManager.createNativeQuery(hql).executeUpdate();

            flag = true;

        } catch (Exception ex) {

            this.rollbackOperation(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), ex);

        }

        return flag;
    }

    protected void rollbackOperation(String nombreClase, String nombreMethod, Exception ex) {

        LOG.log(Level.INFO, "|| Error Transaction || Class => {0} || Method =>  {1} || Message => {2}", new Object[]{nombreClase, nombreMethod, ex.getMessage()});
    }

}
