Include "includes\key_globals.bb"

Global spr_menu, spr_start, spr_quit, spr_credits
Global currentpicked, lastpicked, LeaveMenu=False, ShowCredits=False, speech$, AMMO=42
; Global W_key = 17, S_key=31, A_key=30, D_key=32
Global run_vol#, runchannel
Global PCar_Body,PCar_FRWheel,PCar_FLWheel,PCar_RRWheel,PCar_RLWheel, projectile_sprite, explosion_sprite, hit, shoot, boom	

Function MENU()			; MENU
	
	ShowEntity spr_menu	
	ShowEntity spr_start
	ShowEntity spr_quit	
	ShowEntity spr_credits
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
	LeaveMenu=False
	ShowCredits=False
		
End Function

Function MousePick()		; MOUSEPICK
	currentpicked = CameraPick( camera_player, MouseX(), MouseY() )
	If currentpicked>0 And MouseHit(1)
		If currentpicked = spr_start Then LeaveMenu = True
		If currentpicked = spr_quit Then QUIT=True
		If currentpicked = spr_credits Then ShowCredits = True
		For Character.Sprite = Each Sprite
			If currentpicked = Character\ID
				speech$ = Character\speech$
			Exit
			EndIf
		Next
	EndIf
	If MouseHit(2) Then speech$=""		;Right Mouse Click Closes Speech
	If currentpicked<>lastpicked
		If lastpicked Then EntityAlpha lastpicked, 1	;UNDO fade when mouse leaves
		lastpicked=currentpicked
	EndIf
	If currentpicked
		EntityAlpha currentpicked, Sin( MilliSecs() )*.3+.7
	EndIf
End Function

;Sound FX
Function LoadSFX()	
   	shoot = LoadSound( "SFX\temp\gunbang.wav" )
   	SoundVolume shoot, .9
   	boom = LoadSound( "SFX\temp\explosion.wav" )
   	SoundVolume boom, .5 
  
	run = LoadSound ("SFX\temp\incar.wav")
    LoopSound run			
    SoundVolume run, 0	
    runchannel = PlaySound (run)
End Function

; Keyboard & Mouse Controls
Function object_key_control( obj )
	If KeyHit(57) And AMMO>0 Then CreateProjectile( PCar_Body ) : AMMO=AMMO-1
	run_snd = False		
	If KeyDown( forward_key )=True Then MoveEntity obj, 0, 0, .2 : run_snd=True
	If KeyDown( reverse_key )=True Then MoveEntity obj, 0, 0,-.2 : run_snd=True
	If KeyDown( left_key )=True Then TurnEntity obj, 0, 2, 0
	If KeyDown( right_key )=True Then TurnEntity obj, 0,-2, 0
	If run_snd Then run_vol#=.5 Else run_vol#=0
	
	objx#=EntityX(obj):objz#=EntityZ(obj)		; Make sure object is on Terrain
	PositionEntity obj, objx, TerrainY( temp_land, objx, 0, objz ), objz
End Function

; Shooting from Player Car
Function UpdateProjectiles()
   For P.Projectile=Each Projectile
      UpdateProjectile( P )
   Next
   For E.Explosion=Each Explosion
      UpdateExplosion( E )
   Next
End Function

Function CreateProjectile.Projectile( source )
   P.Projectile = New Projectile
   P\time_out = 150
   P\sprite = CopyEntity( projectile_sprite, source )
   MoveEntity P\sprite, -2.5, 2, 0	
   EntityParent P\sprite,0
   shootChannel = PlaySound (shoot)
   Return P
End Function

Function UpdateProjectile( P.Projectile )
   If CountCollisions( P\sprite )
      If EntityCollided( P\sprite, coll_characters )
         For k=1 To CountCollisions( P\sprite )
            hit=CollisionEntity( P\sprite, k )
            If GetEntityType( hit ) = coll_characters
               Exit
            EndIf
         Next
         boomChannel = PlaySound (boom)
         CreateExplosion( P )
         FreeEntity P\sprite
         Delete P
         Return
      EndIf
   EndIf
   P\time_out=P\time_out-1
   If P\time_out=0
      FreeEntity P\sprite
      Delete P
      Return
   EndIf
   MoveEntity P\sprite,0,0,1
