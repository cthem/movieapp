package com.gnt.movies.utilities;

import java.util.ArrayList;
import java.util.HashSet;

import com.gnt.movies.entities.Genre;
import com.gnt.movies.theMovieDB.ApiGenres;
import com.gnt.movies.theMovieDB.ApiMovieDetails;
import com.gnt.movies.theMovieDB.ApiNewMovie;
import com.gnt.movies.theMovieDB.ApiNewMovieResults;
import com.gnt.movies.theMovieDB.ApiNewShow;
import com.gnt.movies.theMovieDB.ApiNewShowResults;
import com.gnt.movies.theMovieDB.ApiShowDetails;
import com.google.gson.Gson;

public class ApiCalls {
	
	private static ArrayList<ApiClientRunnable> runnables;
	private static ArrayList<Thread> threads;
	
	/**
	 * Get new Movies and Shows from API using threads for getting all pages from API
	 * ==============================================================================
	 **/
	public static HashSet<Genre> getGenres() {
		HashSet<Genre> set = new HashSet<>();
		
		set.addAll(getMovieGenres());
		set.addAll(getShowGenres());
		return set;
	}
	
	private static ArrayList<Genre> getMovieGenres() {
		StringBuilder sb = new StringBuilder(Utils.MOVIE_GENRES).append(Utils.API_KEY).append(Utils.LANGUAGE_FOR_URL);
		String result = ApiClient.getResultFromTMDB(sb.toString());
		ApiGenres apiGenres = new Gson().fromJson(result, ApiGenres.class);
		return apiGenres.getGenres();
	}

	private static ArrayList<Genre> getShowGenres() {
		StringBuilder sb = new StringBuilder(Utils.SHOW_GENRES).append(Utils.API_KEY).append(Utils.LANGUAGE_FOR_URL);
		String result = ApiClient.getResultFromTMDB(sb.toString());
		ApiGenres apiGenres = new Gson().fromJson(result, ApiGenres.class);
		return apiGenres.getGenres();
	}
	
	public static HashSet<ApiNewMovie> getUpcomingMovies() {

		StringBuilder sb = new StringBuilder(Utils.UPCOMING_MOVIES_URL).append(Utils.API_KEY)
				.append(Utils.LANGUAGE_FOR_URL).append(Utils.NUMBER_PAGE_FOR_URL);

		return (HashSet<ApiNewMovie>) getAllResults(sb.toString(), "movie");
	}

	public static HashSet<ApiNewMovie> getNowPlayingMovies() {

		StringBuilder sb = new StringBuilder(Utils.NOW_PLAYING_MOVIES_URL).append(Utils.API_KEY)
				.append(Utils.LANGUAGE_FOR_URL).append(Utils.NUMBER_PAGE_FOR_URL);

		return (HashSet<ApiNewMovie>) getAllResults(sb.toString(), "movie");
	}

	public static HashSet<ApiNewShow> getOnTheAirShows() {

		StringBuilder sb = new StringBuilder(Utils.ON_THE_AIR_SHOWS_URL).append(Utils.API_KEY)
				.append(Utils.LANGUAGE_FOR_URL).append(Utils.NUMBER_PAGE_FOR_URL);

		return (HashSet<ApiNewShow>) getAllResults(sb.toString(), "show");
	}

	public static HashSet<ApiNewShow> getAir2dayShows() {

		StringBuilder sb = new StringBuilder(Utils.AIR2DAY_SHOWS_URL).append(Utils.API_KEY)
				.append(Utils.LANGUAGE_FOR_URL).append(Utils.NUMBER_PAGE_FOR_URL);

		return (HashSet<ApiNewShow>) getAllResults(sb.toString(), "show");
	}
	
	public static ApiMovieDetails getMovieDetailsFromAPI(int id) {
		StringBuilder url = new StringBuilder(Utils.GENERAL_MOVIE_URL).append(Integer.toString(id))
				.append(Utils.API_KEY).append(Utils.IMAGES_URL).append(Utils.CREW_CAST_URL);
		return new Gson().fromJson(ApiClient.getResultFromTMDB(url.toString()), ApiMovieDetails.class);
	}

	public static ApiShowDetails getShowDetailsFromAPI(int id) {
		StringBuilder url = new StringBuilder(Utils.GENERAL_SHOW_URL).append(Integer.toString(id)).append(Utils.API_KEY)
				.append(Utils.IMAGES_URL).append(Utils.CREW_CAST_URL);
		return new Gson().fromJson(ApiClient.getResultFromTMDB(url.toString()), ApiShowDetails.class);
	}

	private static HashSet<?> getAllResults(String url, String type) {
		ApiClientRunnable runnable = firstThreadRun(url);
		int pages = getTotalNumPages(type, runnable);
		runRemainingThreads(url,pages);
		return getResultsFromPages(type);
	}
	
	private static int getTotalNumPages(String type, ApiClientRunnable runnable) {
		int pages = 0;
		if (type == "movie") {
			pages = new Gson().fromJson(runnable.getResult(), ApiNewMovieResults.class).getTotalPages();
		} else if (type == "show") {
			pages = new Gson().fromJson(runnable.getResult(), ApiNewShowResults.class).getTotalPages();
		}
		return pages;
	}
	
	private static ApiClientRunnable firstThreadRun(String url) {
		runnables = new ArrayList<>();
		threads = new ArrayList<>();

		StringBuilder sb = new StringBuilder(url).append("1");
		ApiClientRunnable runnable = new ApiClientRunnable(sb.toString());
		Thread thread = new Thread(runnable);
		
		runnables.add(runnable);
		threads.add(thread);

		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return runnable;
	}
	
	private static void runRemainingThreads(String url, int pages) {
		for (int page = 2; page <= pages; page++) {
			StringBuilder sb = new StringBuilder(url).append(page);
			ApiClientRunnable runnable = new ApiClientRunnable(sb.toString());
			runnables.add(runnable);
			Thread thread = new Thread(runnable);
			threads.add(thread);
		}
		for(int i=1; i<threads.size();i++) {
			threads.get(i).start();
		}
		for (int i = 1; i < threads.size(); i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static HashSet<?>getResultsFromPages(String type){
		if (type == "movie") {
			HashSet<ApiNewMovie> movies = new HashSet<>();
			runnables.stream().forEach(apiClientRunnable->movies.addAll(new Gson().fromJson(apiClientRunnable.getResult(), ApiNewMovieResults.class)
					.getResults()));
			return movies;
		} else {
			HashSet<ApiNewShow> shows = new HashSet<>();
			runnables.stream().forEach(apiClientRunnable->shows.addAll(
					new Gson().fromJson(apiClientRunnable.getResult(), ApiNewShowResults.class).getResults()));
			return shows;
		}
	}
}
