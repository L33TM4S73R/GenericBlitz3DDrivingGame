;Player Car
	;Body
		PCar_Body = LoadMesh( "GFX\TEMP\Chassis_TEMP.3ds" )
			EntityRadius PCar_Body,1
			EntityType PCar_Body,Coll_PlayerCar
		
	;Wheels
		Global PCar_Wheels[4]

			cnt=1
			For z#=1.5 To -1.5 Step -3
				For x#=-1 To 1 Step 2
;					PCar_Wheels[cnt]=LoadMesh( "GFX\TEMP\Wheel_TEMP.3ds" )( PCar_Body )
					PCar_Wheels[cnt]=CreateSphere( 8,PCar_Body )
						EntityAlpha PCar_Wheels[cnt],.5
;						ScaleEntity PCar_Wheels[cnt],.5,.5,.5
						EntityRadius PCar_Wheels[cnt],.5
						PositionEntity PCar_Wheels[cnt],x,0,z
						EntityType PCar_Wheels[cnt],Coll_PlayerCarWheel
					cnt=cnt+1
				Next
			Next

	target=CreatePivot( PCar_Body )
	PositionEntity target,0,5,-12