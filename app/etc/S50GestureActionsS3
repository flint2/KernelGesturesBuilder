#!/sbin/busybox sh

#
# Sample touch gesture actions by Tungstwenty
# Visit the thread at: http://forum.xda-developers.com/showthread.php?t=1831254
#

echo "
# Gesture 1 - swipe 1 finger near the top and one near the bottom from left to right
1:1:(0|240,0|320)     # 1st finger top-left
1:1:(530|800,0|320)   # 1st finger top-right
1:2:(0|240,960|1280)   # 2nd finger bottom-left
1:2:(530|800,960|1280) # 2nd finger bottom-right

# Gesture 2 - swipe 3 fingers from near the top to near the bottom
2:1:(0|800,0|400) 2:1:(0|800,800|1280)  # 1st finger from top to bottom
2:2:(0|800,0|400) 2:2:(0|800,800|1280)  # 2nd finger from top to bottom
2:3:(0|800,0|400) 2:3:(0|800,800|1280)  # 3rd finger from top to bottom

# Gesture 3 - draw a Z with one finger while another is pressed on the middle left
3:1:(0|240,0|320)      # top left
3:1:(530|800,0|320)    # top right
3:1:(0|240,960|1280)    # bottom left
3:1:(530|800,960|1280)  # bottom right

3:2:(0|240,300|500)    # 2nd finger on the middle left

# Gesture 4 - one finger on bottom right while another goes from top-left to middle and back
4:1:(0|240,0|320)      4:1:(180|300,340|460)  4:1:(0|240,0|320) # top-left, middle, top-left
4:2:(530|800,960|1280)  # 2nd finger on the bottom right

# Gesture 5 - one finger on bottom left while another goes from top-right to middle and back
5:1:(530|800,0|320)    5:1:(180|300,340|460)  5:1:(530|800,0|320) # top-right, middle, top-right
5:2:(0|240,960|1280)    # 2nd finger on the bottom left

# Gesture 6 - 2 fingers start from the top-left and bottom-left corners and end in the middle right, like an arrow
6:1:(0|240,0|320)     6:1:(300|800,300|500)  # top-left to middle-right
6:2:(0|240,960|1280)   6:2:(300|800,300|500)  # bottom-left to middle-right

# Gesture 7 - 1 finger draws an X starting at the top left
7:1:(0|240,0|320)     # top left
7:1:(530|800,960|1280) # bottom right
7:1:(530|800,0|320)   # top right
7:1:(0|240,960|1280)   # bottom left

# Gesture 8 - 1 finger from bottom-left, bottom-right, bottom-left, bottom-right
8:1:(0|240,960|1280)   # bottom left
8:1:(530|800,960|1280) # bottom right
8:1:(0|240,960|1280)   # bottom left
8:1:(530|800,960|1280) # bottom right

" > /sys/devices/virtual/misc/touch_gestures/gesture_patterns



# Detect ICS or JB - bluetooth calls are different
case "`getprop ro.build.version.release`" in
    4.1.* ) is_jb=1;;
    * )     is_jb=0;;
esac


( while [ 1 ]
do

    GESTURE=`cat /sys/devices/virtual/misc/touch_gestures/wait_for_gesture`
    
    if [ "$GESTURE" == "1" ]; then

        # Toggle inverted screen colors
        mdnie_status=`cat /sys/class/mdnie/mdnie/negative`
        [ "$mdnie_status" == "0" ] && echo 1 > /sys/class/mdnie/mdnie/negative
        [ "$mdnie_status" != "0" ] && echo 0 > /sys/class/mdnie/mdnie/negative
        
    elif [ "$GESTURE" == "2" ]; then
    
        # Start the camera app
        launch_flags="--activity-exclude-from-recents --activity-reset-task-if-needed"
        
        result=`am start $launch_flags com.sec.android.app.camera/.Camera 2>&1 | grep Error`
        [ "$result" != "" ] && result=`am start $launch_flags com.android.camera/.Camera 2>&1 | grep Error`
        [ "$result" != "" ] && result=`am start $launch_flags com.android.gallery3d/com.android.camera.CameraLauncher 2>&1 | grep Error`
        
    elif [ "$GESTURE" == "3" ]; then
    
        # Edit and uncomment the next line to automatically start a call to the intended phone number
        
        ### EDIT ### service call phone 2 s16 "123456789"
        echo "do nothing" > /dev/null
    
    elif [ "$GESTURE" == "4" ]; then
    
        # Toggle bluetooth on/off
        service call bluetooth 1 | grep "0 00000000" > /dev/null
        if [ "$?" -eq "0" ]; then
            service call bluetooth 3 > /dev/null
        else
            [ "$is_jb" -eq "1" ] && service call bluetooth 5 > /dev/null
            [ "$is_jb" -ne "1" ] && service call bluetooth 4 > /dev/null
        fi
        
        # Small vibration to provide feedback
        service call vibrator 2 i32 100 i32 0
        
    elif [ "$GESTURE" == "5" ]; then

        # Toggle WiFi on/off
        service call wifi 14 | grep "0 00000001" > /dev/null
        if [ "$?" -eq "0" ]; then
            service call wifi 13 i32 1 > /dev/null
        else
            service call wifi 13 i32 0 > /dev/null
        fi
        
        # Small vibration to provide feedback
        service call vibrator 2 i32 100 i32 0

    elif [ "$GESTURE" == "6" ]; then

        # Simulate key press - Play/Pause
        
        # 26 = Power
        # 3 = Home
        # 24/25 = Volume up/down
        # 85 = Media Play / pause
        # 86 = Media stop
        # 87/88 = Media next / previous
        # 164 = Volume mute / unmute
        
        input keyevent 85
        
        # Small vibration to provide feedback
        service call vibrator 2 i32 100 i32 0
    
    elif [ "$GESTURE" == "7" ]; then

        # Simulate key press - Power
        
        # 26 = Power
        # 3 = Home
        # 24/25 = Volume up/down
        # 85 = Media Play / pause
        # 86 = Media stop
        # 87/88 = Media next / previous
        # 164 = Volume mute / unmute
        # 187 = Recent apps
        
        input keyevent 26

    elif [ "$GESTURE" == "8" ]; then

        # Simulate key press - Home
        
        # 26 = Power
        # 3 = Home
        # 24/25 = Volume up/down
        # 85 = Media Play / pause
        # 86 = Media stop
        # 87/88 = Media next / previous
        # 164 = Volume mute / unmute
        # 187 = Recent apps
        
        input keyevent 3
        
    fi;

done ) > /dev/null 2>&1 &
