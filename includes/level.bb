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
Global temp_land = LoadTerrain( "GFX\DEV_SHIT\terrain_hmap.jpg" )
temp_land_texture = LoadTexture( "GFX\DEV_SHIT\GRID.PNG" )
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
Data 0, "char2.bmp", 30, 0, 30, 30, 0, 0, "Test 1"	;adding speech lines(remove this shit)	
Data 1, "char3.bmp",-30, 0, 30, 20, 0, 30, "Test 2"
Data 2, "char4.bmp", 0, 0, 30, -30, 0, 0, "Test 3"

; Playerstuff (I should move this from level.bb to it's own thing)
player = CreateSprite() 
anim_tex=LoadAnimTexture( "GFX\VEHICLES\MIAMI\CAR00.PNG",7,51,48,0,3)
EntityTexture player, anim_tex, 1
HandleSprite player,0,-1
ScaleSprite player, 2, 2
PositionEntity player, 0, 0, 5
EntityParent camera_player, player
EntityRadius player,1
EntityType player, coll_player					

; Comment out water for now(map loading isn't even properly working!)
water_plane=CreatePlane()
water_plane_texture=LoadTexture("GFX\water.jpg")
ScaleTexture water_plane_texture, 10, 10
EntityTexture water_plane,water_plane_texture