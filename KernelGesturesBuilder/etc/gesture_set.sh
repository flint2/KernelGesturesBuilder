#!/sbin/busybox sh

#
# Sample touch gesture actions by Tungstwenty - forum.xda-developers.com
# Modded by GM , dorimanx and flint2.

GESTURES_PATH=/data/gestures

# Load all gesture definitions, removing comments to reduce the total size
cat $GESTURES_PATH/gesture-*.config | sed -e 's/#.*$//' > /sys/devices/virtual/misc/touch_gestures/gesture_patterns

# Start loop listening for triggered gestures
( while [ 1 ]
do

    GESTURE=`cat /sys/devices/virtual/misc/touch_gestures/wait_for_gesture`

    # Launch the action script if it exists, not spawning a separate process
    GESTURE_SCRIPT="$GESTURES_PATH/gesture-$GESTURE.sh"
    if [ -f $GESTURE_SCRIPT ]; then
        . $GESTURE_SCRIPT
    fi

done ) > /dev/null 2>&1 &
