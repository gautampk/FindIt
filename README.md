FindIt
=======

Developed for IC Hack '14: hackathon.io/findit

FindIt is a Glass app allowing the user to store* and retrieve images with
location data, to remind you where you left items, and give directions to them.

*not yet implemented.

## Finding Stuff

To find something previously stored, simply say "Okay Glass, remind me." and
choose the image of the thing you wanted to be reminded the location of.

## Storing Stuff

Not yet implemented.

## Installing

The FindIt .apk is in the /bin directory, ready to be pushed to Glass via adb.

$ adb push /bin/findit.apk /sdcard/findit.apk

$ adb install /sdcard/findit.apk