;	Driber
;	Main Program

AppTitle "Dr1ber 0.0.3 LOLWUT"

; Insert future code HERE! i.e. constants, variables, functions

SeedRnd MilliSecs()
Include "includes\functions.bb"

; "Globalizing" is good for commonly used values
Global screen_width = 800, screen_height = 600
Global windowed_fullscreen = 2
Global gFPS, gRenders, gFPSTimer

; Set video mode 
Graphics3D screen_width,screen_height, 32, windowed_fullscreen
SetBuffer BackBuffer() 

;include "includes\collisions.bb"
Global coll_player=1, coll_characters=2,  coll_objects=3, coll_projectile=4
;Global coll_player=1, coll_player_vehicle=2, coll_pedestrians=3, coll_traffic=4, coll_terrain=5, coll_physics=6, coll_projectile=7, coll_camera=8
;Collisions coll_player, coll_player_vehicle, 1, 2
;Collisions coll_player, coll_pedestrians, 1, 2
;Collisions coll_player, coll_traffic, 2,1
;Collisions coll_player, coll_terrain, 2,2
;Collisions coll_player, coll_physics, 1,1
;Collisions coll_player_vehicle, coll_pedestrians, 2,1
;Collisions coll_player_vehicle, coll_traffic, 2,1
;Collisions coll_player, coll_terrain, 2,2
;Collisions coll_player, coll_physics, 1,1
Collisions coll_projectile, coll_characters, 1,1
Collisions coll_characters, coll_player, 1, 2    
Collisions coll_player, coll_characters ,1, 2   
Collisions coll_player, coll_objects, 2, 2		
Collisions coll_characters, coll_characters, 1, 2	
Collisions coll_characters, coll_objects, 2, 2

; Setup camera 
Global camera_player = CreateCamera()
CameraClsColor camera_player,128,0,128
CameraViewport camera_player, 0, 0, screen_width, screen_height
PositionEntity camera_player, 0, 6, -10

; Load level "TAR, Undercover, and such"
Include "includes\level.bb" 

LoadMusic()		
LoadSFX()

MoveMouse screen_width/2, screen_height/2

fntArial=LoadFont("Arial", 36, True )		 
SetFont fntArial

MENU()

While QUIT = False	

	; key & mouse control functions!	
	If KeyHit( escape_key ) Then MENU()
	MousePick()
	UpdateProjectiles()
	object_key_control( PCar_Body )
	MoveCharacters( PCar_Body )
	ChannelVolume runchannel, run_vol#
	PositionEntity skybox, EntityX(PCar_Body), EntityY(PCar_Body), EntityZ(PCar_Body)

	UpdateWorld 
	RenderWorld

	; 2D stuff here, HUD, text, stats...
	Text 50,50,"Dr1ber Test"
	Text 0,0,FPS()
	Text 50,410, AMMO
	Text screen_width/2, 10, speech$, True

	Flip

Wend

End

; Repeatedly used functions appear here later 