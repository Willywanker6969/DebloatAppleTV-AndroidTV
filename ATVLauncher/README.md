# Instructions to Build and Install ATV Launcher

## Prerequisites
- Android Studio or Android SDK with build tools installed
- ADB connected to your TV (192.168.10.7:43023)

## Build
1. Open the project in Android Studio, or run uild.bat (Windows) on this folder
2. The APK will be generated at: pp/build/outputs/apk/debug/app-debug.apk

## Install
`ash
adb -s 192.168.10.7:43023 install -r app/build/outputs/apk/debug/app-debug.apk
`

## Set as Default Launcher
1. Press Home on your Chromecast HD
2. When prompted, choose "ATV Launcher" and select "Always"
3. Press Home again to see your new Apple TV-style home screen

## Home Assistant Integration
Send a broadcast from Home Assistant to trigger alerts:
`yaml
shell_command:
  doorbell_alert:
    command: adb -s 192.168.10.7:43023 shell am broadcast -a com.atv.launcher.HA_ALERT -n com.atv.launcher/.HAReceiver --es title "Front Door" --es message "Someone is at the door!" --es image "https://your-camera-url/image.jpg"
`

Or directly from your device:
`ash
adb -s 192.168.10.7:43023 shell am broadcast -a com.atv.launcher.HA_ALERT -n com.atv.launcher/.HAReceiver --es title "Doorbell" --es message "Motion detected" --es image "http://YOUR_IMAGE_URL.jpg"
`

## Features
- Apple TV style horizontal app rows
- Nature wallpapers that cycle every 30 seconds
- App icons scale up on focus with white border
- Home Assistant alert overlay in top-right corner (auto-hides after 10s)
- Press Back to stay on home screen
- Automatically loads all launchable apps
