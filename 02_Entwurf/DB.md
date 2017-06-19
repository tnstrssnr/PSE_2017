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
- Benutzer: Benutzer (Fremdschlüssel)
- isAdmin: Boolean
- isRequest: Boolean

Schlüssel: Gruppen-ID + Benutzer

#### GO-Relation

- GO-ID: String
- GO-Verantwortlicher: Benutzer (Fremdschlüssel)
- GO-Name: String
- Beschreibung: String
- Startzeitpunkt: Date
- Endzeitpunkt: Date
- Schwellwert: int
- Gruppe: Gruppen-ID
- Teilnehmer: Benutzer
- Teilnahmestatus: enum

Schlüssel: GO-ID + Benutzer


