# Datenbank - Übersicht

#### Benutzerrelation

- Benutzer-ID: String
- E-Mail: String
- Benutzername: String
- Profilbild (?)

Schlüssel: Benutzer-ID

#### Gruppenrelation

- Gruppen-ID: String
- Gruppenname: String
- Gruppenbild (?)
- Gruppenbeschreibung: String

Schlüssel: Gruppen-ID

### Gruppenmitgliederrelation

- Gruppen-ID (Fremdschlüssel)
- Benutzer-ID (Fremdschlüssel)
- isAdmin: Boolean
- isRequest: Boolean

Schlüssel: Gruppen-ID + Benutzer-ID

#### GO-Relation

- GO-ID: String
- GO-Verantwortlicher: Benutzer (Fremdschlüssel)
- GO-Name: String
- Beschreibung: String
- Startzeitpunkt: Date
- Endzeitpunkt: Date
- Schwellwert: int
- Gruppe: Gruppen-ID


Schlüssel: GO-ID

#### GO-Teilnehmerstatus

- GO-ID (Fremdschlüssel)
- Teilnehmer: Benutzer-ID (Fremdschlüssel)
- Teilnahmestatus: enum

Schlüssel: GO-ID + Benutzer-ID

#### Konsistenzbedingungen

folgende Konsistenzbedingungen sind *nicht* durch Schlüssel abgedeckt:

- ein GO-Verantwortlicher darf nur Teilnehmerstatus 'bestätigt' und 'unterwegs' haben
- es dürfen nur Mitglieder der jeweiligen Gruppe in der Relation Go-Teilnehmerstatus auftauchen
- jedes Mitglied der Gruppe muss für jedes Go der Gruppe in der Relation Go-Teilnehmerstatus auftauchen


