# Scope and Context

Create a simple __NextJS__ web application passed the requirements below 

Note:
- __Using NextJS is required__.
- Though we have resources to make a codebase, we would like to keep you free to define your own project structure ( respecting your taste ;) ).
- You're not required to finish all the requirements. But you know, try to get as far as you can, we're here with you.

*Feel free to use any kind of extra components you want but keep in mind the more you rely on available tools, the harder it will be to custom them when needed.

# Requirements

In PostPrime, we love video playing/streaming features. We're going to create a simple videos board application for this test.

1. Create a video component as the sample in below video :))

![video_01](../../videos/video_01.mp4)

Note that you can just focus on the video player (and its control buttons), no need to add the below white part.

2. As a matter of course, a video should have a thumbnail to display when it is still on loading. Let's make our video player the ability to show a "customed thumbnail" (not the first frame of the video) when it not yet playing.

3. Since PostPrime is a SNS, not just a simple video player application. Your created video player will be used to display __multi videos__ in the timeline of our users. Let's make a container (to fake the users' timeline) and place our video player there, everything will look so nice

![video_02](../../videos/video_02.mp4)

4. If you pay close attention, we really need an efficient way to make sure users don't get bothered by multiple videos playing at the same time. Let's add this ability to our application too.


Please make sure in any case, only __one__ video could be played at a time. Besides, the audio of each video should be well controlled too, we leave it to you

5. As a modern SNS, PostPrime would like to offer users the best experience. That would be nice if our users could what video on their screen side and still read through the timeline. Let's add Picture in picture playing mode for our application.

6. Our backend engineers're trying them best to make sure no error could be occurred in video playing features. But you know, no bugs system doesn't exist! Let's add some kind of warning things so that users know that your video is not available, just stay calm.

7. You're getting that far, that's great 🙌 Beyond this line, everything is up to you. We still have a ton of problems which need to be addressed besides with so much ideas we want to make it come true ;) Try to impress us with what you can something like handling exceptions or improving how video streams handle slow or oversized videos, etc. We're hearing your voice.

## Other notes

We highly appreciate high-quality / production-ready code. Besides, well structured and easy to run is a good point (makes the one who checks your submission not stuck on running your code is a wise choice), so please prepare a docker image that runs your application as side with the source code as your submission.
