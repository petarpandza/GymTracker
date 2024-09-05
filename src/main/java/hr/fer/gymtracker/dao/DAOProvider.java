package hr.fer.gymtracker.dao;

import hr.fer.gymtracker.dao.jpa.JPADAO;

/**
 * Singleton class that provides the DAO object.
 */
public class DAOProvider {

    private static final DAO dao = new JPADAO();

    public static DAO getDAO() {
        return dao;
    }

}
