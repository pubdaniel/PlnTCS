package com.danielqueiroz.constants;

public final class Constants {

	public final static class User {

		public enum Level {

			ADMIN(0, "admin"), USER(1, "Usuário");

			private Integer level;
			private String description;

			Level(Integer level, String description) {
				this.setLevel(level);
				this.setDescription(description);
			}

			public String description() {
				return description;
			}

			private void setDescription(String description) {
				this.description = description;
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
			PERSON("prop"),
			NOUN("n");
			
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
