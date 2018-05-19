;key globals
ControlsINI = ReadFile("controls.ini")
ReadLine(ControlsINI)

Global escape_key = 1, QUIT = False
Global forward_key = ReadLine(ControlsINI)
Global reverse_key = ReadLine(ControlsINI)
Global left_key = ReadLine(ControlsINI)
Global right_key = ReadLine(ControlsINI)
;Global look_forward_key = 200 ;Scancode for Up Key is 200
;Global look_back_key = 208 ;Scancode for Down Key is 208
;Global look_left_key = 203 ;Scancode for Left Key is 203
;Global look_right_key = 205 ;Scancode for Right Key is 205