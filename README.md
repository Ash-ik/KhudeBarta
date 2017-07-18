# KhudeBarta
KhudeBarta is an android app to send compressed bangla SMS. Each SMS can send 1120 bits for the cost of one SMS. As bangla is unicode, it holds 16 bits per character. For that we can send 75 bangla characters in the cost of one SMS. Whereas in english we can send 160 characters as it uses 7 bit encoding. For that we tend to send SMS in english character instead of bangla. With our Bangla Text Compressor app – ক্ষুদেবার্তা, we tried to resolve this problem by deploying 7 bit encoding for bangla characters. 

## How it works
KhudeBarta is a P2P app. Both the sender and receiver must have this app to communicate. When the sender sends the SMS, the text is encoded in 7 bit encoding and converted to a temporary unicode string. It appends a string as a flag in the end. The temporary string is sent through the GSM network as SMS to the receiver. In the receiver end, after receiving any SMS our app receives it via broadcast receiver. It decodes the SMS and if it finds the flag string at the end of the decoded text, it recognises the SMS as sent from our app. Only then it saves the SMS in our database and discards others. We can send 56.25% more bangla character than normal SMS with our app. It will encourage us to write SMS in bangla.

## Pre-requisites
* Android 4.1 or higher

# App design and how to use
Simple yet elegent UI with message thread    |  Sender name and number suggestions    | A set of emoticons with keyboard
:-------------------------:|:-------------------------:|:-------------------------:
![screenshot_2017-01-05-10-09-23](https://cloud.githubusercontent.com/assets/13817511/21696155/ee3af9c4-d3b6-11e6-9709-f985031d142e.jpeg)  |  ![screenshot_2017-01-05-10-05-43](https://cloud.githubusercontent.com/assets/13817511/21696152/ee37f094-d3b6-11e6-90b3-0ba924a15a8f.jpeg)    |    ![screenshot_2017-01-05-10-06-08](https://cloud.githubusercontent.com/assets/13817511/21696509/9794f320-d3b8-11e6-93e0-9d3470013d96.jpeg)


</br>
</br>
</br>


Turn on Bangla Parser from settings    |  Enjoy Typing bangla from any keyboard
:-------------------------:|:-------------------------:
![screenshot_2017-01-05-10-08-37](https://cloud.githubusercontent.com/assets/13817511/21696154/ee394e1c-d3b6-11e6-9420-b80625deee24.jpeg)  | ![screenshot_2017-01-05-10-09-11](https://cloud.githubusercontent.com/assets/13817511/21696153/ee387302-d3b6-11e6-8df4-8ec2805686aa.jpeg)


#Developer Story
It was late summer,an national app development contest was knocking at our door,but i had almost zero knowledge about android app development.But me and my two friends decided to do it anyway.Next 6 days,we worked almost 16 hour per days and finally when the contest day came we saw that many people loved our work.They were listening to us eagerly and appreciated our work for easing human life.We won the 4th position unexpectedly.This was the biggest event of my life.This was the moment when i decided to take android app development very seriously. See,no dream is too big,no dreamer is too small :)

#Achievements
* Took 4th place in IUT 7th National ICT Fest,2015 in Project Showcasing Category
* Special appreciation from honourable Judge and audience in SUST CSE Carnival,2015


#Future Plan
 This app is currently Under construction to make the features more easy and efficient.Keep the patience with us.
 Any type of suggestions are welcome.
