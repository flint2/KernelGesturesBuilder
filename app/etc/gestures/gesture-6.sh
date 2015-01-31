		# Toggle bluetooth on/off
		service call bluetooth 1 | grep "0 00000000" > /dev/null
		if [ "$?" -eq "0" ]; then
			service call bluetooth 3 > /dev/null
		else
			[ "$1" -eq "1" ] && service call bluetooth 5 > /dev/null
			[ "$1" -ne "1" ] && service call bluetooth 4 > /dev/null
		fi;