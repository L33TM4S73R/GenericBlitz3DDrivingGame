;key globals
ControlsINI = ReadFile("controls.ini")
ReadLine(ControlsINI)

Global escape_key = 1, QUIT = False
Global forward_key = ReadLine(ControlsINI)
Global reverse_key = ReadLine(ControlsINI)
Global left_key = ReadLine(ControlsINI)
Global right_key = ReadLine(ControlsINI)
Global look_up_key = ReadLine(ControlsINI)
Global look_down_key = ReadLine(ControlsINI)
Global look_left_key = ReadLine(ControlsINI)
Global look_right_key = ReadLine(ControlsINI)