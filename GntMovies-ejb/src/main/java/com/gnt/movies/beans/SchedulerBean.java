package com.gnt.movies.beans;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gnt.movies.dao.DataProviderHolder;
import com.gnt.movies.entities.Genre;
import com.gnt.movies.entities.Movie;
import com.gnt.movies.entities.Show;
import com.gnt.movies.utilities.ApiCalls;
import com.gnt.movies.utilities.ApiClient;
import com.gnt.movies.utilities.Logger;
import com.gnt.movies.utilities.LoggerFactory;
import com.gnt.movies.utilities.MyExecutor;

@LocalBean
@Singleton
public class SchedulerBean implements DataProviderHolder {
	private static final Logger logger = LoggerFactory.getLogger(SchedulerBean.class);

	@PersistenceContext
	private EntityManager em;

	@EJB
	private GenreBean genreBean;

	@EJB
	private UpcomingMovieBean upcomingMovieBean;

	@EJB
	private NowPlayingMovieBean nowPlayingMovieBean;

	@EJB
	private OnTheAirShowBean onTheAirShowBean;

	@EJB
	private Air2dayShowBean air2dayShowBean;

	private static boolean flag = false;

	public SchedulerBean() {

	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	private void init() {
		ApiClient.init();
		UpcomingMovieBean.init();
		NowPlayingMovieBean.init();
		Air2dayShowBean.init();
		OnTheAirShowBean.init();
		ApiClient.setTimer();
	}

//	@Schedule(dayOfWeek = "*", hour = "*", minute = "*/1", persistent = false)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	private void update() {
		if (flag)
			return;
		flag = true;
		init();
		logger.info("Scheduler updating database!");
		getGenres();
		getUpcomingMovies();
		getNowPlayingMovies();
		getOnTheAirShows();
		getAir2dayShows();
		cleanUp();
		logger.info("Scheduler finished updating database!");
		flag = false;
	}

	private void getGenres() {
		HashSet<Genre> genres = ApiCalls.getGenres();
		genreBean.addGenres(genres);
	}

	private void getUpcomingMovies() {
		upcomingMovieBean.findAllIdTmdb();
		logger.info("Scheduler checking for upcomming movies");
		HashSet<Movie> upcomingMoviesAPI = ApiCalls.getUpcomingMovies();
		ExecutorService executor = MyExecutor.getNewExecutor();
		for (Movie movie : upcomingMoviesAPI) {
			Runnable worker = () -> {upcomingMovieBean.checkUpcomingMovie(movie);};
			executor.execute(worker);
		}
		MyExecutor.terminateExecutor(executor);
		upcomingMovieBean.removeOldNotUpMovies(upcomingMoviesAPI);
		logger.info("Done checking for upcomming movies");
	}

	private void getNowPlayingMovies() {
		nowPlayingMovieBean.findAllIdTmdb();
		logger.info("Scheduler checking for now playing movies");
		HashSet<Movie> nowPlayingMoviesAPI = ApiCalls.getNowPlayingMovies();
		ExecutorService executor = MyExecutor.getNewExecutor();
		for (Movie movie : nowPlayingMoviesAPI) {
			Runnable worker = () -> {nowPlayingMovieBean.checkNowPlayingMovie(movie);};
			executor.execute(worker);
		}
		MyExecutor.terminateExecutor(executor);
		nowPlayingMovieBean.removeOldNotNowPlayingMovies(nowPlayingMoviesAPI);
		logger.info("Done checking for now playing movies");
	}

	private void getOnTheAirShows() {
		onTheAirShowBean.findAllIdTmdb();
		logger.info("Scheduler checking for on the air shows");
		HashSet<Show> onTheAirShows = ApiCalls.getOnTheAirShows();
		ExecutorService executor = MyExecutor.getNewExecutor();
		for (Show show : onTheAirShows) {
			Runnable worker = () -> {onTheAirShowBean.checkOnTheAirShow(show);};
			executor.execute(worker);
		}
		MyExecutor.terminateExecutor(executor);
		onTheAirShowBean.removeOldNotOnTheAirShows(onTheAirShows);
		logger.info("Done checking for on the air shows");
	}

	private void getAir2dayShows() {
		air2dayShowBean.findAllIdTmdb();
		logger.info("Scheduler checking for air today shows");
		HashSet<Show> air2dayShowsAPI = ApiCalls.getAir2dayShows();
		ExecutorService executor = MyExecutor.getNewExecutor();
		for (Show show : air2dayShowsAPI) {
			Runnable worker = () -> {air2dayShowBean.checkAir2dayShow(show);};
			executor.execute(worker);
		}
		MyExecutor.terminateExecutor(executor);
		air2dayShowBean.removeOldNotAir2dayShow(air2dayShowsAPI);
		logger.info("Done checking for air2day shows");
	}

	private void cleanUp() {
		ApiClient.unsetTimer();
	}
}
