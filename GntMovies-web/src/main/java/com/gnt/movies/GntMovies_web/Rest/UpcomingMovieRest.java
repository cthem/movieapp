package com.gnt.movies.GntMovies_web.Rest;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.gnt.movies.beans.UpcomingMovieBean;
import com.gnt.movies.dto.MovieListItemDto;

@Path("/upcoming")
public class UpcomingMovieRest {

	@Inject
	private UpcomingMovieBean upcomingMovieBean;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<MovieListItemDto> getUpcomingMovies(@HeaderParam("token") String token) {
		return upcomingMovieBean.getAllUpcomingMovies();
	}
}
