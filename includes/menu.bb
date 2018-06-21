; Menu GFX
spr_menu = LoadSprite( "GFX\TEMP\TEMP_MENU.PNG",7, Camera_PlayerCar_3rd )		;Why is it invisible...........STILL!
MoveEntity spr_menu, 0, 0, 2
HideEntity spr_menu

spr_start = LoadSprite( "GFX\TEMP\TEMP_GO.PNG",1+2+256, Camera_PlayerCar_3rd )
ScaleSprite spr_start, .25, .25
EntityRadius spr_start, .25
MoveEntity spr_start, -.5, 0, 2
HideEntity spr_start
EntityPickMode spr_start,1

spr_quit = LoadSprite( "GFX\TEMP\TEMP_NO.PNG",1+2+256, Camera_PlayerCar_3rd )
ScaleSprite spr_quit, .25, .25
EntityRadius spr_quit, .25
MoveEntity spr_quit, .5, 0, 2						
HideEntity spr_quit	
EntityPickMode spr_quit,1

spr_credits = LoadSprite( "GFX\TEMP\TEMP_WHO.PNG",1+2+256, Camera_PlayerCar_3rd )	;lists all the cool people
ScaleSprite spr_credits, .25, .25
EntityRadius spr_credits, .25
MoveEntity spr_credits, 0, 0, 2
HideEntity spr_credits	
EntityPickMode spr_credits,1

;spr_freecam = LoadSprite( "GFX\TEMP\TEMP_NO.PNG",1+2+256, Camera_PlayerCar_3rd )	;lists all the cool people
;ScaleSprite spr_freecam, .25, .25
;EntityRadius spr_freecam, .25
;MoveEntity spr_freecam, -10, 0, 2
;HideEntity spr_freecam	
;EntityPickMode spr_freecam,1