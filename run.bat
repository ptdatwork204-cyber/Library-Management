@echo off
chcp 65001 > nul
javac -cp ".;libs/*" -d bin Main.java ui\*.java service\*.java repository\*.java model\*.java database\*.java
java -Dfile.encoding=UTF-8 -cp "bin;libs/*" Main