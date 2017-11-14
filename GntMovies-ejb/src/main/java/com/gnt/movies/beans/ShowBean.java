package com.gnt.movies.beans;

import java.time.LocalDate;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gnt.movies.dao.DataProviderHolder;
import com.gnt.movies.dao.JpaDao;
import com.gnt.movies.dao.ShowDao;
import com.gnt.movies.entities.Show;
import com.gnt.movies.entities.ShowGenre;
import com.gnt.movies.theMovieDB.ApiNewShow;
import com.gnt.movies.theMovieDB.ApiShowDetails;
import com.gnt.movies.utilities.APIClient;
import com.gnt.movies.utilities.Logger;
import com.gnt.movies.utilities.LoggerFactory;

/**
 * Session Bean implementation class ShowBean
 */
@Stateless
@LocalBean
public class ShowBean implements DataProviderHolder{
	private static final Logger logger = LoggerFactory.getLogger(ShowBean.class);	


	@PersistenceContext
	private EntityManager em;
	
	@Inject
	@JpaDao
	@Named("ShowDaoImpl")
	ShowDao showDao;
	
	@EJB
	GenreBean genreBean;
	
	@EJB
	ShowGenreBean showGenreBean;
	
	APIClient apiClient = new APIClient();
	
    public ShowBean() {
    }
    
    @Override
	public EntityManager getEntityManager() {
		return em;
	}
    
    public Show createShowFromAPI(ApiNewShow apiNewShow) {
    	
    	return new Show(LocalDate.parse(apiNewShow.getFirstAirDate()), apiNewShow.getId(), apiNewShow.getName(), apiNewShow.getOriginalLanguage(),
    			apiNewShow.getOriginalName(), apiNewShow.getOriginCountry().toString(), apiNewShow.getOverview(), apiNewShow.getVoteAverage(),
    			apiNewShow.getVoteCount());
    }

    public void updateShowWithDetails(Show show, ApiShowDetails showDetails) {
    	show.setCreatedBy(showDetails.getCreatedBy().toString());
    	show.setHomepage(showDetails.getHomepage());
    	
    	byte inProduction;
    	if(showDetails.isInProduction()) inProduction=1;
    	else inProduction=0;
    	show.setInProduction(inProduction);
    	
    	show.setLastAirDate(LocalDate.parse(showDetails.getLastAirDate()));
    	show.setNetworks(showDetails.getNetworks().toString());
    	show.setNumOfEpisodes(showDetails.getEpisodesNum());
    	show.setNumOfSeasons(showDetails.getEpisodesNum());
    	
    	show.setProductionCountries(showDetails.getProductionCountriesAPI().toString());
    	show.setStatus(showDetails.getStatus());
    	show.setType(showDetails.getType());
	}
    
    public Show addNewShow(ApiNewShow showApi) {
		logger.info("addNewMovieWithGenres movie with tmdbId=" + showApi.getId());
		Show show = createShowFromAPI(showApi);
		
		ApiShowDetails showDetails = apiClient.getShowDetailsFromAPI(show.getIdTmdb());
		genreBean.updateGenres(showDetails.getGenresAPI());
		
		updateShowWithDetails(show, showDetails);
		addShow(show);
		for (ShowGenre showGenre : show.getShowGenres()) {
			showGenreBean.addShowGenre(showGenre);
		}
		return show;
    }
    
    public Show getShow(ApiNewShow apiNewShow) {
    	
    	Show show = findShowByIdTmdb(apiNewShow.getId());
    	
    	if (show == null)
    		show = addNewShow(apiNewShow);
    	
    	return show;
    }
    
    public void addShow(Show show) {
    	logger.info("addShow show with tmdbId="+show.getIdTmdb());
    	showDao.createShow(this, show);
    }
    
    public Show findShowByIdTmdb(int idTmdb) {
    	return showDao.findShowByIdTmdb(this, idTmdb);
    }
    
    public Show findShowByName(String name) {
    	return showDao.findShowByName(this, name);
    }
    
    public Show findShowById(int id) {
    	return showDao.findShowById(this, id);
    }
}
