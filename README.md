# KhudeBarta
KhudeBarta is an android app to send compressed bangla SMS. Each SMS can send 1120 bits for the cost of one SMS. As bangla is unicode, it holds 16 bits per character. For that we can send 75 bangla characters in the cost of one SMS. Whereas in english we can send 160 characters as it uses 7 bit encoding. For that we tend to send SMS in english character instead of bangla. With our Bangla Text Compressor app – ক্ষুদেবার্তা, we tried to resolve this problem by deploying 7 bit encoding for bangla characters. 

##How it works
KhudeBarta is a P2P app. Both the sender and receiver must have this app to communicate. When the sender sends the SMS, the text is encoded in 7 bit encoding and converted to a temporary unicode string. It appends a string as a flag in the end. The temporary string is sent through the GSM network as SMS to the receiver. In the receiver end, after receiving any SMS our app receives it via broadcast receiver. It decodes the SMS and if it finds the flag string at the end of the decoded text, it recognises the SMS as sent from our app. Only then it saves the SMS in our database and discards others. We can send 56.25% more bangla character than normal SMS with our app. It will encourage us to write SMS in bangla.

##Pre-requisites
* Android 4.1 or higher
