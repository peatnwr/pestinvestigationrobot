import cv2
import numpy as np

img = cv2.imread("./Thysanoptera GS/3.jpg")

gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
ret, thresh = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
for c in contours:
    x, y, w, h = cv2.boundingRect(c)
    cv2.rectangle(img, (x, y), (x+w, y+h), (0, 255, 0), 3)
    cv2.putText(img, "Thysanoptera", (x, y-10), cv2.FONT_HERSHEY_COMPLEX, 0.3, (255, 0, 0), 2)

cv2.imshow("Labeled Image", img)
cv2.waitKey(0)