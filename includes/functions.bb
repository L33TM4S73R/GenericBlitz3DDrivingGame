Include "includes\key_globals.bb"

Global spr_menu, spr_start, spr_quit, spr_credits
Global currentpicked, lastpicked, LeaveMenu=False, ShowCredits=False
Global run_vol#, runchannel

Function MENU()			; MENU
	
	ShowEntity spr_menu	
	ShowEntity spr_start
	ShowEntity spr_quit	
	ShowEntity spr_credits
;	ShowEntity spr_freecam
	LeaveMenu=False 
	
	While Not KeyHit( escape_key )
		
		MousePick()
		If QUIT=True Then Return
		If LeaveMenu = True Then Exit
		
		UpdateWorld 
		RenderWorld
		
		If ShowCredits = True Then
			Color 255,255,0
			Text 10,10, "By L33TMaster"
		EndIf
			
		Flip
		Delay 10
	Wend
	
	HideEntity spr_menu	
	HideEntity spr_start
	HideEntity spr_quit	
	HideEntity spr_credits
;	HideEntity spr_freecam
	LeaveMenu=False
	ShowCredits=False
		
End Function

Function MousePick()		; MOUSEPICK
	currentpicked = CameraPick( Camera_PlayerCar_3rd, MouseX(), MouseY() )
	If currentpicked>0 And MouseHit(1)
		If currentpicked = spr_start Then LeaveMenu = True
		If currentpicked = spr_quit Then QUIT = True
		If currentpicked = spr_credits Then ShowCredits = True
;		If currentpicked = spr_freecam Then camera = freecam
	EndIf
	If currentpicked<>lastpicked
		If lastpicked Then EntityAlpha lastpicked, 1	;UNDO fade when mouse leaves
		lastpicked=currentpicked
	EndIf
	If currentpicked
		EntityAlpha currentpicked, Sin( MilliSecs() )*.3+.7
	EndIf
End Function

; Sound FX
Function LoadSFX()	
	run = LoadSound ("SFX\temp\incar.wav")
    LoopSound run			
    SoundVolume run, 0	
    runchannel = PlaySound (run)
End Function

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