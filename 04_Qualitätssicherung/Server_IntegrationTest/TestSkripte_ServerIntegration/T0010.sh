rm T0010_results.txt
mysql -u root -p?? pse_development < T0010_in.sql

echo Newman Run >> T0010_results.txt
date >> T0010_results.txt
newman run T0010.postman_collection.json >> T0010_results.txt

rm T0010_out.csv
rm /var/lib/mysql-files/T0010_out.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
SELECT *
FROM USERS
INTO OUTFILE '/var/lib/mysql-files/T0010_out.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0010_out.csv T0010_out.csv
rm /var/lib/mysql-files/T0010_out.csv

python3 compareCsv.py T0010_expectedDbOutput.csv T0010_out.csv >> T0010_results.txt


