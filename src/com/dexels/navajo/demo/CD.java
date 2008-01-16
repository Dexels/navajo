package com.dexels.navajo.demo;

import java.util.ArrayList;

public class CD {

	private Artist artist;
	private String name;
	private Song [] songs;
	
	private ArrayList<Song> songList = new ArrayList<Song>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		System.err.println("Setting CD title: " + name);
		this.name = name;
	}
	
	public Song[] getSongs() {
		songs = new Song[songList.size()];
		songs = songList.toArray(songs);
		return songs;
	}
	
	public void setSongs(Song[] songs) {
		this.songs = songs;
		for (int i = 0; i < songs.length; i++) {
			System.err.println("Adding song: " + songs[i].getName());
			songList.add(songs[i]);
		}
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		System.err.println("Adding artist: " + artist.getName());
		this.artist = artist;
	}
	
}
