# Client #

1. Früher wurde beim Löschen eines Admins der Gruppe die Methode onMemberRemoved aufgerufen (und dabei 
gecheckt, ob member admin ist und wenn ja: deleteGroup); jetzt: in Activity wird überprüft, ob der 
Member ein Admin ist und wenn ja - direkt deleteGroup (keine onMemberRemoved Methode und kein additional 
Aufwand)

2. Wenn Benutzer aus der GoDetailActivity rausgeht, während die Locations ständig in diesem GO
aktualisiert werden, und er schaut sich ein anderes GO an, wird das LiveData beim GoRepository
überschrieben (mit dem neuen GO) und die Locations werden bei einem anderen (falschen) GO geändert. 
Das wird jetzt behoben, indem die Methode onLocationsUpdated das ID des GOs bekommt.

3. Wenn ein GO/eine Gruppe geändert wird, wird diese aus der Liste gelöscht und wieder zu der Liste
hinzugefügt. Dabei werden die Daten auf dem Screen gerutscht und ein anderes (fremdes) GO/eine
fremde Gruppe wird gezeigt. Der Bug wird behoben indem das alte GO/die alte Gruppe durch das neue/
die neue auf dem gleichen Platz in der Liste ersetzt wird.

# Server #

1. Änderungen beim LocationService (NullPointerException -> Florian bitte ergänzen!)