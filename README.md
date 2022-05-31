# It-conference
Serwis dla strony do obsługi konferencji IT. 
Spring boot.
Uzyte zaleznosci:
-Spring Web
-Lombok
-H2 Database
-Spring Data JPA
-Validation

I dodatkowo Swagger do ułatwienia procesu tworzenia REST API (https://swagger.io/)

Aby uruchomić projekt należy użyć Mavena i wykorzystać następujące komendy:
-mvn clean install
-mvn spring-boot:run
lub przy użyciu pluginów:
-Lifecycle->clean
-Lifecycle->install
-Plugins->spring-boot->spring-boot:run
Zostanie uruchomiony serwer Tomcat na porcie 8080.

Dostępne usługi REST:
LectureController:

GET /api/lectures, param login 
http://localhost:8080/api/lectures?login=
Umozliwia obejrzenie uzytkownikowi, po podaniu loginu, prelekcji, na ktore jest zapisany.

POST /api/lectures, param lectureId oraz body - user
http://localhost:8080/api/lectures?lectureId=
Umozliwia zapisanie sie na prelekcje, jezeli jest miejsce oraz nie jest to jedna z rownoleglych prelekcji.
Wymagane podanie loginu i maila, jesli uzytkownik istnieje zostanie dodana rezerwacja, jesli nie to nowy uzytkownik
zostanie utworzony, nie moga sie powtorzyc dwa takie same loginy przy roznych adresach email.

DELETE /api/lectures, param login,lectureId
// DELETE http://localhost:8080/api/lectures/?login=&lectureId=
Umozliwia usuniecie rezerwacji dla poprawnych parametrow.

GET /api/lectures/interest/category
http://localhost:8080/api/lectures/interest/category
Umozliwia pobranie zestawienia sciezek tematycznych wedlug zainteresowania uczestnikow.

GET /api/lectures/interest/lecture
http://localhost:8080/api/lectures/interest/lecture
Umozliwia pobranie zestawienia wykladow wedlug zainteresowania uczestnikow.

GET /api/schedule
http://localhost:8080/api/schedule
Umozliwia pobranie planu konferencji, ktory zostal zainicjowany w bazie danych.

UserController:

GET /api/users
http://localhost:8080/api/users
Umozliwia wyswietlenie listy zarejestrowanych uzytkownikow wraz z ich adresami email.

PUT /api/users, param userId oraz body - user
http://localhost:8080/api/users?userId=
Umozliwia zaktualizowanie adresu email uzytkownikowi.
