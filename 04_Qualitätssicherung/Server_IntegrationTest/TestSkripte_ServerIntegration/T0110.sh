rm T0110_results.txt

mysql -u root -p?? pse_development < T0100_T0110_in.sql

echo Newman Run >> T0110_results.txt
date >> T0110_results.txt
newman run T0110.postman_collection.json >> T0110_results.txt

rm T0110_out_req.csv
rm /var/lib/mysql-files/T0110_out_req.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
SELECT *
FROM REQUESTS
INTO OUTFILE '/var/lib/mysql-files/T0110_out_req.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

rm T0110_ou_mem.csv
rm /var/lib/mysql-files/T0110_out_mem.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
SELECT *
FROM MEMBERS
INTO OUTFILE '/var/lib/mysql-files/T0110_out_mem.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0110_out_req.csv T0110_out_req.csv
rm /var/lib/mysql-files/T0110_out_req.csv

cp /var/lib/mysql-files/T0110_out_mem.csv T0110_out_mem.csv
rm /var/lib/mysql-files/T0110_out_mem.csv

echo Requests >> T0110_results.txt
python3 compareCsv.py T0110_expectedDbOutput_req.csv T0110_out_req.csv >> T0110_results.txt

echo Members >> T0100_results.txt
python3 compareCsv.py T0110_expectedDbOutput_mem.csv T0110_out_mem.csv >> T0110_results.txt

