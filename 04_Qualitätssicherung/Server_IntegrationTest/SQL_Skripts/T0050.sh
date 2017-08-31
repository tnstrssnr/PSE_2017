rm T0050_results.txt

rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/userDeleted_json.txt
rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/userDeleted_rec.txt

mysql -u root -p69h97jnv pse_development < T0050_T0130_in.sql

echo Newman Run >> T0050_results.txt
date >> T0050_results.txt
newman run T0050.postman_collection.json >> T0050_results.txt

rm T0050_out_going.csv
rm /var/lib/mysql-files/T0050_out_going.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0050_out_going.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0050_out_going.csv T0050_out_going.csv
rm /var/lib/mysql-files/T0050_out_going.csv

echo GOING USERS >> T0050_results.txt
python3 compareCsv.py empty.csv T0050_out_going.csv >> T0050_results.txt

rm T0050_out_notgoing.csv
rm /var/lib/mysql-files/T0050_out_notgoing.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM NOT_GOING_USERS
INTO OUTFILE '/var/lib/mysql-files/T0050_out_notgoing.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0050_out_notgoing.csv T0050_out_notgoing.csv
rm /var/lib/mysql-files/T0050_out_notgoing.csv

echo NOT GOING USERS >> T0050_results.txt
python3 compareCsv.py empty.csv T0050_out_notgoing.csv >> T0050_results.txt

rm T0050_out_gone.csv
rm /var/lib/mysql-files/T0050_out_gone.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GONE_USERS
INTO OUTFILE '/var/lib/mysql-files/T0050_out_gone.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0050_out_gone.csv T0050_out_gone.csv
rm /var/lib/mysql-files/T0050_out_gone.csv

echo GONE USERS >> T0050_results.txt
python3 compareCsv.py empty.csv T0050_out_gone.csv >> T0050_results.txt


rm T0050_out_go.csv
rm /var/lib/mysql-files/T0050_out_go.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOS
INTO OUTFILE '/var/lib/mysql-files/T0050_out_go.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0050_out_go.csv T0050_out_go.csv
rm /var/lib/mysql-files/T0050_out_go.csv

echo GOS >> T0050_results.txt
python3 compareCsv.py empty.csv T0050_out_go.csv >> T0050_results.txt

rm T0050_out_groups.csv
rm /var/lib/mysql-files/T0050_out_groups.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GROUPS
INTO OUTFILE '/var/lib/mysql-files/T0050_out_groups.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0050_out_groups.csv T0050_out_groups.csv
rm /var/lib/mysql-files/T0050_out_groups.csv

echo GROUPS >> T0050_results.txt
python3 compareCsv.py empty.csv T0050_out_groups.csv >> T0050_results.txt


rm T0050_out_mem.csv
rm /var/lib/mysql-files/T0050_out_mem.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM MEMBERS
INTO OUTFILE '/var/lib/mysql-files/T0050_out_mem.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0050_out_mem.csv T0050_out_mem.csv
rm /var/lib/mysql-files/T0050_out_mem.csv

echo MEMBERS >> T0050_results.txt
python3 compareCsv.py empty.csv T0050_out_mem.csv >> T0050_results.txt


rm T0050_out_user.csv
rm /var/lib/mysql-files/T0050_out_user.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM USERS
INTO OUTFILE '/var/lib/mysql-files/T0050_out_user.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0050_out_user.csv T0050_out_user.csv
rm /var/lib/mysql-files/T0050_out_user.csv

echo USERS >> T0050_results.txt
python3 compareCsv.py T0050_expectedDbOutput_user.csv T0050_out_user.csv >> T0050_results.txt

python3 compareJson.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/userDeleted_json_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/userDeleted_json.txt >> T0050_results.txt

python3 compareRecs.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/userDeleted_rec_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/userDeleted_rec.txt >> T0050_results.txt




