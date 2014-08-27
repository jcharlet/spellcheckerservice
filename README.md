spellcheckerservice
===================

Simple spell checker service composed by server and client

Functionalities
	Command line interface that allows to check words from a file with a database. The user can add new words to that database and in different languages.


It consists of
- a WS: wordrepository
- a Command Line Interface written in java: cli

The folder deliverables contains:
- the database dump to start the application
- some test files



To run it, you must 
- have database on localhost:3306 with user:"root", password:""
- use db dump to create the schema, tables and insert data
- run with "java -jar" either on windows or linux the two jar:
     server: wordrepository
     client: cli
