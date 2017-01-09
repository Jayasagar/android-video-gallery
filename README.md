##### Tested on
* Real Device - Android 4.4, 
* Real Devie - Android 6
* Genymotion - Cutom phone 7.0.0 and on 4.4 -> But somehow it has issue with camera :( -> Could not continue

###### Quick notes solution 
* Store video in the internal storage so that it is specific to application
* Store video information(Name, duration, URIs) in the SQLLite so that we can easily query and present to user
* Used Glide for Thumbnail and caching
* Used Sugar ORM for SQLLite

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

###### Issues
*  Android: Sugar ORM No Such Table Exception: it's related to Instant Run. Disable instant run and it works
* Date query lt: https://github.com/satyan/sugar/issues/376

###### Ref
* Endless scroll: https://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView
* SQLLite : https://github.com/satyan/sugar
* Camera dont have internal storage permissions: http://stackoverflow.com/questions/13402187/android-action-image-capture-with-extra-output-in-internal-memory
* play internal video file: http://stackoverflow.com/questions/27165269/play-video-from-android-internal-storage
* Android N file uri issue: https://inthecheesefactory.com/blog/how-to-share-access-to-file-with-fileprovider-on-android-nougat/en