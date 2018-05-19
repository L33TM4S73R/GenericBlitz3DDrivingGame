;	Driber
;	Main Program

AppTitle "Dr1ber 0.1.1 REAL PHYSX"

;Setting up Dev Features
SeedRnd MilliSecs()
Global gFPS, gRenders, gFPSTimer

; Insert future code HERE! i.e. constants, variables, functions
Include "includes\functions.bb"

; INI File Stuff
GraphicsINI = ReadFile("graphics.ini")
ReadLine(GraphicsINI)

Global screen_width = ReadLine(GraphicsINI), screen_height = ReadLine(GraphicsINI)
Global windowed_fullscreen = ReadLine(GraphicsINI)
Global depth = ReadLine(GraphicsINI)

; Set video mode 
Graphics3D screen_width,screen_height, depth, windowed_fullscreen
SetBuffer BackBuffer() 

;include "includes\collisions.bb"
Global Coll_PlayerCar=1,Coll_PlayerCarWheel=2,Coll_Terrain=3

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

; Load MENU Sprites
Include "includes\menu.bb"

; Load HUD and Misc. Sprites
;Include "includes\hud.bb"

;LoadMusic()		
LoadSFX()

MoveMouse screen_width/2, screen_height/2

fntArial=LoadFont("Arial", 36, True )		 
SetFont fntArial

speed#=0
x_vel#=0:prev_x#=EntityX( PCar_Body )
y_vel#=0:prev_y#=EntityY( PCar_Body )
z_vel#=0:prev_z#=EntityZ( PCar_Body )

MENU()

While QUIT = False	

	If KeyHit( escape_key ) Then MENU()
	MousePick()
	ChannelVolume runchannel, run_vol#
	PositionEntity skybox, EntityX(PCar_Body), EntityY(PCar_Body), EntityZ(PCar_Body)

	;Align car body to wheels
	zx#=(EntityX( PCar_Wheels[2],True )+EntityX( PCar_Wheels[4],True ))/2
	zx=zx-(EntityX( PCar_Wheels[1],True )+EntityX( PCar_Wheels[3],True ))/2
	zy#=(EntityY( PCar_Wheels[2],True )+EntityY( PCar_Wheels[4],True ))/2
	zy=zy-(EntityY( PCar_Wheels[1],True )+EntityY( PCar_Wheels[3],True ))/2
	zz#=(EntityZ( PCar_Wheels[2],True )+EntityZ( PCar_Wheels[4],True ))/2
	zz=zz-(EntityZ( PCar_Wheels[1],True )+EntityZ( PCar_Wheels[3],True ))/2
	AlignToVector PCar_Body,zx,zy,zz,1
	
	zx#=(EntityX( PCar_Wheels[1],True )+EntityX( PCar_Wheels[2],True ))/2
	zx=zx-(EntityX( PCar_Wheels[3],True )+EntityX( PCar_Wheels[4],True ))/2
	zy#=(EntityY( PCar_Wheels[1],True )+EntityY( PCar_Wheels[2],True ))/2
	zy=zy-(EntityY( PCar_Wheels[3],True )+EntityY( PCar_Wheels[4],True ))/2
	zz#=(EntityZ( PCar_Wheels[1],True )+EntityZ( PCar_Wheels[2],True ))/2
	zz=zz-(EntityZ( PCar_Wheels[3],True )+EntityZ( PCar_Wheels[4],True ))/2
	AlignToVector PCar_Body,zx,zy,zz,3
	
	;calculate car velocities	
	cx#=EntityX( PCar_Body ):x_vel=cx-prev_x:prev_x=cx
	cy#=EntityY( PCar_Body ):y_vel=cy-prev_y:prev_y=cy
	cz#=EntityZ( PCar_Body ):z_vel=cz-prev_z:prev_z=cz
	
	;Reposition Wheel Locations
	cnt=1
	For z=1.5 To -1.5 Step -3
		For x=-1 To 1 Step 2
;			PositionEntity PCar_Wheels[cnt],0,0,0
;			ResetEntity PCar_Wheels[cnt]
			PositionEntity PCar_Wheels[cnt],x,-1,z
			cnt=cnt+1
		Next
	Next

; Keyboard & Mouse Controls

	Include "includes\controls.bb"
	
; Update 3rd Person Camera
	If speed>=0	
		dx#=EntityX( target,True )-EntityX( Camera_PlayerCar_3rd )
		dy#=EntityY( target,True )-EntityY( Camera_PlayerCar_3rd )
		dz#=EntityZ( target,True )-EntityZ( Camera_PlayerCar_3rd )
		TranslateEntity Camera_PlayerCar_3rd,dx*.1,dy*.1,dz*.1
	EndIf
	PointEntity Camera_PlayerCar_3rd,PCar_Body

	UpdateWorld
	RenderWorld
		
; 2D stuff here, HUD, text, stats.
	Text 50,50,"Dr1ber Test"
	Text 0,0,FPS()

	Flip
Wend

End