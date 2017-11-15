package com.gnt.movies.beans;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.ejb3.annotation.TransactionTimeout;

import com.gnt.movies.dao.DataProviderHolder;
import com.gnt.movies.theMovieDB.ApiNewMovie;
import com.gnt.movies.theMovieDB.ApiNewShow;
import com.gnt.movies.utilities.APIClient;
import com.gnt.movies.utilities.Logger;
import com.gnt.movies.utilities.LoggerFactory;

@Stateless
@LocalBean
@TransactionTimeout(value=1,unit=TimeUnit.HOURS)
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
	
	public SchedulerBean() {

	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	private static boolean flag = false;
	
	@Schedule(dayOfWeek = "*", hour = "*", minute = "*/1",persistent=false)
	public void update() {
		logger.info("Scheduler updating database!");
		APIClient.setTimer();
		getUpcomingMovies();
//		getNowPlayingMovies();
//		getOnTheAirShows();
//		getAir2dayShows();
		APIClient.unsetTimer();
		logger.info("Scheduler finished updating database!");
	}
	
	int i = 1;
	private void getUpcomingMovies() {
		if(flag)
			return;
		flag=true;
		
		logger.info("Scheduler checking for upcomming movies");
		upcomingMovieBean.findAllIdTmdb();
//		ArrayList<ApiNewMovie> upcomingMoviesAPI = apiClient.getUpcomingMoviesFromAPI();
		ArrayList<ApiNewMovie> upcomingMoviesAPI = APIClient.getUpcomingMovies();
		
//		for (ApiNewMovie upcomingMovieAPI : upcomingMoviesAPI) {
////			if(i==10) {
//////				break;
////			}
////			i++;
//			upcomingMovieBean.checkUpcomingMovie(upcomingMovieAPI);
////			
//		}
		upcomingMoviesAPI.stream().parallel().forEach(e->upcomingMovieBean.checkUpcomingMovie(e));
		upcomingMovieBean.removeOldNotUpMovies(upcomingMoviesAPI);
		logger.info("Done checking for upcomming movies");
		
		flag = false;
	}
	
	private void  getNowPlayingMovies() {
		if(flag)
			return;
		flag=true;
		logger.info("Scheduler checking for now playing movies");
		nowPlayingMovieBean.findAllIdTmdb();
		ArrayList<ApiNewMovie> nowPlayingMoviesAPI = APIClient.getNowPlayingMovies();
		i=1;
		for(ApiNewMovie newMovieApi : nowPlayingMoviesAPI) {
//			if(i==10) {
//				break;
//			}
//			i++;
			nowPlayingMovieBean.checkNowPlayingMovie(newMovieApi);
		}
		nowPlayingMovieBean.removeOldNotNowPlayingMovies(nowPlayingMoviesAPI);
		logger.info("Done checking for now playing movies");
		
		flag = false;
	}
	
	private void getOnTheAirShows() {
		if(flag)
			return;
		flag=true;
		logger.info("Scheduler checking for on the air shows");
		onTheAirShowBean.findAllIdTmdb();
		ArrayList<ApiNewShow> onTheAirShowsAPI = APIClient.getOnTheAirShows();
		i=1;
		for(ApiNewShow newShowApi : onTheAirShowsAPI) {
//			if(i==10) {
//				break;
//			}
//			i++;
			onTheAirShowBean.checkOnTheAirShow(newShowApi);
		}
		onTheAirShowBean.removeOldNotOnTheAirShows(onTheAirShowsAPI);
		logger.info("Done checking for on the air shows");
		flag = false;
	}
	
	private void getAir2dayShows() {
		if(flag)
			return;
		flag=true;
		logger.info("Scheduler checking for air today shows");
		air2dayShowBean.findAllIdTmdb();
		ArrayList<ApiNewShow> air2dayShowsAPI = APIClient.getAir2dayShows();
		i=1;
		for(ApiNewShow newShowApi : air2dayShowsAPI) {
//			if(i==10) {
//				break;
//			}
//			i++;
			air2dayShowBean.checkAir2dayShow(newShowApi);
		}
		air2dayShowBean.removeOldNotAir2dayShow(air2dayShowsAPI);
		logger.info("Done checking for air2day shows");
		flag = false;
	}
}
