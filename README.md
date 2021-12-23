Summary
=======

bCloud Lock Widget designed for Android Jelly Bean and above.

Description
===========

This widget puts a 1x1 widget on your home screen. When pushed, it sends a POST request
to your bCloud server telling it to lock your screens.

This app needs the following permissions:

- *Vibrator* - To give haptic feedback when the widget is pushed.
- *Internet* - To contact the bCloud server.

The POST request is sent to http://domain.com/script.php?action=lock

The request contains one attribute/value pair called "apikey" which can be
used to authenticate the user.

The server URI and API key is stored in shared preferences. You are prompted
to enter this information when the widget is added. This app does not create
any shortcuts in the app drawer.

Additional information
======================

*Package name*: org.stormhub.helix.bcloud

*Date*: 2015-01-15

bCloud and the bCloud logo are intellectual property of Peter Caprioli.
