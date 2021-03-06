package com.gnt.movies.beans;

import java.util.ArrayList;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gnt.movies.dao.DataProviderHolder;
import com.gnt.movies.dao.GenreDao;
import com.gnt.movies.dao.JpaDao;
import com.gnt.movies.entities.Genre;
import com.gnt.movies.utilities.Logger;
import com.gnt.movies.utilities.LoggerFactory;

@Stateless
@LocalBean
public class GenreBean implements DataProviderHolder {
	private static final Logger logger = LoggerFactory.getLogger(GenreBean.class);
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

	public Genre findGenreByName(String name) {
		return genreDao.findGenreByName(this, name);
	}

	public ArrayList<Genre> getAllGenres() {
		return genreDao.findAllGenres(this);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void addGenres(Set<Genre> genres) {
		Genre g;
		for (Genre genre : genres) {
			g = findGenreByName(genre.getName());
			if (g == null) {
				addGenre(genre);
			}
		}
	}
	
	private synchronized void addGenre(Genre genre) {
		logger.info("addGenre genre with name=" + genre.getName());
		genreDao.createGenre(this, genre);
	}
}
