# Protokoll vom 17.06.2017

### Betreuertreffen

- Javadoc: lieber auf Englisch, auf Deutsch aber auch möglich. Entwurfsdokument auf deutsch (außer evtl. Klassenbeschreibungen)
- Eventhandler Klassen: jede Klasse ist ihr eigener EventHandler. in der OnClick()-Methode wird anhand der View-id dynamisch die richtige Funktionalität aufgerufen.
- Testklassen müssen im Entwurf noch nicht berücksichtigt werden
- In Sequenzdiagrammen nur den interessanten Teil darstellen --> lieber ein paar Dinge weglassen und dafür übersichtlich
- protected Methoden und Felder vermeiden!
- Eigenschaften einer Klasse (die eig private Felder sind) im Klassendiagramm als öffentliche Felder mit UML-Notationen
- Hibernate für DB-Schnittstelle benutzen

### bis nächsten Dienstag:

**Vova:** Einbinden der Eventhandler-Klassen + Controller, die von den Handlern aufgerufen werden
**Arsenii:** Entwurf für Klassen, die an Clustering beteiligt sind
**Flo:** Entwurf für Hibernate-Schnittstelle zwischen Tomcat und DB
**Tina:** Entwurf für Schnittstelle Android <> Tomcat
