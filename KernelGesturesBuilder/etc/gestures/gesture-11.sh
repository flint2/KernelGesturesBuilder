	# Toggle between the last 2 activities (excluding TW launcher)

        service call vibrator 2 i32 100 i32 0
        dumpsys activity a | grep "Recent #1:.* com.anddoes.launcher"
        if [ "$?" -eq "0" ]; then
            service call activity 24 i32 `dumpsys activity a | grep "Recent #2:" | grep -o -E "#[0-9]+ " | cut -c2-` i32 2
        else
            service call activity 24 i32 `dumpsys activity a | grep "Recent #1:" | grep -o -E "#[0-9]+ " | cut -c2-` i32 2
        fi
	
        