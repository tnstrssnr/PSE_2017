### JSON Schemas für Client-Server Kommunikation:

#### Server --> Client

**Admin Added:** {"user_id":"testid_1","group_id":"0"}

**Go Edited:** {"ID":0,"name":"lunch","description":"test description","start":"Aug 30, 3917 12:00:00 AM","end":"Sep 1, 3917 12:00:00 AM","lat":0,"lon":0} Datum automatisch konvertiert von Java.util.data. Kann evtl noch geändert werden

**Go Removed:** {"id":"0"}

**Group Edited:** {"ID":0,"name":"Foo","description":"Test Descritpion"}

**Group Removed:** {"id":"0"}

**Group Request Received:** {"ID":0,"name":"Foo","description":"Test Descritpion","members":[{"uid":"testid_1","instanceId":"testInstance_1","name":"Bob","email":"bob@testmail.com"},{"uid":"testid_2","instanceId":"testInstance_2","name":"Alice","email":"alice@testmail.com"}],"admins":[{"uid":"testid_1","instanceId":"testInstance_1","name":"Bob","email":"bob@testmail.com"}],"gos":[]}

**Member Added:** {"uid":"testid_2","instanceId":"testInstance_2","name":"Alice","email":"alice@testmail.com"} --> Teilnahmestatus für alle Gos der Gruppe hinzufügen! Alles auf 'Abgelehnt'

**Member Removed:** {"user_id":"testid_2","group_id":0}

**Status changed:** {"user_id":"testid_2","go_id":0,"status":1}
