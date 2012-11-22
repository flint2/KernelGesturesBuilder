#!/sbin/busybox sh

#
# Sample touch gesture actions by Tungstwenty - forum.xda-developers.com
# Modded by GM , dorimanx and flint2.

GESTURES_PATH=/data/gestures

FILE_NAME=$0;

# Load all gesture definitions, removing comments to reduce the total size
cat $GESTURES_PATH/gesture-*.config | sed -e 's/#.*$//' > /sys/devices/virtual/misc/touch_gestures/gesture_patterns

# Detect ICS or JB - bluetooth calls are different
case "`getprop ro.build.version.release`" in
	4.2* ) is_jb=1;;
	4.1* ) is_jb=1;;
	* )    is_jb=0;;
esac

# Start loop listening for triggered gestures
( while [ 1 ]
do

    GESTURE=`cat /sys/devices/virtual/misc/touch_gestures/wait_for_gesture`

    # Launch the action script if it exists, not spawning a separate process
    GESTURE_SCRIPT="$GESTURES_PATH/gesture-$GESTURE.sh"
    if [ -f $GESTURE_SCRIPT ]; then
    
    	log -p i -t $FILE_NAME "*** GESTURE ***: gesture $GESTURE detected, executing $GESTURE_SCRIPT";
    
        . $GESTURE_SCRIPT $is_jb
        
        service call vibrator 2 i32 100 i32 0
        
    fi

    sleep 1

done ) > /dev/null 2>&1 &
