rm T0090_results.txt

rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/requestReceived_json.txt
rm /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/requestReceived_rec.txt

mysql -u root -p?? pse_development < T0070_T0090_in.sql

echo Newman Run >> T0090_results.txt
date >> T0090_results.txt
newman run T0090.postman_collection.json >> T0090_results.txt

rm T0090_out.csv
rm /var/lib/mysql-files/T0090_out.csv

mysql -u "root" "-p??" -D "pse_development" <<EOF
SELECT *
FROM REQUESTS
INTO OUTFILE '/var/lib/mysql-files/T0090_out.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
EOF

cp /var/lib/mysql-files/T0090_out.csv T0090_out.csv
rm /var/lib/mysql-files/T0090_out.csv

python3 compareCsv.py T0090_expectedDbOutput.csv T0090_out.csv >> T0090_results.txt

python3 compareJson.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/requestReceived_json_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/requestReceived_json.txt >> T0090_results.txt

python3 compareRecs.py /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/requestReceived_rec_exp.txt /home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/requestReceived_rec.txt >> T0090_results.txt

