package com.gnt.movies.beans;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gnt.movies.dao.DataProviderHolder;
import com.gnt.movies.dao.JpaDao;
import com.gnt.movies.dao.MovieReviewDao;
import com.gnt.movies.entities.Movie;
import com.gnt.movies.entities.MovieReview;
import com.gnt.movies.entities.User;

/**
 * Session Bean implementation class MovieReviewBean
 */
@Stateless
@LocalBean
public class MovieReviewBean implements DataProviderHolder{


	@PersistenceContext EntityManager em;
	
	@Inject
	@JpaDao
	@Named("MovieReviewDaoImpl")
	MovieReviewDao movieReviewDao;

    public MovieReviewBean() {

    }

	@Override
	public EntityManager getEntityManager() {

		return em;
	}
	
	public void addMovieReview(User user, Movie movie) {
		movieReviewDao.createMovieReview(this, new MovieReview(user, movie));
	}
	
	public ArrayList<MovieReview> getMovieReviewsByMovie(Movie movie){
		return (ArrayList<MovieReview>) movieReviewDao.findMovieReviewByMovieId(this, movie.getId());
	}
}