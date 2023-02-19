import os

for i in range(1, 188, 1):
    old = "D:\Work Files\Pest Invatigation Robot for Smart Argriculture\pestinvestigationrobot\Aulacophora\Aulacophora ("+str(i)+").jpg"
    new = "D:\Work Files\Pest Invatigation Robot for Smart Argriculture\pestinvestigationrobot\Aulacophora\Aulacophora_"+str(i)+".jpg"
    os.rename(old, new)