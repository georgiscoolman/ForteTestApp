# BitmapTestApp

Task :

The task is intended to take no longer than 3 hrs.

The task does not require any diligence with layouts or design.

The task assumes that the developer would make comments of what they would've added to improve the app's performance, look and feel, if there was no set time limit.

The task can be shared via an open git repo that belongs to the developers account.

The minimum requirement is that the app can be easily downloaded from the repo, built and installed and contain no crashes or major flaws.

When performing the task, please do not use any networking libraries (such as Universal Image Loader, Retrofit, Volley, OkHttp, Ion etc). Appcompat libraries can be used if necessary.

There are 2 images that need to be displayed on activity A as shown in the picture attached.

The user should be able to select one of the images, and the floating action button should take the user to activity B. Activity B will show only 1 image that the user selected. When the user returns to Activity A, the selected image should still be selected

Images to display:

 - http://www.heartofgreen.org/.a/6a00d83451cedf69e201a73dcaba0a970d-pi

 - http://images5.fanpop.com/image/photos/27900000/Ocean-Animals-animals-27960311-1920-1200.jpg
 
My solution:
	
-Performance

1) Uploading big resolution images in to memory is absolutely wrong. 
So I use attribute inJustDecodeBounds by object BitmapFactory.Options().
It get me opportunity to get resolution of image without uploading it in to memory.
Then I get ratio of my desired image size to original. I can use it for inSampleSize attribute of BitmapFactory.Options().
It help me upload desired imege size that can save a lot of free memory space or even avoid OutOfMemoryexception on small memory devices.  

2)When I decode file i use Bitmap.Config.RGB_565 instead of default ARGB_8888. By that I turn off transparent pixel processing. It also save half of using memory.

3)In manifest I set android:largeHeap="true" for geting application more memory space that usualy.

4)All work with images I put out of UI thread.

-UI

1)I make additional layout for horizontal orientation for better look.

P.S.
	I think that it is wrong to upload big images at application recources.
	So you should put pictures to default "Downloads" folder on your Android device.
	For your comfort I downloaded images and put it to "pictures" folder in root of the project.