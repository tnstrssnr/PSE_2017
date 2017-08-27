rm T0260_results.txt

mysql -u root -p69h97jnv pse_development < T0040_T0250_T0260_in.sql

echo Newman Run >> T0260_results.txt
date >> T0260_results.txt
newman run T0260.postman_collection.json >> T0260_results.txt

rm T0260_out.csv
rm /var/lib/mysql-files/T0260_out.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOS
INTO OUTFILE '/var/lib/mysql-files/T0260_out.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0260_out.csv T0260_out.csv
rm /var/lib/mysql-files/T0260_out.csv

echo GOS >> T0250_results.txt
python3 compareCsv.py T0260_expectedDbOutput.csv T0260_out.csv >> T0260_results.txt




