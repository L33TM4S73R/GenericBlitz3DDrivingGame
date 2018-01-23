; Menu GFX
spr_menu = LoadSprite( "GFX\OVERLAYS\MENU.PNG", 7, camera_player )		;WHY IS IT INVISIBLE!?!?
ScaleSprite spr_menu, 1.5, 1.5						
MoveEntity spr_menu, 0, 0, 2				
HideEntity spr_menu							

spr_start = LoadSprite( "btn_start.bmp", 7, camera_player )	
ScaleSprite spr_start, .25, .25						
EntityRadius spr_start, .25						
MoveEntity spr_start, -.5, 0, 2						
HideEntity spr_start								
EntityPickMode spr_start,1							

spr_quit = LoadSprite( "btn_quit.bmp", 7, camera_player )		
ScaleSprite spr_quit, .25, .25
EntityRadius spr_quit, .25
MoveEntity spr_quit, 0, 0, 2						
HideEntity spr_quit	
EntityPickMode spr_quit,1

spr_credits = LoadSprite( "btn_credits.bmp", 7, camera_player )	;lists all the cool people
ScaleSprite spr_credits, .25, .25
EntityRadius spr_credits, .25
MoveEntity spr_credits, .5, 0, 2
HideEntity spr_credits	
EntityPickMode spr_credits,1