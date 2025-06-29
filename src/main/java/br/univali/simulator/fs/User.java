package br.univali.simulator.fs;

import lombok.Getter;

import java.util.Objects;

public final class User {

	private final String username;
	@Getter
	private final boolean root;

	public static final User ROOT = new User("root", true);

	public User(String name){
		this(name, false);
	}
	private User(String name, boolean root){
		this.username = name;
		this.root = root;
	}
	public String getName(){ return username; }

	@Override public boolean equals(Object o){
		return (o instanceof User u) && Objects.equals(username,u.username);
	}
	@Override public int hashCode(){ return username.hashCode(); }
	@Override public String toString(){ return username; }
}
