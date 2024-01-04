# Group SMSender

Group SMSender is a robust Android application built to facilitate mass messaging.

## Features

- User-friendly fragments to enter URL and message.
- Token-based authentication for added security.
- Send a specific message to multiple recipients.
- Send different messages to different recipients.

## Usage

### Initial Setup

- You can download the latest version of the Send SMS to Group app from the [releases](https://github.com/oriavsapir/Group-SMSender/releases) page.
- Install the APK file on your Android device.
- Grant necessary permissions when prompted.

### How to Use

#### Sending Same Message to Multiple Recipients
* You must to send it as POST requsts!
* you can add "token" from the app. and hanlde it in the server side for maintain security.
* The expected format in the URL should be like 'number,number' where numbers are separated by commas. Example: '050...05531,0521615..02'.

#### Sending Different Messages to Different Recipients

The expected format in the URL should be like 'number:msg,number:msg' where numbers and messages are separated by commas. Example: '050...05531:Hello,0521615..02:Hi'.

## Screenshots
![WhatsApp Image 2023-08-02 at 10 59 15 (1)](https://github.com/oriavsapir/Group-SMSender/assets/85383966/f8a7bfc0-65c9-44c6-acb3-89b9a09bd668)
![WhatsApp Image 2023-08-02 at 10 59 40 (1)](https://github.com/oriavsapir/Group-SMSender/assets/85383966/72ef9458-37d8-4d95-a882-7daf6e80c190)
![WhatsApp Image 2023-08-02 at 10 59 02 (2)](https://github.com/oriavsapir/Group-SMSender/assets/85383966/d830ab5d-b1c6-43b0-8f72-512da50f8224)


## Permissions

The app requires the following permissions:

- `INTERNET`: To fetch the recipients' list from the provided URL.
- `SEND_SMS`: To send SMS messages to the recipients.

## Contributing

Contributions to this project are welcome. If you find a bug or have an enhancement in mind, please feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Disclaimer
Please note that the Send SMS to Group app does not provide an SMS gateway or service. It utilizes the default SMS service on your Android device. You will be responsible for any costs or charges associated with sending SMS messages through your own service provider.

Ensure that you have an active SMS plan or sufficient credit with your service provider before using this app to send SMS messages. The developers of this app are not responsible for any charges or fees incurred as a result of using the app.

Use your own SMS service responsibly and in compliance with the terms and conditions of your service provider.

The Send SMS to Group app is provided as-is without any warranties or guarantees. The developers of this app are not responsible for any damages or liabilities caused by the use or misuse of this software.

Please use this app responsibly and ensure that you have the necessary permissions and legal rights to send SMS messages to the recipients. It is your responsibility to comply with applicable laws and regulations regarding SMS messaging and privacy.

The app fetches the recipients' list from a provided URL, and it is your responsibility to ensure that the URL is secure and contains valid and authorized recipients' information. The developers of this app do not have control over the content or security of the provided URLs.

By using the Send SMS to Group app, you agree to use it at your own risk. The developers of this app will not be held liable for any damages, losses, or legal issues arising from the use of this app.



### Happy messaging! 📱💌
