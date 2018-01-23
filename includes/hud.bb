; Load HUD Sprites **************************
spr_ammo = LoadSprite( "GFX\OVERLAYS\PISTOL.PNG", 7, camera_player )	
ScaleSprite spr_ammo, .25, .25						
MoveEntity spr_ammo, -1.7, -1.3, 2.1
EntityAlpha spr_ammo, .5	
; *******************************************