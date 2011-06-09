import os;
import shutil;


def copytree(src, dst1, dst2):
    names = os.listdir(src)
    
    if not os.path.exists(dst1):
    	os.makedirs(dst1)
    if not os.path.exists(dst2):
        os.makedirs(dst2)

    count = 0
    for name in names:
        srcname = os.path.join(src, name)
        dst1name = os.path.join(dst1, name)
        dst2name = os.path.join(dst2, name)

        if os.path.isdir(srcname):
            copytree(srcname, dst1name, dst2name)
        elif (count % 2) == 0:
            shutil.copy(srcname, dst1name)
            count += 1
        else:
            shutil.copy(srcname, dst2name)
            count += 1
count = 1
copytree("/home/frank/Desktop/webkb", "/home/frank/Desktop/train", "/home/frank/Desktop/test")
        
  