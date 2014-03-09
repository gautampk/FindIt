FindIt
=======

Developed for IC Hack '14: hackathon.io/findit

FindIt is an app for Google Glass that acts as your virtual memory. You can
get FindIt to remember anything for you, simply by taking a photo. Later, select
the photo, and FindIt will tell you how to get back.

## Storing Stuff

Just say "Okay Glass, remember." FindIt open the camera for you; simply take a photo and FindIt
will tag it with all the info you need to be able to find it later.

## Finding Stuff

To find something previously stored, simply say "Okay Glass, find." and
choose what you want to go back to.

## Installing

The FindIt .apk is in the /bin directory, ready to be pushed to Glass via adb.

$ adb push /bin/findit.apk /sdcard/findit.apk
$ adb install /sdcard/findit.apk