package com.gnt.movies.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gnt.movies.dao.DataProviderHolder;
import com.gnt.movies.dao.GenreDao;
import com.gnt.movies.dao.JpaDao;

/**
 * Session Bean implementation class GenreBean
 */
@Stateless
@LocalBean
public class GenreBean implements DataProviderHolder {
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	@JpaDao
	@Named("GenreDaoImpl")
	GenreDao genreDao;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
    public GenreBean() {
        
    }

}
