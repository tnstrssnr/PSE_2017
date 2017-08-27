rm T0070_results.txt

mysql -u root -p69h97jnv pse_development < T0070_T0090_in.sql

echo Newman Run >> T0070_results.txt
date >> T0070_results.txt
newman run T0070.postman_collection.json >> T0070_results.txt

rm T0070_out.csv
rm /var/lib/mysql-files/T0070_out.csv

mysql -u "root" "-p69h97jnv" -D "pse_development" <<EOF
SELECT *
FROM GROUPS
INTO OUTFILE '/var/lib/mysql-files/T0070_out.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0070_out.csv T0070_out.csv
rm /var/lib/mysql-files/T0070_out.csv

python3 compareCsv.py T0070_expectedDbOutput.csv T0070_out.csv >> T0070_results.txt

