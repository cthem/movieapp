package com.gnt.movies.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the shows database table.
 *
 */
@Entity
@Table(name = "shows")
@NamedQueries({ @NamedQuery(name = "Show.findAll", query = "SELECT s FROM Show s"),
		@NamedQuery(name = "Show.findById", query = "SELECT s FROM Show s WHERE s.id = :id"),
		@NamedQuery(name = "Show.findByName", query = "SELECT s FROM Show s WHERE s.name = :name"),
		@NamedQuery(name = "Show.findByOriginalName", query = "SELECT s FROM Show s WHERE s.originalName = :originalName"),
		@NamedQuery(name = "Show.findByIdTmdb", query = "SELECT s FROM Show s WHERE s.idTmdb = :idTmdb"),
		@NamedQuery(name = "Show.findByHomepage", query = "SELECT s FROM Show s WHERE s.homepage = :homepage"),
		@NamedQuery(name = "Show.findByRuntime", query = "SELECT s FROM Show s WHERE s.runtime = :runtime"),
		@NamedQuery(name = "Show.findByStatus", query = "SELECT s FROM Show s WHERE s.status = :status"),
		@NamedQuery(name = "Show.findByFirstAirDate", query = "SELECT s FROM Show s WHERE s.firstAirDate = :firstAirDate"),
		@NamedQuery(name = "Show.findByLastAirDate", query = "SELECT s FROM Show s WHERE s.lastAirDate = :lastAirDate"),
		@NamedQuery(name = "Show.findByVoteAverage", query = "SELECT s FROM Show s WHERE s.voteAverage = :voteAverage"),
		@NamedQuery(name = "Show.findByVoteCount", query = "SELECT s FROM Show s WHERE s.voteCount = :voteCount"),
		@NamedQuery(name = "Show.findByNumOfEpisodes", query = "SELECT s FROM Show s WHERE s.numOfEpisodes = :numOfEpisodes"),
		@NamedQuery(name = "Show.findByNumOfSeasons", query = "SELECT s FROM Show s WHERE s.numOfSeasons = :numOfSeasons"),
		@NamedQuery(name = "Show.findByOriginalLanguage", query = "SELECT s FROM Show s WHERE s.originalLanguage = :originalLanguage"),
		@NamedQuery(name = "Show.findByCreatedBy", query = "SELECT s FROM Show s WHERE s.createdBy = :createdBy"),
		@NamedQuery(name = "Show.findByNetworks", query = "SELECT s FROM Show s WHERE s.networks = :networks"),
		@NamedQuery(name = "Show.findByOriginCountries", query = "SELECT s FROM Show s WHERE s.originCountries = :originCountries"),
		@NamedQuery(name = "Show.findByProductionCountries", query = "SELECT s FROM Show s WHERE s.productionCountries = :productionCountries"),
		@NamedQuery(name = "Show.findByInProduction", query = "SELECT s FROM Show s WHERE s.inProduction = :inProduction"),
		@NamedQuery(name = "Show.findByType", query = "SELECT s FROM Show s WHERE s.type = :type") 
})
public class Show implements Serializable, Comparable<Show> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Lob
	private String cast;

	private String createdBy;

	@Lob
	private String crew;

	private LocalDate firstAirDate;
	@Lob
	private String homepage;

	private int idTmdb;

	private byte inProduction;

	private LocalDate lastAirDate;

	private String name;

	private String networks;

	private int numOfEpisodes;

	private int numOfSeasons;

	private String originalLanguage;

	private String originalName;

	private String originCountries;

	@Lob
	private String overview;

	private String posterPath;

	private String productionCountries;

	private int runtime;

	private String status;

	private String type;

	private Double voteAverage;

	private int voteCount;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "show_genres", joinColumns = {
			@JoinColumn(name = "showId", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "genreId", referencedColumnName = "id") }
	)
	private Set<Genre> genres;

	@OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
	private List<ShowImage> showImages;

	@OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
	private List<ShowFavorite> showFavorites;

	@OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
	private List<ShowReview> showReviews;
	
	@Transient
	private OnTheAirShow onTheAirShows;

	@Transient
	private Air2dayShow air2dayShow;

	public Show() {
	}

	public Show(String firstAirDate, int idTmdb, String name, String originalLanguage, String originalName,
			String originCountries, String overview, double voteAverage, int voteCount, String posterPath) {
		super();
		if (firstAirDate.length() > 0) {
			this.firstAirDate = LocalDate.parse(firstAirDate);
		}
		this.idTmdb = idTmdb;
		this.name = name;
		this.originalLanguage = originalLanguage;
		this.originalName = originalName;
		this.originCountries = originCountries;
		this.overview = overview;
		this.voteAverage = voteAverage;
		this.voteCount = voteCount;
		this.posterPath = posterPath;
		this.genres = new HashSet<>();
		this.showImages = new ArrayList<>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCast() {
		return this.cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCrew() {
		return this.crew;
	}

	public void setCrew(String crew) {
		this.crew = crew;
	}

	public LocalDate getFirstAirDate() {
		return this.firstAirDate;
	}

	public void setFirstAirDate(LocalDate firstAirDate) {
		this.firstAirDate = firstAirDate;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public int getIdTmdb() {
		return this.idTmdb;
	}

	public void setIdTmdb(int idTmdb) {
		this.idTmdb = idTmdb;
	}

	public byte getInProduction() {
		return this.inProduction;
	}

	public void setInProduction(byte inProduction) {
		this.inProduction = inProduction;
	}

	public LocalDate getLastAirDate() {
		return this.lastAirDate;
	}

	public void setLastAirDate(LocalDate lastAirDate) {
		this.lastAirDate = lastAirDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetworks() {
		return this.networks;
	}

	public void setNetworks(String networks) {
		this.networks = networks;
	}

	public int getNumOfEpisodes() {
		return this.numOfEpisodes;
	}

	public void setNumOfEpisodes(int numOfEpisodes) {
		this.numOfEpisodes = numOfEpisodes;
	}

	public int getNumOfSeasons() {
		return this.numOfSeasons;
	}

	public void setNumOfSeasons(int numOfSeasons) {
		this.numOfSeasons = numOfSeasons;
	}

	public String getOriginalLanguage() {
		return this.originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalName() {
		return this.originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOriginCountries() {
		return this.originCountries;
	}

	public void setOriginCountries(String originCountries) {
		this.originCountries = originCountries;
	}

	public String getOverview() {
		return this.overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getPosterPath() {
		return this.posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getProductionCountries() {
		return this.productionCountries;
	}

	public void setProductionCountries(String productionCountries) {
		this.productionCountries = productionCountries;
	}

	public int getRuntime() {
		return this.runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getVoteAverage() {
		return this.voteAverage;
	}

	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public int getVoteCount() {
		return this.voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public Set<Genre> getGenres() {
		return this.genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public void addGenre(Genre genre) {
		getGenres().add(genre);
	}

	public void removeGenre(Genre genre) {
		getGenres().remove(genre);
	}

	public OnTheAirShow getOnTheAirShows() {
		return this.onTheAirShows;
	}

	public void setOnTheAirShows(OnTheAirShow onTheAirShows) {
		this.onTheAirShows = onTheAirShows;
	}

	public List<ShowFavorite> getShowFavorites() {
		return this.showFavorites;
	}

	public void setShowFavorites(List<ShowFavorite> showFavorites) {
		this.showFavorites = showFavorites;
	}

	public ShowFavorite addShowFavorite(ShowFavorite showFavorite) {
		getShowFavorites().add(showFavorite);
		showFavorite.setShow(this);

		return showFavorite;
	}

	public ShowFavorite removeShowFavorite(ShowFavorite showFavorite) {
		getShowFavorites().remove(showFavorite);
		showFavorite.setShow(null);

		return showFavorite;
	}

	public List<ShowReview> getShowReviews() {
		return this.showReviews;
	}

	public void setShowReviews(List<ShowReview> showReviews) {
		this.showReviews = showReviews;
	}

	public ShowReview addShowReview(ShowReview showReview) {
		getShowReviews().add(showReview);
		showReview.setShow(this);

		return showReview;
	}

	public ShowReview removeShowReview(ShowReview showReview) {
		getShowReviews().remove(showReview);
		showReview.setShow(null);

		return showReview;
	}

	public Air2dayShow getAir2dayShow() {
		return this.air2dayShow;
	}

	public void setAir2dayShow(Air2dayShow air2dayShow) {
		this.air2dayShow = air2dayShow;
	}

	public List<ShowImage> getShowImages() {
		return this.showImages;
	}

	public void setShowImages(List<ShowImage> showImages) {
		this.showImages = showImages;
	}

	public ShowImage addShowImage(ShowImage showImage) {
		getShowImages().add(showImage);
		showImage.setShow(this);

		return showImage;
	}

	public ShowImage removeShowImage(ShowImage showImage) {
		getShowImages().remove(showImage);
		showImage.setShow(null);

		return showImage;
	}

	public double getAverageRating() {
		double rating = 0.0;
		double averageRating;
		if (showReviews == null) {
			averageRating = 0.0;
		} else {
			for (int i = 0; i < showReviews.size(); i++) {
				rating = rating + ((List<ShowReview>) showReviews).get(i).getRating();
			}
			averageRating = rating / showReviews.size();
		}
		return averageRating;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof Movie)) {
			return false;
		}
		Show other = (Show) object;

		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	/** Default sorting for shows, based on our rating **/
	@Override
	public int compareTo(Show show) {
		Double averageRating = this.getAverageRating();
		Double otherAverageRating = show.getAverageRating();
		return -averageRating.compareTo(otherAverageRating);
	}
	/** Compare and sort shows based on vote average of the movie db **/
	public static Comparator<Show> VoteAverageComparator = new Comparator<Show>() {

        @Override
        public int compare(Show show1, Show show2) {
            return show1.getVoteAverage().compareTo(show2.getVoteAverage());
        }
    };

}