package com.gnt.test.arquillian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gnt.movies.beans.GenreBean;
import com.gnt.movies.beans.SchedulerBean;
import com.gnt.movies.entities.Genre;
import com.gnt.movies.utilities.ApiCalls;
import com.gnt.movies.utilities.ApiClient;

@RunWith(Arquillian.class)
public class GenreTester {

	@EJB
	GenreBean genreBean;
	
	@EJB
	SchedulerBean sb;
	
	@Deployment
	public static WebArchive createDeployment() throws IOException 
	{
		WebArchive war = ShrinkWrap.create(WebArchive.class, "testGenreBean.war")
				.addPackages(true, "com.gnt.movies")
	            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
	            .addAsWebInfResource("test-ds.xml")
	            .addAsLibraries(
						Maven.resolver().resolve("com.google.code.gson:gson:2.8.2").withoutTransitivity().asFile())
				.addAsLibraries(
						Maven.resolver().resolve("com.squareup.okio:okio:1.13.0").withoutTransitivity().asFile())
				.addAsLibraries(
						Maven.resolver().resolve("com.squareup.okhttp3:okhttp:3.9.0").withoutTransitivity().asFile())
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return war;
	}
	
	@Before
	public void initialize() {
		ApiClient.init();
		HashSet<Genre> genres = ApiCalls.getGenres();
		genreBean.addGenres(genres);
	}

	@Test
	public void testProximity() {
/*		Genre genre = genreBean.findGenreByName("Action");
		assertEquals(genre.getName(), "Action");
		genreBean.editGenre(genre);
		assertEquals(genre.getName(), "Action");*/
		
		HashMap<String, Integer> genreTimes = new HashMap<>();
		
		ArrayList<Genre> genres = new ArrayList<>();
		genres.addAll(genreBean.getAllGenres());
		
		for (Genre genre : genres) {
			
			if (!genreTimes.containsKey(genre.getName())) genreTimes.put(genre.getName(), 1);
			else genreTimes.put(genre.getName(), 2);
		}
		System.out.println(genreTimes.toString());
		
		Assert.assertFalse(genreTimes.containsValue(2));
	}
}
