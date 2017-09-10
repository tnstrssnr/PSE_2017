rm T0170_results.txt

mysql -u root -p69h97jnv pse_development < T0160_T0170_T0180_in.sql

echo Newman Run >> T0170_results.txt
date >> T0170_results.txt
newman run T0170.postman_collection.json >> T0170_results.txt

rm T0170_out_group.csv
rm /var/lib/mysql-files/T0170_out_group.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
SELECT *
FROM GROUPS
INTO OUTFILE '/var/lib/mysql-files/T0170_out_group.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0170_out_group.csv T0170_out_group.csv
rm /var/lib/mysql-files/T0170_out_group.csv

echo Groups >> T0170_results.txt
python3 compareCsv.py T0170_expectedDbOutput_group.csv T0170_out_group.csv >> T0170_results.txt


rm T0170_out_mem.csv
rm /var/lib/mysql-files/T0170_out_mem.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
SELECT *
FROM MEMBERS
INTO OUTFILE '/var/lib/mysql-files/T0170_out_mem.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0170_out_mem.csv T0170_out_mem.csv
rm /var/lib/mysql-files/T0170_out_mem.csv

echo Members >> T0170_results.txt
python3 compareCsv.py T0170_expectedDbOutput_group.csv T0170_out_mem.csv >> T0170_results.txt


rm T0170_out_user.csv
rm /var/lib/mysql-files/T0170_out_user.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
SELECT *
FROM USERS
INTO OUTFILE '/var/lib/mysql-files/T0170_out_user.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0170_out_user.csv T0170_out_user.csv
rm /var/lib/mysql-files/T0170_out_user.csv

echo Users >> T0170_results.txt
python3 compareCsv.py T0170_expectedDbOutput_user.csv T0170_out_user.csv >> T0170_results.txt


