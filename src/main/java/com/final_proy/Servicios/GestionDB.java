package com.final_proy.Servicios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class GestionDB<T> {

    private static EntityManagerFactory emf;
    private Class<T> claseEntidad;



    public GestionDB(Class<T> claseEntidad) {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        }
        this.claseEntidad = claseEntidad;

    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    /**
     *
     * @param entidad
     */
    public void crear(T entidad){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(entidad);
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param entidad
     */
    public void editar(T entidad){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entidad);
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param entidad
     */
    public void eliminar(T entidad){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    public void eliminarfollower(String usuarioSesion, String usuario){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.createNativeQuery("DELETE FROM USER_FOLLOWERS WHERE USERNAME=? AND FOLLOWER_ID=?").setParameter(1,usuario).setParameter(2,usuarioSesion).executeUpdate();

            //em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

     public void eliminaragcamigo(String usuarioSesion, String usuario){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.createNativeQuery("DELETE FROM USER_AGCAMIGO WHERE USERNAME=? AND AGCAMIGO_ID=?").setParameter(1,usuario).setParameter(2,usuarioSesion).executeUpdate();

            //em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    public void eliminarlikes(String usuarioSesion, long post){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.createNativeQuery("DELETE FROM LIKES WHERE USUARIO_ID=? AND POST_ID=?").setParameter(1,usuarioSesion).setParameter(2,post).executeUpdate();

            //em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public T find(Object id) {
        EntityManager em = getEntityManager();
        try{
            return em.find(claseEntidad, id);
        } catch (Exception ex){
            throw  ex;
        } finally {
            em.close();
        }
    }


    /**
     *
     * @return
     */
    public List<T> findAll(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex){
            throw  ex;
        }finally {
            em.close();
        }
    }

    public List<T> findAll(int rownums){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).setMaxResults(rownums).getResultList();
        } catch (Exception ex){
            throw  ex;
        }finally {
            em.close();
        }
    }
}