rm T0060_results.txt
#./deleteAllData.sh

mysql -u root -p69h97jnv pse_development < T0060_T0360_in.sql

echo Newman Run\n >> T0060_results.txt
date >> T0060_results.txt
newman run T0060.postman_collection.json >> T0060_results.txt

rm T0060_out.csv
rm /var/lib/mysql-files/T0060_out.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GROUPS
INTO OUTFILE '/var/lib/mysql-files/T0060_out.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0060_out.csv T0060_out.csv
rm /var/lib/mysql-files/T0060_out.csv

python3 compareCsv.py T0060_expectedDbOutput.csv T0060_out.csv >> T0060_results.txt

