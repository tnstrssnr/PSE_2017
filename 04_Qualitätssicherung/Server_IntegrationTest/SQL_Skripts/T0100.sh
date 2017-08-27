rm T0100_results.txt

mysql -u root -p69h97jnv pse_development < T0100_T0110_in.sql

echo Newman Run >> T0100_results.txt
date >> T0100_results.txt
newman run T0100.postman_collection.json >> T0100_results.txt

rm T0100_out_req.csv
rm /var/lib/mysql-files/T0100_out_req.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM REQUESTS
INTO OUTFILE '/var/lib/mysql-files/T0100_out_req.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

rm T0100_ou_mem.csv
rm /var/lib/mysql-files/T0100_out_mem.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM MEMBERS
INTO OUTFILE '/var/lib/mysql-files/T0100_out_mem.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0100_out_req.csv T0100_out_req.csv
rm /var/lib/mysql-files/T0100_out_req.csv

cp /var/lib/mysql-files/T0100_out_mem.csv T0100_out_mem.csv
rm /var/lib/mysql-files/T0100_out_mem.csv

echo Requests >> T0100_results.txt
python3 compareCsv.py T0100_expectedDbOutput_req.csv T0100_out_req.csv >> T0100_results.txt

echo Members >> T0100_results.txt
python3 compareCsv.py T0100_expectedDbOutput_mem.csv T0100_out_mem.csv >> T0100_results.txt

