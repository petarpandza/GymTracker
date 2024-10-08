package hr.fer.gymtracker.dao.jpa;

import hr.fer.gymtracker.dao.DAOException;
import jakarta.persistence.EntityManager;

public class JPAEMProvider {

    private static final ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	// TODO popravit
    public static EntityManager getEntityManager() {
        EntityManager em = locals.get();
        if (em == null) {
            em = JPAEMFProvider.getEmf().createEntityManager();
            em.getTransaction().begin();
            locals.set(em);
        }
        return em;
    }

    public static void close() throws DAOException {
        EntityManager em = locals.get();
        if (em == null) {
            return;
        }
        DAOException dex = null;
        try {
            em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            em.close();
        } catch (Exception ex) {
            if (dex != null) { // popraviti!
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }
        locals.remove();
        if (dex != null) throw dex;
    }

}