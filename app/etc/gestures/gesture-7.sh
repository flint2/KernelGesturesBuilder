		# Toggle WiFi on/off
		service call wifi 14 | grep "0 00000001" > /dev/null
		if [ "$?" -eq "0" ]; then
			service call wifi 13 i32 1 > /dev/null
		else
			service call wifi 13 i32 0 > /dev/null
		fi;
		