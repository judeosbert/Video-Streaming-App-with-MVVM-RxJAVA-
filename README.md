# FailDragon🤪

An Android native mobile app copies TikTok ( https://www.tiktok.com ) mechanics and use https://livestreamfails.com videos as a source.
Since the assignment statement only asks to use the videos from livestreamfails.com to be used as source, I found an alternative way to get the video links.
Looking at the videos in the site, the inital approach would be to scrap the site using some html parser, but then the issue of pagination will block the approach and wouldnt we that efficient too. The site itself dosen't provide any APIs. Looking at the reddit page https://reddit.com/r/LivestreamFail/ the same videos appear in the site with the exact number of votes from reddit. Essentialy the site is using reddit as their data source, which means all posts are available in reddit, which provides a clean api for pagination and json results.
I have chosen the minimum API Level to be 21 since it provides approximately 85% device coverage. 

The problem dosen't end there. The links in the post are links to webpages that renders the video inside a page and provides links only to embed the video, which is not what we are looking for. Searching around and looking at twitch leecher and similar projects I found that `https://clips.twitch.tv/api/v2/clips/{$videoname}/status`
provides links of different quality in json format. We get `$videoname` from the post in reddit.
For Example
https://clips.twitch.tv/PrettyCrepuscularClamBabyRage 
is the url in reddit post and `$videoname = PrettyCrepuscularClamBabyRage` 
and
https://clips.twitch.tv/api/v2/clips/PrettyCrepuscularClamBabyRage/status
yields
`{"status":"created","quality_options":[{"quality":"720","source":"https://clips-media-assets2.twitch.tv/AT-cm%7C453722286.mp4","frame_rate":30},{"quality":"480","source":"https://clips-media-assets2.twitch.tv/AT-cm%7C453722286-480.mp4","frame_rate":30},{"quality":"360","source":"https://clips-media-assets2.twitch.tv/AT-cm%7C453722286-360.mp4","frame_rate":30}]}`

Now we have all the data, but reddit contains more than just clips, It includes images too, from which we filter out results that are not from the domain `clips.twitch.tv`.
We chain two calls, one to get the reddit posts and another to get the corresponding JSON links from twitch and WHOLA!! we have a working app with proper data.

To improve app perfomance I have done caching of videos and also used pagination library from google for smooth experience. The intial load time may be a bit more, its just waiting around for the initial network call.😁

## Some Decisions

I initially had plans to integrate firebase in to the app to provide a voting mechanism and a fragment for showing videos from people whom you have followed. It just did not fit into the timeline.
The UI is very simple, it has two text views for post owner name and its title and ofcourse the video view.  The UI from TikTok consists a few more of additional views like like buttons, profile image etc. I think it wouldn't be drastically to different to implement. 

###Final Thoughts
I really enjoyed working on the project. It was a steep learning curve for me. Many of the things in the project are my first time attempts. I initally thought putting a VideoView in the RecyclerView and getting video urls and setting source would do the trick, BOY I WAS WRONG. 😆

Thank you for the experience, and Looking forward to hearing from you. 😁

-Jude Osbert K
