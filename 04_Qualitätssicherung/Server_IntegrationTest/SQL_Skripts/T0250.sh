rm T0250_results.txt

mysql -u root -p69h97jnv pse_development < T0040_T0250_T0260_in.sql

echo Newman Run >> T0250_results.txt
date >> T0250_results.txt
newman run T0250.postman_collection.json >> T0250_results.txt

rm T0250_out_going.csv
rm /var/lib/mysql-files/T0250_out_going.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0250_out_going.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0250_out_going.csv T0250_out_going.csv
rm /var/lib/mysql-files/T0250_out_going.csv

echo GOING USERS >> T0250_results.txt
python3 compareCsv.py empty.csv T0250_out_going.csv >> T0250_results.txt

rm T0250_out_notgoing.csv
rm /var/lib/mysql-files/T0250_out_notgoing.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM NOT_GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0250_out_notgoing.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0250_out_notgoing.csv T0250_out_notgoing.csv
rm /var/lib/mysql-files/T0250_out_notgoing.csv

echo NOT GOING USERS >> T0250_results.txt
python3 compareCsv.py empty.csv T0200_out_notgoing.csv >> T0250_results.txt

rm T0250_out_gone.csv
rm /var/lib/mysql-files/T0250_out_gone.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GONE_USERS
INTO OUTFILE '/var/lib/mysql-files/T0250_out_gone.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0250_out_gone.csv T0250_out_gone.csv
rm /var/lib/mysql-files/T0250_out_gone.csv

echo GONE USERS >> T0250_results.txt
python3 compareCsv.py empty.csv T0250_out_gone.csv >> T0250_results.txt


rm T0250_out_go.csv
rm /var/lib/mysql-files/T0250_out_go.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOS
INTO OUTFILE '/var/lib/mysql-files/T0250_out_go.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0250_out_go.csv T0250_out_go.csv
rm /var/lib/mysql-files/T0250_out_go.csv

echo GOS >> T0250_results.txt
python3 compareCsv.py empty.csv T0250_out_go.csv >> T0250_results.txt




