Include "includes\key_globals.bb"

;Global spr_menu, spr_start, spr_quit, spr_credits
;Global currentpicked, lastpicked, LeaveMenu=False, ShowCredits=False
;Global run_vol#, runchannel
;Global PCar_Body	

;Function MENU()			; MENU
	
;	ShowEntity spr_menu	
;	ShowEntity spr_start
;	ShowEntity spr_quit	
;	ShowEntity spr_credits
;	LeaveMenu=False 
	
;	While Not KeyHit( escape_key )
		
;		MousePick()
;		If QUIT=True Then Return
;		If LeaveMenu = True Then Exit
		
;		UpdateWorld 
;		RenderWorld
		
;		If ShowCredits = True Then
;			Color 255,255,0
;			Text 10,10, "By L33TMaster"
;		EndIf
			
;		Flip
;		Delay 10
;	Wend
	
;	HideEntity spr_menu	
;	HideEntity spr_start
;	HideEntity spr_quit	
;	HideEntity spr_credits
;	LeaveMenu=False
;	ShowCredits=False
		
;End Function

;Function MousePick()		; MOUSEPICK
;	currentpicked = CameraPick( camera_player, MouseX(), MouseY() )
;	If currentpicked>0 And MouseHit(1)
;		If currentpicked = spr_start Then LeaveMenu = True
;		If currentpicked = spr_quit Then QUIT=True
;		If currentpicked = spr_credits Then ShowCredits = True
;	EndIf
;	If currentpicked<>lastpicked
;		If lastpicked Then EntityAlpha lastpicked, 1	;UNDO fade when mouse leaves
;		lastpicked=currentpicked
;	EndIf
;	If currentpicked
;		EntityAlpha currentpicked, Sin( MilliSecs() )*.3+.7
;	EndIf
;End Function

; Sound FX
;Function LoadSFX()	
;	run = LoadSound ("SFX\temp\incar.wav")
;    LoopSound run			
;    SoundVolume run, 0	
;    runchannel = PlaySound (run)
;End Function

; Keyboard & Mouse Controls
;Function object_key_control( obj )
;	If KeyDown( forward_key )=True Then MoveEntity obj, 0, 0, .3 : run_snd=True
;	If KeyDown( reverse_key )=True Then MoveEntity obj, 0, 0,-.3 : run_snd=True
;	If KeyDown( forward_key )=True Or KeyDown( reverse_key )=True And KeyDown( left_key )=True Then TurnEntity obj, 0, 2, 0
;	If KeyDown( forward_key )=True Or KeyDown( reverse_key )=True And KeyDown( right_key )=True Then TurnEntity obj, 0,-2, 0
;	If run_snd Then run_vol#=.5 Else run_vol#=0
	
;	objx#=EntityX(obj):objz#=EntityZ(obj)		; Make sure object is on Terrain
;	PositionEntity obj, objx, TerrainY( temp_land, objx, 0, objz ), objz
;End Function

; Music (Move to Level or expand to make it less generic)
;Function LoadMusic()
;	level_music = PlayMusic ("MUSIC\temp\mlg.mp3")
;	ChannelVolume level_music,.5	
;	If KeyHit( escape_key )
;		StopChannel level_music
;	EndIf
;End Function

; Development Features
Function FPS()
	If MilliSecs() > gFPSTimer Then
		gFPS = gRenders
		gRenders = 0
		gFPSTimer = MilliSecs() + 1000
	Else
		gRenders = gRenders + 1
	End If
	
	Return gFPS
End Function