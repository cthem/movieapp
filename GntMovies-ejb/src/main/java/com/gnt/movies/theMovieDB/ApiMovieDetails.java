package com.gnt.movies.theMovieDB;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ApiMovieDetails {

	@SerializedName("adult")
	private boolean adult;
	@SerializedName("budget")
	private double budget;
	@SerializedName("genres")
	private ArrayList<ApiGenres> apiGenres;
	@SerializedName("homepage")
	private String homepage;
	@SerializedName("id")
	private int id;
	@SerializedName("imdb_id")
	private String imdbId;
	@SerializedName("original_language")
	private String originalLanguage;
	@SerializedName("original_title")
	private String originalTitle;
	@SerializedName("overview")
	private String overview;
	@SerializedName("production_companies")
	private ArrayList<ApiProductionCompanies> apiProductionCompanies;
	@SerializedName("production_countries")
	private ArrayList<ApiProductionCountries> apiProductionCountries;
	@SerializedName("release_date")
	private String releaseDate;
	@SerializedName("revenue")
	private double revenue;
	@SerializedName("runtime")
	private int runtime;
	@SerializedName("spoken_languages")
	private ArrayList<ApiSpokenLanguages> spokenLanguages;
	@SerializedName("status")
	private String status;
	@SerializedName("title")
	private String title;
	@SerializedName("vote_average")
	private double voteAverage;
	@SerializedName("vote_count")
	private int voteCount;
	
	private ArrayList<ApiCastCrew> cast;
	private ArrayList<ApiCastCrew> crew;

	public boolean getAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public ArrayList<ApiGenres> getMovieGenresAPI() {
		return apiGenres;
	}

	public void setMovieGenresAPI(ArrayList<ApiGenres> apiGenres) {
		this.apiGenres = apiGenres;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public ArrayList<ApiProductionCompanies> getProductionCompaniesAPI() {
		return apiProductionCompanies;
	}

	public void setProductionCompaniesAPI(ArrayList<ApiProductionCompanies> apiProductionCompanies) {
		this.apiProductionCompanies = apiProductionCompanies;
	}

	public ArrayList<ApiProductionCountries> getProductionCountriesAPI() {
		return apiProductionCountries;
	}

	public void setProductionCountriesAPI(ArrayList<ApiProductionCountries> apiProductionCountries) {
		this.apiProductionCountries = apiProductionCountries;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public ArrayList<ApiSpokenLanguages> getSpokenLanguages() {
		return spokenLanguages;
	}

	public void setSpokenLanguages(ArrayList<ApiSpokenLanguages> spokenLanguages) {
		this.spokenLanguages = spokenLanguages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public ArrayList<ApiGenres> getGenresAPI() {
		return apiGenres;
	}

	public void setGenresAPI(ArrayList<ApiGenres> apiGenres) {
		this.apiGenres = apiGenres;
	}

	public ArrayList<ApiCastCrew> getCast() {
		return cast;
	}

	public void setCast(ArrayList<ApiCastCrew> cast) {
		this.cast = cast;
	}

	public ArrayList<ApiCastCrew> getCrew() {
		return crew;
	}

	public void setCrew(ArrayList<ApiCastCrew> crew) {
		this.crew = crew;
	}

	@Override
	public String toString() {
		return "ApiMovieDetails [adult=" + adult + ", budget=" + budget + ", apiGenres=" + apiGenres + ", homepage="
				+ homepage + ", id=" + id + ", imdbId=" + imdbId + ", originalLanguage=" + originalLanguage
				+ ", originalTitle=" + originalTitle + ", overview=" + overview + ", apiProductionCompanies="
				+ apiProductionCompanies + ", apiProductionCountries=" + apiProductionCountries + ", releaseDate="
				+ releaseDate + ", revenue=" + revenue + ", runtime=" + runtime + ", spokenLanguages=" + spokenLanguages
				+ ", status=" + status + ", title=" + title + ", voteAverage=" + voteAverage + ", voteCount="
				+ voteCount + ", cast=" + cast + ", crew=" + crew + "]";
	}
	
	
}