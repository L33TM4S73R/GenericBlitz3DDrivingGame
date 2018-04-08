;	Driber
;	Main Program

AppTitle "Dr1ber 0.0.4 LOLWUT"

; Insert future code HERE! i.e. constants, variables, functions
Include "includes\functions.bb"

SeedRnd MilliSecs()

; "Globalizing" is good for commonly used values
Global screen_width = 800, screen_height = 600
Global windowed_fullscreen = 2
Global gFPS, gRenders, gFPSTimer

; Set video mode 
Graphics3D screen_width,screen_height, 32, windowed_fullscreen
SetBuffer BackBuffer() 

;include "includes\collisions.bb"
Global Coll_PlayerCar=1, Coll_PlayerCarWheel=2, Coll_Pedestrians=3,  Coll_Terrain=4, coll_projectile=5
;Global coll_player=1, Coll_PlayerCar=2, Coll_Pedestrians=3, coll_traffic=4, Coll_Terrain=5, coll_physics=6, coll_projectile=7, coll_camera=8
;Collisions coll_player, Coll_PlayerCar, 1, 2
;Collisions coll_player, coll_pedestrians, 1, 2
;Collisions coll_player, coll_traffic, 2,1
;Collisions coll_player, Coll_Terrain, 2,2
;Collisions coll_player, coll_physics, 1,1
Collisions Coll_PlayerCar, Coll_Pedestrians, 1,2
;Collisions Coll_PlayerCar, coll_traffic, 2,1
Collisions Coll_PlayerCar, Coll_Terrain, 2,3
;Collisions Coll_PlayerCar, coll_physics, 1,1
Collisions Coll_PlayerCarWheel, Coll_Terrain,2,3
Collisions coll_projectile, Coll_Pedestrians, 1,1
Collisions Coll_Pedestrians, Coll_PlayerCar, 1, 2 
Collisions Coll_Pedestrians, Coll_Pedestrians, 1, 2	
Collisions Coll_Pedestrians, Coll_Terrain, 2, 2
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

	Flip

Wend

End