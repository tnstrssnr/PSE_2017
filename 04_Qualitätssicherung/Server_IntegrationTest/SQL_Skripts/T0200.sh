rm T0200_results.txt

mysql -u root -p69h97jnv pse_development < T0200_in.sql

echo Newman Run >> T0200_results.txt
date >> T0200_results.txt
newman run T0200.postman_collection.json >> T0200_results.txt

rm T0200_out_going.csv
rm /var/lib/mysql-files/T0200_out_going.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0200_out_going.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0200_out_going.csv T0200_out_going.csv
rm /var/lib/mysql-files/T0200_out_going.csv

echo GOING USERS >> T0200_results.txt
python3 compareCsv.py T0200_expectedDbOutput_going.csv T0200_out_going.csv >> T0200_results.txt

rm T0200_out_notgoing.csv
rm /var/lib/mysql-files/T0200_out_notgoing.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM NOT_GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0200_out_notgoing.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0200_out_notgoing.csv T0200_out_notgoing.csv
rm /var/lib/mysql-files/T0200_out_notgoing.csv

echo NOT GOING USERS >> T0200_results.txt
python3 compareCsv.py T0200_expectedDbOutput_notgoing.csv T0200_out_notgoing.csv >> T0200_results.txt

rm T0200_out_gone.csv
rm /var/lib/mysql-files/T0200_out_gone.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GONE_USERS
INTO OUTFILE '/var/lib/mysql-files/T0200_out_gone.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0200_out_gone.csv T0200_out_gone.csv
rm /var/lib/mysql-files/T0200_out_gone.csv

echo GONE USERS >> T0200_results.txt
python3 compareCsv.py T0200_expectedDbOutput_gone.csv T0200_out_gone.csv >> T0200_results.txt




