package com.danielqueiroz.constants;

import java.util.Arrays;

public final class Constants {

	public final static class User {

		public enum Level {

			ADMIN(0), USER(1);

			private Integer level;

			Level(Integer level) {
				this.setLevel(level);
			}

			public Integer level() {
				return level;
			}

			private void setLevel(Integer level) {
				this.level = level;
			}

		}



	}
	
	public final static class Entity {
		
		public static enum Type {
			PERSON("person"),
			TIME("time"),
			PLACE("place"),
			ORGANIZATION("organization"),
			NUMERIC("numeric"),
			NOUN("n"), 
			DATE("date"), 
			EVENT("event"), 
			ACTION("v-fin");
			
			private String name;

			Type(String name) {
				this.name = name;
			}

			public String getName() {
				return name;
			}

		}

		
	}

}
