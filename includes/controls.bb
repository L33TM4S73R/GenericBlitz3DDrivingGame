; Keyboard & Mouse Controls
;Function object_key_control( obj )
;run_snd=True ;Put this at the end of 'reverse' or 'forward'
	If KeyDown( forward_key ) Or KeyDown( reverse_key ) And KeyDown( left_key ) TurnEntity PCar_Body,0,1,0
	If KeyDown( forward_key ) Or KeyDown( reverse_key ) And KeyDown( right_key ) TurnEntity PCar_Body,0,-1,0
	If EntityCollided( PCar_Body,Coll_Terrain )
		If KeyDown( forward_key )
			speed=speed+.02
			If speed>.7 speed=.7;: run_snd=True
		Else If KeyDown( reverse_key )
			speed=speed-.01
			If speed<-.5 speed=-.5
		Else
			speed=speed*.9
		EndIf
		MoveEntity PCar_Body,0,0,speed
		TranslateEntity PCar_Body,0,GRAVITY,0
	Else
		TranslateEntity PCar_Body,x_vel,y_vel+GRAVITY,z_vel
	EndIf
	If run_snd Then run_vol#=.5 Else run_vol#=0
	
;	objx#=EntityX(obj):objz#=EntityZ(obj)		; Make sure object is on Terrain
;	PositionEntity obj, objx, TerrainY( temp_land, objx, 0, objz ), objz
;End Function