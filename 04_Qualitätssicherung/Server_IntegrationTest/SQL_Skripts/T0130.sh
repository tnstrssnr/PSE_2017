rm T0130_results.txt

mysql -u root -p69h97jnv pse_development < T0050_T0130_in.sql

echo Newman Run >> T0130_results.txt
date >> T0130_results.txt
newman run T0130.postman_collection.json >> T0130_results.txt

rm T0130_out_going.csv
rm /var/lib/mysql-files/T0130_out_going.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0130_out_going.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0130_out_going.csv T0130_out_going.csv
rm /var/lib/mysql-files/T0130_out_going.csv

echo GOING USERS >> T0130_results.txt
python3 compareCsv.py empty.csv T0130_out_going.csv >> T0130_results.txt

rm T0130_out_notgoing.csv
rm /var/lib/mysql-files/T0130_out_notgoing.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM NOT_GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0130_out_notgoing.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0130_out_notgoing.csv T0130_out_notgoing.csv
rm /var/lib/mysql-files/T0130_out_notgoing.csv

echo NOT GOING USERS >> T0130_results.txt
python3 compareCsv.py empty.csv T0130_out_notgoing.csv >> T0130_results.txt

rm T0130_out_gone.csv
rm /var/lib/mysql-files/T0130_out_gone.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GONE_USERS
INTO OUTFILE '/var/lib/mysql-files/T0130_out_gone.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0130_out_gone.csv T0130_out_gone.csv
rm /var/lib/mysql-files/T0130_out_gone.csv

echo GONE USERS >> T0130_results.txt
python3 compareCsv.py empty.csv T0130_out_gone.csv >> T0130_results.txt


rm T0130_out_go.csv
rm /var/lib/mysql-files/T0130_out_go.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOS
INTO OUTFILE '/var/lib/mysql-files/T0130_out_go.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0130_out_go.csv T0130_out_go.csv
rm /var/lib/mysql-files/T0130_out_go.csv

echo GOS >> T0130_results.txt
python3 compareCsv.py empty.csv T0130_out_go.csv >> T0130_results.txt

rm T0130_out_groups.csv
rm /var/lib/mysql-files/T0130_out_groups.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GROUPS
INTO OUTFILE '/var/lib/mysql-files/T0130_out_groups.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0130_out_groups.csv T0130_out_groups.csv
rm /var/lib/mysql-files/T0130_out_groups.csv

echo GROUPS >> T0130_results.txt
python3 compareCsv.py empty.csv T0050_out_groups.csv >> T0130_results.txt


rm T0130_out_mem.csv
rm /var/lib/mysql-files/T0130_out_mem.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM MEMBERS
INTO OUTFILE '/var/lib/mysql-files/T0130_out_mem.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0130_out_mem.csv T0130_out_mem.csv
rm /var/lib/mysql-files/T0130_out_mem.csv

echo MEMBERS >> T0130_results.txt
python3 compareCsv.py empty.csv T0130_out_mem.csv >> T0130_results.txt





