# ONTU Student helper (Telegram Bot)
![logo](https://ontu.edu.ua/themes/default/images/logo.png) 

It's server side application. Telegram Bot just reading **yaml** files 
from "resources/tg bot/activity" folder and build UI for telegram application.

Get started:
* Activity Types
  * activity - used for building buttons for client
  * link - if button clicked with this type, telegram just redirect to
    content link
  * text-message - button with this type just return content text
  * multipart - smart type for building heavy message
    * This type contains:
      * @img {url} - image type with url
      * @text {text} - text type

For building UI we need create yaml file. Base yaml structure:\
activity-lang-key: {your lang key}\
activity-name: {your activity name}\
activity-text: {your activity text message (optional)}\
content:\
&#45; callback: {your callback name}\
&nbsp;&nbsp;label: {your button label}\
&nbsp;&nbsp;type: {your type (see "resources/tg bot/activity" files)}