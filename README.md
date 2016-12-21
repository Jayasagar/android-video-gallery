###### Solution 
* Store video and thumbnail in the internal storage so that it is specific to application
* Store video information(Name, duration, URIs) in the SQLLite so that we can easily query and present to user 
* On click play video

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

###### Remember
* min 4.1
* 

###### Ref
* SQLLite : https://github.com/satyan/sugar
* 