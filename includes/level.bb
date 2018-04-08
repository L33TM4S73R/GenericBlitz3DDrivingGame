; Load level skybox and light
Include "includes\skybox.bb"
AmbientLight 200, 200, 200
sun=CreateLight(2)
; Reduce the default light range of 1000 down to 20
LightRange sun, 20

; Gravity and such
Const GRAVITY#=-0.01 ;gravity constant

; Load World Objects Here

Type Projectile			
   Field sprite, time_out
End Type

Type Explosion			
   Field sprite, scale#
End Type

Type Sprite	
	Field ID, char_tex
	Field pt1, pt2
	Field targetpt
	Field temper
	Field speech$	; Let's add some conversation!
End Type

; MAKE TERRAIN!
Global temp_land = LoadTerrain( "GFX\TEMP\TEMP_TERRAIN_HMAP.JPG" )
temp_land_texture = LoadTexture( "GFX\TEMP\TEMP_TERRAIN.PNG" )
ScaleTexture temp_land_texture, 10, 10
EntityTexture temp_land, temp_land_texture
PositionEntity temp_land,-512,-8,-512
ScaleEntity temp_land,2,25,2

; Load Mesh
; miami_day = LoadMesh("miami_day.3ds")
; ScaleEntity miami_day, .25, .25, .25
; PositionEntity miami_day, 0, TerrainY( temp_land, 0, 0, 30 ), 30
; EntityType miami_day, coll_objects

; Load MENU Sprites #########################
Include "includes\menu.bb"

; Load HUD Sprites ##########################
Include "includes\hud.bb"

; Annoying Character Handling Shit That ALWAYS Seems to Fukkin CRASH when changed!!!

explosion_sprite=LoadSprite( "explosion.bmp" )
HideEntity explosion_sprite

projectile_sprite=LoadSprite( "GFX\OVERLAYS\BULLET.PNG" )
EntityRadius projectile_sprite, 2
EntityType projectile_sprite, coll_projectile
HideEntity projectile_sprite

For C=1 To 3
	Character.Sprite = New Sprite
	Character\ID = CreateSprite()
	Read temper, img$, X, Y, Z, X2, Y2, Z2, speech$		;adding speech variable
	Character\speech$ = speech$
	speech$=""		; Clear the speech variable 
		
	Character\char_tex=LoadAnimTexture( img$,7,32,48,0,4)
	EntityTexture Character\ID, Character\char_tex, 1
	HandleSprite Character\ID,0,-1
	ScaleSprite Character\ID, 2, 2
	SpriteViewMode Character\ID,3
	EntityAutoFade Character\ID,100,120
	PositionEntity Character\ID, X, Y, Z

	Character\temper = temper
	Character\pt1 = CreatePivot() : Character\pt2 = CreatePivot()
	
	; Adjust Path Endpoints to Terrain Level
	PositionEntity Character\pt1, X, TerrainY( temp_land, X ,0, Z ), Z	
	PositionEntity Character\pt2, X2, TerrainY( temp_land, X2 ,0, Z2 ), Z2

	Character\targetpt = Character\pt2
	EntityRadius Character\ID, 2	
	EntityType Character\ID, coll_characters
	EntityPickMode Character\ID,1		;Add to pickables
Next

.Characters 
Data 0, "char2.bmp", 30, 0, 30, 30, 0, 0, "Test 1"	;adding speech lines(remove this)	
Data 1, "char3.bmp",-30, 0, 30, 20, 0, 30, "Test 2"
Data 2, "char4.bmp", 0, 0, 30, -30, 0, 0, "Test 3"

; Playerstuff (I REALLY should move this from level.bb to it's own thing)
;Player Car
	;Body
	PCar_Body = LoadMesh( "GFX\TEMP\Chassis_TEMP.3ds" )
	PositionEntity PCar_Body, 0, 1, 5
	EntityRadius PCar_Body,1
	ScaleEntity PCar_Body, .08, .08, .08
;	RotateEntity PCar_Body,0,180,0

	;Wheels
		;Front Right
		PCar_FRWheel=LoadMesh( "GFX\TEMP\Wheel_TEMP.3ds" )
		PositionEntity PCar_FRWheel, 2, 1, 8
		ScaleEntity PCar_FRWheel,0.05,0.05,0.05
;		RotateEntity PCar_FRWheel,0,0,90
;		EntityColor PCar_FRWheel,0,0,254

		;Front Left
		PCar_FLWheel=CopyEntity(PCar_FRWheel)
		PositionEntity PCar_FLWheel,-2,1,8
;		ScaleEntity PCar_FLWheel,-2.3.5,2.3

		;--rearright
		PCar_RRWheel=CopyEntity(PCar_FRWheel)
		PositionEntity PCar_RRWheel,2,1,-1
;		ScaleEntity PCar_RRWheel,2.3,0.5,2.3

		;--rearleft
		PCar_RLWheel=CopyEntity(PCar_FRWheel)
		PositionEntity PCar_RLWheel,-2,1,-1
;		ScaleEntity PCar_RLWheel,2.3,0.5,2.3

	;Player Car Entity Parents
		EntityParent camera_player, PCar_Body
		EntityParent PCar_FRWheel,PCar_Body
		EntityParent PCar_FLWheel,PCar_Body
		EntityParent PCar_RRWheel,PCar_Body
		EntityParent PCar_RLWheel,PCar_Body				

	;Player Car Entity Types(For Collision)
		EntityType PCar_Body, coll_player
		EntityType PCar_FRWheel, coll_player
		EntityType PCar_FLWheel, coll_player
		EntityType PCar_RRWheel, coll_player
		EntityType PCar_RLWheel, coll_player

; Comment out water for now(map loading isn't even completely working!)
;water_plane=CreatePlane()
;water_plane_texture=LoadTexture("GFX\water.jpg")
;ScaleTexture water_plane_texture, 10, 10
;EntityTexture water_plane,water_plane_texture