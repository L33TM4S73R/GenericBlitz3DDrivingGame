;	Driber
;	Main Program

AppTitle "Dr1ber 0.1.0 REAL PHYSX"

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
Global Coll_PlayerCar=1,Coll_PlayerCarWheel=2,Coll_Terrain=3
;Global Coll_PlayerCar=1, Coll_PlayerCarWheel=2, Coll_Physics=3, Coll_Terrain=4, Coll_Camera=5

Collisions Coll_PlayerCar,Coll_Terrain,2,3
Collisions Coll_PlayerCarWheel,Coll_Terrain,2,3

; Setup camera 
Global Camera_PlayerCar_3rd = CreateCamera()
CameraClsColor Camera_PlayerCar_3rd,128,0,128
CameraViewport Camera_PlayerCar_3rd, 0, 0, screen_width, screen_height
PositionEntity Camera_PlayerCar_3rd, 0, 6, -13

; Load level&PlayerCar
Include "includes\player.bb"
Include "includes\level.bb" 

;LoadMusic()		
;LoadSFX()

MoveMouse screen_width/2, screen_height/2

fntArial=LoadFont("Arial", 36, True )		 
SetFont fntArial

target=CreatePivot( PCar_Body )
PositionEntity target,0,5,-12

speed#=0
x_vel#=0:prev_x#=EntityX( PCar_Body )
y_vel#=0:prev_y#=EntityY( PCar_Body )
z_vel#=0:prev_z#=EntityZ( PCar_Body )

;MENU()

While QUIT = False	

;	If KeyHit( escape_key ) Then MENU()
;	MousePick()
;	object_key_control( PCar_Body )
;	MoveCharacters( PCar_Body )
;	ChannelVolume runchannel, run_vol#
	PositionEntity skybox, EntityX(PCar_Body), EntityY(PCar_Body), EntityZ(PCar_Body)

	;Align car body to wheels
	zx#=(EntityX( wheels[2],True )+EntityX( wheels[4],True ))/2
	zx=zx-(EntityX( wheels[1],True )+EntityX( wheels[3],True ))/2
	zy#=(EntityY( wheels[2],True )+EntityY( wheels[4],True ))/2
	zy=zy-(EntityY( wheels[1],True )+EntityY( wheels[3],True ))/2
	zz#=(EntityZ( wheels[2],True )+EntityZ( wheels[4],True ))/2
	zz=zz-(EntityZ( wheels[1],True )+EntityZ( wheels[3],True ))/2
	AlignToVector PCar_Body,zx,zy,zz,1
	
	zx#=(EntityX( wheels[1],True )+EntityX( wheels[2],True ))/2
	zx=zx-(EntityX( wheels[3],True )+EntityX( wheels[4],True ))/2
	zy#=(EntityY( wheels[1],True )+EntityY( wheels[2],True ))/2
	zy=zy-(EntityY( wheels[3],True )+EntityY( wheels[4],True ))/2
	zz#=(EntityZ( wheels[1],True )+EntityZ( wheels[2],True ))/2
	zz=zz-(EntityZ( wheels[3],True )+EntityZ( wheels[4],True ))/2
	AlignToVector PCar_Body,zx,zy,zz,3
	
	;calculate car velocities	
	cx#=EntityX( PCar_Body ):x_vel=cx-prev_x:prev_x=cx
	cy#=EntityY( PCar_Body ):y_vel=cy-prev_y:prev_y=cy
	cz#=EntityZ( PCar_Body ):z_vel=cz-prev_z:prev_z=cz
	
	;resposition wheels
	cnt=1
	For z=1.5 To -1.5 Step -3
	For x=-1 To 1 Step 2
;		PositionEntity wheels[cnt],0,0,0
;		ResetEntity wheels[cnt]
		PositionEntity wheels[cnt],x,-1,z
		cnt=cnt+1
	Next
	Next

	;move car
	If KeyDown( forward_key ) Or KeyDown( reverse_key ) And KeyDown( left_key ) TurnEntity PCar_Body,0,2,0
	If KeyDown( forward_key ) Or KeyDown( reverse_key ) And KeyDown( right_key ) TurnEntity PCar_Body,0,-2,0
	If EntityCollided( PCar_Body,Coll_Terrain )
		If KeyDown( forward_key )
			speed=speed+.02
			If speed>.7 speed=.7
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

	; Update 3rd Person Camera
	If speed>=0	
		dx#=EntityX( target,True )-EntityX( Camera_PlayerCar_3rd )
		dy#=EntityY( target,True )-EntityY( Camera_PlayerCar_3rd )
		dz#=EntityZ( target,True )-EntityZ( Camera_PlayerCar_3rd )
		TranslateEntity Camera_PlayerCar_3rd,dx*.1,dy*.1,dz*.1
	EndIf
	PointEntity Camera_PlayerCar_3rd,PCar_Body
	
	; 2D stuff here, HUD, text, stats...
		Text 50,50,"Dr1ber Test"
		Text 0,0,FPS()
	
	UpdateWorld
	RenderWorld
	Flip
Wend

End