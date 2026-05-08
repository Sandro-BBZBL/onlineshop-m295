Datenbankverbindung (PostgreSQL)
-	DB-Name: onlineshop
-	Benutzername: postgres
-	Passwort: admin

Keycloak Einstellungen
-	Realmname: onlineshop
-	Client-ID: onlineshop-client
-	Roles admin (Erstellen, Bearbeiten und Löschen, Update)
-	Role: user (Produkte sehen, Bestellung aufgeben, Custommer erstellen)
-	Users:
  o	test-admin
    - pwd: admin

  o	test-user
    - pwd: admin

Verwendete Netzwerk-Ports
-	Spring Boot backend (inkl. Swagger)
  o	Port: 8081
-	Keycloak
  o	Port: 8080
-	PostgreSQL Datenbank:
  o	Port: 5432