End Function

Function CreateExplosion.Explosion( P.Projectile )
   E.Explosion=New Explosion
   E\scale = 1
   E\sprite = CopyEntity( explosion_sprite, P\sprite )
   EntityParent E\sprite,0
   Return E
End Function

Function UpdateExplosion( E.Explosion )
   If E\scale < 5
      E\scale = E\scale + .2
      ScaleSprite E\sprite, E\scale, E\scale
   Else
      FreeEntity E\sprite
      Delete E
      For Character.Sprite = Each Sprite
         If Character\ID = hit Then Delete Character : FreeEntity hit
      Next
   EndIf
End Function
; Characters(Remove this.....somehow)

Function MoveCharacters(target)
	For Character.Sprite = Each Sprite
	  Select Character\temper
		Case 0		; Static
			; Do Nothing or maybe run idle animation?
			
		Case 1		; Path 
			PointEntity Character\ID, Character\targetpt
			MoveEntity Character\ID, 0, 0, Rnd(.05,.1)
			AnimSprite(Character\ID, Character\char_tex, 200, 4)
			If EntityDistance(Character\ID,Character\targetpt) < 1
				If Character\targetpt = Character\pt1 Then 
					Character\targetpt = Character\pt2
					Else Character\targetpt = Character\pt1
				EndIf
			EndIf		
		Case 2		; Path with Pursuit
			If EntityDistance(Character\ID,target)<20
				PointEntity Character\ID, target
				MoveEntity Character\ID, 0, 0, Rnd(.1,.19)
				AnimSprite(Character\ID, Character\char_tex, 200, 4)
			Else
				PointEntity Character\ID, Character\targetpt
				MoveEntity Character\ID, 0, 0, Rnd(.05,.1)
				AnimSprite(Character\ID, Character\char_tex, 200, 4)
				If EntityDistance(Character\ID,Character\targetpt) < 1
					If Character\targetpt = Character\pt1 Then 
						Character\targetpt = Character\pt2
						Else Character\targetpt = Character\pt1
					EndIf
				EndIf
			EndIf		
	  End Select
	
	objx#=EntityX(Character\ID):objz#=EntityZ(Character\ID)		; Make sure object is always on Terrain
	PositionEntity Character\ID, objx, TerrainY( temp_land,objx,0,objz ), objz
	Next 
End Function

Function LoadRandomSprites(sprite$, total, x1, x2, z1, z2)
	temp_sprite=LoadSprite( sprite$ , 7 )
	HandleSprite temp_sprite,0,-1
	SpriteViewMode temp_sprite,3
	EntityAutoFade temp_sprite,100,120
	
	EntityType temp_sprite, coll_objects
	EntityRadius temp_sprite,1
	
	For s=1 To total
		sx#=Rnd(x1,x2) : sz#=Rnd(z1,z2)
		copied_sprite=CopyEntity( temp_sprite )
		PositionEntity copied_sprite, sx, 0, sz
		ScaleSprite copied_sprite,Rand(2,3),Rand(4,6)
		
		objx#=EntityX(copied_sprite):objz#=EntityZ(copied_sprite)		; Make sure object is always on Terrain
		PositionEntity copied_sprite, objx, TerrainY( temp_land,objx,0,objz ), objz
	Next
	FreeEntity temp_sprite
	
End Function

Function AnimSprite(obj, atex, d, f)
	frame=MilliSecs()/d Mod f
	EntityTexture obj,atex,frame
End Function

; Music (Move to Level or expand to make it less generic)
Function LoadMusic()
	level_music = PlayMusic ("MUSIC\temp\mlg.mp3")
	ChannelVolume level_music,.5	
	If KeyHit( escape_key )
		StopChannel level_music
	EndIf
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