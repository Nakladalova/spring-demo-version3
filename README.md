DESIGN OF COMPUTER EXERCISES FOR SECURITY OF INFORMATION SYSTEMS
Author: Kateřina Nakládalová

This project includes a web application in Java programming language to demonstrate attacks targeting it. The application is built using the Spring framework and connected to a PostgreSQL database. The most common web attacks are demonstrated and described
applications, which are Brute force attack, SQL injection attack, Cross-site scripting attack and Cross-site request forgery, access control vulnerabilities, sensitive data exposure, inconsistency data in the web application, and interference with the manipulation of web application parameters. Specific ways of implementing protection against these attacks are given to ensure their safe use.

Usage:

1. Fill database "sales" in PostgreSQL with data from file sales.sql
2. Run application Time Zone
3. Try Brute force attack with script bruteforceattack.py in Python. Download file with passwords rockyou.txt and change file path in script to launch the attack. 
   File path in script is now: file = open("C:\\Files_2023_BP_Nakladalova_Katerina_220713\\rockyou.txt", "r")
4. Try attack Cross-site request forgery with file surprice.html
5. Try other web application attacks described in thesis


