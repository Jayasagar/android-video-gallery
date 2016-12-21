###### Solution 
* Store video in the internal storage so that it is specific to application
* Store video information(Name, duration, URIs) in the SQLLite so that we can easily query and present to user 
* On click play video

###### Expected/Not Clear
* On click on video plays continuously
* 

###### Pending
* Card view margin top -- DONE
* Order By creation date - ORM - DONE
* Play video on click - DONE
** Stop continuous playing -- Not Clear to fix - TODO
* Infinite Scroll -- DONE
* Date conversion -- DONE
* User to fill the video name
* Loggers
* Notify adapter after Recorded Video -- DONE
* Play icon on card --

###### Features
* Video recording
** Start Camera intent/Trigger a camera app
** Listen on stop
** compress video to max extent
* https://developer.android.com/training/camera/videobasics.html
* Store videos
** Save with in app ?? -- Spike ?
** App-specific files/ Internal app-specific directories
*** /data/data/your.package.name/
*** Sub directory: databases – for SQLite databases
                     
** Choose storage ?
** DB?
** Shared Pref?
** Internal storage?
** 
* Play/Display videos
** play -- Start in video player app -- Spike ??
** Thumbnail of the video - Spike ??
** Duration
** Creation time
** Video Name
* https://developer.android.com/training/camera/videobasics.html

###### Issues
*  Android: Sugar ORM No Such Table Exception: it's related to Instant Run. Disable instant run and it works
* Date query lt: https://github.com/satyan/sugar/issues/376
###### Remember
* min 4.1
* 

###### Improve 
* Look more into Dynamic permissions
* 

###### Ref
* Endless scroll: https://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView
* SQLLite : https://github.com/satyan/sugar
* Camera dont have internal storage permissions: http://stackoverflow.com/questions/13402187/android-action-image-capture-with-extra-output-in-internal-memory