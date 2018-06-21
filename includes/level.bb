; Load level skybox and light
Include "includes\skybox.bb"
AmbientLight 200, 200, 200
sun=CreateLight(2)
; Reduce the default light range of 1000 down to 20
LightRange sun, 20

; Gravity and such
Const GRAVITY#=-0.01 ;gravity constant

; Load World Objects Here

; Load temporary land
Global temp_land = LoadTerrain( "GFX\TEMP\TEMP_TERRAIN_HMAP.JPG" )
temp_land_texture = LoadTexture( "GFX\TERR\DEV.PNG" )
ScaleTexture temp_land_texture, 10, 10
EntityTexture temp_land, temp_land_texture
PositionEntity temp_land,-512,-8,-512
ScaleEntity temp_land,2,25,2
EntityType temp_land,Coll_Terrain

; Load Mesh
;miami_day = LoadMesh("miami_day.3ds")
;ScaleEntity miami_day, .25, .25, .25
;PositionEntity miami_day, 0, TerrainY( temp_land, 0, 0, 30 ), 30
;EntityType miami_day, Coll_Terrain

; Music (Expand to make it less generic)
Function LoadMusic()
;	level_music = ReadLine(MusicINI)
	level_music = PlayMusic ("MUSIC\TEMP\1.MP3")
	ChannelVolume level_music,.5	
	If KeyHit( escape_key )
		StopChannel level_music
	EndIf
End Function

; Playerstuff
PositionEntity PCar_Body,0,3,0

; Water Plane - Comment out water for now(map loading isn't even completely working!)
;water_plane=CreatePlane()
;water_plane_texture=LoadTexture("GFX\water.jpg")
;ScaleTexture water_plane_texture, 10, 10
;EntityTexture water_plane,water_plane_texture