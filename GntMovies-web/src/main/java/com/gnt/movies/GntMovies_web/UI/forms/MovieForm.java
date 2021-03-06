package com.gnt.movies.GntMovies_web.UI.forms;

import com.gnt.movies.GntMovies_web.UI.layouts.MovieFormLayout;
import com.gnt.movies.GntMovies_web.UI.utils.Utilities;
import com.gnt.movies.entities.Movie;
import com.vaadin.data.Binder;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MovieForm extends VerticalLayout {
	private static String FIELD_WIDTH = "95%";
	private final Binder<Movie> binder = new Binder<>();
	private MovieFormLayout movieFormLayout;
	private boolean isFieldChanged = false;
	private TextField titletf;
	private TextField overviewtf;
	private TextField homepagetf;
	private TextField statustf;
	private TextField originalLanguagetf;
	private TextField adulttf;
	
	public MovieForm(MovieFormLayout movieFormLayout) {
		this.movieFormLayout=movieFormLayout;
		setSizeFull();
		initFields();
		addPlaceHolders();
		addValidation();
		addComponents(titletf, overviewtf, homepagetf, statustf, originalLanguagetf, adulttf);
		updateFields();
	}
	
	private void initFields() {
		titletf = new TextField(Utilities.NAME_FIELD);
		overviewtf = new TextField(Utilities.LAST_NAME_FIELD);
		homepagetf = new TextField(Utilities.USERNAME_FIELD);
		statustf = new TextField(Utilities.EMAIL_FIELD);
		originalLanguagetf = new TextField(Utilities.PASSWORD_FIELD);
		adulttf = new TextField(Utilities.AGE_FIELD);
	}

	private void addPlaceHolders() {
		titletf.setPlaceholder(Utilities.NAME_FIELD);
		overviewtf.setPlaceholder(Utilities.LAST_NAME_FIELD);
		homepagetf.setPlaceholder(Utilities.USERNAME_FIELD);
		statustf.setPlaceholder(Utilities.EMAIL_FIELD);
		originalLanguagetf.setPlaceholder(Utilities.PASSWORD_FIELD);
		adulttf.setPlaceholder(Utilities.AGE_FIELD);
	}

	private void addValidation() {
		binder.forField(titletf).asRequired("Name is required").bind(Movie::getTitle, Movie::setTitle);
		binder.forField(overviewtf).asRequired("Overview is required").bind(Movie::getOverview, Movie::setOverview);
		binder.forField(homepagetf).asRequired("Homepage is required").bind(Movie::getHomepage, Movie::setHomepage);
		binder.forField(statustf).asRequired("Status is required").bind(Movie::getStatus, Movie::setStatus);
	}

	private void updateFields() {
		setTextFieldWidth();
		setRequiredFields();
		addValueChangeListener();
	}

	private void setTextFieldWidth() {
		titletf.setWidth(FIELD_WIDTH);
		overviewtf.setWidth(FIELD_WIDTH);
		homepagetf.setWidth(FIELD_WIDTH);
		statustf.setWidth(FIELD_WIDTH);
		originalLanguagetf.setWidth(FIELD_WIDTH);
		adulttf.setWidth(FIELD_WIDTH);
	}

	private void addValueChangeListener() {
		titletf.addValueChangeListener(event -> setFieldChanged(true));
		overviewtf.addValueChangeListener(event -> setFieldChanged(true));
		homepagetf.addValueChangeListener(event -> setFieldChanged(true));
		statustf.addValueChangeListener(event -> setFieldChanged(true));
		originalLanguagetf.addValueChangeListener(event -> setFieldChanged(true));
		adulttf.addValueChangeListener(event -> setFieldChanged(true));
	}

	private void setRequiredFields() {
		titletf.setRequiredIndicatorVisible(true);
		overviewtf.setRequiredIndicatorVisible(true);
		homepagetf.setRequiredIndicatorVisible(true);
		statustf.setRequiredIndicatorVisible(true);
	}
	
	public void clearFormFields() {
		titletf.clear();
		overviewtf.clear();
		homepagetf.clear();
		statustf.clear();
		originalLanguagetf.clear();
		adulttf.clear();
	}

	public MovieFormLayout getMovieFormLayout() {
		return movieFormLayout;
	}

	public void setMovieFormLayout(MovieFormLayout movieFormLayout) {
		this.movieFormLayout = movieFormLayout;
	}

	public boolean isFieldChanged() {
		return isFieldChanged;
	}

	public void setFieldChanged(boolean isFieldChanged) {
		this.isFieldChanged = isFieldChanged;
	}

	public TextField getTitletf() {
		return titletf;
	}

	public void setTitletf(TextField titletf) {
		this.titletf = titletf;
	}

	public TextField getOverviewtf() {
		return overviewtf;
	}

	public void setOverviewtf(TextField overviewtf) {
		this.overviewtf = overviewtf;
	}

	public TextField getHomepagetf() {
		return homepagetf;
	}

	public void setHomepagetf(TextField homepagetf) {
		this.homepagetf = homepagetf;
	}

	public TextField getStatustf() {
		return statustf;
	}

	public void setStatustf(TextField statustf) {
		this.statustf = statustf;
	}

	public TextField getOriginalLanguagetf() {
		return originalLanguagetf;
	}

	public void setOriginalLanguagetf(TextField originalLanguagetf) {
		this.originalLanguagetf = originalLanguagetf;
	}

	public TextField getAdulttf() {
		return adulttf;
	}

	public void setAdulttf(TextField adulttf) {
		this.adulttf = adulttf;
	}

	public Binder<Movie> getBinder() {
		return binder;
	}
	
}
