package cl.puntoventa.app.dao;



import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public interface AppDao<T> {

    public List<T> findAll();

    public List<T> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);

    public Long count(Map<String, FilterMeta> filterBy);

    public boolean executeSQL(String hql);

}
