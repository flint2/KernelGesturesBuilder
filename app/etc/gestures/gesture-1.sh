	mdnie_status=`cat /sys/class/mdnie/mdnie/negative`
	if [ "$mdnie_status" -eq "0" ]; then
		echo 1 > /sys/class/mdnie/mdnie/negative
	else
		echo 0 > /sys/class/mdnie/mdnie/negative
	fi;