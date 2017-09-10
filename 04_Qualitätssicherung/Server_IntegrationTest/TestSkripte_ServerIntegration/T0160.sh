rm T0160_results.txt

rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/memberRemoved_json.txt
rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/memberRemoved_rec.txt

mysql -u root -p?? pse_development < T0160_T0170_T0180_in.sql

echo Newman Run >> T0160_results.txt
date >> T0160_results.txt
newman run T0160.postman_collection.json >> T0160_results.txt

rm T0160_out.csv
rm /var/lib/mysql-files/T0160_out.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
SELECT *
FROM MEMBERS
INTO OUTFILE '/var/lib/mysql-files/T0160_out.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0160_out.csv T0160_out.csv
rm /var/lib/mysql-files/T0160_out.csv

python3 compareCsv.py T0160_expectedDbOutput.csv T0160_out.csv >> T0160_results.txt

python3 compareJson.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/memberRemoved_json_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/memberRemoved_json.txt >> T0160_results.txt

python3 compareRecs.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/memberRemoved_rec_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/memberRemoved_rec.txt >> T0160_results.txt

