import cv2
import os

# image = cv2.imread('./veg_flea_beetle/1.jpg', cv2.IMREAD_UNCHANGED)
# image_grayscale = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# cv2.imshow('image', image_grayscale)

# cv2.imwrite('./veg_flea_beetle_grayscale./1.jpg', image_grayscale)

# cv2.waitKey(0)

# cv2.destroyAllWindows()

directory = 'Thysanoptera'
count = 1

for fileName in os.listdir(directory):
    f = os.path.join(directory, fileName)
    if os.path.isfile(f):
        image = cv2.imread(f, cv2.IMREAD_UNCHANGED)
        image_grayscale = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        cv2.imwrite('./Thysanoptera GS/'+str(count)+'.jpg', image_grayscale)
        count+=1
