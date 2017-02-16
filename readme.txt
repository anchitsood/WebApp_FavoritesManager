	Description: This is implemented as a web app which involves using Java, MySQL, HTML, CSS, and
	JavaScript. In simple terms, it is a favorites manager website, which allows users to login and
	save their favorite URLs. Anyone can read and go to those URLs, but only the user who created the
	Favorite in the first place can edit or remove those favorite URLs after logging in.
	
	This is interesting because it involves not only the use of low level data structures in Java,
	but also blends Java with MySQL queries and updates, and uses JavaScript Tag Libraries, which by
	design, require that the underlying structures be implemented using OOP concepts. For example,
	there is a User class, which stores all details of the user, and then there is a Favorite class,
	which includes the URL, a comment, and a click-counter.
	
	Operationally, the project is implemented in a Model-View-Controller schematic, which enforces
	modularity, and splits the different parts of the project into user specific code. For example,
	the backend guys need not worry about what the frontend is doing, and the web designers need no
	inherent knowledge of what happens in the background. This project demonstrates a full-stack
	implementation of a simple web app.
	
	The FavoritesManager.war file is a web archive file which can be deployed in Eclipse to reconstruct
	the entire project to a working state. A prerequisite is to have MySQL running, and further, there
	needs to be an anonymous user who can access the MySQL server with empty strings as login credentials.
	
	Code practices to note: Various classes have been used to build the solution. Note use of OOP
	practices for simplicity and reusability. The project is designed keeping in mind robustness and
	reusability. It handles all error cases, and even implements encryption of user credentials for
	a truly real world experience. The code is modular, uses encapsulation and abstraction.
	It is built to last.
