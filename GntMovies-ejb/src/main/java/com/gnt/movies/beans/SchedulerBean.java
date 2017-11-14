package com.gnt.movies.beans;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gnt.movies.dao.DataProviderHolder;
import com.gnt.movies.theMovieDB.ApiNewMovie;
import com.gnt.movies.utilities.APIClient;
import com.gnt.movies.utilities.Logger;
import com.gnt.movies.utilities.LoggerFactory;

@Stateless
@LocalBean
public class SchedulerBean implements DataProviderHolder {
	private static final Logger logger = LoggerFactory.getLogger(SchedulerBean.class);
	@PersistenceContext
	private EntityManager em;

	@EJB
	private UpcomingMovieBean upcomingMovieBean;

	@EJB
	private NowPlayingMovieBean nowPlayingMovieBean;

	@EJB
	private OnTheAirShowBean onTheAirShowBean;

	@EJB
	private Air2dayShowBean air2dayShowBean;

	APIClient apiClient = new APIClient();
	
	public SchedulerBean() {

	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	private static boolean flag = false;
	
	@Schedule(dayOfWeek = "*", hour = "*", minute = "*/1",second="*",persistent=true)
	public void update() {
		getUpcomingMovies();
		getNowPlayingMovies();
		getAir2dayShows();
		getOnTheAirShows();
	}
	int i = 1;
	public void getUpcomingMovies() {
		if(flag)
			return;
		flag=true;
		
		logger.info("Scheduler checking for upcomming movies");
		upcomingMovieBean.findAllIdTmdb();
		ArrayList<ApiNewMovie> upcomingMoviesAPI = apiClient.getUpcomingMoviesFromAPI();
		
		for (ApiNewMovie upcomingMovieAPI : upcomingMoviesAPI) {
			if(i==10) {
				break;
			}
			i++;
			upcomingMovieBean.checkUpcomingMovie(upcomingMovieAPI);
		}
		upcomingMovieBean.removeOldNotUpMovies(upcomingMoviesAPI);
		logger.info("Done checking for upcomming movies");
		
		flag = false;
	}
	
	public void  getNowPlayingMovies() {
		if(flag)
			return;
		flag=true;
		logger.info("Scheduler checking for now playing movies");
		nowPlayingMovieBean.findAllIdTmdb();
		ArrayList<ApiNewMovie> nowPlayingMoviesAPI = apiClient.getNowPlayingMoviesFromAPI();
		i=1;
		for(ApiNewMovie newMovieApi : nowPlayingMoviesAPI) {
			if(i==10) {
				break;
			}
			i++;
			nowPlayingMovieBean.checkNowPlayingMovie(newMovieApi);
		}
		nowPlayingMovieBean.removeOldNotNowPlayingMovies(nowPlayingMoviesAPI);
		logger.info("Done checking for now playing movies");
		
		flag = false;
	}
	
	public void getOnTheAirShows() {
		
	}
	
	public void getAir2dayShows() {
		
	}
}
