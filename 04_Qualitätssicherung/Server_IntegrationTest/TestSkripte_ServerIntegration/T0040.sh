rm T0040_results.txt

mysql -u root -p?? pse_development < T0040_T0250_T0260_in.sql

echo Newman Run >> T0040_results.txt
date >> T0040_results.txt
newman run T0040.postman_collection.json >> T0040_results.txt

