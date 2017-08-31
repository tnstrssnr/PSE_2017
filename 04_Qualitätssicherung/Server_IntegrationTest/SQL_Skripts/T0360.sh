rm T0360_results.txt

mysql -u root -p69h97jnv pse_development < T0060_T0360_in.sql

echo Newman Run >> T0360_results.txt
date >> T0360_results.txt
newman run T0360.postman_collection.json >> T0360_results.txt

rm T0360_out.csv
rm /var/lib/mysql-files/T0050_out.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GOS
INTO OUTFILE '/var/lib/mysql-files/T0360_out.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0360_out.csv T0360_out.csv
rm /var/lib/mysql-files/T0360_out.csv

python3 compareCsv.py T0360_expectedDbOutput.csv T0360_out.csv >> T0360_results.txt
