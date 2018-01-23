;	Driber
;	Main Program

AppTitle "Dr1ber 0.0.1 LOLWUT"

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
PositionEntity camera_player, 0, 5, -2

; Load level "TAR, Undercover, and such" - ONLY MIAMI TAR NOW
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
	If run_vol#>0 Then AnimSprite(player, anim_tex,200,3)
	object_key_control( player )
	MoveCharacters( player )
	ChannelVolume runchannel, run_vol#
	PositionEntity skybox, EntityX(player), EntityY(player), EntityZ(player)

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
