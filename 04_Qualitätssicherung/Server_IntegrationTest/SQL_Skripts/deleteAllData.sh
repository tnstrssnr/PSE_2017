#!/bin/bash

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
delete from GOS;
DELETE FROM GOING_USERS;
DELETE FROM NOT_GOING_USERS;
DELETE FROM GONE_USERS;
delete from ADMINS;
delete from MEMBERS;
delete from GROUPS;
delete from USERS;
EOF
