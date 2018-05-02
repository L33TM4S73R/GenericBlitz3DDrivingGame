;Player Car
	;Body
		PCar_Body = LoadMesh( "GFX\TEMP\Chassis_TEMP.3ds" )
			EntityRadius PCar_Body,1
			EntityType PCar_Body,Coll_PlayerCar
		
	;Wheels
		Global wheels[4]

			cnt=1
			For z#=1.5 To -1.5 Step -3
				For x#=-1 To 1 Step 2
;					wheels[cnt]=LoadMesh( "GFX\TEMP\Wheel_TEMP.3ds" )( 8,PCar_Body )
					wheels[cnt]=CreateSphere( 8,PCar_Body )
						EntityAlpha wheels[cnt],.5
						ScaleEntity wheels[cnt],.5,.5,.5
						EntityRadius wheels[cnt],.5
						PositionEntity wheels[cnt],x,0,z
						EntityType wheels[cnt],Coll_PlayerCarWheel
					cnt=cnt+1
				Next
			Next

	target=CreatePivot( PCar_Body )
	PositionEntity target,0,5,-12