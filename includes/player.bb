;Player Car
	;Body
	PCar_Body = LoadMesh( "GFX\TEMP\Chassis_TEMP.3ds" )
	PositionEntity PCar_Body, 0, -2, 5
	EntityRadius PCar_Body,1

	;Wheels
		;Front Right
		PCar_FRWheel=LoadMesh( "GFX\TEMP\Wheel_TEMP.3ds" )
		PositionEntity PCar_FRWheel, 2, 1, 8

		;Front Left
		PCar_FLWheel=CopyEntity(PCar_FRWheel)
		PositionEntity PCar_FLWheel,-2,1,8

		;Rear Right
		PCar_RRWheel=CopyEntity(PCar_FRWheel)
		PositionEntity PCar_RRWheel,2,1,-1

		;Rear Left
		PCar_RLWheel=CopyEntity(PCar_FRWheel)
		PositionEntity PCar_RLWheel,-2,1,-1

	;Player Car Entity Parents
		EntityParent camera_player, PCar_Body
		EntityParent PCar_FRWheel,PCar_Body
		EntityParent PCar_FLWheel,PCar_Body
		EntityParent PCar_RRWheel,PCar_Body
		EntityParent PCar_RLWheel,PCar_Body				

	;Player Car Entity Types(For Collision)
		EntityType PCar_Body, Coll_PlayerCar
		EntityType PCar_FRWheel, Coll_PlayerCarWheel
		EntityType PCar_FLWheel, Coll_PlayerCarWheel
		EntityType PCar_RRWheel, Coll_PlayerCarWheel
		EntityType PCar_RLWheel, Coll_PlayerCarWheel