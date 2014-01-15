Introduction:

This is an application developed by me to help professors manage their emails in college classes. Most emails professors send to their students are repetitive (eg: exam annoucements, assignment reminders, etc). This application enables professors to write emails ONCE and use them for all consecutive semesters that they teach the course.

Build Instructions:

First, ensure that you have Ant. Ant can be downloaded in Ubuntu by running 'sudo apt-get install ant'

Then, navigate to the root directory of this application and run 'ant'. Ant will use build.xml to build the binaries for this project.

Usage Instructions:

In ./dist, you will find the binary executable for this application

Usage #1: java -jar <nameofprogramfile>.jar <email_address> <password> <directory_of_textfiles> <directory_to_place_files_after_processing>

Usage #2: java -jar <nameofprogramfile>.jar regextest <regexExpression>

Email Files:

Look at SAMPLE.txt for a sample email file

A few things to remember:

1) Both the list of adresses and subject MUST be only one line in the text file, the message can take up multiple lines
2) You can name each of the text files whatever you want, it doesn't matter!
3) Don't shut down your computer right after writing the text files, wait for the emails to be sent (approx 2 mins)

For more information, please contact me at dmanuel2@illinois.edu
