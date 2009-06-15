DEMO INSTALLATION:
:::::::::::::::::::::

Standalone Demo (no server-functionality available):
....................................................
1. you can use the demo by double clicking on the kafenio_preview.html page.
   please note that you will not be able to use the image-insert and insert internal
   link features until you followed the next steps.

Client-Server-Demo:
...................
1. modify the url in the file getDemoData.php to point to the url where the file
   getDemoData.php is located. (i.e.: http://www.yourserver.com/path/to/getDemoData.php)

2. replace all ocurrences of "http://www.xeinfach.de/media/projects/kafenio/demo/" 
   in the files kafenio_preview.html and kafenio_multi_preview.html with the path to your 
   demo-directory on your webserver.

3. upload the folders "demo" and "screenshots to a web-accessible directory on
   a webserver of your choice.

4. make postTester.php and getDemoData.php executable (i.e.: chmod 777)

5. call the url to kafenio_preview.html in your browser
   (i.e.: http://www.yourserver.com/path/to/demo/kafenio_preview.html)

6. you can now save the written content using the green button in the toolbar
