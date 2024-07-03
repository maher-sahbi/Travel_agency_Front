package dao;

import java.util.List;

/**
 
 * 
 * @author Eric Sultan
 * @param <BO>
 *          
 * @param <PK>
 *        
 */
public interface Dao<BO, PK>
{

    /**
     
     * 
     * @param id
     *           
     * @return 
     */
    BO findById(PK id);

    /**
     
     * 
     * @return 
     */
    List<BO> findAll();

    /**
    
     * 
     * @param obj
     *        
     */
    void create(BO obj);

    /**
     
     * 
     * @param obj
     *            
     * @return L'objet métier mis à jour
     */
    BO update(BO obj);

    /**
     
     * 
     * @param obj
     *   
     */
    void delete(BO obj);
}
