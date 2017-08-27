rm T0180_results.txt

rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/goAdded_json.txt
rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/goAdded_rec.txt

mysql -u root -p69h97jnv pse_development < T0160_T0170_T0180_in.sql

echo Newman Run >> T0180_results.txt
date >> T0180_results.txt
newman run T0180.postman_collection.json >> T0180_results.txt

rm T0180_out_go.csv
rm /var/lib/mysql-files/T0180_out_go.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOS
INTO OUTFILE '/var/lib/mysql-files/T0180_out_go.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0180_out_go.csv T0180_out_go.csv
rm /var/lib/mysql-files/T0180_out_go.csv

echo GOS >> T0180_results.txt
python3 compareCsv.py T0180_expectedDbOutput_go.csv T0180_out_go.csv >> T0180_results.txt

rm T0180_out_going.csv
rm /var/lib/mysql-files/T0180_out_going.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0180_out_going.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0180_out_going.csv T0180_out_going.csv
rm /var/lib/mysql-files/T0180_out_going.csv

echo GOING USERS >> T0180_results.txt
python3 compareCsv.py T0180_expectedDbOutput_going.csv T0180_out_going.csv >> T0180_results.txt

rm T0180_out_notgoing.csv
rm /var/lib/mysql-files/T0180_out_notgoing.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM NOT_GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0180_out_notgoing.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0180_out_notgoing.csv T0180_out_notgoing.csv
rm /var/lib/mysql-files/T0180_out_notgoing.csv

echo NOT GOING USERS >> T0180_results.txt
python3 compareCsv.py T0180_expectedDbOutput_notgoing.csv T0180_out_notgoing.csv >> T0180_results.txt

rm T0180_out_gone.csv
rm /var/lib/mysql-files/T0180_out_gone.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GONE_USERS
INTO OUTFILE '/var/lib/mysql-files/T0180_out_gone.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0180_out_gone.csv T0180_out_gone.csv
rm /var/lib/mysql-files/T0180_out_gone.csv

echo GONE USERS >> T0180_results.txt
python3 compareCsv.py T0180_expectedDbOutput_gone.csv T0180_out_gone.csv >> T0180_results.txt

python3 compareJson.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/goAdded_json_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/goAdded_json.txt >> T0180_results.txt

python3 compareRecs.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/goAdded_rec_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/goAdded_rec.txt >> T0180_results.txt




