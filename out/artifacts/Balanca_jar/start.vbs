Set oShell = CreateObject ("Wscript.Shell") 
Dim strArgs
strArgs = "cmd /c init.bat"
oShell.Run strArgs, 0, false