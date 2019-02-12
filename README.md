News_demo
===========
引导页
https://github.com/PaoloRotolo/AppIntro 

CSDN
https://blog.csdn.net/c__chao/article/details/78573737

 新闻API
https://www.apiopen.top/journalismApi

姬长信API
https://api.isoyu.com/#/

天气API
https://api.isoyu.com/index.php/api/Weather/get_weather?city=
 
[ ღ⊙□⊙╱ ] 一个列子，搞懂Android RecyclerView！
https://www.jianshu.com/p/4782824d9307
 
DrawerLayout
https://github.com/IvanBean/Day18
 
 设置页
https://github.com/ComeOnKissMe/SettingsActivity



介绍
=====
首次启动

![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/FirstStart.gif)

新闻列表RecyclerView用了[[ ღ⊙□⊙╱ ] 一个列子，搞懂Android RecyclerView！](https://www.jianshu.com/p/4782824d9307)

![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/News.gif)

新闻详细内容和分享功能，新闻详细内容，是一个内嵌网页，用webView控件显示

![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/tetail%20share.gif)

新闻的刷新功能，并不会添加新的数据！！！

![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/Refresh.gif)

天气页,显示当天天气，当天的生活指数，实时温度，空气质量和未来三天的天气，默认城市是深圳，设置页里更改城市位置
demo里写了四个城市作为演示，可以values.arrays.xml添加城市

![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/weather.gif)

获取新闻内容信息需要时间，启动时候会有一个简单的实时天气，显示城市，实时温度和今天大致天气状况

![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/Started.gif)

简单的实时天气可以在设置里面关闭

![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/Switch.gif)

"关于"分两种方法点击跳转到Github的demo上

![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/about.gif)
![gif](https://github.com/ComeOnKissMe/News_demo/blob/master/gif/about1.gif)

用法：
=====

将JitPack存储库添加到构建文件中。将其添加到存储库末尾的根build.gradle中：


allprojects {

		repositories {
  
			...
   
			maven { url "https://jitpack.io" }
   
		}
  
	}
 
添加的依赖项

dependencies {

    implementation 'com.android.support:recyclerview-v7:28.0.0'
    
	   implementation 'com.github.bumptech.glide:glide:4.7.1'
    
    implementation 'com.github.paolorotolo:appintro:v4.2.3'
    
    implementation 'com.getbase:floatingactionbutton:1.9.0'
    
    implementation group: 'com.android.support', name: 'preference-v7', version: '28.0.0'
    
    implementation 'com.android.support:design:28.0.0'
    
	}
 
 另外
 =====
 代码有注释，没有注释的请看上方的链接！！！
