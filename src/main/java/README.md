##Important
###For Laptop or any usb to laptop Cameras

Default; if reconverting, just add getLaptopMat in the RecognizeFace class (line 58)

    Note: If I rename later: search for "//CAMERA CHANGE HERE" 
    
###For a camera connected to the raspberry pi
1. Make sure you are running Version 1.4 on the raspberry pi (WPIlib)
2. Attach the java-multiCameraServer.jar to raspberry pi (follow readme there)
3. Start up romi (monitor Romi webconsole output)
4. Note if you want to restart the camera, terminate the process in romi websconsole
5. Make sure getMat is set at previously  mentioned place

    Note: There is a delay which varies from like 100ms to a couple seconds; don't know why it varies so much.

