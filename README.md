# M223 Punchclock

Folgende Schritte sind notwendig um die Applikation zu erstellen und zu starten: 
1. Stellen Sie sicher, dass OpenJDK 11 oder höher installiert und JAVA_HOME korrekt gesetzt ist.  
2. Installieren Sie (falls noch nicht vorhanden) Apache Maven 3.8.1 oder höher
3. Wechseln Sie auf der Kommandozeile in den Ordner dieser Applikation. 
`cd m223-punchclock-quarkus/`
4. Starten Sie die Applikation mit 
```shell script
./mvnw compile quarkus:dev
```

Folgende Dienste stehen während der Ausführung im Profil dev zur Verfügung:

Swagger API: http://localhost:8080/q/swagger-ui/

H2 Console: http://localhost:8080/h2/ 
Datenquelle: jdbc:h2:mem:punchclock
Benutzername: zli
Passwort: zli

Diese Applikation dient dazu, Zeiten zu erfassen, in denen bestimmte Mitarbeiter etwas bestimmtes gearbeitet haben.
öffnen Sie diese Applikation in Ihrem Web Browser und geben Sie ihre gewünschten Daten ein, um zu starten. Admins können nur von 
anderen Admins erstellt werden. Es besteht bereits ein Admin namens Elia. 

Beispieldaten werden via Startup.java geladen:
Rolle "user" und Rolle "admin",
Admin "Elia" mit Passwort 1234,
Kategorie "Default Category".