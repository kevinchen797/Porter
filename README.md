# KuaiChuan(仿茄子快传)
[English](https://github.com/dreamfish797/KuaiChuan/blob/master/README_EN.md)

仿茄子快传的一款文件传输应用， 涉及到Socket通信，包括TCP，UDP通信。（喜欢的给一个star, 有帮助的给一个fork， 欢迎Star和Fork ^_^）


## 效果预览

### 主页 ###
![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/home.gif)
### 文件发送端 ###
![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/fs_1.gif)
![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/fs_2.gif)
![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/fs_3.gif)
### 文件接收端 ###
![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/fr_1.gif)
![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/fr_2.gif)

### 网页传 ###
![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/w_1.gif)

![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/w_2.jpg)
![Alt text](https://github.com/dreamfish797/KuaiChuan/blob/master/ScreenShot/w_3.jpg)

## 原理


快传有两种方式可以传输文件：

1. Android应用端发送到Android应用端（必须安装应用）
2. 通过Web浏览器来实现文件的传送 （不必安装应用）

第一种方式主要是是通过设备间发送文件。 文件传输在文件发送端或者是文件接收端通过自定义协议的Socket通信来实现。由于文件接收方和文件发送方都要有文件的缩略图，这里采用了header + body的自定义协议, header部分包括了文件的信息（长度，大小，缩略图）， body部分就是文件。

第二种方式主要是在android应用端架设微型Http服务器来实现文件的传输。这里可以用ftp来实现，为什么不用ftp呢？因为没有缩略图，这是重点！

## issue

Email:cmy797@126.com


## License
    Copyright 2017 dreamfish

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.