import cv2
import os
from decimal import Decimal
import pandas as pd


def main():
    videoDir = "/Volumes/SSD/ET_VIDS"
    ssimDir = "/Volumes/SSD/SSIM"
    imageDir = "/Volumes/SSD/IMAGES"
    for video in os.listdir(videoDir):
        vidPath = os.path.join(videoDir, video)
        sess = video.split("_")[0]
        print sess
        ssim = [f for f in os.listdir(ssimDir) if ((sess in f) and ("._" not in f))]
        if ssim:
            ssimPath = os.path.join(ssimDir, ssim[0])
            # print vidPath, ssimPath
            ssim_series = pd.read_csv(ssimPath, header=None, names=["Time", "SSIM"])
            ssim_crossings = ssim_series[ssim_series["SSIM"] <= 0.83]
            # print ssim_crossings
            # times = (ssim_crossings["Time"]).tolist()
            times = (ssim_crossings["Time"] * 1000).tolist()
            # print times
            vidcap = cv2.VideoCapture(vidPath)
            for el in times:
                print el
                imgName = sess + "_" + str(el) + ".jpg"
                imgPath = os.path.join(imageDir, imgName)
                vidcap.set(0, el)
                # vidcap.set(cv2.CAP_PROP_POS_MSEC, el)  # just cue to 20 sec. position
                success, image = vidcap.read()
                if success:
                    cv2.imwrite(imgPath, image)
    return


main()
