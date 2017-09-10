rm T0070_results.txt

rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/groupEdited_json.txt
rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/groupEdited_rec.txt

mysql -u root -p?? pse_development < T0070_T0090_in.sql

echo Newman Run >> T0070_results.txt
date >> T0070_results.txt
newman run T0070.postman_collection.json >> T0070_results.txt

rm T0070_out.csv
rm /var/lib/mysql-files/T0070_out.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
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

python3 compareJson.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/groupEdited_json_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/groupEdited_json.txt >> T0070_results.txt

python3 compareRecs.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/groupEdited_rec_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/groupEdited_rec.txt >> T0070_results.txt

